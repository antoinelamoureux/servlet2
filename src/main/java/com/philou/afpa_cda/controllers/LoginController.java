/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.controllers;

import com.philou.afpa_cda.beans.User;
import com.philou.afpa_cda.dao.DaoUser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private Connection cnx;
    private DaoUser daoUser;
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("users/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cnx = (Connection)getServletContext().getAttribute("connexion");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        daoUser = new DaoUser(cnx);
        user = daoUser.checkLogin(username, password);
        
        String view = "";
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            System.out.println(user.getFirstName());
            view = "/home/home.jsp";
        } else { 
            String message = "Invalid username or password";
            request.setAttribute("message", message);
            
            view = "users/error.jsp";
        }
            request.getRequestDispatcher(view).forward(request, response);
        }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
