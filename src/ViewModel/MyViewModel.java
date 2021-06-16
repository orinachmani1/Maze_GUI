package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
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
        this.imodel.assignObserver(this);
    }

    public void generateMaze(int rows, int cols) throws IOException {
        imodel.generateMaze(rows, cols);
    }

    public void solveMaze(){
        imodel.solveMaze();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o==imodel){
            characterPositionRowIndex = this.imodel.getPlayerRow();
            characterRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = this.imodel.getPlayerCol();
            characterColumn.set(characterPositionColumnIndex + "");
            //Solution solution= this.imodel.getSolution();
            setChanged();
            notifyObservers(arg);
        }
    }

    public int[][] getMaze()
    {
        Maze maze = imodel.getMaze();
        if(maze==null){return null;}
        return maze.getGrid();
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction = null;
        System.out.println(keyEvent.getCode());
        String key = keyEvent.getCode().getName();
        System.out.println(key);

        if (key.equals("Right")|| key.equals("Numpad 6")){direction = MovementDirection.RIGHT;}
        if (key.equals("Up")|| key.equals("Numpad 8")){direction = MovementDirection.UP;}
        if (key.equals("Down")|| key.equals("Numpad 2")){direction = MovementDirection.DOWN;}
        if (key.equals("Left")|| key.equals("Numpad 4")){direction = MovementDirection.LEFT;}
        if (key.equals("Numpad 1")){direction = MovementDirection.DOWNLEFT;}
        if (key.equals("Numpad 3")){direction = MovementDirection.DOWNRIGHT;}
        if (key.equals("Numpad 7")){direction = MovementDirection.UPLEFT;}
        if (key.equals("Numpad 9")){direction = MovementDirection.UPRIGHT;}
        imodel.updatePlayerLocation(direction);

//        switch (key){
//            case "Right" :direction = MovementDirection.RIGHT;
//            case "Up" : direction = MovementDirection.UP;
//            case "Down" : direction = MovementDirection.DOWN;
//            case "Left" : direction = MovementDirection.LEFT;
//            imodel.updatePlayerLocation(direction);
//            default : {
//                return;
//            }
//        }
//        switch (keyEvent.getCode()){
//            case RIGHT :direction = MovementDirection.RIGHT;
//            case UP: direction = MovementDirection.UP;
//            case DOWN : direction = MovementDirection.DOWN;
//            case LEFT : direction = MovementDirection.LEFT;
//            imodel.updatePlayerLocation(direction);
//            default : {
//               return;
//            }
//        }
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
        int row = imodel.getPlayerRow();
        return row;
    }

    public int getPlayerCol() {
        int col = imodel.getPlayerCol();
        return col;
    }

    public int getGoalRow() {
        int row = imodel.getMaze().getGoalPosition().getRowIndex();
        return row;
    }

    public int getGoalCol() {
        int col =  imodel.getMaze().getGoalPosition().getColumnIndex();
        return col;
    }
}
