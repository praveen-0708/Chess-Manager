package com.DAO;

import com.models.Match;

import java.util.List;

public interface PairingDAO {
    List<Match> pairing(int tournamentId, int roundNumber);
}
