package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

class comparator implements Comparator<AState>
{
    public int compare(AState a, AState b)
    {
        int aCost = (int) a.getCost();
        int bCost = (int)b.getCost();
        int res = (aCost- bCost);
        return res;
    }
}
public class BestFirstSearch extends BreadthFirstSearch {
    private PriorityQueue<AState> statesArray;

    public BestFirstSearch() {
        name = "BestFirstSearch";
        this.statesArray = new PriorityQueue<AState>(new comparator());
    }

}
