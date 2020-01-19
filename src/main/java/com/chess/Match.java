package com.chess;

public class Match {
    private int roundNumber;
    private Player player1;
    private Player player2;
    private MatchResult result=MatchResult.NO_RESULT;

    public Match(){

    }

    public Match(int roundNumber, Player player1, Player player2, MatchResult result) {
        this.roundNumber = roundNumber;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public enum MatchResult {
        NO_RESULT,
        PLAYER_1_WON,
        PLAYER_2_WON,
        DRAW,
        BYE
    }
}
