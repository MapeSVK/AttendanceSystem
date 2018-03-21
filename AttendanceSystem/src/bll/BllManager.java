package bll;

import be.Attendance;
import dal.DalManager;
import java.util.List;


public class BllManager {
    
    private DalManager manager = new DalManager();
    
    public List<Attendance> getAllAttendance() {
        manager.getAllAttendance();
        return null;
    }
    
}
