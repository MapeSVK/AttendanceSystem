package gui.model;


import be.Attendance;
import bll.BllManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




public class ModelManager {
    
    private BllManager manager = new BllManager();


    private ObservableList<Attendance> allAttendance = FXCollections.observableArrayList();
    private ObservableList<Attendance> attendanceOfOneStudent = FXCollections.observableArrayList();
    
    
    
    public ObservableList<Attendance> getAttandanceOfStudent(int id)
    {
        attendanceOfOneStudent.setAll(manager.getAttandanceOfStudent(id));   
        return attendanceOfOneStudent; 
    }
    
    
    public void loadAllStudentsAttendance()
    {
        allAttendance.clear();
        allAttendance.addAll(manager.getAllStudentsAttendance());
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
}
