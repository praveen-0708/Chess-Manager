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


@WebServlet(name="SelectedTournament",urlPatterns = "/SelectedTournament")
public class SelectedTournamentDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId=Integer.parseInt(req.getParameter("ID"));
        TournamentManager tournamentManager=new TournamentManager();
        String query="select *from Tournament where TournamentId="+tournamentId;
        List<Tournament> selectedTournament=tournamentManager.getTournamentDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(selectedTournament));

    }
}
