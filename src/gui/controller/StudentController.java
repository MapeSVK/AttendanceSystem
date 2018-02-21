package gui.controller;

import be.Day;
import be.Student;
import com.jfoenix.controls.JFXButton;
import gui.model.ModelManager;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentController implements Initializable {

    ModelManager manager = new ModelManager();

    @FXML
    private Label submisionLabel;

    @FXML
    private Label takenL;

    @FXML
    private JFXButton attendanceButton;

    @FXML
    private Label percentage;

    @FXML
    private Label monthName;

    @FXML
    private TableView<Day> studentTable;

    @FXML
    private TableColumn<Day, String> date;

    @FXML
    private TableColumn<Day, String> attendance;

    private String[] months = new String[12];
    private int t;
    private int present=0;
    private int allDays=0;
    private int weekOfYeat=0;
    private boolean fake = false;
    Date currentDate = new Date();
    DateFormat dateFormatterMonth = new SimpleDateFormat("MM");
    DateFormat dateFormatterFull = new SimpleDateFormat("dd/MM/yyyy");
    int month = Integer.parseInt(dateFormatterMonth.format(currentDate));
    int[] daysInWeek = new int[5];
    String[] skippedDay= new String[5];
    int max=0;
    String skippedDayResult="";

    public void fakeAnimation(MouseEvent event) throws InterruptedException, IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if(fake==false) {
            stage.setMaxWidth(596);
            fake=true;
        }
        else
        {
            stage.setMinWidth(265);
            stage.setMaxWidth(265);
            fake=false;
        }
 }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            date.setCellValueFactory(new PropertyValueFactory("date"));
            attendance.setCellValueFactory(new PropertyValueFactory("attendance"));

            manager.fill(months);
            monthName.setText(months[t]);
            t=Integer.parseInt(dateFormatterMonth.format(currentDate))-1;

            updateAttendance();
            changeLabel();
            checkForSubmission();
            updateStudent();

        } catch (IOException e) { e.printStackTrace(); }
        catch (ParseException e) { e.printStackTrace();}

    }

    public void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Log Out");
        stage.show();
    }

    public void changeAttendance(ActionEvent event) throws IOException {
        List<Day> myList = new ArrayList();
        submisionLabel.setText("Present");
        submisionLabel.setStyle("-fx-text-fill : limegreen");
       attendanceButton.setDisable(true);

        for(Day day : manager.getDays(months[month-1]))
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
        manager.updateMonth(myList,months[month-1]);
        updateStudent();
        changeLabel();
        changeLabel();
    }

    public void rightM(MouseEvent event){
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

        iLikeToSing.addAll(manager.getDays(months[t]));
        iLikeToDance.addAll(manager.getDays(months[t]));

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

            manager.updateMonth(iLikeToDance, months[t]);
        }
    }

    private void checkForSubmission() throws IOException {
        for(Day day : filterDate(months[t]))
        {
            if(day.getAttendance().equals("not submitted"))
            {
                attendanceButton.setDisable(false);
            }
            else
            {
                attendanceButton.setDisable(true);
            }
        }
    }

    private void updateStudent() throws IOException {
        List<Student> studentList = new ArrayList();
        studentList.addAll(manager.getStudents());
        String currentAttendance="";

        for(Day day : manager.getDays(months[month-1]))
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
                studente.setPercentage(""+(present*100)/allDays);
                studente.setTakenLessons(""+weekOfYeat);
                studente.setSkippedDay(skippedDayResult);
                studente.setMonths("T");
            }
        }
        manager.updateStudent(studentList,"Students");
    }
}
