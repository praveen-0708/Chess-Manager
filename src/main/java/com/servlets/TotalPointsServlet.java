package com.servlets;

import com.chess.MatchManager;
import com.chess.Tournament;
import com.chess.TournamentManager;
import com.database.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="calculateTotalPoints",urlPatterns = "/calculateTotalPoints")
public class TotalPointsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roundNumber=Integer.parseInt(req.getParameter("roundNumber"));
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");

        TournamentManager tournamentManager=new TournamentManager();
        Tournament tournament=tournamentManager.getTournamentDetails("select *from TOURNAMENT where TOURNAMENT_ID="+tournamentId).get(0);

        if(tournament.getRounds()==tournament.getRoundsCompleted()){
            if(tournament.getResult().equals("NO")){
                System.out.println(roundNumber);
                MatchManager matchManager1=new MatchManager();
                matchManager1.calculateTotalPoints(roundNumber,tournamentId);
                DatabaseConnection databaseConnection=new DatabaseConnection();
                databaseConnection.updateQuery("update TOURNAMENT set RESULT='YES' where TOURNAMENT_ID="+tournamentId);
                databaseConnection.closeConnection();
            }
        }else{
            MatchManager matchManager=new MatchManager();
            matchManager.calculateTotalPoints(roundNumber,tournamentId);
        }


    }
}
