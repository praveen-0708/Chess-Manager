package com.chess;

import com.DAO.PlayerDAO;
import com.database.DatabaseConnection;
import com.models.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements PlayerDAO {
    @Override
    public List<Player> getPlayerDetails(String query){
        DatabaseConnection databaseConnection=new DatabaseConnection();
        ResultSet rs = databaseConnection.selectQuery2(query);
        List<Player> allPlayers=new ArrayList<>();
        try {
            while(rs.next()) {
                Player player=createPlayer(rs);
                allPlayers.add(player);
            }
            return allPlayers;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return allPlayers;
        }
        finally {
            databaseConnection.closeConnection();
        }
    }
    public Player createPlayer(ResultSet resultSet) {
        Player player=new Player();
        try {
            player.setPlayerID(resultSet.getInt(1));
            player.setName(resultSet.getString(2));
            player.setEmail(resultSet.getString(3));
            player.setPassword(resultSet.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }
    @Override
    public int getTotalPointsOfAPlayer(int playerId,int tournamentId){
        DatabaseConnection databaseConnection=new DatabaseConnection();
        String query="select TOTAL_POINTS from PLAYER_IN_TOURNAMENT where PLAYER_ID="+playerId+" and TOURNAMENT_ID="+tournamentId;
        ResultSet rs=databaseConnection.selectQuery2(query);
        int totalPoints=0;
        try {
            while(rs.next())
                totalPoints=rs.getInt(1);
            return totalPoints;
        } catch (SQLException e) {
            e.printStackTrace();
            return totalPoints;
        }
        finally {
            databaseConnection.closeConnection();
        }
    }
    @Override
    public List<Player> getResultPlayerDetails(String query){
        DatabaseConnection databaseConnection=new DatabaseConnection();
        ResultSet rs = databaseConnection.selectQuery2(query);
        List<Player> allPlayers=new ArrayList<>();
        try {
            while(rs.next()) {
                Player player=createPlayer2(rs);
                allPlayers.add(player);
            }
            return allPlayers;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return allPlayers;
        }
        finally {
            databaseConnection.closeConnection();
        }
    }
    public Player createPlayer2(ResultSet resultSet) {
        Player player=new Player();
        try {
            player.setPlayerID(resultSet.getInt(1));
            player.setName(resultSet.getString(2));
            player.setEmail(resultSet.getString(3));
            player.setPoints(resultSet.getInt(7));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }


}
