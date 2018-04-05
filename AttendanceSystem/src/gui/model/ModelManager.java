package gui.model;

import be.Attendance;
import be.Student;
import bll.BllManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

public class ModelManager {

    private BllManager manager = new BllManager();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd-MM-yyyy");
    private final Image present = new Image("file:images/presentImage.png");
    private final Image absent = new Image("file:images/absentImage.png");
    
    SimpleStringProperty percentageProperty = new SimpleStringProperty("tequila");
    SimpleStringProperty takenLessonsProperty = new SimpleStringProperty("lemon");

    private ObservableList<Attendance> allAttendance = FXCollections.observableArrayList();
    private ObservableList<Attendance> attendanceOfOneStudent = FXCollections.observableArrayList();
    private ObservableList<Student> allStudentsWithStatus = FXCollections.observableArrayList();
    private ObservableList<Attendance> dateFromTo = FXCollections.observableArrayList();
    SortedList<Student> sortedStudents = new SortedList<>(allStudentsWithStatus, Comparator.comparing(Student::getStatus).reversed());

    private int presentCounter;
    private int absentCounter;

    public ObservableList<Attendance> getAttandanceOfStudent(int id) {
        attendanceOfOneStudent.clear();
        attendanceOfOneStudent.setAll(manager.getAttandanceOfStudent(id));
        return attendanceOfOneStudent;
    }

    public ObservableList<Attendance> getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(ObservableList<Attendance> allAtendance, Date from, Date to) {
        dateFromTo.clear();
        presentCounter=0;
        for (Attendance attendance : allAtendance) {
            if (!attendance.getDate().before((java.util.Date) from) && !attendance.getDate().after((java.util.Date) to)) {
                if (attendance.getStatus().equals("present")) {
                    
                    presentCounter++;
                }
                dateFromTo.add(attendance);
            }
        }
        if (dateFromTo.size() == 0) {
            percentageProperty.set("0 %");
            takenLessonsProperty.set("0 / 0");
        } else {
            absentCounter=dateFromTo.size()-presentCounter;
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

//    public void showChangeAttendanceButton(TableView tw) {
//        tw.setRowFactory(tableView -> {
//            final TableRow<Attendance> row = new TableRow<>();
//
//            row.hoverProperty().addListener((observable) -> {
//                final Attendance attendance = row.getItem();
//                row.setStyle("-fx-background-color:white;");
//
//                for (Attendance att : getAttendanceOfOneStudent()) {
//
//                    att.getChangeAttendanceButton().getStyleClass().clear();
//                    att.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");
//
//                    if (row.isHover() && attendance == att) {
//
//                        att.getChangeAttendanceButton().setVisible(true);
//                        row.setStyle("-fx-background-color:#000;-fx-opacity: 0.7;");
//
//                    } else {
//                        att.getChangeAttendanceButton().setVisible(false);
//
//                    }
//                    att.getChangeAttendanceButton().setOnAction((event) -> {
//                        if (att.getStatus().equals("present")) {
//                            changeStudentAttendance(att);
//
//                        } else if (att.getStatus().equals("absent")) {
//                            att.setStatus("present");
//                            changeStudentAttendance(att);
//                        }
//                    });
//                }
//            });
//            return row;
//        });
//
//    }

    public void showChangeAttendanceButtonTomek(TableView tw) {
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
                             changeStatusToImageTomek(att.getStudentId());
                             presentCounter--;
                             absentCounter++;
                        changeProperty();
                        } else if (att.getStatus().equals("absent")) {           
                            changeStudentAttendance(att);
                            dateFromTo.set(row.getIndex(), new Attendance(att.getStudentId(), att.getDate(), "present"));
                changeStatusToImageTomek(att.getStudentId());
                   presentCounter++;
                             absentCounter--;
                        changeProperty();
                            
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

//    public void showChangeAttendanceButtonTeacher(TableView tw, Student st) {
//        tw.setRowFactory(tableView -> {
//        final TableRow<Student> row = new TableRow<>();
//
//            row.hoverProperty().addListener((observable) -> {
//                final Student student = row.getItem();
//                row.setStyle("-fx-background-color:white;");
//                
//                
//                    
//                    st.getChangeAttendanceButton().getStyleClass().clear();
//                    st.getChangeAttendanceButton().getStyleClass().add("changeAttendanceButton");
//
//                    
//                    if (row.isHover() && student.equals(st)) {
//                        
//                        st.getChangeAttendanceButton().setVisible(true);
//                        row.setStyle("-fx-background-color:#000;-fx-opacity: 0.7;");  
//                        
//                    } 
//                    else {   
//                        st.getChangeAttendanceButton().setVisible(false);              
//                        
//                    }
//                    
//                             
//            });
//       return row;
//        });
//        
//        
//    }
    public void changeStatusToImage(int id) {
        for (Attendance att : getAttandanceOfStudent(id)) {
            if (att.getStatus().equals("absent")) {
                att.getAttendanceImage().setImage(absent);

            } else if (att.getStatus().equals("present")) {

                att.getAttendanceImage().setImage(present);

            }
        }
    }

    public void changeStatusToImageTomek(int id) {
        for (Attendance att : dateFromTo) {
            if (att.getStatus().equals("absent")) {
                att.getAttendanceImage().setImage(absent);

            } else if (att.getStatus().equals("present")) {

                att.getAttendanceImage().setImage(present);

            }
        }
    }

    public void changeStatusToImage(Student st) {

        if (st.getStatus().equals("absent")) {
            st.getAttendanceImage().setImage(absent);

        } else if (st.getStatus().equals("present")) {

            st.getAttendanceImage().setImage(present);

        }

    }

    public SortedList<Student> getSortedStudents() {
        return sortedStudents;
    }

    public StringProperty getStudentPercentageInPeriod() {
        
        return percentageProperty;
    }

    public StringProperty getStudentTakenLessonsInPeriod() {
        return takenLessonsProperty;
    }

    private void changeProperty()
    {
        percentageProperty.set((presentCounter*100)/(presentCounter+absentCounter)+" %");
        takenLessonsProperty.set(presentCounter+" / "+(absentCounter+presentCounter));
    }
  

}
