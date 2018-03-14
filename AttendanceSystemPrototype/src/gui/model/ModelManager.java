/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.model;

import be.Day;
import be.Student;
import bll.BllManager;
import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;


/**
 *
 * @author Pepe15224
 */
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
}
