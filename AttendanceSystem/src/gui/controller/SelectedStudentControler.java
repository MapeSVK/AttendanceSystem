
package gui.controller;

import gui.model.ModelManager;
import be.Attendance;
import be.Student;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;


public class SelectedStudentControler implements Initializable {

    @FXML
    private TableColumn<Attendance, Date> dateColumn;
    @FXML
    private TableColumn<Attendance, String> attendanceColumn;
    
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
    private TableView<Attendance> studentTable;
    @FXML
    private TableColumn<Attendance, String> changeAttendanceColumn;
    
    
    private Student student;
    private ModelManager model;
    
    private final Image present = new Image("file:images/presentImage.png");
    private final Image absent = new Image("file:images/absentImage.png");
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceImage"));    
        changeAttendanceColumn.setCellValueFactory( new PropertyValueFactory<>("changeAttendanceButton"));
        
        
    
    }    
    
    public void setStudent(ModelManager model, Student student){
        this.model = model;
        this.student = student;
        
        
        getDatesOfSelectedStudent();
        changeStatusToImage();
        showChangeAttendanceButton();
        studentTable.setSelectionModel(null);
    }
    
    public void getDatesOfSelectedStudent() {
        
        studentTable.setItems(model.getAttandanceOfStudent(student.getId()));
        
         
    }
    
    public void changeStatusToImage() {
        for (Attendance att : model.getAttandanceOfStudent(student.getId())) {
            if (att.getStatus().equals("absent")) {
                att.getAttendanceImage().setImage(absent);
                
                
                
            }
            else if(att.getStatus().equals("present")) {
                
                att.getAttendanceImage().setImage(present);
                
            }
        }
    }
    
    
    public void showChangeAttendanceButton() {
        studentTable.setRowFactory(tableView -> {
        final TableRow<Attendance> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Attendance attendance = row.getItem();
                row.setStyle("-fx-background-color:white;");
                
                for (Attendance att : model.getAttendanceOfOneStudent()) {
                    
                    att.getChangeAttendanceButton().getStyleClass().clear();
                    att.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");

                    
                    if (row.isHover() && attendance == att) {
                        
                        att.getChangeAttendanceButton().setVisible(true);
                        row.setStyle("-fx-background-color:#000;-fx-opacity: 0.7;");  
                        
                    } 
                    else {   
                        att.getChangeAttendanceButton().setVisible(false);              
                        
                    }
                    att.getChangeAttendanceButton().setOnAction((event) -> {
                        System.out.println(att.getStatus());
                    });
                }               
            });
       return row;
        });
        
        
    }
    
    
//    public void colouredRows() {       
//        studentTable.setRowFactory(tableView -> new TableRow<Attendance>() {
//            
//            @Override
//            protected void updateItem(Attendance attendance, boolean empty) {
//                super.updateItem(attendance, empty);
//                if (empty) {
//                    setStyle("");
//                } else if (attendance.getStatus().equals("present")) {
//                    setStyle("-fx-background-color:green;");
//                } else if (attendance.getStatus().equals("absent")) {
//                    setStyle("-fx-background-color:red;");
//                } else {
//                    setStyle("-fx-background-color:white;");
//                }
//            }
//        });       
//    } 
    
    
    

    
    
    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMinWidth(544);
        stage.setMaxWidth(544);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/TeacherView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Teacher View");
        stage.show();
    }

  

    @FXML
    private void leftM(MouseEvent event) {
    }

    @FXML
    private void rightM(MouseEvent event) {
    }
    
    
    
    
    
    
    
 /***************** PROTOTYPE METHODS ******************/
    
    /*
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
this.studentIde=studentId;
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendance"));
           fill(); 
        changeLabel();
        setName();
        updateStudent();
        month.setText(months[t]);
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
    
    private void setName() throws IOException
    {
        for(Student student : manager.getStudents())
        {
           if(student.getMonths().equals(studentIde)) 
           name.setText(student.getName());
        }
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
    private ObservableList<Attendance> filterDate(String month) throws IOException {
        ObservableList<Attendance> days = FXCollections.observableArrayList();

        setDaysInWeek();

        for(Attendance day : manager.getDays(month))
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
        if(t>0) {
            t--;
            month.setText(months[t]);
        }
        else
        {
            t=11;
            month.setText(months[t]);
        }
        changeLabel();
    }

    @FXML
    private void rightM(MouseEvent event) {
        if(t<11) {
            t++;
            month.setText(months[t]);
        }
        else
        {
            t=0;
            month.setText(months[t]);
        }
     changeLabel();
    }
    
    private void updateStudent() throws IOException {
        List<Student> studentList = new ArrayList();
        studentList.addAll(manager.getStudents());
        String currentAttendance="";

        for(Day day : manager.getDays(studentIde+months[t]))
        {
            if(day.getDate().equals(dateFormatterFull.format(currentDate)))
            {
                 currentAttendance = day.getAttendance();
            }
        }
        for(Student studente : studentList)
        {
            if(studente.getName().equals(name.getText()))
            {
                studente.setAttendance(currentAttendance);
                studente.setPercentage(""+percentage.getText());
                studente.setTakenLessons(takenL.getText());
                studente.setSkippedDay(skippedDayResult);
                studente.setMonths(studentIde);
            }
        }
        manager.updateStudent(studentList,"Students");
    }

    
    }
    @FXML
    private void changeAttendance(MouseEvent event) throws IOException {
         Day selectedDay = studentTable.getSelectionModel().getSelectedItem();
        List<Day> justList = new ArrayList();
       if (event.getClickCount() == 2 && !event.isConsumed() && selectedDay!=null)
       {
           for(Day day : manager.getDays(studentIde+months[t]))
           {
               if(day.getDate().equals(selectedDay.getDate()))
               {
                 if(day.getAttendance().equals("false"))  
                 {
                   day.setAttendance("true");
                    justList.add(day);
                 }
                 else if(day.getAttendance().equals("true"))
                 {
                    day.setAttendance("false");
                     justList.add(day);
                 }
           }
               else
               {
                  justList.add(day); 
               }
       }
           manager.updateMonth(justList,studentIde+months[t]);
           changeLabel();
           updateStudent();
    }
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
    */
    
    
}
