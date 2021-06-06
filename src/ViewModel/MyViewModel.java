package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

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

    void MyViewModel(IModel im)
    {
        this.imodel = im;
    }

    public void generateMaze(int rows, int cols){
        imodel.generateMaze(rows, cols);
    }

    public void solve(){
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

    public Maze getMaze()
    {
        return this.imodel.getMaze();
    }

    public void moveCharacter(KeyCode moveEvent){
        this.imodel.moveCharacter(moveEvent);
    }

    public void saveMaze(File f){
        imodel.saveMaze(f);
    }

    public void loadMaze(File f){
        imodel.loadMaze(f);
    }

}
