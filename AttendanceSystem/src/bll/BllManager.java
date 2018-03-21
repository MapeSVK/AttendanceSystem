package bll;

import be.Attendance;
import dal.DalManager;
import java.util.List;


public class BllManager {
    
    private DalManager manager = new DalManager();
<<<<<<< HEAD
    
    public List<Attendance> getAttandanceOfStudent(int id)
   {
       return manager.getAttandanceOfStudent(id);
   }
    
    public List<Attendance> getAllStudentsAttendance()
   {
       return manager.getAllStudentsAttendance();
   }
=======

    public int getUserId(String username, String password)
    {
        return manager.getUserId(username, password);
    }
    
    public Object logIn(int userId)
    {
        return manager.logIn(userId);
    }
>>>>>>> 540a7e7b4df5c42f1e82a50e06c50df42c649621
}
