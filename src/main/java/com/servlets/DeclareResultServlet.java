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

@WebServlet(name="result",urlPatterns = "/result")
public class DeclareResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId=Integer.parseInt(req.getParameter("tournamentID"));
        PlayerManager playerManager=new PlayerManager();
        String query="select *from users u,PlayersIn p where p.tournamentId="+tournamentId+" and u.ID=p.ID order by TOTAL_POINTS desc";
        List<Player> players=playerManager.getResultPlayerDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(players));

    }
}
