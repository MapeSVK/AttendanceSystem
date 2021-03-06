package bll;

import be.Attendance;
import be.Student;
import be.TodayStudents;
import dal.DalManager;
import java.util.Date;
import java.util.List;

public class BllManager {

    private DalManager manager = new DalManager();

    public List<Attendance> getAttandanceOfStudent(int id) {
        return manager.getAttandanceOfStudent(id);
    }

    public List<Attendance> getAllStudentsAttendance() {
        return manager.getAllStudentsAttendance();
    }

    public List<TodayStudents> getTodayStudent() {
        return manager.getTodayAttendance();
    }

    public int getUserId(String username, String password) {

        return manager.getUserId(username, password);
    }

    public Object logIn(int userId) {
        return manager.logIn(userId);
    }

    public List<Student> getAllStudents() {
        return manager.getAllStudents();
    }

    public Object returnStudent(int userId) {
        return manager.returnStudent(userId);
    }

    public boolean changeStudentAttendance(Attendance attendance) {
        return manager.changeStudentAttendance(attendance);
    }

    public boolean changeStudentAttendance(int id, Date date, String att) {
        return manager.changeStudentAttendance(id, date, att);
    }
}
