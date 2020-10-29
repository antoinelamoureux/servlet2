package com.philou.afpa_cda.controllers;

import com.philou.afpa_cda.beans.News;
import com.philou.afpa_cda.beans.Tag;
import com.philou.afpa_cda.beans.Theme;
import com.philou.afpa_cda.dao.DaoNews;
import com.philou.afpa_cda.dao.DaoTag;
import com.philou.afpa_cda.dao.DaoTheme;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "NewsController", urlPatterns = {"/news"})
public class NewsController extends HttpServlet {
    //
    private Connection cnx;
        private List<News> liste;
    private List<Tag> allTags;
    private List<Tag> listeTagsForZeNews;
    List<Theme> allThemes;
    private News news;
    private DaoNews dao;
    private DaoTheme daoTheme;
    private DaoTag daoTag;
    private final String SECTION = "SECTION-NEWS";

//------------------------------------------------------------------------------
//-- doGet(HttpServletRequest request, HttpServletResponse response)
//------------------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Trace
        System.out.println("NewsController::DOGET");
        
        // On attrape la connexion sql crée et mise dans une variable d'application dans EntranceServlet::init()
        cnx = (Connection)getServletContext().getAttribute("connexion");
        
        // Business
        liste = new ArrayList();
        allThemes = new ArrayList();
        allTags = new ArrayList();
        listeTagsForZeNews = new ArrayList();
        dao = new DaoNews(cnx); // Un objet dao pour News
        daoTheme = new DaoTheme(cnx); // Un objet dao pour Theme
        daoTag = new DaoTag(cnx); // Un objet dao pour Theme
        news = new News(); // Un objet News
        Long id = -1L; // Un id
        String view = ""; // La JSP finale
        
        // Gestion de l'action
        // Par défaut
        String action = "0";
        // Présence param http action ?
        if(request.getParameter("action") != null){
            // On écrase l'action par défaut
            action = request.getParameter("action");      
        }
        // On stocke l'action dans un attribut de requète (lisible par la page web)
        request.setAttribute("action", action);
        
        // Gestion de la section
        // On stocke la section dans un attribut de requète (lisible par la page web)
        request.setAttribute("section", SECTION);
        
        // Quelle action ?
        switch(action){
            
            // action 0 (liste)
            case "0":
                liste = dao.liste();
                request.setAttribute("listeNews", liste);
                view = "news/liste.jsp";
            break;
            
            // action 1 (création)
            case "1":
                allThemes = daoTheme.liste();
                request.setAttribute("allThemes", allThemes);
                view = "news/create.jsp";
            break;
            
            // action 2 (modification)
            case "2":
                if(request.getParameter("id") != null) id = Long.parseLong(request.getParameter("id"));
                news = dao.find(id);
                request.setAttribute("news", news);
                
                allThemes = daoTheme.liste();
                request.setAttribute("allThemes", allThemes);
                
                view = "news/modify.jsp";
            break;
            
            // action 3 (suppression)
            case "3":
                if(request.getParameter("id") != null) id = Long.parseLong(request.getParameter("id"));
                news = dao.find(id);
                request.setAttribute("news", news);
                
                view = "news/delete.jsp";
            break;
            // action 4 (gestion des news)
            case "4":
                
                if(request.getParameter("id") != null) id = Long.parseLong(request.getParameter("id"));
                
                // Liste ALL tags
                allTags = daoTag.liste();
                // ON PEUT VOIR DES TRACES DURANT UNE REQUETE HTTP INVISIBLES DEPUIS LE CONTEXTE DE LA REQUETE HTTP ELLE-MEME
                System.out.println("ALL TAGS ===>  "+allTags);
                request.setAttribute("allTagsJSP", allTags);
                
                // Liste TAGS for the news
                listeTagsForZeNews = dao.allTagsForTheNews(id);
                System.out.println("TAGS FOR ZE NEWS===>  "+listeTagsForZeNews);
                request.setAttribute("listeTagsForZeNewsJSP", listeTagsForZeNews);
                
                // Quelle News ?
                news = dao.find(id);
                //news.setListeTags(listeTagsForZeNews); // could have been so
                request.setAttribute("news", news);
                
                
                intersection2(allTags, listeTagsForZeNews);
                
                
                view = "news/news_tags.jsp";
            break;
            case "5": 
                if(request.getParameter("id") != null) id = Long.parseLong(request.getParameter("id"));
                news = dao.find(id);
                request.setAttribute("news", news);
                
                listeTagsForZeNews = dao.allTagsForTheNews(id);
                request.setAttribute("listeTagsForNews", listeTagsForZeNews);
                
                view = "news/details.jsp";
                break;
                
        }
        
