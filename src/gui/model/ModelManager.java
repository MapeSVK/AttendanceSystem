package gui.model;

import be.Day;
import be.Student;
import bll.BllManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ModelManager {

    private BllManager manager = new BllManager();

    public void saveUsername(String username) throws IOException {
        manager.saveUsername(username);
    }

    public String readUsername() throws IOException, ClassNotFoundException {
        return manager.readUsername();
    }

    public ObservableList<Day> getDays(String month) throws IOException {
        return manager.getDays(month);
    }

    public void updateMonth(List<Day> dayList, String file) throws IOException {
        manager.updateMonth(dayList, file);
    }

    public void updateStudent(List<Student> studentList, String file) throws IOException {
        manager.updateStudent(studentList, file);
    }

    public ObservableList<Student> getStudents() throws IOException {
        return manager.getStudents();
    }
    //----------------------------------------------------------
    public void fill(String[] months)
    {
        months[0]="January";
        months[1]="February";
        months[2]="March";
        months[3]="April";
        months[4]="May";
        months[5]="June";
        months[6]="July";
        months[7]="August";
        months[8]="September";
        months[9]="October";
        months[10]="November";
        months[11]="December";
    }
}