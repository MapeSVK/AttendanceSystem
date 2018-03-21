package dal;

<<<<<<< HEAD
import be.Attendance;
import be.Student;
=======
import be.Student;
import be.Teacher;
>>>>>>> 540a7e7b4df5c42f1e82a50e06c50df42c649621
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
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
=======


public class DalManager {

    ConnectionManager cm = new ConnectionManager();
    
    public int getUserId(String username, String password)
    {
        int userId=0;
              try(Connection con = cm.getConnection())
        {
                    String query =
                            "SELECT u.id "
                            + "FROM Users u "
                            + "WHERE u.username LIKE ?"
                            + " AND u.password LIKE ?";
                            
                    PreparedStatement pstmt
                    = con.prepareStatement(query);
                    
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            
           ResultSet rs = pstmt.executeQuery();
           
            while (rs.next()) {
                userId=rs.getInt("id");
            }    
            
        } catch (SQLException ex) {
            return 0;
        }    
        return userId;
    }
    
    public Object logIn(int userId)
    {
       try (Connection con = cm.getConnection())
        {  
        String query =
                            "SELECT t.* "
                            + "FROM Teacher t "
                            + "WHERE t.id LIKE ? ";
                            
                            
                    PreparedStatement pstmt
                    = con.prepareStatement(query);
                    
            pstmt.setInt(1,userId);
            
           ResultSet rs = pstmt.executeQuery();
           
            while (rs.next()) {
                return new Teacher(userId,
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            } 
        } catch (SQLException ex) {
        }  
        return returnStudent(userId);
    }
    
    private Object returnStudent(int userId)
    {
       try (Connection con = cm.getConnection())
        {  
        String query =
                            "SELECT s.* "
                            + "FROM Student s "
                            + "WHERE s.id LIKE ? ";
                            
                            
                    PreparedStatement pstmt
                    = con.prepareStatement(query);
                    
            pstmt.setInt(1,userId);
            
           ResultSet rs = pstmt.executeQuery();
           
            while (rs.next()) {
                return new Student(userId, "sada", "sada", "sada", "sada", userId);
            } 
        } catch (SQLException ex) {
        } 
       return null;
    }
>>>>>>> 540a7e7b4df5c42f1e82a50e06c50df42c649621
}
    