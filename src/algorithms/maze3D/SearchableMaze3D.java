package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {

    Maze3DState start;
    Maze3DState goal;
    Maze3D maze;
    int rows;
    int cols;
    int depth;

    public SearchableMaze3D(Maze3D maze)
    {
        this.maze = maze;
        start = new Maze3DState(maze.getStartPosition());
        goal = new Maze3DState(maze.getGoalPosition());
        rows = maze.getRows();
        cols = maze.getCols();
        depth = maze.getDepth();
    }

    @Override
    public AState getStartState() {
        return start;
    }

    @Override
    public AState getGoalState() {
        return goal;
    }



    @Override
    public ArrayList<AState> getAllSuccessors(AState s)
    {
        Position3D curPos = ((Maze3DState) s).getCurPosition();
        ArrayList<AState> possibleStates = new ArrayList<AState>();

        int pDepth = curPos.getDepthIndex();
        int pRow = curPos.getRowIndex();
        int pCol = curPos.getColumnIndex();

        //Regular steps
        Position3D up = new Position3D(pDepth+1,pRow, pCol);
        Position3D down = new Position3D(pDepth-1, pRow, pCol);

        Position3D forward = new Position3D(pDepth,pRow + 1, pCol);
        Position3D backwards = new Position3D(pDepth,pRow - 1, pCol);
        Position3D right = new Position3D(pDepth, pRow, pCol + 1);
        Position3D left = new Position3D(pDepth, pRow, pCol - 1);

        if (maze.isValidMove(up)) { possibleStates.add(new Maze3DState(up, s)); }
        if (maze.isValidMove(down)) { possibleStates.add(new Maze3DState(down, s)); }

        if (maze.isValidMove(forward)) { possibleStates.add(new Maze3DState(forward, s)); }
        if (maze.isValidMove(backwards)) { possibleStates.add(new Maze3DState(backwards, s)); }
        if (maze.isValidMove(right)) { possibleStates.add(new Maze3DState(right, s)); }
        if (maze.isValidMove(left)) { possibleStates.add(new Maze3DState(left, s)); }

        return possibleStates;
    }
}
