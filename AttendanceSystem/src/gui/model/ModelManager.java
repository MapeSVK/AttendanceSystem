package gui.model;


import bll.BllManager;




public class ModelManager {
    
     private BllManager manager = new BllManager();

    public int getUserId(String username, String password)
    {
        return manager.getUserId(username, password);
    }  
    
    public Object logIn(int userId)
    {
        return manager.logIn(userId);
    }
}
