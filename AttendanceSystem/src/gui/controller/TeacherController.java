package gui.controller;

import be.Attendance;
import be.Student;
import be.TodayStudents;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TeacherController implements Initializable {

    @FXML
    private TableView<TodayStudents> studentsTable;
    @FXML
    private TableColumn<TodayStudents, String> nameColumn;
    @FXML
<<<<<<< HEAD
    private TableColumn<Attendance, String> attendanceColumn;

=======
    private TableColumn<TodayStudents, String> attendanceColumn;
    
   
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
    @FXML
    private Label dateLabel;
    @FXML
    private JFXComboBox<String> classBox;
    @FXML
    private JFXTextField searchField;

    @FXML
    private TableColumn<TodayStudents, ImageView> photoColumn;
    @FXML
<<<<<<< HEAD
    private TableColumn<Student, Button> changeAttendanceColumn;

=======
    private TableColumn<TodayStudents, Button> changeAttendanceColumn;
    
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
    ModelManager model = new ModelManager();
    @FXML
    private Label date;
    DateFormat dateFormatterFull = new SimpleDateFormat("dd.MM.yyyy");
    String currentDate = dateFormatterFull.format(new Date());
<<<<<<< HEAD
=======
    @FXML
    private Button showStatisticsButton;
    
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoColumn.setCellValueFactory(new PropertyValueFactory("photo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendanceImage"));
<<<<<<< HEAD
        changeAttendanceColumn.setCellValueFactory(new PropertyValueFactory("changeAttendanceButton"));

        showStudents();
        changeAttendanceTeacher();

        date.setText(currentDate);

        classBox.getItems().addAll("CS2017_B");
        classBox.getSelectionModel().selectFirst();

    }

    private void showStudents() {
        studentsTable.setItems(model.getSortedStudents());
=======
        changeAttendanceColumn.setCellValueFactory( new PropertyValueFactory("changeAttendanceButton"));
        
        model.loadTodayStudents();
        showStudents();
        changeAttendanceButton();
        setPhotosAndStatusImages();
        
        
        //changeAttendanceTeacher();
        
        
        date.setText(currentDate);        
        classBox.getItems().addAll("CS2017_B");
        classBox.getSelectionModel().selectFirst();    
        showStatisticsButton.setText("M\nO\nR\nE");
    }
   
   public void setPhotosAndStatusImages(){
       for (TodayStudents ts : model.getAllTodayStudents()) {
           ts.getPhoto().setImage(new Image("file:"+ts.getImageLink()));
           model.changeStatusToImage(ts);
       }
   }
   
    
    
    public void showStudents()
    {
     studentsTable.setItems(model.getSortedStudents());
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
    }

    public void presentFirst() {

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
<<<<<<< HEAD

    public void changeAttendanceTeacher() {
        for (Student st : model.getAllStudentsWithStatus()) {
            st.getChangeAttendanceButton().getStyleClass().clear();
            st.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");

            st.getPhoto().setImage(new Image("file:" + st.getImageLink()));

            model.changeStatusToImage(st);

            st.getChangeAttendanceButton().setOnAction((event) -> {

                /* NOT WORKING */
//                            for (Attendance att : model.getAllStudentsAttendance()) {
//                                if (att.getStudentId() == st.getId() && st.getStatus().equals("absent")) {
//                                 
//                                        att.setStatus("present");
//                                        model.editStatus(att);
//                                    
//                                }
//                            }
            });
        }
=======
    
    public void changeAttendanceButton() {
      
        studentsTable.setRowFactory(tableView -> {
        final TableRow<TodayStudents> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final TodayStudents todayStudents = row.getItem();
                row.setStyle("-fx-background-color:white;");
                
                for (TodayStudents ts : model.getAllTodayStudents()) {
                    
                    ts.getChangeAttendanceButton().getStyleClass().clear();
                    ts.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");

                    
                    if (row.isHover() && todayStudents == ts) {
                        
                        ts.getChangeAttendanceButton().setVisible(true);
                        row.setStyle("-fx-background-color:#000;-fx-opacity: 0.7;");  
                        
                    } 
                    else {   
                        ts.getChangeAttendanceButton().setVisible(false);              
                        
                    }
                    ts.getChangeAttendanceButton().setOnAction((event) -> {
                        if (ts.getStatus().equals("present")) {
                            ts.setStatus("absent");
                            
                            
                            
                        }
                        else if (ts.getStatus().equals("absent")){
                            ts.setStatus("present");
                            
                        }
                    });
                }               
            });
       return row;
        });
        
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
    }

    @FXML
    public void clickStudent(MouseEvent event) throws IOException {
<<<<<<< HEAD
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && !event.isConsumed() && selectedStudent != null) {

=======
        
        TodayStudents selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
     
        Student student = null;
        for (Student s : model.getAllStudents()) {
            
            
            if (s.getFullName().equals(selectedStudent.getFullName())) {
                student = new Student(s.getId(),s.getFirstName(),s.getLastName(),s.getImageLink(),s.getEmail(), s.getClassId());
                
            }
        }
        
        
       
        
       if (event.getClickCount() == 2 && !event.isConsumed() && selectedStudent!=null)
       {    
            
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SelectedStudentView.fxml"));
            root = loader.load();
            SelectedStudentControler controller = loader.getController();
<<<<<<< HEAD
            controller.setStudent(model, selectedStudent);
=======
            controller.setStudent(model,student);
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMinWidth(574);
            stage.setMaxWidth(574);
            stage.setMinHeight(600);
            stage.setMaxHeight(600);
            stage.setTitle(selectedStudent.getFullName());
            stage.show();

        }
    }

//    private void searchStudent()
//    {
//        searchField.textProperty().addListener(e->{
//            studentsTable.getItems().clear();
//            try {
//                for(Student st : model.getAllStudentsWithStatus())
//                {
//                    if(st.getFullName().toLowerCase().contains(searchField.getText().toLowerCase()) )                   
//                            
//                    {
//                        //
//                    }
//                }
//            } catch (IOException ex) {
//            }
//        });
//    }
<<<<<<< HEAD
    /**
     * ************* PROTOTYPE METHODS *************
     */
=======

    @FXML
    private void showStatisticsButtonClick(ActionEvent event) {
    }
    
    
    
    

    /*************** PROTOTYPE METHODS **************/
>>>>>>> 6de1c512199304583994def1b57ee9ebbf5f1334
    /*
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
     */
}
