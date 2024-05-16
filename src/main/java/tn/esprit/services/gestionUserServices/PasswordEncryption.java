package tn.esprit.services.gestionUserServices;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean verifyPassword(String password, String hashedPassword) {
        if(hashedPassword.startsWith("$2y$"))
        {
            hashedPassword = "$2a$" + hashedPassword.substring(4);
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
}
