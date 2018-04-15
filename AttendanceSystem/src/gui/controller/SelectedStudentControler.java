package gui.controller;

import gui.model.ModelManager;
import be.Attendance;
import be.Student;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SelectedStudentControler implements Initializable {
    
    @FXML
    private TableColumn<Attendance, Date> dateColumn;
    @FXML
    private TableColumn<Attendance, String> attendanceColumn;
    
    @FXML
    private Label name;
    @FXML
    private Label percentage;
    @FXML
    private Label takenL;
    @FXML
    private Label skippedDay;
    @FXML
    private TableView<Attendance> studentTable;
    @FXML
    private TableColumn<Attendance, String> changeAttendanceColumn;
    
    private Student student;
    private ModelManager model;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private JFXComboBox<Integer> weekBox;
    
    java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
    private Date fromDate;
    private Date toDate;
    private Calendar cal = Calendar.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceImage"));
        changeAttendanceColumn.setCellValueFactory(new PropertyValueFactory<>("changeAttendanceButton"));
    }
    
    public void setStudent(ModelManager model, Student student) {
        this.model = model;
        this.student = student;
        
        setDateFromTo();
        updatePercentageAndLessons();
        updateDateFromTo();
        updateWeek();
        model.changeStatusToImageWithId(student.getId());
        model.showChangeAttendanceButton(studentTable);
        studentTable.setSelectionModel(null);
        fillWeekBox();
        name.setText(student.getFullName());
    }
    
    private void setDateFromTo() {
        dateFrom.setValue(LocalDate.parse("2018-01-01"));
        dateTo.setValue(LocalDate.parse(currentDate.toString()));
        fromDate = java.sql.Date.valueOf(dateFrom.getValue());
        toDate = java.sql.Date.valueOf(dateTo.getValue());
        studentTable.setItems(model.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(model.getAttandanceOfStudent(student.getId()), fromDate, toDate));
    }
    
    private void updatePercentageAndLessons() {
        percentage.textProperty().bind(model.getStudentPercentageInPeriod());
        takenL.textProperty().bind(model.getStudentTakenLessonsInPeriod());
        skippedDay.textProperty().bind(model.getskippedDayProperty());
    }
    
    private void updateDateFromTo() {
        dateFrom.valueProperty().addListener(e -> {
            fromDate = java.sql.Date.valueOf(dateFrom.getValue());
            studentTable.setItems(model.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(model.getAttandanceOfStudent(student.getId()), fromDate, toDate));
            updatePercentageAndLessons();
            model.changeStatusToImageWithId(student.getId());
            model.showChangeAttendanceButton(studentTable);
        });
        dateTo.valueProperty().addListener(e -> {
            toDate = java.sql.Date.valueOf(dateTo.getValue());
            studentTable.setItems(model.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(model.getAttandanceOfStudent(student.getId()), fromDate, toDate));
            updatePercentageAndLessons();
            model.changeStatusToImageWithId(student.getId());
            model.showChangeAttendanceButton(studentTable);
        });
    }
    
    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMinWidth(560);
        stage.setMaxWidth(560);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/TeacherView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Teacher View");
        stage.show();
    }
    
    private void fillWeekBox() {
        cal.setTime(currentDate);
        for (int i = cal.get(Calendar.WEEK_OF_YEAR); i > 0; i--) {
            weekBox.getItems().add(i);
        }
    }
    
    private void updateWeek(){
       
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            weekBox.valueProperty().addListener(e -> {
                cal.set(Calendar.WEEK_OF_YEAR, weekBox.getValue());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                dateFrom.setValue(LocalDate.parse(sdf.format(cal.getTime())));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                dateTo.setValue(LocalDate.parse(sdf.format(cal.getTime())));

            });
    }

}
