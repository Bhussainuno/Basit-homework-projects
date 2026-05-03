
package edu.uno.ai.planning.ex;

import java.util.*;
import edu.uno.ai.SearchBudget;
import edu.uno.ai.logic.Conjunction;
import edu.uno.ai.logic.Literal;
import edu.uno.ai.logic.Proposition;
import edu.uno.ai.logic.State;
import edu.uno.ai.planning.Plan;
import edu.uno.ai.planning.Step;
import edu.uno.ai.planning.ss.StateSpaceNode;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;

/** state space planners with the HSP style deleting the relaxation heuristic 
 * and FF style help action filtering.  Writing the for edu.uno.ai.logic.* API. */
public class MyHeuristicSearch extends StateSpaceSearch {


    /** Using the SUM as a heuristic for more robust recommendations. Using the MAX for allowable variant. */
    private enum HMode { HSP_SUM, HSP_MAX }
    private static final HMode H_MODE = HMode.HSP_SUM;

    /** actions help reducing the branching  */
    private static final boolean USE_HELPFUL = true;

    /** Safety cap to avoid runaway recursion in heuristic on pathological cycles. */
    private static final int MAX_DIST_DEPTH = 128;


    /** Prioritizing the queue arranged by f = g + h; tie-break by h, then g (lower is preferable). */
    private final PriorityQueue<StateSpaceNode> frontier =
        new PriorityQueue<>(new Comparator<StateSpaceNode>() {
            @Override
            public int compare(StateSpaceNode a, StateSpaceNode b) {
                double fa = f(a), fb = f(b);
                if (fa < fb) return -1;
                if (fa > fb) return 1;
                double ha = h(a), hb = h(b);
                if (ha < hb) return -1;
                if (ha > hb) return 1;
                return Integer.compare(g(a), g(b));
            }
        });

    /** Closed set visited states  */
    private final HashSet<State> closed = new HashSet<>();

    /** Best known path cost to a state; prunes dominated re-expansions. */
    private final HashMap<State, Integer> bestG = new HashMap<>();


    /** For each literal which steps add it . */
    private final HashMap<Literal, List<Step>> achievers = new HashMap<>();

    /** Per-state memo for relaxed distances. */
    private HashMap<Literal, Double> memo;

    /** Caching h state to avoid the recomputing in comparator. */
    private final HashMap<State, Double> hCache = new HashMap<>();

    public MyHeuristicSearch(StateSpaceProblem problem, SearchBudget budget) {
        super(problem, budget);
        indexAchievers();  // building the achiever lists from effects once problem
    }

    @Override
    public Plan solve() {
        frontier.add(root);

        while (!frontier.isEmpty()) {
            StateSpaceNode current = frontier.poll();
            State s = (State) current.state;

            // Goal test
            if (problem.isSolution(current.plan))
                return current.plan;

            // Path dominance prune and skip worse paths to same state
            int cg = g(current);
            Integer bg = bestG.get(s);
            if (bg != null && cg > bg) continue;
            bestG.put(s, cg);

            // Skip revisits
            if (closed.contains(s)) continue;
            closed.add(s);

            // Gathering the applicable steps
            List<Step> applicable = new ArrayList<>();
            for (Step step : problem.steps) {
                if (step.precondition.isTrue(s)) applicable.add(step);
            }

            if (USE_HELPFUL) {
                // Helpful set and goal literals add one step supporters of those goals
                HashSet<Literal> helpful = new HashSet<>(flatten(problem.goal));
                for (Literal gLit : flatten(problem.goal)) {
                    List<Step> adds = achievers.get(gLit);
                    if (adds != null) {
                        for (Step a : adds) helpful.addAll(flatten(a.precondition));
                    }
                }

                
                int added = 0;
                for (Step step : applicable) {
                    if (addsAny(step, helpful)) {
                        StateSpaceNode child = current.expand(step); // respects SearchBudget/time
                        if (child != null && !closed.contains((State) child.state)) {
                            frontier.add(child);
                            added++;
                        }
                    }
                }
                // Fallback if nothing helpful then add the rest to keep completeness
                if (added == 0) {
                    for (Step step : applicable) {
                        StateSpaceNode child = current.expand(step);
                        if (child != null && !closed.contains((State) child.state)) {
                            frontier.add(child);
                        }
                    }
                }
            } else {
                // No helpful filtering
                for (Step step : applicable) {
                    StateSpaceNode child = current.expand(step);
                    if (child != null && !closed.contains((State) child.state)) {
                        frontier.add(child);
                    }
                }
            }
        }

        // No solution under budget time
        return null;
    }


