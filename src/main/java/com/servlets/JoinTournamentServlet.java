package com.servlets;

import com.chess.TournamentManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="joinTournament",urlPatterns = "/joinTournament")
public class JoinTournamentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int playerID= Integer.parseInt(req.getParameter("ID"));
        int tournamentID=Integer.parseInt(req.getParameter("clickedTournamentID"));

        TournamentManager tournamentManager=new TournamentManager();
        int rows=tournamentManager.joinTournament(playerID,tournamentID);

        resp.getWriter().write(String.valueOf(rows));
    }
}
