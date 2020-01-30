package com.servlets;

import com.chess.PlayerRegistration;
import com.database.DatabaseConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name="register",urlPatterns = "/register")

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //System.out.println(req.getParameter("eid"));
        String name=req.getParameter("name");
        String email=req.getParameter("email");
        String password=req.getParameter("password");

        DatabaseConnection databaseConnection=new DatabaseConnection();
        String query="select *from USER where EMAIL='"+email+"'";
        ResultSet rs=databaseConnection.selectQuery2(query);
        try{
            if(!rs.next()){
                PlayerRegistration registration=new PlayerRegistration();
                int returnValue=registration.addUser(name,email,password);
                if(returnValue==1)
                    resp.getWriter().write("registered");
                else if(returnValue==0)
                    resp.getWriter().write("Failed");
            }else{
                resp.getWriter().write("exists");
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }

    }
}
