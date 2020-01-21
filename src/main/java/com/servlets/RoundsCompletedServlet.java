package com.servlets;

import com.chess.TournamentManager;
import com.database.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="getRounds",urlPatterns = "/getRounds")
public class RoundsCompletedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId=Integer.parseInt(req.getParameter("tournamentID"));
        TournamentManager tournamentManager=new TournamentManager();
        int roundsCompleted=tournamentManager.getRoundsCompleted(tournamentId);
        resp.getWriter().write(String.valueOf(roundsCompleted));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId=Integer.parseInt(req.getParameter("tournamentID"));
        int roundsCompleted=Integer.parseInt(req.getParameter("roundsCompleted"));
        String query="update Tournament set ROUNDS_COMPLETED="+roundsCompleted+" where TournamentId="+tournamentId;
        DatabaseConnection databaseConnection=new DatabaseConnection();
        databaseConnection.updateQuery(query);
    }
}
