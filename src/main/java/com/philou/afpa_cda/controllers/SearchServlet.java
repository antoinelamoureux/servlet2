/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.controllers;

import com.philou.afpa_cda.beans.News;
import com.philou.afpa_cda.dao.DaoNews;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    private Connection cnx;
    private List<News> results;
    private DaoNews daoNews;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cnx = (Connection)getServletContext().getAttribute("connexion");
        daoNews = new DaoNews(cnx); 
        
        String searchQuery = request.getParameter("search");
        List<News> searchResults = daoNews.getNewsBySearch(searchQuery);
        
        request.setAttribute("searchQuery", searchQuery);     
        request.setAttribute("searchResults", searchResults);
        
        request.getRequestDispatcher("search/searchview.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
