package gui.controller;

import be.Day;
import be.Student;
import gui.model.ModelManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import java.io.IOException;


public class SelectedStudentControler {

    @FXML
    private TableColumn<Day, String> dateColumn;

    @FXML
    private TableColumn<Day, String> attendanceColumn;

    @FXML
    private TableColumn<?, ?> changeAttendanceColumn;

    @FXML
    private Label name;

    @FXML
    private Label month;

    @FXML
    private Label takenL;

    @FXML
    private Label percentage;

    @FXML
    private Label skuppedDay;

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
