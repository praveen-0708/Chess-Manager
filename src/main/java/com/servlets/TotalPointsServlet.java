package com.servlets;

import com.chess.MatchManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="calculateTotalPoints",urlPatterns = "/calculateTotalPoints")
public class TotalPointsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatchManager matchManager=new MatchManager();
        matchManager.calculateTotalPoints(1,11);
    }
}
