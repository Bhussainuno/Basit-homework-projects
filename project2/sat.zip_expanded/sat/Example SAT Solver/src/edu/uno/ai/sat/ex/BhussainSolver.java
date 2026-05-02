
package edu.uno.ai.sat.ex;

import java.util.ArrayList;
import java.util.List;

import edu.uno.ai.sat.Assignment;
import edu.uno.ai.sat.Solver;
import edu.uno.ai.sat.Value;
import edu.uno.ai.sat.Variable;
import edu.uno.ai.sat.Clause;
import edu.uno.ai.sat.Literal;

/**
 * BhussainSolver — Enhanced DPLL:
 *  Early TRUE detection
 *  Unit clause propagation
 *  Pure literal elimination
 *  MOMS heuristic for variable selection
 *
 * @author Basit Hussain
 */
public class BhussainSolver extends Solver {

    public BhussainSolver() {
        super("bhussain");
    }

    @Override
    public boolean solve(Assignment assignment) {
        if (assignment.problem.variables.size() == 0)
            return assignment.getValue() == Value.TRUE;
        return backtrack(assignment);
    }

    private boolean backtrack(Assignment a) {
        Value fval = a.getValue();
        if (fval == Value.FALSE) return false;
        if (fval == Value.TRUE)  return true;

        // Inference: unit propagation + pure-literal elimination
        List<Variable> trail = new ArrayList<>();
        if (!propagateUnits(a, trail) || !eliminatePureLiterals(a, trail)) {
            undoTrail(a, trail);
            return false;
        }

        fval = a.getValue();
        if (fval == Value.FALSE) { undoTrail(a, trail); return false; }
        if (fval == Value.TRUE)  { return true; }

        // Choose variable using MOMS heuristic 
        Variable var = chooseByMOMS(a);
        if (var == null) return true;

        // Try best value first (heuristic: TRUE then FALSE)
        Value old = a.getValue(var);
        a.setValue(var, Value.TRUE);
        if (backtrack(a)) return true;
        a.setValue(var, old);

        a.setValue(var, Value.FALSE);
        if (backtrack(a)) return true;
        a.setValue(var, old);

        undoTrail(a, trail);
        return false;
    }

    /** Unit clause propagation */
    private boolean propagateUnits(Assignment a, List<Variable> trail) {
        boolean changed;
        do {
            changed = false;
            for (Clause c : a.problem.clauses) {
                Value cval = a.getValue(c);
                if (cval == Value.FALSE) return false;
                if (cval == Value.TRUE) continue;

                Literal unknownLit = null;
                int unknownCount = 0;
                for (Literal lit : c.literals) {
                    Value lval = a.getValue(lit);
                    if (lval == Value.TRUE) { unknownCount = 0; break; }
                    if (lval == Value.UNKNOWN) { unknownLit = lit; unknownCount++; }
                }

                if (unknownCount == 1 && unknownLit != null) {
                    Variable v = unknownLit.variable;
                    Value old = a.getValue(v);
                    Value needed = unknownLit.valence ? Value.TRUE : Value.FALSE;
                    if (old == Value.UNKNOWN) {
                        a.setValue(v, needed);
                        trail.add(v);
                        changed = true;
                    } else if (old != needed) return false;
                }
            }
        } while (changed);
        return true;
    }

    /** Pure-literal elimination */
    private boolean eliminatePureLiterals(Assignment a, List<Variable> trail) {
        boolean changed;
        do {
            changed = false;
            for (Variable v : a.problem.variables) {
                if (a.getValue(v) != Value.UNKNOWN) continue;

                boolean seenPos = false, seenNeg = false;
                for (Literal lit : v.literals) {
                    Value cval = a.getValue(lit.clause);
                    if (cval == Value.FALSE || cval == Value.TRUE) continue;
                    if (lit.valence) seenPos = true; else seenNeg = true;
                }

                if (seenPos && !seenNeg) {
                    a.setValue(v, Value.TRUE);
                    trail.add(v);
                    changed = true;
                } else if (seenNeg && !seenPos) {
                    a.setValue(v, Value.FALSE);
                    trail.add(v);
                    changed = true;
                }
            }
        } while (changed);
        return true;
    }

    /** Undo trail */
    private void undoTrail(Assignment a, List<Variable> trail) {
        for (int i = trail.size() - 1; i >= 0; i--) {
            a.setValue(trail.get(i), Value.UNKNOWN);
        }
        trail.clear();
    }

    /** MOMS heuristic: pick variable in smallest clauses with highest frequency */
    private Variable chooseByMOMS(Assignment a) {
        int minSize = Integer.MAX_VALUE;
        for (Clause c : a.problem.clauses) {
            if (a.getValue(c) == Value.UNKNOWN) {
                int size = countUnknownLiterals(a, c);
                if (size < minSize) minSize = size;
            }
        }

        Variable bestVar = null;
        int bestScore = -1;
        for (Variable v : a.problem.variables) {
            if (a.getValue(v) != Value.UNKNOWN) continue;
            int score = 0;
            for (Literal lit : v.literals) {
                if (a.getValue(lit.clause) == Value.UNKNOWN &&
                    countUnknownLiterals(a, lit.clause) == minSize) {
                    score++;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestVar = v;
            }
        }
        return bestVar;
    }

    private int countUnknownLiterals(Assignment a, Clause c) {
        int count = 0;
        for (Literal lit : c.literals) {
            if (a.getValue(lit.variable) == Value.UNKNOWN) count++;
        }
        return count;
    }
}
