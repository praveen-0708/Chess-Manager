package com.servlets;

import com.chess.Match;
import com.chess.PairingManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="paired",urlPatterns = "/paired")
public class PairedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roundNumber=Integer.parseInt(req.getParameter("round"));
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");

        PairingManager pairingManager=new PairingManager();
        List<Match> matchList=pairingManager.paired(tournamentId,roundNumber);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(matchList));
    }
}
