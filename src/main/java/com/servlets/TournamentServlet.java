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

@WebServlet(name="createTournament",urlPatterns = "/createTournament")
public class TournamentServlet extends HttpServlet {
    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name=req.getParameter("name");
        String dateRange=req.getParameter("daterange");
        String locationInput=req.getParameter("locationInput");
        int rounds=Integer.parseInt(req.getParameter("rounds"));
        String duration=req.getParameter("duration");
        int Win=Integer.parseInt(req.getParameter("Win"));
        int Loss=Integer.parseInt(req.getParameter("Loss"));
        int BYE=Integer.parseInt(req.getParameter("BYE"));
        int Draw=Integer.parseInt(req.getParameter("Draw"));
        //int createdBy=Integer.parseInt(req.getParameter("createdBy"));
        HttpSession session = req.getSession(false);
        int createdBy=(Integer)session.getAttribute("userId");


        TournamentManager tournamentManager =new TournamentManager();
        tournamentManager.addTournament(name,dateRange,locationInput,rounds,duration,Win,Loss,BYE,Draw,createdBy);
        resp.getWriter().write("success");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int createdBy=(Integer)session.getAttribute("userId");
        TournamentManager tournamentManager =new TournamentManager();
        String query="select *from TOURNAMENT where NOT CREATED_BY="+createdBy;
        List<Tournament> allTournaments=tournamentManager.getTournamentDetails(query);

        Gson gson = new Gson();

        resp.getWriter().write(gson.toJson(allTournaments));

    }
}
