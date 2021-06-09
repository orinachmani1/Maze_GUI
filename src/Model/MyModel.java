package Model;

import Client.*;
import Server.*;
import IO.*;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

public class MyModel extends Observable implements IModel {

    private Server serverGenerateMaze;
    private Server serverSolveSearchMaze;
    private Maze maze;
    private Solution solution;

    private int characterPositionRow;
    private int characterPositionColumn;
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
        Maze m = generator.generate(10,10);
        this.maze = m;
        setChanged();
        notifyObservers();
        System.out.println("genrated");
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

    public void movePlayer(KeyEvent movement) {
        //TODO
    }

    @Override
    public int getCharacterPositionRow() {
        return this.characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return this.characterPositionColumn;
    }

    @Override
    public void setCharacterPositionRow(int characterPositionRow) {
        this.characterPositionRow = characterPositionRow;
    }

    @Override
    public void setCharacterPositionColumn(int characterPositionColumn) {
        this.characterPositionColumn = characterPositionColumn;
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