        // On forward la requète
        request.getRequestDispatcher(view).forward(request, response); 

    }

//------------------------------------------------------------------------------
//-- doPost(HttpServletRequest request, HttpServletResponse response)
//-----------------------------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Trace
        System.out.println("NewsController::DOPOST");
        
        // On attrape la connexion sql crée et mise dans une variable d'application dans EntranceServlet::init()
        cnx = (Connection)getServletContext().getAttribute("connexion");
        
        // Business
        liste = new ArrayList();
        dao = new DaoNews(cnx); // Un objet dao pour News
        daoTheme = new DaoTheme(cnx); // Un objet dao pour Theme
        news = new News(); // Un objet News
        Long id = -1L; // Un id
        String view = ""; // La JSP finale
        
        // Gestion de l'action
        // Par défaut
        String action = "0";
        // Présence param http action ?
        if(request.getParameter("action") != null){
            // On écrase l'action par défaut
            action = request.getParameter("action");      
        }
        // On stocke l'action dans un attribut de requète (lisible par la page web)
        request.setAttribute("action", action);
        
        // Gestion de la section
        // On stocke la section dans un attribut de requète (lisible par la page web)
        request.setAttribute("section", SECTION);
        
        // Which action ?
        switch(action){
            
            // action 1 (création)
            case "1": 
            // On récupère les données du form
            if(request.getParameter("titre") != null) news.setTitre(request.getParameter("titre"));
            if(request.getParameter("content") != null) news.setContent(request.getParameter("content"));
            if(request.getParameter("theme") != null) news.getTheme().setIdTheme(Integer.parseInt(request.getParameter("theme")));

            // On insère en BD
            dao.insert(news);

            // On raffraichit la liste
            liste = dao.liste();
            request.setAttribute("listeNews", liste);
            view = "news/liste.jsp";
            break;
            
            case "2": 
            // On récupère les données du form
            if(request.getParameter("id") != null) news.setIdNews( Long.parseLong(request.getParameter("id")));
            if(request.getParameter("titre") != null) news.setTitre(request.getParameter("titre"));
            if(request.getParameter("content") != null) news.setContent(request.getParameter("content"));
            if(request.getParameter("theme") != null) news.getTheme().setIdTheme(Integer.parseInt(request.getParameter("theme")));
            //System.out.println("news" + news);
            // On update
            dao.update(news);

            // On raffraichit la liste
            liste = dao.liste();
            request.setAttribute("listeNews", liste);
            view = "news/liste.jsp";
            break;
            
            // action 3 (suppression)
            case "3":
            // On récupère les données du form
            if(request.getParameter("id") != null) 
                dao.delete(Long.parseLong(request.getParameter("id")));
            // On raffraichit la liste
            liste = dao.liste();
            request.setAttribute("listeNews", liste);
            view = "news/liste.jsp";
            break;
            
            // action 3 (gestion des tags)
            case "4":
            if(request.getParameter("id") != null) id = Long.parseLong(request.getParameter("id"));
            
            String[] tagsForNews = null;
            
            if(request.getParameterValues("liste2")!=null){
                tagsForNews = request.getParameterValues("liste2");

            }
            
            dao.insertTags(id, tagsForNews);
            
            // On raffraichit la liste
            liste = dao.liste();
            request.setAttribute("listeNews", liste);
            view = "news/liste.jsp";
            
            break;
        }

        
        // On forward la requète
        request.getRequestDispatcher(view).forward(request, response);

    }
    
    
    
    public void intersection2(List<Tag> list1, List<Tag> list2) {

        List<Tag> tmp = new ArrayList<>();
        for(Tag t : list1){
            for(Tag t2 : list2){
                if(t2.equals(t)) {
                tmp.add(t);
                System.out.println("EQUALS !");
                }
            }
        }
        
        // ON NETTOIE 
        list1.removeAll(tmp);

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
