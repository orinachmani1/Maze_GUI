package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

//based on https://www.youtube.com/watch?v=8UM1HziG3hw

public class DepthFirstSearch extends ASearchingAlgorithm {
    HashSet<String> visited;
    Stack<AState> stack;

    public DepthFirstSearch() {
        name = "DeptFirstSearch";
    }

    @Override
    public Solution solve(ISearchable problemToSolve) {

        if(problemToSolve==null){return null;}

        this.visited = new HashSet<>();
        this.stack = new Stack<>();

        AState start = problemToSolve.getStartState();
        AState goal = problemToSolve.getGoalState();
        stack.push(start);

        boolean solved=false;
        while (!stack.isEmpty()&& !solved)
        {
            AState cur = stack.pop();
            //System.out.print(cur.toString());//tmp
            if(cur==null){break;}
            if (!(visited.contains(cur.toString())))
            {
                visited.add(cur.toString());
                numOfEvaluatedNodes++;
                if (!goal.equals(cur))
                {
                    ArrayList<AState> neighbors = problemToSolve.getAllSuccessors(cur);
                    addNeighborsToOpenList(neighbors,cur);
                }
                else
                    {
                        goal.setMyFather(cur);
                        solved=true;
                    }
            }

        }
        return getSolutionPath(goal,start);
    }

    public void addNeighborsToOpenList(ArrayList<AState> neighbors, AState currentState){
        if (!(neighbors == null))
        {
            for (AState state: neighbors) {
                if (!visited.contains(state.toString()))
                {
                    state.setMyFather(currentState);
                    stack.push(state);
                }
            }
        }
    }
}

