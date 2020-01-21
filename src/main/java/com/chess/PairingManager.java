package com.chess;

import com.database.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;

public class PairingManager {

    public List<Match> pairing(int tournamentId,int roundNumber){

        String query="select *from users u,PlayersIn p where p.TournamentID="+tournamentId+" and u.ID=p.ID";

        PlayerManager playerManager=new PlayerManager();
        List<Player> players=playerManager.getPlayerDetails(query);
        List<Match> matches=createMatches(players,roundNumber);
        return matches;

    }
    public List<Match> createMatches(List<Player> players,int roundNumber){
        List<Match> matches=new ArrayList<>();
        int numberOfPlayers=players.size();
        int partSize=numberOfPlayers/2;
        for(int index=0;index<partSize;index++){
            Match match=new Match();
            match.setRoundNumber(roundNumber);
            match.setPlayer1(players.get(index));
            match.setPlayer2(players.get(index+partSize));
            matches.add(match);
        }
        if(numberOfPlayers%2==1){
            Match match=new Match();
            match.setRoundNumber(roundNumber);
            match.setPlayer1(players.get(numberOfPlayers-1));
            match.setResult(Match.MatchResult.BYE);
            matches.add(match);
        }
        return matches;
    }
    public void updateScore(int playerId,int tournamentId,int points,int roundNumber){

        String query="update Tournament"+tournamentId+"PointsTable set ROUND"+roundNumber+"="+points+
                " where PLAYERID="+playerId;
        DatabaseConnection databaseConnection=new DatabaseConnection();
        databaseConnection.updateQuery(query);
        databaseConnection.closeConnection();
    }


}
