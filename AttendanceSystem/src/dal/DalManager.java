package dal;

import be.Student;
import be.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
}
    