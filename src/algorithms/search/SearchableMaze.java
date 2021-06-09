package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    MazeState start;
    MazeState goal;
    Maze maze;
    int rows;
    int cols;


    public SearchableMaze(Maze maze)
    {
        this.maze = maze;
        start = new MazeState(maze.getStartPosition());
        goal = new MazeState(maze.getGoalPosition());
        rows = maze.getRows();
        cols = maze.getCols();
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
        Position curPos = ((MazeState) s).getCurPosition();
        ArrayList<AState> possibleStates = new ArrayList<AState>();

        int pRow = curPos.getRowIndex();
        int pCol = curPos.getColumnIndex();

        //Regular steps = costs 10
        Position up = new Position(pRow - 1, pCol);
        Position down = new Position(pRow + 1, pCol);
        Position right = new Position(pRow, pCol + 1);
        Position left = new Position(pRow, pCol - 1);
        //Diagonals steps = costs 15
        Position upRight = new Position(pRow - 1, pCol + 1);
        Position upLeft = new Position(pRow - 1, pCol - 1);
        Position downRight = new Position(pRow + 1, pCol + 1);
        Position downLeft = new Position(pRow + 1, pCol - 1);

        if (maze.isValidMove(up)) {
            possibleStates.add(new MazeState(up, 10, s));
            if (maze.isValidMove(upRight)) { possibleStates.add(new MazeState(upRight, 15, s)); }
            if (maze.isValidMove(upLeft)) { possibleStates.add(new MazeState(upLeft, 15, s)); }
        }

        if (maze.isValidMove(down)) {
            possibleStates.add(new MazeState(down, 10, s));
            if (maze.isValidMove(downRight)) { possibleStates.add(new MazeState(downRight, 15, s)); }
            if (maze.isValidMove(downLeft)) { possibleStates.add(new MazeState(downLeft, 15, s)); }
        }

        if (maze.isValidMove(right)) {
            possibleStates.add(new MazeState(right, 10, s));
            if (maze.isValidMove(upRight)) { possibleStates.add(new MazeState(upRight, 15, s)); }
            if (maze.isValidMove(downLeft)) { possibleStates.add(new MazeState(downLeft, 15, s)); }
        }

        if (maze.isValidMove(left)) {
            possibleStates.add(new MazeState(left, 10, s));
            if (maze.isValidMove(upLeft)) { possibleStates.add(new MazeState(upLeft, 15, s)); }
            if (maze.isValidMove(downLeft)) { possibleStates.add(new MazeState(downLeft, 15, s)); }
        }

        return possibleStates;
    }
}

