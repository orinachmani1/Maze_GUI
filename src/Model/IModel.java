package Model;

import algorithms.mazeGenerators.*;
import algorithms.search.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.util.Observer;

public interface IModel {

    void generateMaze(int rows, int cols) throws IOException;
    Maze getMaze();
    Solution getSolution();
    void solveMaze();

    public void updatePlayerLocation(MovementDirection direction);
    public void movePlayer(int row, int col);
    public int getPlayerRow();
    public int getPlayerCol();
    void assignObserver(Observer o);
//    void setCharacterPositionRow(int characterPositionRow);
//    void setCharacterPositionColumn(int characterPositionColumn);

    int getGoalRow();
    void setGoalRow(int goalRow);
    int getGoalColumn();
    void setGoalColumn(int goalColumn);

    void startServers();
    void stopServers();

    void saveMaze(File f);
    void loadMaze(File f);
    void closeApp();
}
