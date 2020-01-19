package com.servlets;

import com.chess.Tournament;
import com.chess.TournamentManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="createdTournaments",urlPatterns = "/created")
public class CreatedTournamentServlets extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int TournamentId= Integer.parseInt(req.getParameter("ID"));
        String query="select * from Tournament where CREATED_BY="+TournamentId+"";

        TournamentManager tournamentManager=new TournamentManager();
        List<Tournament> createdTournament=tournamentManager.getTournamentDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(createdTournament));
    }
}
