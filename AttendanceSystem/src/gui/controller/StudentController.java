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

public class StudentController implements Initializable {

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
    private int animation = 0;
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
        studentTable.setSelectionModel(null);

        
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if (animation == 1) {
            animationMethod(stage);
        }
        stage.minWidthProperty().unbind();
        stage.maxWidthProperty().unbind();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("log In");
        stage.setScene(scene);
        stage.setMinWidth(263);
        stage.setMaxWidth(263);
        stage.setMinHeight(373);
        stage.setMaxHeight(373);
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
        animationMethod(stage);
    }

    private void animationMethod(Stage stage) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        switch (animation) {
                            case 0:
                                for (int i = 251; i < 720; i = i + 2) {
                                    Thread.sleep(2);
                                    updateValue(i);
                                }
                                animation = 1;
                                calendarImg.setImage(img_minus);

                                break;
                            case 1:
                                for (int i = 720; i > 251; i = i - 2) {
                                    Thread.sleep(2);
                                    updateValue(i);
                                }
                                animation = 0;
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

    private void setCurrentAttendanceAndSubmissionLabel() {
        for (Attendance allAttendance : manager.getAttandanceOfStudent(studentId)) {
            if (allAttendance.getDate().equals(currentDate)) {
                currentAttendance = allAttendance;
                submissionLabel.setText(currentAttendance.getStatus());
            }
        }
    }

    private void submissionLabelAndDisableButtonListener() {
        submissionLabel.textProperty().addListener(e -> {
            if (submissionLabel.getText().equals("present")) {
                submissionLabel.setStyle("-fx-text-fill : green");
                attendanceButton.setDisable(true);
            } else if (submissionLabel.getText().equals("absent")) {
                submissionLabel.setStyle("-fx-text-fill : red");
                attendanceButton.setDisable(true);
            }
        });
    }

    private void changeCurrentAttendance() {
        manager.changeStudentAttendance(currentAttendance);
    }

    private void setDateFromTo() {
        dateFrom.setValue(LocalDate.parse("2018-01-01"));
        dateTo.setValue(LocalDate.parse(currentDate.toString()));
        fromDate = Date.valueOf(dateFrom.getValue());
        ToDate = Date.valueOf(dateTo.getValue());
        studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
    }

    private void updateDateFromTo() {
        dateFrom.valueProperty().addListener(e -> {
            fromDate = Date.valueOf(dateFrom.getValue());
            studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
            updatePercentageAndLessons();
        });
        dateTo.valueProperty().addListener(e -> {
            ToDate = Date.valueOf(dateTo.getValue());
            studentTable.setItems(manager.getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(manager.getAttandanceOfStudent(studentId), fromDate, ToDate));
            updatePercentageAndLessons();
        });
    }

    private void updatePercentageAndLessons() {
        percentage.textProperty().bind(manager.getStudentPercentageInPeriod());
        takenL.textProperty().bind(manager.getStudentTakenLessonsInPeriod());
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
