package Model;

import Client.*;
import Server.*;
import IO.*;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {

    private Server serverGenerateMaze;
    private Server serverSolveSearchMaze;
    private Maze maze;
    private Solution solution;

    private int playerRow = 0;
    private int playerCol = 0;
    private int goalRow;
    private int goalColumn;

    public MyModel()
    {
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
        MyMazeGenerator generator = new MyMazeGenerator();
        Maze m = generator.generate(rows,cols);
        this.maze = m;
        setChanged();
        notifyObservers("maze generated");
        System.out.println("generated");
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
        //this.solution =
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
                }

            }
//            switch (direction) {
//                case UP: {
//                    if (playerRow > 0)
//                        movePlayer(playerRow - 1, playerCol);
//                }
//                case DOWN : {
//                    if (playerRow < maze.getRows() - 1)
//                        movePlayer(playerRow + 1, playerCol);
//                }
//                case LEFT :{
//                    if (playerCol > 0)
//                        movePlayer(playerRow, playerCol - 1);
//                }
//                case RIGHT : {
//                    if (playerCol < maze.getCols() - 1)
//                        movePlayer(playerRow, playerCol + 1);
//                }
//                default:return;
//            }
        }

    public void movePlayer(int row, int col){
        int valid = maze.getGrid()[row][col];
        if(this.maze != null && valid==0 )
        {
            this.playerRow = row;
            this.playerCol = col;
            setChanged();
            notifyObservers("player moved");
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

    }

    @Override
    public void loadMaze(File f) {

    }

    @Override
    public void closeApp() {
        stopServers();
    }
}
