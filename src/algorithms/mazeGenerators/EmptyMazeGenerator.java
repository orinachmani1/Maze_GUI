package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rows, int cols) {

        Maze maze= new Maze(rows, cols);
        Position startPos = new Position(0,0);
        Position endPos = new Position(rows-1,cols-1);
        int[][] emptyMaze = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                emptyMaze[i][j] = 0;
        }

        maze.setGrid(emptyMaze);
        maze.setStart(startPos);
        maze.setEnd(endPos);
        return maze;
    }
}
