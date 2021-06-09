package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    LinkedList<AState> statesArray;
    HashSet <String> visited;
    double totalCost;

    public BreadthFirstSearch() {
        name = "BreadthFirstSearch";
        this.statesArray = new LinkedList<>();
    }

    public Solution solve(ISearchable problemToSolve) {

        AState start = problemToSolve.getStartState();
        AState goal = problemToSolve.getGoalState();

        this.visited = new HashSet<>();
        statesArray.add(start);
        boolean solved = false;

        while (statesArray.size() > 0 && !solved) {
            AState currentState = statesArray.poll();
            //System.out.print(currentState.toString());//tmp
            if (!visited.contains(currentState.toString())) {
                visited.add(currentState.toString());
                numOfEvaluatedNodes++;
                if (!goal.toString().equals(currentState.toString())) {
                    ArrayList<AState> neighbors = problemToSolve.getAllSuccessors(currentState);
                    addNeighborsToOpenList(neighbors,currentState);
                }
                else {
                    goal.setMyFather(currentState);
                    solved = true;
                }
            }
        }
        return getSolutionPath(goal, start);

    }

    //update father + add to open list
    public void addNeighborsToOpenList(ArrayList<AState> neighbors, AState currentState) {
        if (!(neighbors == null)) {
            for (AState state : neighbors) {
                if (!visited.contains(state.toString())) {
                    if(this.getName().equals("BestFirstSearch.java"))
                    {
                        totalCost = state.getCost() + currentState.getCost();
                        state.setCost(totalCost);
                    }
                    state.setMyFather(currentState);
                    statesArray.add(state);
                }
            }
        }
    }
}