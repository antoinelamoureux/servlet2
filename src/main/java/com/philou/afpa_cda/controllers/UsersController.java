/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.controllers;

import java.io.IOException;
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
@WebServlet(name = "UsersController", urlPatterns = {"/users"})
public class UsersController extends HttpServlet {
    private HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getParameter("register") != null) {
            
        } else if (request.getParameter("login") != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            
            request.getRequestDispatcher("home").forward(request, response);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
