package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {
    ArrayList<AState> solution;

    public Solution() {
        solution = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath() {
        return solution;
    }

    public void setSolution(ArrayList<AState> solution) {
        this.solution = solution;
    }

    public void addStateToSolution(AState state)
    {
        solution.add(0,state);
    }

    public boolean solutionContainsState(AState state)
    {
        return solution.contains(state);
    }

}
