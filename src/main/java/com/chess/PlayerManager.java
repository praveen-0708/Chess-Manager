package com.chess;

import com.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

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

}
