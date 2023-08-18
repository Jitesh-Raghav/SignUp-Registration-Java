package com.jkr.registration;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "login", value = "/Login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/signup", "root", "7412369");
            PreparedStatement pst= con.prepareStatement("select * form users where uemai = ? and upwd = ?");
            pst.setString(1, uemail);
            pst.setString(2, upwd);

            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                session.setAttribute("name", rs.getString("unamel"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            }else{
                request.setAttribute("status", "failed");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            }


            //dispatcher.forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
