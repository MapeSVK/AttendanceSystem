package gui.controller;

import be.Day;
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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    ModelManager manager = new ModelManager();
    @FXML
    private AnchorPane pane;

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

    public void fakeAnimation(MouseEvent event) throws InterruptedException, IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if(fake==false) {
            stage.setMinWidth(596);
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

        date.setCellValueFactory(new PropertyValueFactory("date"));
        attendance.setCellValueFactory(new PropertyValueFactory("attendance"));

        fill();
    }

    public void logOut(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/LogInView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student");
        stage.setMaxWidth(265);
        stage.setMaxHeight(375);
        stage.setMinWidth(265);
        stage.setMinHeight(375);
        stage.show();
    }

    public void changeAttendance(ActionEvent event)
    {
        submisionLabel.setText("Present");
        submisionLabel.setStyle("-fx-text-fill : limegreen");
        attendanceButton.setDisable(true);
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

    private void fill()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        t=Integer.parseInt(dateFormat.format(date))-1;
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

    private ObservableList<Day> filterDate(String month) throws IOException {
        ObservableList<Day> days = FXCollections.observableArrayList();
        for(Day day : manager.getDays(month))
        {
            try {
                DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateW = dateFormatter.parse(day.getDate());
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
        DateFormat dateFormatte2r = new SimpleDateFormat("dd/MM/yyyy");
        Date curentdate = new Date();
        dateFormatte2r.format(curentdate);
        Calendar caa = Calendar.getInstance();
        caa.setTime(curentdate);
        int dWeek = caa.get(Calendar.WEEK_OF_YEAR);
        return dWeek;
    }
}
