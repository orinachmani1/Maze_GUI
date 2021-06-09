package Model;

import algorithms.mazeGenerators.*;
import algorithms.search.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;

public interface IModel {

    void generateMaze(int rows, int cols);
    Maze getMaze();
    Solution getSolution();
    void solveMaze();

    void movePlayer(KeyEvent movement);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void setCharacterPositionRow(int characterPositionRow);
    void setCharacterPositionColumn(int characterPositionColumn);

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
