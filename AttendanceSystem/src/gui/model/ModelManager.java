package gui.model;


import be.Attendance;
import bll.BllManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




public class ModelManager {
    
    private BllManager manager = new BllManager();

<<<<<<< HEAD
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
    
    
    
    
    
    
=======
    public int getUserId(String username, String password)
    {
        return manager.getUserId(username, password);
    }  
>>>>>>> 540a7e7b4df5c42f1e82a50e06c50df42c649621
    
    public Object logIn(int userId)
    {
        return manager.logIn(userId);
    }
}
