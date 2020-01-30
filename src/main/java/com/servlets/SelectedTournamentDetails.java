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


@WebServlet(name="SelectedTournament",urlPatterns = "/SelectedTournament")
public class SelectedTournamentDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");
        TournamentManager tournamentManager=new TournamentManager();
        String query="select *from TOURNAMENT where TOURNAMENT_ID="+tournamentId;
        List<Tournament> selectedTournament=tournamentManager.getTournamentDetails(query);
        Gson gson=new Gson();
        resp.getWriter().write(gson.toJson(selectedTournament));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentID=Integer.parseInt(req.getParameter("tournamentID"));
        int win=Integer.parseInt(req.getParameter("win"));
        int loss=Integer.parseInt(req.getParameter("loss"));
        int bye=Integer.parseInt(req.getParameter("bye"));
        int draw=Integer.parseInt(req.getParameter("draw"));
        int totalRounds=Integer.parseInt(req.getParameter("totalRounds"));
        HttpSession session = req.getSession(false);
        session.setAttribute("TournamentID",tournamentID);
        session.setAttribute("win",win);
        session.setAttribute("loss",loss);
        session.setAttribute("bye",bye);
        session.setAttribute("draw",draw);
        session.setAttribute("totalRounds",totalRounds);


    }
}
