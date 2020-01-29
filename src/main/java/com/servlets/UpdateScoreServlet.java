package com.servlets;

import com.chess.PairingManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");
        int win=(Integer)session.getAttribute("win");
        int loss=(Integer)session.getAttribute("loss");
        int draw=(Integer)session.getAttribute("draw");

        int playerId1=Integer.parseInt(req.getParameter("playerID1"));
        //int points1=Integer.parseInt(req.getParameter("points1"));
        int roundNumber=Integer.parseInt(req.getParameter("roundNumber"));

        int playerId2=Integer.parseInt(req.getParameter("playerID2"));
        //int points2=Integer.parseInt(req.getParameter("points2"));
        String result=req.getParameter("result");
        int points1=0,points2=0;
        if(result.equals("PLAYER_1_WON")){
            points1+=win;
            points2+=loss;
        }
        else if(result.equals("PLAYER_2_WON")){
            points1+=loss;
            points2+=win;
        }
        else if(result.equals("DRAW")){
            points1+=draw;
            points2+=draw;
        }
        PairingManager pairingManager=new PairingManager();
        pairingManager.updateScore(playerId1,playerId2,tournamentId,roundNumber,points1,points2,result);


    }
    public void addScoreWithBye(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        int tournamentId=(Integer)session.getAttribute("TournamentID");
        int bye=(Integer)session.getAttribute("bye");
        int playerId=Integer.parseInt(req.getParameter("playerID"));

        int points=0;
        points+=bye;
        int roundNumber=Integer.parseInt(req.getParameter("roundNumber"));
        String result=req.getParameter("result");

        PairingManager pairingManager=new PairingManager();
        pairingManager.updateScore(playerId,0,tournamentId,roundNumber,points,0,result);

    }
}
