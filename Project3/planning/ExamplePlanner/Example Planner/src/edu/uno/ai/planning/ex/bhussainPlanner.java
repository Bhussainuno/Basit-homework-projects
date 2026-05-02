package edu.uno.ai.planning.ex;

import edu.uno.ai.SearchBudget;
import edu.uno.ai.planning.ss.StateSpacePlanner;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;

/**
 * Basit Hussain's heuristic state space planner. 
 * Uses MyHeuristicSearch (A* with target count heuristic).
 */
public class bhussainPlanner extends StateSpacePlanner {

    /**
     * Constructor.
     * The string "bhussain" is the planner ID that will appear in results.html.
     */
    public bhussainPlanner() {
        super("bhussain");
    }

    @Override
    protected StateSpaceSearch makeStateSpaceSearch(StateSpaceProblem problem,
                                                    SearchBudget budget) {
        // Use our custom heuristic search instead of plain BFS
        return new MyHeuristicSearch(problem, budget);
    }
}
