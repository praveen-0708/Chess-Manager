package com.servlets;

import com.chess.PairingManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="updateScore",urlPatterns = "/updateScore")
public class UpdateScoreServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String case1=req.getParameter("case");
        if(case1.equals("NOBYE"))
            addScoreToDBWithoutBye(req);
        else if(case1.equals("BYE"))
            addScoreWithBye(req);

        resp.getWriter().write("success");
    }

    public void addScoreToDBWithoutBye(HttpServletRequest req){
        int playerId1=Integer.parseInt(req.getParameter("playerID1"));
        int tournamentId=Integer.parseInt(req.getParameter("tournamentID"));
        int points1=Integer.parseInt(req.getParameter("points1"));
        int roundNumber=Integer.parseInt(req.getParameter("roundNumber"));

        int playerId2=Integer.parseInt(req.getParameter("playerID2"));
        int points2=Integer.parseInt(req.getParameter("points2"));
        String result=req.getParameter("result");

        //PlayerManager playerManager=new PlayerManager();
        //int totalPoints1=playerManager.getTotalPointsOfAPlayer(playerId1,tournamentId);
        //int totalPoints2=playerManager.getTotalPointsOfAPlayer(playerId2,tournamentId);
        PairingManager pairingManager=new PairingManager();
        pairingManager.updateScore(playerId1,playerId2,tournamentId,roundNumber,points1,points2,result);


    }
    public void addScoreWithBye(HttpServletRequest req){
        int playerId=Integer.parseInt(req.getParameter("playerID"));
        int tournamentId=Integer.parseInt(req.getParameter("tournamentID"));
        int points=Integer.parseInt(req.getParameter("points"));
        int roundNumber=Integer.parseInt(req.getParameter("roundNumber"));
        String result=req.getParameter("result");



        PairingManager pairingManager=new PairingManager();
        pairingManager.updateScore(playerId,0,tournamentId,roundNumber,points,0,result);

    }
}
