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

@WebServlet(name="createTournament",urlPatterns = "/createTournament")
public class TournamentServlet extends HttpServlet {
    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String name=req.getParameter("name");
        String dateRange=req.getParameter("daterange");
        String locationInput=req.getParameter("locationInput");
        int rounds=Integer.parseInt(req.getParameter("rounds"));
        String duration=req.getParameter("duration");
        int Win=Integer.parseInt(req.getParameter("Win"));
        int Loss=Integer.parseInt(req.getParameter("Loss"));
        int BYE=Integer.parseInt(req.getParameter("BYE"));
        int Draw=Integer.parseInt(req.getParameter("Draw"));
        int createdBy=Integer.parseInt(req.getParameter("createdBy"));

        TournamentManager tournamentManager =new TournamentManager();
        tournamentManager.addTournament(name,dateRange,locationInput,rounds,duration,Win,Loss,BYE,Draw,createdBy);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TournamentManager tournamentManager =new TournamentManager();
        String query="select *from Tournament";
        List<Tournament> allTournaments=tournamentManager.getTournamentDetails(query);

        Gson gson = new Gson();

        resp.getWriter().write(gson.toJson(allTournaments));

    }
}
