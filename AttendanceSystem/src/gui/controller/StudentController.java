package gui.controller;

import be.Attendance;
import be.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class StudentController implements Initializable{

    @FXML
    private AnchorPane pane;
    @FXML
    private TableView<Attendance> studentTable;
    @FXML
    private TableColumn<Attendance, Date> date;
    @FXML
    private TableColumn<Attendance, String> attendance;
    @FXML
    private Label takenL;
    @FXML
    private Label percentage;
    @FXML
    private JFXButton attendanceButton;
    @FXML
    private ImageView calendarImg;
    @FXML
    private Label studentName;
    private int studentId;
    private int fakeAnimation=0;
    private Date fromDate;
    private Date ToDate;  
    private Calendar cal = Calendar.getInstance();
    
    private final Image img_minus = new Image("file:images/calendar-minus.png");
    private final Image img_plus = new Image("file:images/calendar-plus.png");

    ModelManager manager = new ModelManager();
    
    
    java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
    Attendance currentAttendance;
    @FXML
    private Label submissionLabel;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private JFXComboBox<Integer> weekBox;
    @FXML
    private TableColumn<Attendance, Button> changeAttendanceColumn;
    
    ModelManager model = new ModelManager();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        date.setCellValueFactory(new PropertyValueFactory("date"));
        attendance.setCellValueFactory(new PropertyValueFactory("status"));
        changeAttendanceColumn.setCellValueFactory(new PropertyValueFactory("changeAttendanceButton"));
                
        calendarImg.setImage(img_plus);
        submissionLabelAndDisableButtonListener();
        
                    
    }

    public void getStudentId(int studentId) {
        this.studentId = studentId;
        
      Student student = (Student) manager.returnStudent(studentId);
      studentName.setText(student.getFullName());
       setCurrentAttendanceAndSubmissionLabel();
       
       setDateFromTo();
       updateDateFromTo();
       updatePercentageAndLessons();
       fillWeekBox();
       updateWeek();
       
       /* Something like this need to be here, but it is not working. So take a look and if it 
       is not working just create new showChangeAttendanceButton() method also here. 
       
       model.changeStatusToImageTomek(studentId);
        model.showChangeAttendanceButtonTomek(studentTable);
        studentTable.setSelectionModel(null);
       */
       
       
    }
     
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if(fakeAnimation==1)
        {
            fakeAnimationMethod(stage);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("log In");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void changeAttendance(ActionEvent event) {
        submissionLabel.setText("present");
        manager.changeStudentAttendance(currentAttendance);
       
        manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate);  
        updatePercentageAndLessons();
    }

    @FXML
    private void fakeAnimation(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        fakeAnimationMethod(stage);
    }

    private void fakeAnimationMethod(Stage stage)
    {
        Service service = new Service() {
            @Override
            protected Task createTask() {
               return new Task() {
                   @Override
                   protected Object call() throws Exception {
                       switch(fakeAnimation)
                       {
                           case 0:
                       for(int i=251;i<720;i=i+2)
                {
                       Thread.sleep(2);
                       updateValue(i);     
                }
                       fakeAnimation=1;
                       calendarImg.setImage(img_minus);
                       
                      
                       
                       break;
                           case 1:
                             for(int i=720;i>251;i=i-2)
                {
                       Thread.sleep(2);
                       updateValue(i);     
                }
                             fakeAnimation=0;
                             calendarImg.setImage(img_plus);
                       break;                        
                   }
                       stage.setMinWidth(251);
                       return null;
                   }
               };
            }
        };
        stage.minWidthProperty().bind(service.valueProperty());
        stage.maxWidthProperty().bind(service.valueProperty());
        service.start();
    }
    
    private void setCurrentAttendanceAndSubmissionLabel()
    {
        for(Attendance allAttendance : manager.getAttandanceOfStudent(studentId))
       {
           if(allAttendance.getDate().equals(currentDate))
           {
               currentAttendance=allAttendance;
               submissionLabel.setText(currentAttendance.getStatus());
           }
       }
    }
    
    private void submissionLabelAndDisableButtonListener()
    {
        submissionLabel.textProperty().addListener(e ->{
            if(submissionLabel.getText().equals("present"))
            {
                submissionLabel.setStyle("-fx-text-fill : green");
               attendanceButton.setDisable(true);
            }
            else if(submissionLabel.getText().equals("absent"))
                    {
                         submissionLabel.setStyle("-fx-text-fill : red");
                          attendanceButton.setDisable(true);
                    }
        });
    }

    private void changeCurrentAttendance()
    {
        manager.changeStudentAttendance(currentAttendance);
    }
    
    private void setDateFromTo()
    {
        dateFrom.setValue(LocalDate.parse("2018-01-01"));
       dateTo.setValue(LocalDate.parse(currentDate.toString()));
       fromDate = Date.valueOf(dateFrom.getValue()); 
       ToDate = Date.valueOf(dateTo.getValue());
       studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
    }
    
    private void updateDateFromTo()
    {
        dateFrom.valueProperty().addListener(e ->{
           fromDate = Date.valueOf(dateFrom.getValue());   
           studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
     updatePercentageAndLessons();
        });
        dateTo.valueProperty().addListener(e ->{
            ToDate = Date.valueOf(dateTo.getValue());  
           studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
           updatePercentageAndLessons();
        });
    }
    
    private void updatePercentageAndLessons()
    {
        percentage.textProperty().bind(manager.getStudentPercentageInPeriod());
        takenL.textProperty().bind(manager.getStudentTakenLessonsInPeriod());
    }
    
    private void fillWeekBox()
    {
        cal.setTime(currentDate);
       for(int i=cal.get(Calendar.WEEK_OF_YEAR);i>0;i--)
       {
           weekBox.getItems().add(i);
       }
    }
    
    private void updateWeek() throws NullPointerException
    {
try{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        weekBox.valueProperty().addListener(e ->{
            cal.set(Calendar.WEEK_OF_YEAR, weekBox.getValue());
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            dateFrom.setValue(LocalDate.parse(sdf.format(cal.getTime())));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            dateTo.setValue(LocalDate.parse(sdf.format(cal.getTime())));
     
        });
}
catch(java.lang.NullPointerException n)
{
    
}
    }
    
    
    
    
    
    
    
   /****************** PROTOTYPE METHODS *****************/
    
    /*private String[] months = new String[12];
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
*/
}
