package com.database;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection(){
        establishConnection();
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public void establishConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "admin", "admin");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectQuery(PreparedStatement preparedStatement){
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
