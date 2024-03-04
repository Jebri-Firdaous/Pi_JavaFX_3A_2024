package tn.pidev.utils.Parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private final String URL="jdbc:mysql://localhost:3306/e-city";
    private final String USER="root";
    private final String PSW="";
    public static MyDataBase instance;
    private Connection connection;
    private MyDataBase(){
        try{
            connection = DriverManager.getConnection(URL,USER,PSW);
            System.out.println("Connected");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static MyDataBase getInstance() {
        if(instance == null)
            instance = new MyDataBase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
