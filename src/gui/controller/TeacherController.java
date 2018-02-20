package gui.controller;

import be.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.model.ModelManager;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> attendanceColumn;

    @FXML
    private TableColumn<Student, String> percentageColumn;

    @FXML
    private TableColumn<Student, String> takenLessonsColumn;

    @FXML
    private JFXButton logOutButton;

    @FXML
    private JFXComboBox<String> classBox;

    @FXML
    private Label dateLabel;

    @FXML
    private JFXTextField searchField;

    ModelManager manager = new ModelManager();

    Date currentDate = new Date();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory("attendance"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory("percentage"));
        takenLessonsColumn.setCellValueFactory(new PropertyValueFactory("takenLessons"));

        try {
            loadStudents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillBoxAndSetDate();
    }

    @FXML
    void logOut(ActionEvent event) {
    }

    private void fillBoxAndSetDate()
    {
        classBox.getItems().clear();
        classBox.getItems().add("CS2017B");
        classBox.getSelectionModel().selectFirst();
        dateLabel.setText(" "+dateFormatterFull.format(currentDate));
    }

    private void loadStudents() throws IOException {
        studentsTable.setItems(manager.getStudents());
    }

    public void clickStudent(MouseEvent event) throws IOException {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();

       if (event.getClickCount() == 2 && !event.isConsumed() && selectedStudent!=null)
       {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SelectedStudentView.fxml"));
            root = loader.load();
            SelectedStudentControler controller = loader.getController();
            controller.setStudentId(selectedStudent.getMonths());
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("SelectedStudent");
            stage.show();


        }
    }
}
