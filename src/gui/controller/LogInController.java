package gui.controller;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LogInController implements Initializable {

    private ModelManager manager = new ModelManager();

    @FXML
    private JFXTextField loginField;

    @FXML
    private Label inLabel;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXCheckBox rememberCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if(manager.readUsername()==null){}
            else
            {
                loginField.setText(manager.readUsername());
                rememberCheck.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void enterButton(javafx.scene.input.KeyEvent key) throws IOException {
        if(key.getCode()== KeyCode.ENTER)
        {
            login(key);
        }

    }

    public void clickLogIn(ActionEvent event) throws IOException {
        login(event);
    }

    private void login(Event event) throws IOException  {
        if(loginField.getText().equals("Tomek")&&passwordField.getText().equals("qwerty"))
        {
            if(rememberCheck.isSelected()==true)
                manager.saveUsername(loginField.getText());
            else
                manager.saveUsername(null);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/gui/view/StudentView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student");
            stage.setMaxWidth(265);
            stage.setMaxHeight(375);
            stage.setMinWidth(265);
            stage.setMinHeight(375);
            stage.show();
        }
        else if(loginField.getText().equals("Jeppe")&&passwordField.getText().equals("qwerty"))
        {
            if(rememberCheck.isSelected()==true)
                manager.saveUsername(loginField.getText());
            else
                manager.saveUsername(null);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/gui/view/TeacherView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teacher");
            stage.setMaxWidth(265);
            stage.setMaxHeight(375);
            stage.setMinWidth(265);
            stage.setMinHeight(375);
            stage.show();
        }
        else
        {
            inLabel.setVisible(true);
        }
    }
    }

