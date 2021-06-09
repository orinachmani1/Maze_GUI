package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable{
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(Position pos) {
        row = pos.row;
        col = pos.col;
    }

    public int getRowIndex()
    {
        return this.row;
    }
    public int getColumnIndex()
    {
        return this.col;
    }

    @Override
    public boolean equals(Object obj) {
        boolean a = ((Position)obj).getRowIndex() == row;
        boolean b = ((Position)obj).getColumnIndex()== col;
        if (a&&b){return true;}
        return false;
    }

    @Override
    public String toString() {
        return "{" + row + "," + col + "}";
    }
}
