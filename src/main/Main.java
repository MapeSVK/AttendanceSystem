package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/StudentView.fxml"));
        primaryStage.setTitle("Log In");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setMaxWidth(265);
        primaryStage.setMaxHeight(334);
        primaryStage.setMinWidth(265);
        primaryStage.setMinHeight(334);
        primaryStage.setIconified(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
