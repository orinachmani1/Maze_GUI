package View;

import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;

    @Override
    public void update(Observable o, Object arg) {

    }

    public void generateMaze(ActionEvent actionEvent) {
        int rows= Integer.valueOf(textField_mazeRows.getText());
        int cols= Integer.valueOf(textField_mazeColumns.getText());
        Maze m =new Maze(rows,cols);
        System.out.println("rows:" + rows +" cols: " + cols );
    }

    public void solveMaze(ActionEvent actionEvent) {
        System.out.println("oh");
    }
}
