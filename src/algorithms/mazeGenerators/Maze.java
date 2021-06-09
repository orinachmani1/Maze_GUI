package algorithms.mazeGenerators;

import algorithms.maze3D.Maze3D;

import java.io.Serializable;
import java.util.ArrayList;

public class Maze implements Serializable {

    private Position start;
    private Position end;
    private int rows;
    private int cols;
    private int[][] grid;
    private boolean Solved;


    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];

    }

    public Maze(byte[] comMaze)
    {
        this.rows = comMaze[0]*127 + comMaze[1];
        this.cols = comMaze[2]*127 + comMaze[3];
        grid = new int[rows][cols];

        Position start = new Position(comMaze[4]*127 +comMaze[5],comMaze[6]*127 +comMaze[7] );
        Position end = new Position(comMaze[8]*127 +comMaze[9],comMaze[10]*127 +comMaze[11] );

        setStart(start);
        setEnd(end);

        int index = 12;
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < cols ; j++) {
                Position p = new Position(i,j);
                setCell(p,comMaze[index]);
                index++;
            }
        }
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Position getStartPosition()
    {
        return start;
    }

    public Position getGoalPosition() { return this.end; }

    public int[][] getGrid() {
        return grid;
    }

    public boolean getCell(Position p){
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        if (grid[row][col]==0){return false;}
        return true;
    }

    public void setCell(Position p, int n)
    {
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        grid[row][col] = n;
    }

    public boolean isValidMove(Position p)
    {
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();

        if (pRow < 0 || pCol < 0 || pRow >= rows || pCol >= cols){return false;}
        if (isWall(p)|| isStart(p)){return false;}
        return true;
    }
    public boolean isWall (Position p){
        if(grid [p.getRowIndex()] [p.getColumnIndex()] == 1)
        {
            return true;
        }
        return false;
    }

    public boolean isStart (Position p){
        if(p.getRowIndex() == start.getRowIndex() && p.getColumnIndex() == start.getColumnIndex())
        {
            return true;
        }
        return false;
    }

    public boolean isEnd (Position p){
        if(p.getRowIndex() == end.getRowIndex() && p.getColumnIndex() == end.getColumnIndex())
        {
            return true;
        }
        return false;
    }

    public void print()
    {
        System.out.print(toString());
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < rows; i++) {
            s.append("{");
            for (int j = 0; j < cols; j++) {
                Position pos = new Position(i, j);
                if (isEnd(pos)) { s.append(" E "); }
                else if (isWall(pos)) { s.append(" 1 "); }
                else if (isStart(pos)) { s.append(" S "); }
                else if (!isWall(pos)) { s.append(" 0 "); }
            }
            s.append("}\n");
        }

        // █ ░ - symbols for empty and full cells, maybe we used it in future
        return s.toString();

    }

    public byte[] toByteArray()
    {
        int length = rows;
        int width = cols;

        int startRow = this.getStartPosition().getRowIndex();
        int startCol = this.getStartPosition().getColumnIndex();
        int endRow = this.getGoalPosition().getRowIndex();
        int endCol = this.getGoalPosition().getColumnIndex();

        int size = length*width + 12;
        byte[] compressedMaze = new byte[size];

        compressedMaze[0] = (byte)(length/127);
        compressedMaze[1] = (byte)(length%127);
        compressedMaze[2] = (byte)(width/127);
        compressedMaze[3] = (byte)(width%127);

        compressedMaze[4] = (byte)(startRow/127);
        compressedMaze[5] = (byte)(startRow%127);
        compressedMaze[6] = (byte)(startCol/127);
        compressedMaze[7] = (byte)(startCol%127);

        compressedMaze[8] = (byte)(endRow/127);
        compressedMaze[9] = (byte)(endRow%127);
        compressedMaze[10] = (byte)(endCol/127);
        compressedMaze[11] = (byte)(endCol%127);

        int index = 12;
        for(int i=0; i<length; i++)
        {
            for(int j=0; j<width; j++)
            {
                compressedMaze[index] = (byte)grid[i][j];
                index++;
            }
        }
        return compressedMaze;
    }
}
