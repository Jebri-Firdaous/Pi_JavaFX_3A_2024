package tn.esprit.services.gestionUserServices;

import tn.esprit.entities.gestionUserEntities.User;

public class Session {

    private User currentuser ;

    private static Session instance ;


    private Session()
    {
    }

    public static synchronized Session getInstance()
    {
        if(instance == null)
        {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(User user)
    {
        this.currentuser = user ;
    }

    public User getCurrentUser()
    {
        return this.currentuser;
    }
}
