package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements IView, Observer, Initializable {
    public MyViewModel viewModel;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public Label playerRow;
    public Label playerCol;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
    }

    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    public void generateMaze(ActionEvent actionEvent) {
        int rows= Integer.valueOf(textField_mazeRows.getText());
        int cols= Integer.valueOf(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        int[][] maze = viewModel.getMaze();
        this.mazeDisplayer.setMaze(maze);
        this.mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        viewModel.solveMaze();
    }

    public void keyPressed(KeyEvent keyEvent) {
        //System.out.println("please");
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        System.out.println("UPDATED: " + change);
        if (change==null){return;}

        if (change.equals("maze generated"))
        {
            mazeGenerated();
        }
        if (change.equals("player moved"))
        {
            playerMoved();
        }
        if (change.equals("maze solved"))
        {
            mazeSolved();
        }

//        switch (change){
//            case ("maze generated"):
//                mazeGenerated();
//            case ("player moved") :
//                playerMoved();
//            case ("maze solved"):
//                mazeSolved();
//        }
    }

    private void mazeSolved() {
        System.out.println("winner!");
        mazeDisplayer.setSolution(viewModel.getSolution());
    }

    private void playerMoved() {
        setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        mazeDisplayer.setGoalRow(getGoalRow());
        mazeDisplayer.setGoalCol(getGoalCol());
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public int getGoalRow()
    {
        int row = viewModel.getGoalRow();
        return row;
    }

    public int getGoalCol()
    {
        int col = viewModel.getGoalCol();
        return col;
    }


}
