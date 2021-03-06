package Main;

///import algorithms.maze3D.Maze3DState;
import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import View.MyViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage mazeNew;
    private MediaPlayer music;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*String fileName =  "../Resources/GameOT.mp3";
        File musicPath=new File(fileName);
        music=new MediaPlayer(new Media(musicPath.toURI().toString()));
        music.play();*/

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Maze Game");
        primaryStage.setScene(new Scene(root,720 , 360));
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        Main.mazeNew= primaryStage;
    }

    public static void reset () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        Scene scene = new Scene(root,720,360);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        Main.mazeNew.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
