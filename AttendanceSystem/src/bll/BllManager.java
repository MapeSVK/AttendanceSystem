package bll;

import be.Attendance;
import be.Student;
import dal.DalManager;
import java.util.List;


public class BllManager {
    
    private DalManager manager = new DalManager();
  
    public List<Attendance> getAttandanceOfStudent(int id)
   {
       return manager.getAttandanceOfStudent(id);
   }
    
    public List<Attendance> getAllStudentsAttendance()
   {   
       return manager.getAllStudentsAttendance();
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
    
    
}
