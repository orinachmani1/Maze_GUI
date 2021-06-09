package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Position3D {

    private int row;
    private int col;
    private int depth;


    public Position3D(int depth,int row, int col) {
        this.depth = depth;
        this.row = row;
        this.col = col;
    }

    public Position3D(Position3D pos) {
        row = pos.row;
        col = pos.col;
        depth = pos.depth;
    }

    public int getRowIndex()
    {
        return this.row;
    }
    public int getColumnIndex()
    {
        return this.col;
    }
    public int getDepthIndex(){return this.depth;}

    @Override
    public boolean equals(Object obj) {
        boolean a = ((Position3D)obj).getRowIndex() == row;
        boolean b = ((Position3D)obj).getColumnIndex()== col;
        boolean c = ((Position3D)obj).getDepthIndex()== depth;
        if (a && b && c){return true;}
        return false;
    }

    @Override
    public String toString() {
        return "{" + depth + ","  + row + "," + col + "}";
    }

}
