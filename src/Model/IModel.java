package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

public interface IModel {

    void generateMaze(int rows, int cols);
    Maze getMaze();
    Solution getSolution();
    void solveMaze();

    void moveCharacter(KeyCode movement);
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
