package com.servlets;

import com.database.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name="login",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email=req.getParameter("email");
        String password=req.getParameter("password");
        resp.setContentType("text/plain");

        String query="select * from USER where EMAIL=? and PASSWORD=MD5(?)";
        DatabaseConnection databaseConnection=new DatabaseConnection();

        try {
            PreparedStatement preparedStatement=databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet rs = databaseConnection.selectQuery(preparedStatement);
            int ID=0;
            while(rs.next())
                ID=rs.getInt(1);
            if (ID!=0) {
                HttpSession oldSession = req.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", ID);
                resp.getWriter().write(String.valueOf(ID));
            }
            else{
                resp.getWriter().write("failure");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            databaseConnection.closeConnection();
        }

    }
}
