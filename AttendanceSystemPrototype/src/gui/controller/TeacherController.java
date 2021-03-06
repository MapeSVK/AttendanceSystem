/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import be.Student;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TeacherController implements Initializable {

    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> attendanceColumn;
    @FXML
    private TableColumn<Student, String> percentageColumn;
    @FXML
    private TableColumn<Student, String> takenLessonsColumn;
    @FXML
    private Label dateLabel;
    @FXML
    private JFXComboBox<String> classBox;
    @FXML
    private JFXTextField searchField;

    /**
     * Initializes the controller class.
     */
    ModelManager manager = new ModelManager();

    Date currentDate = new Date();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendance"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory("percentage"));
        takenLessonsColumn.setCellValueFactory(new PropertyValueFactory("takenLessons"));
        try {
            sort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillBoxAndSetDate();
        searchStudent();
        
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMinWidth(251);
        stage.setMaxWidth(251);
        stage.setMinHeight(356);
        stage.setMaxHeight(356);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Log Out");
        stage.show();
    }

    private void fillBoxAndSetDate()
    {
        classBox.getItems().clear();
        classBox.getItems().add("CS2017B");
        classBox.getSelectionModel().selectFirst();
        dateLabel.setText(" "+dateFormatterFull.format(currentDate));
    }

    private void ifAttendance(Student student)
    {
        if(student.getAttendance().equals("false"))
            {
                student.setAttendance("absent");
                studentsTable.getItems().add(student);
            }
            else if(student.getAttendance().equals("true"))
                    {
                     student.setAttendance("present");
                studentsTable.getItems().add(student);   
                    }
            else
            {
             studentsTable.getItems().add(student);   
            }
    }

    @FXML
    public void clickStudent(MouseEvent event) throws IOException {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();

       if (event.getClickCount() == 2 && !event.isConsumed() && selectedStudent!=null)
       {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SelectedStudentView.fxml"));
            root = loader.load();
            SelectedStudentControler controller = loader.getController();
            controller.setStudentId(selectedStudent.getMonths());
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMinWidth(574);
            stage.setMaxWidth(574);
            stage.setMinHeight(600);
            stage.setMaxHeight(600);
            stage.setTitle("SelectedStudent");
            stage.show();


        }
    }
    
    private void searchStudent()
    {
        searchField.textProperty().addListener(e->{
            studentsTable.getItems().clear();
            try {
                for(Student student : manager.getStudents())
                {
                    if(student.getName().toLowerCase().contains(searchField.getText().toLowerCase())                    
                            )
                    {
                        ifAttendance(student);
                    }
                }
            } catch (IOException ex) {
            }
        });
    }
    private void sort() throws IOException           
    {
        studentsTable.getItems().clear();
        for(int i=0;i<=100; i++)
        {
        for(Student student : manager.getStudents())
        {
            if(student.getPercentage().equals(i+" %"))
                ifAttendance(student);
        }
        }
    }
    
}
