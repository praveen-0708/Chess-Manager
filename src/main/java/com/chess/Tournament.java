package com.chess;

public class Tournament {
    private int tournamentId;
    private String name;
    private String dateRange;
    private String locationInput;
    private int rounds;
    private String duration;
    private int win;
    private int loss;
    private int bye;
    private int draw;
    private int createdBy;


    public Tournament(){

    }

    public Tournament(int tournamentId, String name, String dateRange, String locationInput, int rounds, String duration, int win, int loss, int bye, int draw, int createdBy) {
        this.tournamentId = tournamentId;
        this.name = name;
        this.dateRange = dateRange;
        this.locationInput = locationInput;
        this.rounds = rounds;
        this.duration = duration;
        this.win = win;
        this.loss = loss;
        this.bye = bye;
        this.draw = draw;
        this.createdBy = createdBy;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getLocationInput() {
        return locationInput;
    }

    public void setLocationInput(String locationInput) {
        this.locationInput = locationInput;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getBye() {
        return bye;
    }

    public void setBye(int bye) {
        this.bye = bye;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
