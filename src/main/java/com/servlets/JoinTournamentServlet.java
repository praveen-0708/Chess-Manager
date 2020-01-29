package com.servlets;

import com.chess.TournamentManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="joinTournament",urlPatterns = "/joinTournament")
public class JoinTournamentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        int playerID=(Integer)session.getAttribute("userId");
        int tournamentID=Integer.parseInt(req.getParameter("clickedTournamentID"));

        TournamentManager tournamentManager=new TournamentManager();
        String returnValue=tournamentManager.joinTournament(playerID,tournamentID);
        if(returnValue.equals("already joined"))
            resp.getWriter().write("already joined");
        else
            resp.getWriter().write(returnValue);
    }
}
