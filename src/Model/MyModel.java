package Model;

import Client.*;
import Server.*;
import IO.*;
import View.MazeDisplayer;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {

    private Server serverGenerateMaze;
    private Server serverSolveSearchMaze;
    private static Maze maze;
    private Solution solution;
    private MyViewModel viewM;
    private MazeDisplayer mazeD;
    private int playerRow = 0;
    private int playerCol = 0;
    private int goalRow;
    private int goalColumn;

    boolean win = false;

    public MyModel()
    {
         viewM= new MyViewModel(this);
        mazeD=new MazeDisplayer();
        //this.serverGenerateMaze =  new Server(5400, 1000, new ServerStrategyGenerateMaze());
        //this.serverSolveSearchMaze =  new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //startServers();
    }


    @Override
    public void startServers() {
        this.serverGenerateMaze.start();
        this.serverSolveSearchMaze.start();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        /*
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    //TODO!!!
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        //Main.reset();
        MyMazeGenerator generator = new MyMazeGenerator();
        Maze m = generator.generate(rows,cols);
        this.maze = m;
        setGoalRow(this.maze.getGoalPosition().getRowIndex());
        setGoalColumn(this.maze.getGoalPosition().getColumnIndex());
        this.solution=null;
        setChanged();
        notifyObservers("maze generated");
        //System.out.println("generated");
        //this.maze=new Maze(rows,cols);
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void solveMaze() {
        if (this.maze!=null)
        {
            SearchableMaze searchableMaze = new SearchableMaze(this.maze);
            BestFirstSearch bfs = new BestFirstSearch();
            Solution sol = bfs.solve(searchableMaze);
            this.solution = sol;
            setChanged();
            notifyObservers("solution");
        }

    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {

        int rows = maze.getRows();
        int cols = maze.getCols();

        String key = direction.name();

        if (maze!=null)
        {
            if (key.equals("UP"))
            {
                if (playerRow > 0)
                    movePlayer(playerRow - 1, playerCol);
            }
            if (key.equals("DOWN"))
            {
                if (playerRow < maze.getRows() - 1)
                    movePlayer(playerRow + 1, playerCol);
            }

            if (key.equals("LEFT"))
            {
                if (playerCol > 0)
                    movePlayer(playerRow, playerCol - 1);
            }
            if (key.equals("RIGHT"))
            {
                if (playerCol < maze.getCols() - 1)
                movePlayer(playerRow, playerCol + 1);

                //System.out.println("Yaminaaaaaa");
            }

            if (key.equals("UPRIGHT"))
            {
                if (playerCol < maze.getCols() - 1 && playerRow < maze.getRows() - 1)
                    movePlayer(playerRow-1, playerCol + 1);
            }

            if (key.equals("UPLEFT"))
            {
                if (playerCol < maze.getCols() - 1 && playerRow < maze.getRows() - 1)
                    movePlayer(playerRow-1, playerCol - 1);
            }

            if (key.equals("DOWNLEFT"))
            {
                if (playerCol < maze.getCols() - 1 && playerRow < maze.getRows() - 1)
                    movePlayer(playerRow+1, playerCol - 1);
            }

            if (key.equals("DOWNRIGHT"))
            {
                if (playerCol < maze.getCols() - 1 && playerRow < maze.getRows() - 1)
                    movePlayer(playerRow+1, playerCol + 1);
            }




            }
        }

    public void movePlayer(int row, int col){
        int valid = maze.getGrid()[row][col];
        if(this.maze != null && valid==0 )
        {
            this.playerRow = row;
            this.playerCol = col;
            setChanged();
            notifyObservers("player moved");
            System.out.println("playerRow: " +playerRow +  "playerCol: " +playerCol + "GoalRow: " +goalRow + "GoalCol: " +goalColumn );
            if(this.playerRow==this.goalRow && this.playerCol==this.goalColumn &&
                    this.goalRow!=0)
            {
                this.win = true;
                setChanged();
                notifyObservers("maze solved");
            }
        }
    }

    @Override
    public int getPlayerRow() {return this.playerRow;}

    @Override
    public int getPlayerCol() { return this.playerCol;}

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }


    @Override
    public int getGoalRow() {
        return this.goalRow;
    }

    @Override
    public void setGoalRow(int goalRow) {
        this.goalRow = goalRow;
    }

    @Override
    public int getGoalColumn() {
        return this.goalColumn;
    }

    @Override
    public void setGoalColumn(int goalColumn) {
        this.goalColumn = goalColumn;
    }


    @Override
    public void stopServers() {
        serverGenerateMaze.stop();
        serverSolveSearchMaze.stop();
    }

    @Override
    public void saveMaze(File f) {
        try
        {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(maze);
            out.flush();
            out.close();
        }
        catch (IOException e) {
        }
    }
    public void MouseMovePlayer(MouseEvent mouseEvent, MazeDisplayer mazeDisplayer) {
        if (maze != null) {


            double xValue = mouseEvent.getX() / (mazeDisplayer.getWidth() / maze.getGrid()[0].length);

            double firstDouble = mouseEvent.getY();

            double yValue = firstDouble / (mazeDisplayer.getHeight() / maze.getGrid().length);

            if (yValue < mazeDisplayer.getPlayerRow()) {
                viewM.moveByMouse(KeyCode.UP);
                updatePlayerLocation(MovementDirection.UP);
            } else if (xValue > mazeDisplayer.getPlayerCol() +1) {
                viewM.moveByMouse(KeyCode.RIGHT);
                updatePlayerLocation(MovementDirection.RIGHT);

            } else if (xValue < mazeDisplayer.getPlayerCol()) {
                viewM.moveByMouse(KeyCode.LEFT);
                updatePlayerLocation(MovementDirection.LEFT);
            } else if (yValue > mazeDisplayer.getPlayerRow() +1) {
                viewM.moveByMouse(KeyCode.DOWN);
                updatePlayerLocation(MovementDirection.DOWN);
            }
            //updatePlayerLocation(MovementDirection.RIGHT);
            mazeD.draw();


        }
    }

        @Override
    public void loadMaze(File file) {
        try{
            ObjectInputStream objectInputStream= new ObjectInputStream((new FileInputStream(file)));
            this.maze = (Maze) objectInputStream.readObject();

            playerRow = maze.getStartPosition().getRowIndex();
            playerCol = maze.getStartPosition().getColumnIndex();

            goalRow = maze.getGoalPosition().getRowIndex();
            goalColumn = maze.getGoalPosition().getColumnIndex();

            solution = null;
            this.win = false;

            setChanged();
            notifyObservers("Load Maze");

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeApp() {
        stopServers();
    }
}
