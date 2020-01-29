package com.chess;

import java.util.Date;

public class Tournament {
    private int tournamentId;
    private String name;
    private Date startDate;
    private Date endDate;
    private String locationInput;
    private int rounds;
    private String duration;
    private int win;
    private int loss;
    private int bye;
    private int draw;
    private int createdBy;
    private int roundsCompleted;
    private String result;

    public Tournament(){

    }

    public Tournament(int tournamentId, String name, Date startDate, Date endDate, String locationInput, int rounds, String duration, int win, int loss, int bye, int draw, int createdBy, int roundsCompleted, String result) {
        this.tournamentId = tournamentId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationInput = locationInput;
        this.rounds = rounds;
        this.duration = duration;
        this.win = win;
        this.loss = loss;
        this.bye = bye;
        this.draw = draw;
        this.createdBy = createdBy;
        this.roundsCompleted = roundsCompleted;
        this.result = result;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRoundsCompleted() {
        return roundsCompleted;
    }

    public void setRoundsCompleted(int roundsCompleted) {
        this.roundsCompleted = roundsCompleted;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
