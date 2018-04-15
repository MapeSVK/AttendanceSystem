package gui.model;

import be.Attendance;
import be.Student;
import be.TodayStudents;
import bll.BllManager;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

public class ModelManager implements Initializable {

    private final BllManager manager = new BllManager();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd-MM-yyyy");
    private final Image present = new Image("file:images/presentImage.png");
    private final Image absent = new Image("file:images/absentImage.png");

    SimpleStringProperty percentageProperty = new SimpleStringProperty("tequila");
    SimpleStringProperty takenLessonsProperty = new SimpleStringProperty("lemon");
    SimpleStringProperty skippedDayProperty = new SimpleStringProperty("no");

    private final Calendar cal = Calendar.getInstance();
    private final Integer[] skippedDay = new Integer[8];

    private final ObservableList<Attendance> allAttendance = FXCollections.observableArrayList();
    private final ObservableList<Attendance> attendanceOfOneStudent = FXCollections.observableArrayList();
    private final ObservableList<Student> allStudentsWithStatus = FXCollections.observableArrayList();
    private final ObservableList<TodayStudents> allTodayStudents = FXCollections.observableArrayList();
    private final ObservableList<Attendance> dateFromTo = FXCollections.observableArrayList();
    SortedList<TodayStudents> sortedStudents = new SortedList<>(allTodayStudents, Comparator.comparing(TodayStudents::getStatus).reversed());

    private int presentCounter;
    private int absentCounter;

    public ObservableList<Attendance> getAttandanceOfStudent(int id) {
        attendanceOfOneStudent.clear();
        attendanceOfOneStudent.setAll(manager.getAttandanceOfStudent(id));
        return attendanceOfOneStudent;
    }

    public ObservableList<Attendance> getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(ObservableList<Attendance> allAtendance, Date from, Date to) {
        dateFromTo.clear();
        presentCounter = 0;
        resetArray();
        for (Attendance attendance : allAtendance) {
            if (!attendance.getDate().before((java.util.Date) from) && !attendance.getDate().after((java.util.Date) to)) {
                if (attendance.getStatus().equals("present")) {

                    presentCounter++;
                } else if (attendance.getStatus().equals("absent")) {
                    cal.setTime(attendance.getDate());
                    skippedDay[cal.get(Calendar.DAY_OF_WEEK)]++;
                }

                dateFromTo.add(attendance);
            }
        }
        countSkippedDay();
        if (dateFromTo.size() == 0) {
            percentageProperty.set("0 %");
            takenLessonsProperty.set("0 / 0");
        } else {
            absentCounter = dateFromTo.size() - presentCounter;
            changeProperty();
        }
        return dateFromTo;
    }

    public ObservableList<Attendance> returnCurrentDateFromToList() {
        return dateFromTo;
    }

    public ObservableList<Attendance> getAttendanceOfOneStudent() {
        return attendanceOfOneStudent;
    }

    public void loadTodayStudents() {
        allTodayStudents.clear();
        allTodayStudents.addAll(manager.getTodayStudent());
    }

    public ObservableList<TodayStudents> getAllTodayStudents() {
        return allTodayStudents;
    }

    public void loadAllStudentsAttendance() {

        allAttendance.clear();
        allAttendance.addAll(manager.getAllStudentsAttendance());
    }

    public ObservableList<Student> getAllStudentsWithStatus() {
        getStudentItsCurrentAttendance();

        return allStudentsWithStatus;
    }

    public ObservableList<Attendance> getAllStudentsAttendance() {
        return allAttendance;
    }

    public int getUserId(String username, String password) {
        return manager.getUserId(username, password);
    }

    public Object logIn(int userId) {
        return manager.logIn(userId);
    }

    public List<Student> getAllStudents() {
        return manager.getAllStudents();
    }

    private int setStudentPercentage(int StudentId) {
        int notPresent = 0;
        int present = 0;
        for (Attendance attendance : getAttandanceOfStudent(StudentId)) {
            if (attendance.getStatus().contains("present")) {
                present++;
            } else {
                notPresent++;
            }
        }
        return (present * 100) / (present + notPresent);
    }

    public void getStudentItsCurrentAttendance() {
        allStudentsWithStatus.clear();
        for (Student student : getAllStudents()) {
            for (Attendance attendance : manager.getAllStudentsAttendance()) {
                String currentDate = dateFormatterFull.format(new Date());
                String dateAttendance = dateFormatterFull.format(attendance.getDate());

                if (attendance.getStudentId() == student.getId() && dateAttendance.equals(currentDate)) {
                    student.setPercentage(setStudentPercentage(student.getId()));
                    student.setStatus(attendance.getStatus());

                    allStudentsWithStatus.add(student);

                }

            }
        }
    }

