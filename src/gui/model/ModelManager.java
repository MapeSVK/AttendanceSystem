package gui.model;

import be.Day;
import bll.BllManager;
import javafx.collections.ObservableList;

import java.io.IOException;

public class ModelManager {

    private BllManager manager = new BllManager();

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
