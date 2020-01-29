package com.servlets;

import com.chess.TournamentManager;
import com.database.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="getRounds",urlPatterns = "/getRounds")
public class RoundsCompletedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");
        int totalRounds=(Integer)session.getAttribute("totalRounds");
        TournamentManager tournamentManager=new TournamentManager();
        int roundsCompleted=tournamentManager.getRoundsCompleted(tournamentId);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("roundsCompleted",roundsCompleted);
        jsonObject.addProperty("totalRounds",totalRounds);
        Gson gson=new Gson();

        resp.getWriter().write(gson.toJson(jsonObject));
    }

}
