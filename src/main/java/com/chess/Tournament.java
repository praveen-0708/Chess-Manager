package com.chess;

import java.sql.*;

public class Tournament {

    private Connection connection;
    public Tournament(){
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


    public void addTournament(String name,String dateRange,String locationInput,int rounds,String duration,int win,int loss,int bye,int draw){

        String query="insert into Tournament(name,daterange,location,rounds,duration,win,loss,bye,draw) values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,dateRange);
            statement.setString(3,locationInput);
            statement.setInt(4,rounds);
            statement.setString(5,duration);
            statement.setInt(6,win);
            statement.setInt(7,loss);
            statement.setInt(8,bye);
            statement.setInt(9,draw);


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }
}
