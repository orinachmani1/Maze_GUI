package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Maze3D {
    private Position3D start;
    private Position3D end;
    private int rows;
    private int cols;
    private int depth;
    private int[][][] grid;


    public Maze3D(int depth, int rows, int cols) {
        this.depth = depth;
        this.rows = rows;
        this.cols = cols;
        grid = new int[depth][rows][cols];
    }

    public void setStart(Position3D start) { this.start = start; }

    public void setEnd(Position3D end) { this.end = end; }

    public void setGrid(int[][][] grid) {
        this.grid = grid;
    }

    public Position3D getStartPosition() { return start; }

    public Position3D getGoalPosition() { return end;
    }
    public int getRows() { return rows; }

    public int getCols() { return cols; }

    public int getDepth() { return depth; }


    public void setCell(Position3D p, int n)
    {
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        grid[depth][row][col] = n;
    }

    public boolean isValidMove(Position3D p)
    {
        int pDepth = p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();

        if (pDepth <0 || pRow < 0 || pCol < 0 ||pDepth>=depth || pRow >= rows || pCol >= cols){return false;}
        if (isWall(p)|| isStart(p)){return false;}
        return true;
    }

    public boolean isWall (Position3D p)
    {
        int pDepth = p.getDepthIndex();
        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();

        if(grid [pDepth][pRow][pCol] == 1)
        {
            return true;
        }
        return false;
    }

    public boolean isStart (Position3D p){
        if (p.equals(start))
        //if(p.getDepthIndex() == start.getDepthIndex() && p.getRowIndex() == start.getRowIndex() && p.getColumnIndex() == start.getColumnIndex())
        {
            return true;
        }
        return false;
    }

    public boolean isEnd (Position3D p){
        if (p.equals(end))
        //if(p.getRowIndex() == end.getRowIndex() && p.getColumnIndex() == end.getColumnIndex())
        {
            return true;
        }
        return false;
    }

    public void print()
    {
        System.out.println("{");
        for(int depth = 0; depth < grid.length; depth++){
            for(int row = 0; row < grid[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < grid[0][0].length; col++) {
                    if (depth == start.getDepthIndex() && row == start.getRowIndex() && col == start.getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == end.getDepthIndex() && row == end.getRowIndex() && col == end.getColumnIndex()) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(grid[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if(depth < grid.length - 1) {
                System.out.print("---");
                for (int i = 0; i < grid[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }
        //System.out.print(toString());


    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("{\n");
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                s.append("{ ");
                for (int k = 0; k < cols ; k++) {
                    Position3D pos = new Position3D(i,j,k);
                    if (isEnd(pos)) { s.append("E "); }
                    else if (isWall(pos)) { s.append("1 "); }
                    else if (isStart(pos)) { s.append("S "); }
                    else if (!isWall(pos)) { s.append("0 "); }
                }
                s.append("}\n");
            }
            if(i<depth-1) {
                s.append("-------------\n");
            }
        }
        s.append("}\n\n");

        // █ ░ - symbols for empty and full cells, maybe we used it in future
        return s.toString();

    }

    public boolean getCell(Position3D p) {
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        if (grid[depth][row][col]==0){return false;}
        return true;

    }
}
