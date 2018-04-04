package gui.model;


import be.Attendance;
import be.Student;
import bll.BllManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.text.DateFormatter;




public class ModelManager {
    
    private BllManager manager = new BllManager();
    DateFormat dateFormatterFull = new SimpleDateFormat("dd-MM-yyyy");
    
    private ObservableList<Attendance> allAttendance = FXCollections.observableArrayList();
    private ObservableList<Attendance> attendanceOfOneStudent = FXCollections.observableArrayList();
    private ObservableList<Student> allStudentsWithStatus = FXCollections.observableArrayList();
    private ObservableList<Attendance> dateFromTo = FXCollections.observableArrayList();
    private String studentPercentageInPeriod;
    private String studentTakenLessonsInPeriod;
    public ObservableList<Attendance> getAttandanceOfStudent(int id)
    {   
        attendanceOfOneStudent.clear();
        attendanceOfOneStudent.setAll(manager.getAttandanceOfStudent(id));   
        return attendanceOfOneStudent;  
    }

    public ObservableList<Attendance> getStudentAttendanceAndPercentageAndTakenLessonsInPeriod(ObservableList<Attendance> allAtendance, Date from, Date to)
    {
        int present = 0;
        dateFromTo.clear();
        for(Attendance attendance : allAtendance)
        {
            if(!attendance.getDate().before((java.util.Date) from) && !attendance.getDate().after((java.util.Date) to))
            {
                if(attendance.getStatus().equals("present"))
                {
                    present++;
                }
                dateFromTo.add(attendance);
            }
        }
        studentTakenLessonsInPeriod= present+" / "+dateFromTo.size();
        studentPercentageInPeriod=(present*100)/dateFromTo.size()+" %";
        return dateFromTo;
    }
    
    public ObservableList<Attendance> returnCurrentDateFromToList()
    {
        return dateFromTo;
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
        int notPresent=0;
        int present=0;
        for(Attendance attendance : getAttandanceOfStudent(StudentId))
        {
            if(attendance.getStatus().contains("present"))
            {
                present++;
            }
            
            else{
                notPresent++;
            }
        }       
        return (present*100)/(present+notPresent);
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
    
    public String getStudentPercentageInPeriod()
    {
        return studentPercentageInPeriod;
    }
    public String getStudentTakenLessonsInPeriod()
    {
        return studentTakenLessonsInPeriod;
    }
}
