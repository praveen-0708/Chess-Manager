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

@WebServlet(name="joined",urlPatterns = "/joined")
public class JoinedTournamentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId= Integer.parseInt(req.getParameter("ID"));
        String query="select *from Tournament t,PlayersIn p where p.ID="+playerId+" and t.TournamentID=p.TournamentID";
        TournamentManager tournamentManager=new TournamentManager();
        List<Tournament> joinedTournaments=tournamentManager.getTournamentDetails(query);

        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(joinedTournaments));
    }
}
