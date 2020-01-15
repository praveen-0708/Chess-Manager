package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name="login",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email=req.getParameter("email");
        String password=req.getParameter("password");
        resp.setContentType("text/plain");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "admin", "admin");
            Statement statement=connection.createStatement();
            String query="select *from users where Email='"+email+"' and password='"+password+"'";

            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                resp.getWriter().write("success");
            }
            else{
                resp.getWriter().write("failure");
            }


        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
