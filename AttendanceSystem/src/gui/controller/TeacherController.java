package gui.controller;

import be.Attendance;
import be.Student;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TeacherController implements Initializable {

    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Attendance, String> attendanceColumn;

    @FXML
    private Label dateLabel;
    @FXML
    private JFXComboBox<String> classBox;
    @FXML
    private JFXTextField searchField;

    @FXML
    private TableColumn<Student, ImageView> photoColumn;
    @FXML
    private TableColumn<Student, Button> changeAttendanceColumn;

    ModelManager model = new ModelManager();
    @FXML
    private Label date;
    DateFormat dateFormatterFull = new SimpleDateFormat("dd.MM.yyyy");
    String currentDate = dateFormatterFull.format(new Date());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoColumn.setCellValueFactory(new PropertyValueFactory("photo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendanceImage"));
        changeAttendanceColumn.setCellValueFactory(new PropertyValueFactory("changeAttendanceButton"));

        showStudents();
        changeAttendanceTeacher();

        date.setText(currentDate);

        classBox.getItems().addAll("CS2017_B");
        classBox.getSelectionModel().selectFirst();

    }

    private void showStudents() {
        studentsTable.setItems(model.getSortedStudents());
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
    }

    @FXML
    public void clickStudent(MouseEvent event) throws IOException {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && !event.isConsumed() && selectedStudent != null) {

            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SelectedStudentView.fxml"));
            root = loader.load();
            SelectedStudentControler controller = loader.getController();
            controller.setStudent(model, selectedStudent);
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
    /**
     * ************* PROTOTYPE METHODS *************
     */
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
