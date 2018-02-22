 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import be.Day;
import be.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author Pepe15224
 */
public class DalManager {
 
    
public void saveUsername(String username) throws IOException {
    ObjectOutputStream oos =
            new ObjectOutputStream(
                    new FileOutputStream("fakeDB/Username.ser")
            );
    oos.writeObject(username);
}

public String readUsername() throws IOException, ClassNotFoundException {
    ObjectInputStream ois =
            new ObjectInputStream(
                    new FileInputStream("fakeDB/Username.ser")
            );
String kanapka = (String)ois.readObject();
    return kanapka;
}
    public ObservableList<Day> getDays(String month) throws IOException
    {
        ObservableList<Day> days = FXCollections.observableArrayList();
        try(BufferedReader br = new BufferedReader(new FileReader("fakeDB/"+month+".csv")))
        {

            Scanner scanner = new Scanner(br);
            scanner.nextLine();
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                Day day = new Day(fields[0],fields[1]);
                days.add(day);
            }
        }
        return days;
    }

    public void updateMonth(List<Day> dayList, String file) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("fakeDB/"+file+".csv")))
        {
            bw.write("Date, Attendance");
            bw.newLine();
            for (Day days : dayList) {
                bw.write(days.getDate() + ","
                        + days.getAttendance());
                        bw.newLine();
            }

        }
    }

    public void updateStudent(List<Student> studentList, String file) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("fakeDB/"+file+".csv")))
        {
            bw.write("name, attendance, percentage, takenLessons, skippedDay, months");
            bw.newLine();
            for (Student student : studentList) {
                bw.write(student.getName() + ","+
                        student.getAttendance() + ","+
                        student.getPercentage() + ","+
                        student.getTakenLessons() + ","+
                        student.getSkippedDay() + ","+
                         student.getMonths());
                bw.newLine();
            }

        }
    }
    public ObservableList<Student> getStudents() throws IOException
    {
        ObservableList<Student> students = FXCollections.observableArrayList();
        try(BufferedReader br = new BufferedReader(new FileReader("fakeDB/Students.csv")))
        {

            Scanner scanner = new Scanner(br);
            scanner.nextLine();
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                Student student = new Student(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5]);
                students.add(student);
            }
        }
        return students;
    }
}
