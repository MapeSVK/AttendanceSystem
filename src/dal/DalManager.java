package dal;

import be.Day;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.List;
import java.util.Scanner;

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
}
