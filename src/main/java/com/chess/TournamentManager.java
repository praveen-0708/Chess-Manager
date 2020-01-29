package com.chess;

import com.database.DatabaseConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TournamentManager {

    private Connection connection;
    public TournamentManager(){

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


    public void addTournament(String name,String dateRange,String locationInput,int rounds,String duration,int win,int loss,int bye,int draw,int createdBy){
        establishConnection();
        String[] arrOfStr = dateRange.split("-");
        Date startDate=stringToDate(arrOfStr[0],"dd/MM/yyyy");
        Date endDate=stringToDate(arrOfStr[1],"dd/MM/yyyy");
        try {
            String query="insert into TOURNAMENT(NAME,START_DATE,END_DATE,LOCATION,ROUNDS,DURATION,WIN,LOSS,BYE,DRAW,CREATED_BY) values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setDate(2,new java.sql.Date(startDate.getTime()));
            statement.setDate(3, new java.sql.Date(endDate.getTime()));
            statement.setString(4,locationInput);
            statement.setInt(5,rounds);
            statement.setString(6,duration);
            statement.setInt(7,win);
            statement.setInt(8,loss);
            statement.setInt(9,bye);
            statement.setInt(10,draw);
            statement.setInt(11,createdBy);
            statement.executeUpdate();
            statement.close();

            query="select * from TOURNAMENT where NAME=? and CREATED_BY=?";
            statement=connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setInt(2,createdBy);
            ResultSet rs=statement.executeQuery();
            int ID=0;
            while(rs.next())
                ID=rs.getInt(1);

            statement.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    public List<Tournament> getTournamentDetails(String query){
        establishConnection();
        List<Tournament> allTournaments=new ArrayList<>();
        //String query="select *from Tournament";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Tournament tournament=createTournament(rs);
                allTournaments.add(tournament);
            }
            return allTournaments;

        } catch (SQLException e) {
            e.printStackTrace();
            return allTournaments;
        }finally {
            closeConnection();
        }
    }



    public String joinTournament(int playerID,int tournamentID){
        establishConnection();
        String query="insert into PLAYER_IN_TOURNAMENT(PLAYER_ID,TOURNAMENT_ID) values(?,?)";
        try {
            Statement statement2=connection.createStatement();
            ResultSet rs=statement2.executeQuery("select *from PLAYER_IN_TOURNAMENT where PLAYER_ID="+playerID+" and TOURNAMENT_ID="+tournamentID);
            if(rs.next())
                return "already joined";
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setInt(1,playerID);
            statement.setInt(2,tournamentID);
            int rows=statement.executeUpdate();
            statement.close();
            return String.valueOf(rows);
        } catch (SQLException e) {

            e.printStackTrace();
            return "0";
        }
        finally {

            closeConnection();
        }
    }

    public Tournament createTournament(ResultSet resultSet) {
        Tournament tournament = new Tournament();
        try {
            tournament.setTournamentId(resultSet.getInt(1));
            tournament.setName(resultSet.getString(2));
            tournament.setStartDate(resultSet.getDate(3));
            tournament.setEndDate(resultSet.getDate(4));
            tournament.setLocationInput(resultSet.getString(5));
            tournament.setRounds(resultSet.getInt(6));
            tournament.setDuration(resultSet.getString(7));
            tournament.setWin(resultSet.getInt(8));
            tournament.setLoss(resultSet.getInt(9));
            tournament.setBye(resultSet.getInt(10));
            tournament.setDraw(resultSet.getInt(11));
            tournament.setCreatedBy(resultSet.getInt(12));
            tournament.setRoundsCompleted(resultSet.getInt(13));
            tournament.setResult(resultSet.getString(14));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tournament;
    }


    public int getRoundsCompleted(int tournamentId){
        DatabaseConnection databaseConnection=new DatabaseConnection();
        String query="select ROUNDS_COMPLETED from TOURNAMENT where TOURNAMENT_ID="+tournamentId;
        int rounds=0;
        try{
            ResultSet rs=databaseConnection.selectQuery2(query);
            while(rs.next())
                rounds=rs.getInt(1);

            return rounds;
        }

        catch (SQLException e){
            e.printStackTrace();
            return rounds;
        }
        finally {
            databaseConnection.closeConnection();
        }
    }

    public Date stringToDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
