package bll;

import be.Attendance;
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
}