    /** f(n) = g(n) + h(n) */
    private double f(StateSpaceNode n) { return g(n) + h(n); }

    /** g(n) = plan length robust to different Plan APIs. */
    private int g(StateSpaceNode n) {
        if (n.plan == null) return 0;
        try {
            return n.plan.size(); // common API
        } catch (Throwable t1) {
            try {
                java.lang.reflect.Field stepsField = n.plan.getClass().getField("steps");
                Object steps = stepsField.get(n.plan);
                return java.lang.reflect.Array.getLength(steps);
            } catch (Throwable t2) {
                int count = 0;
                try {
                    for (Iterator<?> it = n.plan.iterator(); it.hasNext();) { it.next(); count++; }
                } catch (Throwable ignore) {}
                return count;
            }
        }
    }

    /** HSP delete relaxation distance from state to goal SUM or MAX as configured. */
    private double h(StateSpaceNode n) {
        State s = (State) n.state;
        Double cached = hCache.get(s);
        if (cached != null) return cached;

        memo = new HashMap<>();
        double value;

        if (H_MODE == HMode.HSP_SUM) {
            double sum = 0.0;
            for (Literal gLit : flatten(problem.goal)) {
                double d = dist(s, gLit, new HashSet<>(), 0);
                if (Double.isInfinite(d)) { hCache.put(s, d); return d; }
                sum += d;
            }
            value = sum;
        } else { // HSP MAX admissible
            double best = 0.0;
            for (Literal gLit : flatten(problem.goal)) {
                double d = dist(s, gLit, new HashSet<>(), 0);
                if (Double.isInfinite(d)) { hCache.put(s, d); return d; }
                if (d > best) best = d;
            }
            value = best;
        }

        hCache.put(s, value);
        return value;
    }

    
    private double dist(State s, Literal L, HashSet<Literal> onStack, int depth) {
        if (depth > MAX_DIST_DEPTH) return Double.POSITIVE_INFINITY;

        Double m = memo.get(L);
        if (m != null) return m;

        if (L.isTrue(s)) { memo.put(L, 0.0); return 0.0; }

        if (onStack.contains(L)) return Double.POSITIVE_INFINITY; // break cycles

        List<Step> adds = achievers.get(L);
        if (adds == null || adds.isEmpty()) {
            memo.put(L, Double.POSITIVE_INFINITY);
            return Double.POSITIVE_INFINITY;
        }

        onStack.add(L);
        double best = Double.POSITIVE_INFINITY;

        for (Step a : adds) {
            double sum = 0.0;
            for (Literal pre : flatten(a.precondition)) {
                double cp = dist(s, pre, onStack, depth + 1);
                if (Double.isInfinite(cp)) { sum = Double.POSITIVE_INFINITY; break; }
                sum += cp;
            }
            if (!Double.isInfinite(sum)) best = Math.min(best, 1.0 + sum);
        }

        onStack.remove(L);
        memo.put(L, best);
        return best;
    }

    /** achievers the map from positive impact literals. */
    private void indexAchievers() {
        for (Step step : problem.steps) {
            for (Literal eff : flatten(step.effect)) {
                // Positive literals are used in teaching domains.
                achievers.computeIfAbsent(eff, k -> new ArrayList<>()).add(step);
            }
        }
    }

    /** Convert a proposition (literal or conjunction of literals) into a list of literals by flattening it. */
    private static List<Literal> flatten(Proposition proposition) {
        ArrayList<Literal> list = new ArrayList<>();
        if (proposition instanceof Literal) {
            list.add((Literal) proposition);
        } else {
            Conjunction conj = (Conjunction) proposition;
            for (Proposition p : conj.arguments) list.add((Literal) p);
        }
        return list;
    }

    
    private static boolean addsAny(Step step, HashSet<Literal> targets) {
        for (Literal eff : flatten(step.effect)) {
            if (targets.contains(eff)) return true;
        }
        return false;
    }
}
