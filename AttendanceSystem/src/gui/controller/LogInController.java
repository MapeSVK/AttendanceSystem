
package gui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;





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
    }
        
        
        
    private void changeScene(String view,Event event) throws IOException {
        
        // Here goes method for login automatically and it checks username after start app and figured out if it is 
        // teacher or student

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/"+view+"View.fxml"));
        if (view == "Student") {
            stage.setMinWidth(251);
            stage.setMaxWidth(251);
            stage.setMinHeight(356);
            stage.setMaxHeight(356);
        }
        else if (view == "Teacher") {
            stage.setMinWidth(544);
            stage.setMaxWidth(544);
            stage.setMinHeight(600);
            stage.setMaxHeight(600);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(view);
        stage.show();
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
            changeScene("Student",event); 
            
        }
        else if(loginField.getText().equals("Teacher")&&passwordField.getText().equals("qwerty"))
        {
            changeScene("Teacher",event);    
        }
        else
        {
            inLabel.setVisible(true);
            
        }
    }
    
*/
}