/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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





/**
 *
 * @author Pepe15224
 */
public class LogInController implements Initializable {

    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXCheckBox rememberCheck;
    @FXML
    private Label inLabel;
    
    private ModelManager manager = new ModelManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //        try {
//            if(manager.readUsername()==null){}
//            else
//            {
//                loginField.setText(manager.readUsername());
//                rememberCheck.setSelected(true);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
System.out.println(manager.readUsername());
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void login(Event event) throws IOException {
        if(loginField.getText().equals("Student")&&passwordField.getText().equals("qwerty"))
        {
            manager.saveUsername("Student");
            changeScene("Student",event);
            
        }
        else if(loginField.getText().equals("Teacher")&&passwordField.getText().equals("qwerty"))
        {
            manager.saveUsername("Teacher");
            changeScene("Teacher",event);
        }
        else
        {
            inLabel.setVisible(true);
            
        }
    }
    private void changeScene(String view,Event event) throws IOException {
        if(rememberCheck.isSelected()==true)
            manager.saveUsername(loginField.getText());
        else
            manager.saveUsername(null);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/"+view+"View.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(view);
        stage.show();
    }
}