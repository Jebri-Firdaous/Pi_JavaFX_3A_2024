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
    public boolean isLoggedIn() {
        Session session = Session.getInstance();
        return session.getCurrentUser() != null;
    }
    public User getCurrentUser()
    {
        return this.currentuser;
    }
    public void logout() {
        Session session = Session.getInstance();
        session.setCurrentUser(null); // Clear the current user from the session
        // Redirect or update UI to reflect logged out state
        // For example, you could close the current window or show a login screen again
    }
}
