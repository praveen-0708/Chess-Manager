package com.servlets;

import com.models.Tournament;
import com.chess.TournamentManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="createdTournaments",urlPatterns = "/home/created")
public class CreatedTournamentServlets extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int playerID=(Integer)session.getAttribute("userId");
        String query="select * from TOURNAMENT where CREATED_BY="+playerID;
        TournamentManager tournamentManager=new TournamentManager();
        List<Tournament> createdTournament=tournamentManager.getTournamentDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(createdTournament));
    }
}
