package com.DAO;

import com.models.Player;

import java.util.List;

public interface PlayerDAO {
    List<Player> getPlayerDetails(String query);
    int getTotalPointsOfAPlayer(int playerId,int tournamentId);
    List<Player> getResultPlayerDetails(String query);
}
