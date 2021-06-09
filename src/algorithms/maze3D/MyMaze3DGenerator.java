package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator {

    public boolean visited[][][];
    ArrayList<Position3D> redNodes;

    @Override
    public Maze3D generate(int depth, int row, int column) {
        Random random = new Random();
        Maze3D maze = new Maze3D(depth,row, column);
        visited = new boolean[depth][row][column];
        redNodes = new ArrayList<Position3D>();

        int startD = 0;
        //int startR = random.nextInt(row);
        int startR = 0;
        int startC = 0;

        //int endD= depth-1;
        int endD= random.nextInt(depth-1)+1;
        int endR = random.nextInt(row-1)+1;
        int endC= column-1;

        Position3D goal = new Position3D(endD,endR,endC);
        maze.setEnd(goal);
        Position3D curPosition = new Position3D(startD,startR,startC);
        maze.setStart(curPosition);
        maze.setCell(curPosition, 0);
        setVisited(curPosition);
        redNodes.add(curPosition);

        while(redNodes.size() > 0)
        {
            Position3D next = getRandomRed(curPosition);

            if (next!=null)
            {
                //System.out.print(next.toString());//TODO - remove
                goFromCurrrentToRed(curPosition,next);
                curPosition = next;
            }
            else
            {
                curPosition = redNodes.remove(0);
            }
        }

        for (int i = 0; i < depth; i++)
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < column ; k++) {
                Position3D p = new Position3D(i,j,k);
                if(!getVisited(p))
                {
                    maze.setCell(p,1);
                }
                if ((j==row-1|| k==column-1) && maze.getCell(p) )
                {
                    int randomNum=random.nextInt(10);
                    if (randomNum>2){ maze.setCell(p,0);}
                }
            }
        }

        maze.setCell(goal,0);
        Position3D p = new Position3D(goal.getDepthIndex(),goal.getRowIndex()-1,goal.getColumnIndex());
        maze.setCell(p,0);
        Position3D p2 = new Position3D(0,0,1);
        maze.setCell(p2,0);
        return maze;
    }

    public void setVisited(Position3D p)
    {
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        visited[depth][row][col] = true;
    }

    public Position3D getRandomRed(Position3D pos)
    {
        ArrayList<Position3D> reds = new ArrayList<Position3D>();
        Position3D redTmp;
        int d = pos.getDepthIndex();
        int r = pos.getRowIndex();
        int c = pos.getColumnIndex();

        //UP
        try
        {
            redTmp = new Position3D(d+2,r ,c);
            if (!getVisited(redTmp)) { reds.add(redTmp); }
        }
        catch (IndexOutOfBoundsException err){};

        //DOWN
        try
        {
            redTmp = new Position3D(d-2,r ,c);
            if (!getVisited(redTmp)) { reds.add(redTmp); }
        }
        catch (IndexOutOfBoundsException err){};

        //FORWARD
        try
        {
            redTmp = new Position3D(d,r-2 ,c);
            if (!getVisited(redTmp)) { reds.add(redTmp); }
        }
        catch (IndexOutOfBoundsException err){};

        //BACKWARDS
        try
        {
            redTmp = new Position3D(d,r+2 ,c);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};

        //RIGHT
        try
        {
            redTmp = new Position3D(d,r,c+2);
            if (!getVisited(redTmp))
            {
                reds.add(redTmp);
            }
        }
        catch (IndexOutOfBoundsException err){};

        //LEFT
        try
        {
            redTmp = new Position3D(d, r, c-2);
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

    public Boolean getVisited(Position3D p)
    {
        int depth = p.getDepthIndex();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        if (visited[depth][row][col] == true)
        {
            return true;
        }
        return false;
    }

    public void goFromCurrrentToRed(Position3D curPosition, Position3D next)
    {
        Position3D middle = null;
        int curDep = curPosition.getDepthIndex();
        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();
        int nextDep = next.getDepthIndex();
        int nextRow = next.getRowIndex();
        int nextCol = next.getColumnIndex();

        //UP
        if ( curDep + 2 == nextDep && curRow == nextRow && curCol == nextCol ){ middle = new Position3D(curDep+1,curRow,curCol);}
        //DOWN
        if ( curDep - 2 == nextDep && curRow == nextRow && curCol == nextCol ){ middle = new Position3D(curDep-1,curRow,curCol);}

        //FORWARD
        if ( curDep == nextDep && curRow - 2 == nextRow && curCol == nextCol ){ middle = new Position3D(curDep,curRow-1,curCol);}
        //BACKWARD
        if ( curDep == nextDep && curRow + 2 == nextRow && curCol == nextCol ){ middle = new Position3D(curDep,curRow+1,curCol);}
        //RIGHT
        if ( curDep == nextDep && curRow == nextRow && curCol + 2  == nextCol ){ middle = new Position3D(curDep, curRow,curCol+1);}
        //LEFT
        if ( curDep == nextDep && curRow == nextRow && curCol - 2 == nextCol ){ middle = new Position3D(curDep, curRow,curCol-1);}

        setVisited(next);
        setVisited(middle);
        redNodes.add(next);
    }
}


