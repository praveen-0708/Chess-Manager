package com.chess;

import com.DAO.MatchDAO;
import com.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchManager implements MatchDAO {

    public void updateTotalPointsInDB(int playerID,int points,int tournamentID,DatabaseConnection databaseConnection){
        PlayerManager playerManager=new PlayerManager();
        int totalPointsOfPlayer=playerManager.getTotalPointsOfAPlayer(playerID,tournamentID);
        String query="update PLAYER_IN_TOURNAMENT set TOTAL_POINTS="+(points+totalPointsOfPlayer)+" where PLAYER_ID="+playerID+" and TOURNAMENT_ID="+tournamentID;
        databaseConnection.updateQuery(query);

    }

    @Override
    public void calculateTotalPoints(int roundNumber, int tournamentID) {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        String query="select *from PLAYER_MATCH where ROUND_NUMBER="+roundNumber+" and TOURNAMENT_ID="+tournamentID;
        ResultSet rs=databaseConnection.selectQuery2(query);
        try{
            while (rs.next()){
                if(rs.getString(7).equals("BYE")){
                    System.out.println("P1-"+rs.getInt(1)+" points="+rs.getInt(5));
                    updateTotalPointsInDB(rs.getInt(1),rs.getInt(5),tournamentID,databaseConnection);
                }else{
                    System.out.println("P1-"+rs.getInt(1)+" points1="+rs.getInt(5));
                    System.out.println("P2-"+rs.getInt(2)+" points2="+rs.getInt(6));
                    updateTotalPointsInDB(rs.getInt(1),rs.getInt(5),tournamentID,databaseConnection);
                    updateTotalPointsInDB(rs.getInt(2),rs.getInt(6),tournamentID,databaseConnection);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            databaseConnection.closeConnection();
        }
    }
}
