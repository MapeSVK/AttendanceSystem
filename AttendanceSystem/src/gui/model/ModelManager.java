package gui.model;


import be.Attendance;
import be.Student;
import bll.BllManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javax.swing.text.DateFormatter;




public class ModelManager {
    
    private BllManager manager = new BllManager();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd-MM-yyyy");
    private final Image present = new Image("file:images/presentImage.png");
    private final Image absent = new Image("file:images/absentImage.png");
    
    private ObservableList<Attendance> allAttendance = FXCollections.observableArrayList();
    private ObservableList<Attendance> attendanceOfOneStudent = FXCollections.observableArrayList();
    private ObservableList<Student> allStudentsWithStatus = FXCollections.observableArrayList();

    public ObservableList<Attendance> getAttandanceOfStudent(int id)
    {   
        attendanceOfOneStudent.clear();
        attendanceOfOneStudent.setAll(manager.getAttandanceOfStudent(id));   
        return attendanceOfOneStudent;  
    }

    public ObservableList<Attendance> getAttendanceOfOneStudent() {
        return attendanceOfOneStudent;
    }

    public void loadAllStudentsAttendance()
    {
        allAttendance.clear();
        allAttendance.addAll(manager.getAllStudentsAttendance());
    }
    
    public ObservableList<Student> getAllStudentsWithStatus()
    {
        getStudentItsCurrentAttendance();
        
        return allStudentsWithStatus;
    }

    public ObservableList<Attendance> getAllStudentsAttendance()
    {
        return allAttendance;
    }

    public int getUserId(String username, String password)
    {
        return manager.getUserId(username, password);
    }  
 
    public Object logIn(int userId)
    {
        return manager.logIn(userId);
    }
    
    public List<Student> getAllStudents()
    {
     return manager.getAllStudents();
    }
    
    private int setStudentPercentage(int StudentId)
    {
        int absent=0;
        int present=0;
        int notSubmitted=0;
        for(Attendance attendance : getAttandanceOfStudent(StudentId))
        {
            if(attendance.getStatus().contains("absent"))
            {
                absent++;
            }
            else if(attendance.getStatus().contains("present"))
            {
                present++;
            }
            else{
                notSubmitted++;
            }
        }
        
        return (present*100)/(absent+present+notSubmitted);
    }
    
    public void getStudentItsCurrentAttendance()
    {
        allStudentsWithStatus.clear();
        for(Student student : getAllStudents())
        {
            for(Attendance attendance : manager.getAllStudentsAttendance())
            {
                String currentDate = dateFormatterFull.format(new Date());
                String dateAttendance = dateFormatterFull.format(attendance.getDate());
                
                if(attendance.getStudentId()==student.getId() && dateAttendance.equals(currentDate) )
                {
                    student.setPercentage(setStudentPercentage(student.getId()));
                    student.setStatus(attendance.getStatus());
                    
                    allStudentsWithStatus.add(student);
                    
                }
                
            }
        }
    }
    
    
    
    
   
    public Object returnStudent(int userId)
    {
        return manager.returnStudent(userId);
    }
    
    public boolean changeStudentAttendance(Attendance attendance)
    {
        return manager.changeStudentAttendance(attendance);
    }
    
    
    
    
    public void showChangeAttendanceButton(TableView tw) {
        tw.setRowFactory(tableView -> {
        final TableRow<Attendance> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Attendance attendance = row.getItem();
                row.setStyle("-fx-background-color:white;");
                
                for (Attendance att : getAttendanceOfOneStudent()) {
                    
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
                
                
                
            }
            else if(att.getStatus().equals("present")) {
                
                att.getAttendanceImage().setImage(present);
                
            }
        }
    }
    
    public void changeStatusToImage(Student st) {
        
            if (st.getStatus().equals("absent")) {
                st.getAttendanceImage().setImage(absent);
                
                
                
            }
            else if(st.getStatus().equals("present")) {
                
                st.getAttendanceImage().setImage(present);
                
            }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
