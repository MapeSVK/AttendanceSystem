package dal;

import be.Attendance;
import be.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DalManager {
    private ConnectionManager cm = new ConnectionManager();
    
    
    
    public List<Attendance> getAttandanceOfStudent(int id)
    {
        List<Attendance> attendanceOfStudent = new ArrayList();
        try (Connection con = cm.getConnection())
        {
         String query = "SELECT a.* FROM Attendance a"
                 + " JOIN Student s ON s.id = a.studentId WHERE s.id = ?";
         
         PreparedStatement pstmt
                    = con.prepareStatement(query);
            pstmt.setInt(1,id);
         
         ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Attendance a = new Attendance(rs.getInt("studentId"),
                       rs.getDate("date"),
                        rs.getString("status"));
      
                attendanceOfStudent.add(a);
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return attendanceOfStudent;
}
    
    
    
    
    public List<Attendance> getAllStudentsAttendance()
    {
        List<Attendance> allStudentsAttendance = new ArrayList();
        try (Connection con = cm.getConnection())
        {
         
         
         PreparedStatement pstmt = con.prepareCall("SELECT * FROM Attendance");
         ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Attendance a = new Attendance(rs.getInt("studentId"),
                       rs.getDate("date"),
                        rs.getString("status"));
      
                allStudentsAttendance.add(a);
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return allStudentsAttendance;
}
}
