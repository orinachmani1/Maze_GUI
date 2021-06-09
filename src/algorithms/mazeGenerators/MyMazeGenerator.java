package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


// Prim Algorithm
public class MyMazeGenerator extends AMazeGenerator{

    public boolean visited[][];
    ArrayList<Position> redNodes;

    @Override
    public Maze generate(int rows, int cols)
    {
        Random random = new Random();
        Maze maze = new Maze(rows, cols);
        visited = new boolean[rows][cols];
        redNodes = new ArrayList<Position>();

        int startR = 0;
        int startC = 0;

        int endR = random.nextInt(rows-1)+1;
        //int endR = 1;
        int endC= cols-1;

        Position goal = new Position(endR,endC);

        maze.setEnd(goal);
        Position curPosition = new Position(startR,startC);
        maze.setStart(curPosition);
        maze.setCell(curPosition, 0);
        setVisited(curPosition);
        redNodes.add(curPosition);

        while(redNodes.size() > 0)
        {
            Position next = getRandomRed(curPosition);
            if (next!=null)
            {
                goFromCurrrentToRed(curPosition,next);
                curPosition = next;
            }
            else
                {
                    curPosition = redNodes.remove(0);
                }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols ; j++) {
                Position p = new Position(i,j);
                if(!getVisited(p))
                {
                    maze.setCell(p,1);
                }
                if ((i==rows-1|| j==cols-1) && maze.getCell(p) ){
                    int randomNum=random.nextInt(10);
                    if (randomNum>7)
                    {
                        maze.setCell(p,0);
                    }
                }
            }
        }

        maze.setCell(goal,0);
        Position p = new Position(goal.getRowIndex()-1,goal.getColumnIndex());
        maze.setCell(p,0);
        Position p2 = new Position(startC,startC+1);
        maze.setCell(p2,0);
        return maze;
    }

    public boolean onFrame(Position p,Maze m) {
        int row = p.getRowIndex();
        int col = p.getColumnIndex();

        int mazeRows = m.getRows();
        int mazeCols = m.getCols();

        if (col == mazeCols-1 || row == mazeRows-1 ){
            if (col != 0 && col > (2*mazeCols/3) && row!=m.getStartPosition().getRowIndex()){return true;}
        }
        return false;

    }

    public void goFromCurrrentToRed(Position curPosition, Position next)
    {
        Position middle = null;
        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();
        int nextRow = next.getRowIndex();
        int nextCol = next.getColumnIndex();

        //UP
        if ( curRow - 2 == nextRow && curCol == nextCol ){ middle = new Position(curRow-1,curCol);}
        //DOWN
        if ( curRow + 2 == nextRow && curCol == nextCol ){ middle = new Position(curRow+1,curCol);}
        //RIGHT
        if ( curRow == nextRow && curCol + 2  == nextCol ){ middle = new Position(curRow,curCol+1);}
        //LEFT
        if ( curRow == nextRow && curCol - 2 == nextCol ){ middle = new Position(curRow,curCol-1);}

        setVisited(next);
        setVisited(middle);
        redNodes.add(next);
    }

    public Position getRandomRed(Position pos)
    {
        ArrayList<Position> reds = new ArrayList<Position>();
        Position redTmp;
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();

        //UP
        try
        {
            redTmp = new Position(r-2 ,c);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};

        //DOWN
        try
        {
            redTmp = new Position(r+2 ,c);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};

        //RIGHT
        try
        {
            redTmp = new Position(r,c+2);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};

        //LEFT
        try
        {
            redTmp = new Position(r, c-2);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};


        if(reds.size() > 0){
            Collections.shuffle(reds);
            return reds.remove(0);
        }
        return null;

    }

    public void setVisited(Position p)
    {
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        visited[row][col] = true;
    }

    public Boolean getVisited(Position p)
    {
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        if (visited[row][col] == true)
        {
            return true;
        }
        return false;
    }
}
