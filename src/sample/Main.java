package sample;

///import algorithms.maze3D.Maze3DState;
import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import View.MyViewController;
import com.sun.glass.ui.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        primaryStage.setTitle("Maze Game");
        primaryStage.setScene(new Scene(root,720 , 360));
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
