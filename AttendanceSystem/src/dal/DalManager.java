package dal;


import be.Attendance;
import be.Student;
import be.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    
    public Object returnStudent(int userId)
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
                return new Student(userId, rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("imageLink"),
                        rs.getString("email"),
                rs.getInt("classId"));
            } 
        } catch (SQLException ex) {
        } 
       return null;
    }
    
    public List<Student> getAllStudents()
    {
   List<Student> allStudents = new ArrayList();
        try (Connection con = cm.getConnection())
        {
         
         
         PreparedStatement pstmt = con.prepareCall("SELECT * FROM Student");
         ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
               Student stu = new Student(rs.getInt("id"),
                       rs.getString("firstName"),
                       rs.getString("lastName"),
                       rs.getString("imageLink"),
                       rs.getString("email"),
                       rs.getInt("classId"));
      
                allStudents.add(stu);
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return allStudents; 
}   
    
    public boolean changeStudentAttendance(Attendance attendance)                 
    {
        String status="absent";
        if(attendance.getStatus().equals("absent")|| attendance.getStatus().equals("not submitted"))
        {
            status="present";
        }
    try (Connection con = cm.getConnection()) {
            String sql
                    = "UPDATE Attendance SET "
                    + "status=? "
                    + "WHERE studentId=? AND date=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, attendance.getStudentId());
            pstmt.setDate(3, (java.sql.Date) attendance.getDate());
            int affected = pstmt.executeUpdate();
            if (affected<1)
                return false;
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        } 
    return true;
    }
}
    