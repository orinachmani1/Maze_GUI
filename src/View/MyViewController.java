package View;

import ViewModel.MyViewModel;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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

    public void generateMaze(ActionEvent actionEvent) throws IOException {
        int rows= Integer.valueOf(textField_mazeRows.getText());
        int cols= Integer.valueOf(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        int[][] maze = viewModel.getMaze();
        this.mazeDisplayer.setPlayerPosition(0,0);
        this.mazeDisplayer.setMaze(maze);
        this.mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze(ActionEvent actionEvent) {
        if(this.viewModel.getMaze()==null){return;}
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
        if (change.equals("solution"))
        {
            solution();
        }
        if (change.equals("maze solved"))
        {
            try {
                mazeSolved();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
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

    public void solution() {
        Solution sol = viewModel.getSolution();
        mazeDisplayer.setSolution(sol);
        System.out.println("solution updated");
    }

    private void mazeSolved() throws IOException, InterruptedException {
        System.out.println("winner!");
        //mazeDisplayer.winner=true;
        //mazeDisplayer.draw();
        //Rules();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You won!!\nCongratulations!!");
        alert.show();
        mazeDisplayer.setSolution(viewModel.getSolution());
        //mazeDisplayer.draw();
        //TimeUnit.SECONDS.sleep(2);
        Main.reset();


    }

    public void About(){
        try {
            Stage stage = new Stage();
            stage.setTitle("About the game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml")/*.openStream()*/);
            Scene scene = new Scene(root, 450, 255);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
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


    public void NewButtonPressed(ActionEvent actionEvent) throws IOException {
        Main.reset();
    }

    public void about(ActionEvent actionEvent) {
        About();
    }

    public void Properties(){
        try {
            Stage stage = new Stage();
            stage.setTitle("About the game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml")/*.openStream()*/);
            Scene scene = new Scene(root, 450, 255);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
    }
    public void PropertiesButtonPressed(ActionEvent actionEvent) {
        Properties();

    }
}

