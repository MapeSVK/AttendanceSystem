package bll;

import be.Day;
import dal.DalManager;
import javafx.collections.ObservableList;

import java.io.IOException;

public class BllManager {

    private DalManager manager = new DalManager();

    public void saveUsername(String username) throws IOException {
        manager.saveUsername(username);
    }

    public String readUsername() throws IOException, ClassNotFoundException {
        return manager.readUsername();
    }

    public ObservableList<Day> getDays(String month) throws IOException
    {
        return manager.getDays(month);
    }
}
