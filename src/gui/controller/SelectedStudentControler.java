/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import be.Student;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class SelectedStudentControler implements Initializable {

    @FXML
    private TableColumn<Student, String> dateColumn;
    @FXML
    private TableColumn<Student, String> attendanceColumn;
    @FXML
    private TableColumn<?, ?> changeAttendanceColumn;
    @FXML
    private Label name;
    @FXML
    private Label month;
    @FXML
    private Label percentage;
    @FXML
    private Label takenL;
    @FXML
    private Label skuppedDay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private String studentIde="";

     ModelManager manager = new ModelManager();

     private int percentageLabel;
     private int takenLessonsLabel;
     private String nameLabel="";
     private String skippedDayLabel="";
    private String[] months = new String[12];

    public void setStudentId(String studentId) throws IOException {

        this.studentIde=studentId;

        setInformation();
        fillLabels();
        manager.fill(months);
    }

    private void setInformation() throws IOException {

     for(Student student : manager.getStudents())
     {
         if(student.getMonths().equals(studentIde))
         {
             percentageLabel=Integer.parseInt(student.getPercentage().replace("%",""));
             takenLessonsLabel=Integer.parseInt(student.getTakenLessons().replace("/5",""));
             nameLabel=student.getName();
             skippedDayLabel=student.getSkippedDay();
         }
     }
        System.out.println(percentageLabel);
    }

    private void fillLabels()
    {
        name.setText(nameLabel);
        percentage.setText(""+percentageLabel+"%");
        skuppedDay.setText(skippedDayLabel);
        takenL.setText(""+takenLessonsLabel+"/5");
    }

}
