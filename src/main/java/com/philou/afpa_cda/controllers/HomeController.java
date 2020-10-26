/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.controllers;

import com.philou.afpa_cda.beans.News;
import com.philou.afpa_cda.beans.Tag;
import com.philou.afpa_cda.dao.DaoNews;
import com.philou.afpa_cda.dao.DaoTag;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
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
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    
    private Connection cnx;
    private List<News> listeNews;
    private List<Tag> listeTags;
    private DaoNews daoNews;
    private List<Tag> allTags;
    private DaoTag daoTags;
    private boolean isNewsForTag;
    private boolean isNewsForTheme;
    private final String SECTION = "SECTION-HOME";
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("users/login.jsp").forward(request, response);
        }
        
        cnx = (Connection)getServletContext().getAttribute("connexion");
        daoNews = new DaoNews(cnx); 
        daoTags = new DaoTag(cnx);
        listeNews = new ArrayList();
        listeTags = new ArrayList();
        isNewsForTag = false;
        
        listeNews = daoNews.liste();
        request.setAttribute("listeNews", listeNews);
        
        listeTags = getRandomTags(daoTags.liste());
        request.setAttribute("listeTags", listeTags);
        
        request.setAttribute("section", SECTION);
        
        //String action = request.getParameter("action");
        //request.setAttribute("action", action);
        
        if ("1".equals(request.getParameter("action"))) {
            List<News> listeNewsForTag = new ArrayList();
          
            Long idTag = -1L;
            
            if(request.getParameter("id") != null) idTag = Long.parseLong(request.getParameter("id"));
            
            listeNewsForTag = daoNews.getNewsforTag(idTag);

            if (listeNewsForTag != null) isNewsForTag = true;
            
            request.setAttribute("listeNewsForTag", listeNewsForTag);
            request.setAttribute("isNewsForTag", isNewsForTag);
            
        } else if ("2".equals(request.getParameter("action"))) {
            List<News> listeNewsForTheme = new ArrayList();
            
            Long idTheme = -1L;
            
            if(request.getParameter("id") != null) idTheme = Long.parseLong(request.getParameter("id"));
        
            listeNewsForTheme = daoNews.getNewsforTheme(idTheme);
            
            if (listeNewsForTheme != null) isNewsForTheme = true;
            
            String theme = request.getParameter("theme");
                   
            request.setAttribute("theme", theme);
            request.setAttribute("listeNewsForTheme", listeNewsForTheme);
            request.setAttribute("isNewsForTheme", isNewsForTheme);
        }
        
        request.getRequestDispatcher("home/home.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public List<Tag> getRandomTags(List<Tag> entriesTags) {
        List<Tag> listeTags = new ArrayList();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<5; i++) {
            list.add(i);
        }
        
        Collections.shuffle(list);
        for (int i = 0; i < 5; i++) {
            listeTags.add(entriesTags.get(list.get(i)));
        }
        
        return listeTags;
    }
}
