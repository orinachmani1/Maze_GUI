package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel imodel;

    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    private Solution solution;

    public StringProperty characterRow = new SimpleStringProperty("");
    public StringProperty characterColumn = new SimpleStringProperty("");

    public MyViewModel(IModel model) {
        this.imodel = model;
    }


    public void generateMaze(int rows, int cols){
        imodel.generateMaze(rows, cols);
    }

    public void solveMaze(){
        imodel.solveMaze();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o==imodel){
            characterPositionRowIndex = this.imodel.getCharacterPositionRow();
            characterRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = this.imodel.getCharacterPositionColumn();
            characterColumn.set(characterPositionColumnIndex + "");
            Solution solution= this.imodel.getSolution();
            setChanged();
            notifyObservers();
        }
    }

    public int[][] getMaze()
    {
        return this.imodel.getMaze().getGrid();
    }

    public void movePlayer(KeyEvent moveEvent){
        this.imodel.movePlayer(moveEvent);
    }

    public void saveMaze(File f){
        imodel.saveMaze(f);
    }

    public void loadMaze(File f){
        imodel.loadMaze(f);
    }

    public Solution getSolution() {
        Solution sol = imodel.getSolution();
        return sol;
    }

    public int getPlayerRow() {
        int row = imodel.getCharacterPositionRow();
        return row;
    }

    public int getPlayerCol() {
        int col = imodel.getCharacterPositionColumn();
        return col;
    }
}
