package com.servlets;

import com.chess.Player;
import com.chess.PlayerManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name="PlayerDetails",urlPatterns = "/PlayerDetails")
public class PlayerDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId=Integer.parseInt(req.getParameter("ID"));
        String query="select *from users u,PlayersIn p where p.TournamentID="+tournamentId+" and u.ID=p.ID";
        PlayerManager playerManager=new PlayerManager();
        List<Player> players=playerManager.getPlayerDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(players));
    }
}
