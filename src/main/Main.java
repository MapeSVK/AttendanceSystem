package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/TeacherView.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setMaxWidth(265);
//        primaryStage.setMaxHeight(375);
//        primaryStage.setMinWidth(265);
//        primaryStage.setMinHeight(375);
        primaryStage.setIconified(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
