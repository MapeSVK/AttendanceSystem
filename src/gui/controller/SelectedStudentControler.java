/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import be.Day;
import be.Student;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class SelectedStudentControler implements Initializable {

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
    private Label percentage;
    @FXML
    private Label takenL;
    @FXML
    private Label skuppedDay;
    @FXML
    private TableView<Day> studentTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    int[] daysInWeek = new int[5];
    String[] skippedDay= new String[5];
    int max=0;
    private String studentIde="";
    DateFormat dateFormatterFull = new SimpleDateFormat("dd/MM/yyyy");

     ModelManager manager = new ModelManager();

     private int t;
     private int present=0;
     private int allDays=0;
     private int weekOfYeat=0;
    private String[] months = new String[12];
    String skippedDayResult="";
    Date currentDate = new Date();
    DateFormat dateFormatterMonth = new SimpleDateFormat("MM");

    public void setStudentId(String studentId) throws IOException {

        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendance"));
            
        this.studentIde=studentId;
        changeLabel();     
    }
    public void fill()
    {
        month.setText(months[t]);
            t=Integer.parseInt(dateFormatterMonth.format(currentDate))-1;
        months[0]="January";
        months[1]="February";
        months[2]="March";
        months[3]="April";
        months[4]="May";
        months[5]="June";
        months[6]="July";
        months[7]="August";
        months[8]="September";
        months[9]="October";
        months[10]="November";
        months[11]="December";
        changeLabel();
    }
    private void setDaysInWeek()
    {
        skippedDay[0]="Monday";
        skippedDay[1]="Tuesday";
        skippedDay[2]="Wednesday";
        skippedDay[3]="Thursday";
        skippedDay[4]="Friday";
        max=0;
        for(int i=0;i<5;i++)
        {
            daysInWeek[i]=0;
        }
    }

    private void checkForSkippedDay(int dayNumber)
    {

        if(dayNumber==2)
            daysInWeek[0]++;
        else if(dayNumber==3)
            daysInWeek[1]++;
        else if(dayNumber==4)
            daysInWeek[2]++;
        else if(dayNumber==5)
            daysInWeek[3]++;
        else if(dayNumber==6)
            daysInWeek[4]++;

        for (int i = 0; i < 5; i++)
        {
            if (daysInWeek[i] > max)
            {
                max =daysInWeek[i];
            }
        }
        skippedDayResult = skippedDay[max];
    }
    private int getWeekInt()
    {
        dateFormatterFull.format(currentDate);
        Calendar caa = Calendar.getInstance();
        caa.setTime(currentDate);
        int dWeek = caa.get(Calendar.WEEK_OF_YEAR);
        return dWeek;
    }
    
    private void changeLabel()
    {

        try {
            studentTable.setItems(filterDate(studentIde+months[t]));
            percentage.setText(""+(present*100)/allDays+" %");
            takenL.setText(weekOfYeat+"/5");
            skuppedDay.setText(skippedDayResult);
            allDays=0;
            present=0;
            weekOfYeat=0;
        } catch (IOException e) {
            studentTable.getItems().clear();
            percentage.setText(""+0+" %");
            takenL.setText("0/5");
        }
    }
    private ObservableList<Day> filterDate(String month) throws IOException {
        ObservableList<Day> days = FXCollections.observableArrayList();

        setDaysInWeek();

        for(Day day : manager.getDays(month))
        {
            try {
                Date dateW = dateFormatterFull.parse(day.getDate());
                Calendar c = Calendar.getInstance();
                c.setTime(dateW);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);

                if(dayOfWeek!=7 && dayOfWeek!=1)
                {

                    allDays++;
                    if(day.getAttendance().equals("true"))
                    {
                        present++;
                        if(weekOfYear==getWeekInt())
                        {
                            weekOfYeat++;
                        }
                        day.setAttendance("present");
                        days.add(day);
                    }
                    else if (day.getAttendance().equals("false"))
                    {
                        day.setAttendance("absent");
                        days.add(day);
                        checkForSkippedDay(dayOfWeek);
                    }
                    else if (day.getAttendance().equals("not submitted"))
                    {
                        day.setAttendance("not submitted");
                        days.add(day);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return days;
    }

    @FXML
    private void leftM(MouseEvent event) {
    }

    @FXML
    private void rightM(MouseEvent event) {
    }
}
