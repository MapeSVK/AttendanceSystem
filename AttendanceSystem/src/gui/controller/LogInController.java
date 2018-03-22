
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





public class LogInController implements Initializable {

    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label inLabel;
    
    private ModelManager manager = new ModelManager();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }     
    
    @FXML
    public void enterButton(javafx.scene.input.KeyEvent key) throws IOException {
        if(key.getCode()== KeyCode.ENTER)
        {
            login(key);
        }
    }
    
    @FXML
    public void clickLogIn(ActionEvent event) throws IOException {
        login(event);
    }
   
    private void login(Event event) throws IOException {
        
       if(manager.getUserId(loginField.getText(), passwordField.getText())!=0)
               {
                   if(manager.logIn(manager.getUserId(loginField.getText(), passwordField.getText())).toString().equalsIgnoreCase("Teacher"))
                   {
                       changeScene("Teacher", "Teacher window", event);
                   }
                   else if(manager.logIn(manager.getUserId(loginField.getText(), passwordField.getText())).toString().equalsIgnoreCase("Student"))
                   {
                       changeScene("Student", "Student window", event);
                   }
               }
       else
       {
          inLabel.setVisible(true);
       }
    }   
    
        private void changeScene(String window,String title,Event event)
        {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/gui/view/"+window+"View.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
  
        
        
        
        
        
    /************* PROTOTYPE METHODS **************/    
        
    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        try {
            if(manager.readUsername().equals("non")){}
            else
            {
                loginField.setText(manager.readUsername());
                rememberCheck.setSelected(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    

   

    
  
    
*/
}