package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Average Weather");
        primaryStage.setResizable(false); // the size of the window cannot be changed
        primaryStage.setScene(new Scene(root, 414, 435));
        primaryStage.show(); //open the application window
    }


    public static void main(String[] args) {
        launch(args);
    }
}
