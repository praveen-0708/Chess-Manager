package com.servlets;

import com.chess.Match;
import com.chess.PairingManager;
import com.chess.PlayerManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="pairing",urlPatterns = "/pairing")
public class PairingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roundNumber=Integer.parseInt(req.getParameter("round"));
        int tournamentId=Integer.parseInt(req.getParameter("ID"));

        PairingManager pairingManager=new PairingManager();
        List<Match> matchList=pairingManager.pairing(tournamentId,roundNumber);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(matchList));
    }

}
