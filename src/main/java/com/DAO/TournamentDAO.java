package com.DAO;

import com.models.Tournament;

import java.util.List;

public interface TournamentDAO {
    void addTournament(String name,String dateRange,String locationInput,int rounds,String duration,int win,int loss,int bye,int draw,int createdBy);
    List<Tournament> getTournamentDetails(String query);
    String joinTournament(int playerID,int tournamentID);
    int getRoundsCompleted(int tournamentId);
}
