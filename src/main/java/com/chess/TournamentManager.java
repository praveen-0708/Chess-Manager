package com.chess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try {
            String query="insert into Tournament(name,daterange,location,rounds,duration,win,loss,bye,draw,CREATED_BY) values(?,?,?,?,?,?,?,?,?,?)";
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
            statement.setInt(10,createdBy);
            statement.executeUpdate();
            statement.close();

            query="select * from Tournament where name=? and CREATED_BY=?";
            statement=connection.prepareStatement(query);
            statement.setString(1,name);
            statement.setInt(2,createdBy);
            ResultSet rs=statement.executeQuery();
            int ID=0;
            while(rs.next())
                ID=rs.getInt(1);

            statement.close();
            createPointsTable(ID,rounds);


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



    public int joinTournament(int playerID,int tournamentID){
        establishConnection();
        String query="insert into PlayersIn values(?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setInt(1,playerID);
            statement.setInt(2,tournamentID);
            int rows=statement.executeUpdate();
            statement.close();

            query="insert into Tournament"+tournamentID+"PointsTable(PLAYERID) values(?)";
            statement=connection.prepareStatement(query);
            statement.setInt(1,playerID);
            statement.executeUpdate();
            statement.close();

            return rows;
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
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
            tournament.setDateRange(resultSet.getString(3));
            tournament.setLocationInput(resultSet.getString(4));
            tournament.setRounds(resultSet.getInt(5));
            tournament.setDuration(resultSet.getString(6));
            tournament.setWin(resultSet.getInt(7));
            tournament.setLoss(resultSet.getInt(8));
            tournament.setBye(resultSet.getInt(9));
            tournament.setDraw(resultSet.getInt(10));
            tournament.setCreatedBy(resultSet.getInt(11));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tournament;
    }

    public void createPointsTable(int tournamentId,int rounds){
        establishConnection();
        StringBuilder partQuery= new StringBuilder();
        for(int i=1;i<=rounds;i++)
            partQuery.append(",ROUND").append(i).append(" int DEFAULT 0");
        String query="create table Tournament"+tournamentId+"PointsTable(PLAYERID int primary key"+partQuery+",TOTAL_POINTS int DEFAULT 0)";
        try{
            Statement statement=connection.createStatement();
            statement.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }

    }

}
