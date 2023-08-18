package com.jkr.registration;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "register", value = "/register")
public class Servlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uname= request.getParameter("name");
        String uemail= request.getParameter("email");
        String upwd= request.getParameter("pass");
        String uphone= request.getParameter("contact");
        RequestDispatcher dispatcher= null;
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             con= DriverManager.getConnection("jdbc:mysql://localhost:3306/signup", "root", "7412369");
            PreparedStatement pst= con.prepareStatement("insert into users(uname, upwd, uemail, uphone) values(?,?,?,?)");

            pst.setString(1, uname);
            pst.setString(2, upwd);
            pst.setString(3, uemail);
            pst.setString(4, uphone);

            int rowCount=pst.executeUpdate();
            dispatcher=request.getRequestDispatcher("registration.jsp");
          if(rowCount>0){

              request.setAttribute("status", "success");
          }else{
              request.setAttribute("status", "failed");
          }
          dispatcher.forward(request,response);
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            try {
                assert con != null;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
