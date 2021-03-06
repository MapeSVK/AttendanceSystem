/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import be.Day;
import be.Student;
import com.jfoenix.controls.JFXButton;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class StudentController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label submisionLabel;
    @FXML
    private TableView<Day> studentTable;
    @FXML
    private TableColumn<Day, String> date;
    @FXML
    private TableColumn<Day, String> attendance;
    @FXML
    private Label takenL;
    @FXML
    private Label percentage;
    @FXML
    private Label monthName;
    @FXML
    private JFXButton attendanceButton;
    
    private final Image img_minus = new Image("file:images/calendar-minus.png");
    private final Image img_plus = new Image("file:images/calendar-plus.png");

    

    /**
     * Initializes the controller class.
     */
    ModelManager manager = new ModelManager();
    private String[] months = new String[12];
    private int t;
    private int present=0;
    private int allDays=0;
    private int weekOfYeat=0;
    private boolean fake = true;
    Date currentDate = new Date();
    DateFormat dateFormatterMonth = new SimpleDateFormat("MM");
    DateFormat dateFormatterFull = new SimpleDateFormat("dd/MM/yyyy");
    int month = Integer.parseInt(dateFormatterMonth.format(currentDate));
    int[] daysInWeek = new int[5];
    String[] skippedDay= new String[5];
    int max=0;
    String skippedDayResult="";
    @FXML
    private ImageView calendarImg;

    @FXML
    public void fakeAnimation(MouseEvent event) throws InterruptedException, IOException {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if(fake==false) {
            calendarImg.setImage(img_plus);
            stage.setMinWidth(251);
            stage.setMaxWidth(251);
            stage.setMinHeight(356);
            stage.setMaxHeight(356);
            fake=true;
            
        }
        else
        {
            calendarImg.setImage(img_minus);
            stage.setMinWidth(700);
            stage.setMaxWidth(700);
            stage.setMinHeight(356);
            stage.setMaxHeight(356);
            fake=false;
        }
        
        
 }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            date.setCellValueFactory(new PropertyValueFactory("date"));
            attendance.setCellValueFactory(new PropertyValueFactory("attendance"));
            calendarImg.setImage(img_plus);
           
            fill();
            updateAttendance();
            changeLabel();
            checkForSubmission();
            updateStudent();
            leftM();
            rightM();
            
            
      
            
           
            
            

        } catch (IOException e) { e.printStackTrace(); }
        catch (ParseException e) { e.printStackTrace();}

    }
    
    
    

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMinWidth(251);
        stage.setMaxWidth(251);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Log Out");
        stage.show();
    }

    @FXML
    public void changeAttendance(ActionEvent event) throws IOException {
        List<Day> myList = new ArrayList();
        submisionLabel.setText("Present");
        submisionLabel.setStyle("-fx-text-fill :#347C17");
       attendanceButton.setDisable(true);

        for(Day day : manager.getDays("T"+months[month-1]))
        {
            if(day.getAttendance().equals("not submitted"))
            {
                day.setAttendance("true");
                myList.add(day);

            }
            else
            {
                myList.add(day);
            }
        }
        manager.updateMonth(myList,"T"+months[month-1]);
        changeLabel();
        changeLabel();
        updateStudent();

    }
    public void fill()
    {
        
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
        monthName.setText(months[t]);
        changeLabel();
    }

    @FXML
    public void rightM(){
        if(t<11) {
            t++;
            monthName.setText(months[t]);
        }
        else
        {
            t=0;
            monthName.setText(months[t]);
        }
     changeLabel();
    }

    @FXML
    public void leftM(){
        if(t>0) {
            t--;
            monthName.setText(months[t]);
        }
        else
        {
            t=11;
            monthName.setText(months[t]);
        }
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
    private ObservableList<Day> filterDate(String month) throws IOException {
        ObservableList<Day> days = FXCollections.observableArrayList();

        setDaysInWeek();

        for(Day day : manager.getDays("T"+month))
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

    private void changeLabel()
    {

        try {
            studentTable.setItems(filterDate(months[t]));
            percentage.setText(""+(present*100)/allDays+" %");
            takenL.setText(weekOfYeat+"/5");
            allDays=0;
            present=0;
            weekOfYeat=0;
        } catch (IOException e) {
            studentTable.getItems().clear();
            percentage.setText(""+0+" %");
            takenL.setText("0/5");
        }
    }

    private int getWeekInt()
    {
        dateFormatterFull.format(currentDate);
        Calendar caa = Calendar.getInstance();
        caa.setTime(currentDate);
        int dWeek = caa.get(Calendar.WEEK_OF_YEAR);
        return dWeek;
    }

    private void updateAttendance() throws IOException, ParseException {

        List<Day> iLikeToSing = new ArrayList();
        List<Day> iLikeToDance = new ArrayList();

        iLikeToSing.addAll(manager.getDays("T"+months[t]));
        iLikeToDance.addAll(manager.getDays("T"+months[t]));

        DateFormat dateFormatterDay = new SimpleDateFormat("dd");
        DateFormat dateFormatterYear = new SimpleDateFormat("YYYY");

        String newDateMonth = dateFormatterMonth.format(currentDate);
        String newDateYear = dateFormatterYear.format(currentDate);

        Date lastDate = dateFormatterDay.parse(iLikeToSing.get(iLikeToSing.size()-1).getDate());

        int currentDay = Integer.parseInt(dateFormatterDay.format(currentDate));
        int lastDateDay = Integer.parseInt(dateFormatterDay.format(lastDate));

        String newDate = "/" + newDateMonth + "/" + newDateYear;

        if(lastDateDay<currentDay) {

            for (int i = lastDateDay + 1; i < currentDay; i++) {
                Day day = new Day(i + newDate, "false");
                iLikeToDance.add(day);

            }

            iLikeToDance.add(new Day(currentDay + newDate, "not submitted"));

            manager.updateMonth(iLikeToDance, "T"+months[t]);
        }
    }

    private void checkForSubmission() throws IOException {
        for(Day day : filterDate(months[t]))
        {
            if(day.getAttendance().equals("not submitted"))
            {
                attendanceButton.setDisable(false);
                submisionLabel.setText("not submitted");
                submisionLabel.setStyle("-fx-text-fill : grey");    
            }
            
            else if (day.getAttendance().equals("present"))
            {
                attendanceButton.setDisable(true);
                submisionLabel.setText("Present");
                submisionLabel.setStyle("-fx-text-fill :  #347C17"); 
                
            }
            else
            {
                attendanceButton.setDisable(true);
                submisionLabel.setText("Absent");
                submisionLabel.setStyle("-fx-text-fill :  red"); 
               
                
            }
            
            
        }
    }

    private void updateStudent() throws IOException {
        List<Student> studentList = new ArrayList();
        studentList.addAll(manager.getStudents());
        String currentAttendance="";

        for(Day day : manager.getDays("T"+months[month-1]))
        {
            if(day.getDate().equals(dateFormatterFull.format(currentDate)))
            {
                 currentAttendance = day.getAttendance();
            }
        }
        for(Student studente : studentList)
        {
            if(studente.getName().equals("Tomasz Plesniak"))
            {
                studente.setAttendance(currentAttendance);
                studente.setPercentage(""+percentage.getText());
                studente.setTakenLessons(""+weekOfYeat+"/5");
                studente.setSkippedDay(skippedDayResult);
                studente.setMonths("T");
            }
        }
        manager.updateStudent(studentList,"Students");
    }
}
