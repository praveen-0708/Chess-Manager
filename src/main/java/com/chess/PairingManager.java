package com.chess;

import com.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PairingManager {

    public List<Match> pairing(int tournamentId,int roundNumber){

        String query="select *from USER u,PLAYER_IN_TOURNAMENT p where p.TOURNAMENT_ID="+tournamentId+" and u.PLAYER_ID=p.PLAYER_ID order by TOTAL_POINTS desc";

        PlayerManager playerManager=new PlayerManager();
        List<Player> players=playerManager.getPlayerDetails(query);
        List<Match> matches=createMatches(players,roundNumber);
        //System.out.println(matches);
        addPairingToDB(matches,tournamentId);

        TournamentManager tournamentManager=new TournamentManager();
        int roundsCompleted=tournamentManager.getRoundsCompleted(tournamentId);

        query="update TOURNAMENT set ROUNDS_COMPLETED="+(roundsCompleted+1)+" where TOURNAMENT_ID="+tournamentId;
        DatabaseConnection databaseConnection=new DatabaseConnection();
        databaseConnection.updateQuery(query);
        databaseConnection.closeConnection();

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

    private boolean haveAlreadyPlayed(List<Match> finishedMatches, Player player1, Player player2) {
        return false;
    }

    public void updateScore(int player1ID,int player2ID,int tournamentId,int roundNumber,int player1Points,int player2Points,String result){

//        String query="update Tournament"+tournamentId+"PointsTable set ROUND"+roundNumber+"="+points+
//                " where PLAYERID="+playerId;

        String query="update PLAYER_MATCH set RESULT='"+result+"',PLAYER1_POINTS="+player1Points+",PLAYER2_POINTS="+player2Points+" where PLAYER1_ID="+player1ID+" and PLAYER2_ID="+player2ID
                +" and ROUND_NUMBER="+roundNumber+" and TOURNAMENT_ID="+tournamentId;



        DatabaseConnection databaseConnection=new DatabaseConnection();
        databaseConnection.updateQuery(query);

//        int totalPointsOfPlayer1=0;
//        int totalPointsOfPlayer2=0;
//        PlayerManager playerManager=new PlayerManager();
//        if(result.equals("PLAYER_1_WON") || result.equals("BYE")){
//            totalPointsOfPlayer1=playerManager.getTotalPointsOfAPlayer(player1ID,tournamentId);
//            query="update PlayersIn set TOTAL_POINTS="+(player1Points+totalPointsOfPlayer1)+" where ID="+player1ID+" and TournamentID="+tournamentId;
//            databaseConnection.updateQuery(query);
//        }
//        else if(result.equals("PLAYER_2_WON")){
//            totalPointsOfPlayer2=playerManager.getTotalPointsOfAPlayer(player2ID,tournamentId);
//            query="update PlayersIn set TOTAL_POINTS="+(player2Points+totalPointsOfPlayer2)+" where ID="+player2ID+" and TournamentID="+tournamentId;
//            databaseConnection.updateQuery(query);
//        }
//        else if(result.equals("DRAW")){
//            totalPointsOfPlayer1=playerManager.getTotalPointsOfAPlayer(player1ID,tournamentId);
//            totalPointsOfPlayer2=playerManager.getTotalPointsOfAPlayer(player2ID,tournamentId);
//            query="update PlayersIn set TOTAL_POINTS="+(player1Points+totalPointsOfPlayer1)+" where ID="+player1ID+" and TournamentID="+tournamentId;
//            databaseConnection.updateQuery(query);
//            query="update PlayersIn set TOTAL_POINTS="+(player2Points+totalPointsOfPlayer2)+" where ID="+player2ID+" and TournamentID="+tournamentId;
//            databaseConnection.updateQuery(query);
//        }
//        System.out.println(query);
        databaseConnection.closeConnection();
    }

    public void addPairingToDB(List<Match> matches,int tournamentId){
        int player1ID,player2ID=0;
        String query;

        for(Match match:matches){
            player2ID=0;
            int roundNumber=match.getRoundNumber();
            player1ID=match.getPlayer1().getPlayerID();
            if(!match.getResult().equals(Match.MatchResult.BYE))
                player2ID=match.getPlayer2().getPlayerID();

            query="insert into PLAYER_MATCH(PLAYER1_ID,PLAYER2_ID,ROUND_NUMBER,TOURNAMENT_ID) values("+player1ID+","+player2ID+","+roundNumber+","+tournamentId+")";
            DatabaseConnection databaseConnection=new DatabaseConnection();
            databaseConnection.updateQuery(query);
            databaseConnection.closeConnection();
        }
    }

    public List<Match> paired(int tournamentId,int roundNumber){
        String query="select *from USER u,PLAYER_MATCH m where m.ROUND_NUMBER="+roundNumber+" and TOURNAMENT_ID="+tournamentId+" and u.PLAYER_ID=m.PLAYER1_ID";
        String query2="select *from USER u,PLAYER_MATCH m where m.ROUND_NUMBER="+roundNumber+" and TOURNAMENT_ID="+tournamentId+" and u.PLAYER_ID=m.PLAYER2_ID";

        DatabaseConnection databaseConnection=new DatabaseConnection();

        List<Match> matches=new ArrayList<>();
        try{
            ResultSet rs=databaseConnection.selectQuery2(query);
            ResultSet rs2=databaseConnection.selectQuery2(query2);
            while(rs.next()){
                Match match=new Match();
                match.setRoundNumber(rs.getInt(7));
                match.setResult(Match.MatchResult.valueOf(rs.getString(11)));
                Player player1=new Player();
                player1.setName(rs.getString(2));
                player1.setEmail(rs.getString(3));
                player1.setPlayerID(rs.getInt(1));
                match.setPlayer1(player1);
                matches.add(match);
            }
            int i=0;
            while (rs2.next()){
                Player player2=new Player();
                player2.setPlayerID(rs2.getInt(1));
                player2.setName(rs2.getString(2));
                player2.setEmail(rs2.getString(3));
                Match match=new Match();
                match=matches.get(i);
                match.setPlayer2(player2);
                matches.set(i,match);
                i++;
            }
            return matches;
        }catch (SQLException e){
            e.printStackTrace();
            return matches;
        }finally {
            databaseConnection.closeConnection();
        }
    }

}
