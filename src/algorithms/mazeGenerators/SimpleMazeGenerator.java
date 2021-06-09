package algorithms.mazeGenerators;

import org.junit.jupiter.api.DynamicTest;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator
{
    @Override

    public Maze generate(int rows, int cols) {
        int[][] simpleMaze = new int[rows][cols];
        Random randomNum = new Random();
        //Position startPos = new Position(0,randomNum.nextInt(cols-1));
        Position startPos = new Position(0,0);
        //Position endPos = new Position(randomNum.nextInt(rows-1),cols-1);
        Position endPos = new Position(randomNum.nextInt(rows-1)+1,cols-1);
        simpleMaze[startPos.getRowIndex()][startPos.getColumnIndex()]=0;
        simpleMaze[endPos.getRowIndex()][endPos.getColumnIndex()]=0;
        for (int i =0;i<rows;i++){
            for (int j =0;j<cols;j++){
                if (j==0&&i==0)
                    continue;
              if ( i==endPos.getRowIndex() || j==startPos.getColumnIndex() ){  simpleMaze[i][j]=0; }
              else {
                  if(randomNum.nextInt(100)>50){simpleMaze[i][j]=1; }
                  else simpleMaze[i][j]=0;
              }
            }
        }
        Maze maze= new Maze(rows, cols);
        maze.setGrid(simpleMaze);
        maze.setStart(startPos);
        maze.setEnd(endPos);
        return maze;
    }
}
