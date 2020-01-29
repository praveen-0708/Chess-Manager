package com.chess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Registration {

    private Connection connection;
    public Registration(){
        establishConnection();
    }

    public void establishConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "admin", "admin");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addUser(String name,String email,String password){
        String query="insert into USER(NAME,EMAIL,PASSWORD) values('"+name+"','"+email+"',MD5('"+password+"'))";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            closeConnection();
        }

    }

}
