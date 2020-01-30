package com.servlets;

import com.models.Player;
import com.chess.PlayerManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="result",urlPatterns = "/result")
public class DeclareResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");
        PlayerManager playerManager=new PlayerManager();
        String query="select *from USER u,PLAYER_IN_TOURNAMENT p where p.TOURNAMENT_ID="+tournamentId+" and u.PLAYER_ID=p.PLAYER_ID order by TOTAL_POINTS desc";
        List<Player> players=playerManager.getResultPlayerDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(players));

    }
}
