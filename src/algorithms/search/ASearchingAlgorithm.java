package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    String name;
    int numOfEvaluatedNodes;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfEvaluatedNodes;
    }

    public Solution getSolutionPath(AState goalState, AState startState)
    {
        Solution route = new Solution();
        AState current = goalState;

        //while (!current.toString().equals(startState.toString()))
        while (current != null)
        {
            current = current.getMyFather();
            if(current == null){return route;}
            route.addStateToSolution(current);
        }
        return route;
    }
}