    public Object returnStudent(int userId) {
        return manager.returnStudent(userId);
    }

    public boolean changeStudentAttendance(Attendance attendance) {
        return manager.changeStudentAttendance(attendance);
    }

    public void showChangeAttendanceButton(TableView tw) {
        tw.setRowFactory(tableView -> {
            final TableRow<Attendance> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Attendance attendance = row.getItem();
                row.setStyle("-fx-background-color:white;");

                for (Attendance att : dateFromTo) {

                    att.getChangeAttendanceButton().getStyleClass().clear();
                    att.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");
                    
                   

                    if (row.isHover() && attendance == att) {

                        att.getChangeAttendanceButton().setVisible(true);
                        row.setStyle("-fx-background-color:#000;-fx-opacity: 0.7;");

                        att.getChangeAttendanceButton().setOnAction((event) -> {
                            if (att.getStatus().equals("present")) {
                                changeStudentAttendance(att);
                                dateFromTo.set(row.getIndex(), new Attendance(att.getStudentId(), att.getDate(), "absent"));
                                changeStatusToImageWithId(att.getStudentId());
                                presentCounter--;
                                absentCounter++;
                                changeProperty();
                                cal.setTime(att.getDate());
                                skippedDay[cal.get(Calendar.DAY_OF_WEEK)]++;
                                countSkippedDay();
                            } else if (att.getStatus().equals("absent")) {
                                changeStudentAttendance(att);
                                dateFromTo.set(row.getIndex(), new Attendance(att.getStudentId(), att.getDate(), "present"));
                                changeStatusToImageWithId(att.getStudentId());
                                presentCounter++;
                                absentCounter--;
                                changeProperty();
                                cal.setTime(att.getDate());
                                skippedDay[cal.get(Calendar.DAY_OF_WEEK)]--;
                                countSkippedDay();
                            }
                        });
                    } else {
                        att.getChangeAttendanceButton().setVisible(false);

                    }
                }
            });
            return row;
        });
    }

    public void changeStatusToImageWithId(int id) {
        for (Attendance att : dateFromTo) {
            if (att.getStatus().equals("absent")) {
                att.getAttendanceImage().setImage(absent);

            } else if (att.getStatus().equals("present")) {

                att.getAttendanceImage().setImage(present);

            }
        }
    }

    public void changeStatusToImage(TodayStudents ts) {

        if (ts.getStatus().equals("absent")) {
            ts.getAttendanceImage().setImage(absent);

        } else if (ts.getStatus().equals("present")) {

            ts.getAttendanceImage().setImage(present);

        }

    }

    public SortedList<TodayStudents> getSortedStudents() {
        return sortedStudents;
    }

    public StringProperty getStudentPercentageInPeriod() {

        return percentageProperty;
    }

    public StringProperty getStudentTakenLessonsInPeriod() {
        return takenLessonsProperty;
    }

    private void changeProperty() {
        percentageProperty.set((presentCounter * 100) / (presentCounter + absentCounter) + " %");
        takenLessonsProperty.set(presentCounter + " / " + (absentCounter + presentCounter));
    }

    public boolean changeStudentAttendance(int id, Date date, String att) {
        return manager.changeStudentAttendance(id, date, att);
    }

    private void resetArray() {
        for (int i = 0; i < 8; i++) {
            skippedDay[i] = 0;
        }
    }

    private void countSkippedDay() {
        int currentMax = skippedDay[0];
        int max = -1;
        for (int i = 2; i < 8; i++) {
            if (currentMax < skippedDay[i]) {
                currentMax = i;
            }
        }
        max = currentMax;
        if (max == 2) {
            skippedDayProperty.set("Monday");
        } else if (max == 3) {
            skippedDayProperty.set("Tuesday");
        } else if (max == 4) {
            skippedDayProperty.set("Wednesday");
        } else if (max == 5) {
            skippedDayProperty.set("Thursday");
        } else if (max == 6) {
            skippedDayProperty.set("Friday");
        } else {
            skippedDayProperty.set("no");
        }
    }

    public StringProperty getskippedDayProperty() {
        return skippedDayProperty;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetArray();
    }
}
