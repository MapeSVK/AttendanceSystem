package bll;

import dal.DalManager;


public class BllManager {
    
    private DalManager manager = new DalManager();

    public int getUserId(String username, String password)
    {
        return manager.getUserId(username, password);
    }
    
    public Object logIn(int userId)
    {
        return manager.logIn(userId);
    }
}
