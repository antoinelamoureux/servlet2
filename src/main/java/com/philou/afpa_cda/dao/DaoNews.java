package com.philou.afpa_cda.dao;

import com.philou.afpa_cda.beans.News;
import com.philou.afpa_cda.beans.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philou
 */
public class DaoNews implements Dao<News>{
    
    private Connection cnx;

    public DaoNews(Connection cnx) {
        this.cnx = cnx;
    }
    
    

    @Override
    public List<News> liste() {
        
        List<News> liste = new ArrayList();
        
        String sql = "SELECT news.id_news, news.titre, news.content, news.date_pub, news.theme, themes.libelle "
                + "FROM news "
                + "INNER JOIN themes "
                + "ON news.theme = themes.id_theme ORDER BY themes.libelle, news.titre";
        
        // Avec les 'try-with-ressources'
        // Les classes qui implantent l'interface autoCloseable sont acceptées comme ressources
        try(
            PreparedStatement ps = cnx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ){
	
	while(rs.next()) {
		News obj = new News();
                obj.setIdNews(rs.getLong("id_news")); // nom de la colonne
                obj.setTitre(rs.getString("titre"));				
                obj.getTheme().setLibelle(rs.getString("libelle"));				
                obj.setDateCreation(rs.getTimestamp("date_pub").toLocalDateTime());
                //System.out.println("222222222222222222 "+obj.getDateCreation().toString());
		liste.add(obj);		
	}
	
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
        
        return liste;
    }

    @Override
    public boolean insert(News obj) {
        
        String sql = "INSERT INTO news (titre, content, theme) VALUES (?,?,?)";

        try(PreparedStatement ps = cnx.prepareStatement(sql);){

        ps.setString(1, obj.getTitre());
        ps.setString(2, obj.getContent());
        ps.setLong(3, obj.getTheme().getIdTheme());

        int i = ps.executeUpdate();
        
        if(i == 1) {
                return true;
        }
        }
        catch (SQLException e) {
        System.out.println(e.getMessage());
        }
	
    return false;
        
        
    }

    @Override
    public News find(Long id) {
        

        
        News obj = new News();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT news.id_news, news.titre, news.content, news.date_pub, news.theme, themes.id_theme, themes.libelle "
                + "FROM news "
                + "INNER JOIN themes "
                + "ON news.theme = themes.id_theme WHERE id_news=?";

        try{
            ps = cnx.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                obj.setIdNews(rs.getLong("id_news"));
                obj.setTitre(rs.getString("titre"));
                obj.setContent(rs.getString("content"));
                obj.getTheme().setIdTheme(rs.getLong("id_theme"));
                obj.getTheme().setLibelle(rs.getString("libelle"));
            }		
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if(ps != null | rs != null) {
            try {
                ps.close();
                rs.close();
            }
            catch(SQLException e) {
            System.out.println(e.getMessage());	                        
            }
            }
        }
    return obj;
        
    }

    @Override
    public boolean update(News obj) {
        
        System.out.println(obj);
        
        String sql = "UPDATE news SET titre=?, content=?, theme=? WHERE id_news=?";

        try(PreparedStatement ps = cnx.prepareStatement(sql);){

        ps.setString(1, obj.getTitre());
        ps.setString(2, obj.getContent());
        ps.setLong(3, obj.getTheme().getIdTheme());
        ps.setLong(4, (obj.getIdNews()));

        int i = ps.executeUpdate();

        if(i >= 1) {
                return true;
        }		

        } catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println(e.getMessage());
        }

    return false;
        
    }

    @Override
    public void delete(Long id) {
        
        String sql = "DELETE FROM news WHERE id_news=?";

        try(PreparedStatement ps = cnx.prepareStatement(sql);)
        {

        ps.setLong(1, id);
        ps.executeUpdate();

        } 
        catch (SQLException e) {
        System.out.println(e.getMessage());
    }
          
    }
    
        public List<Tag> allTagsForTheNews(Long id){
            
            List<Tag> liste = new ArrayList();
            
                String sql = "SELECT tags.id_tag, tags.libelle "
                + "FROM news_tags "
                + "INNER JOIN tags "
                + "ON news_tags.tag = tags.id_tag WHERE news_tags.news = ?";
                
                try(
                        PreparedStatement ps = cnx.prepareStatement(sql);
                    ){
                    
                    // On valorise l'attribut de requète SQL
                    ps.setLong(1, id);
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()) {
                    Tag obj = new Tag();
                    obj.setIdTag(rs.getLong("id_tag")); // nom de la colonne
                    obj.setLibelle(rs.getString("libelle"));				
                    liste.add(obj);		
                    }
                    
                } 
                catch(SQLException sqle){
                    System.out.println("sqle" + sqle.getMessage());
                }
            
            return liste;
            
        }
        
        public void insertTags(Long id, String[] tagsForNews){
            
            // On supprime toutes les associations
            String sql = "DELETE FROM news_tags WHERE news_tags.news = ?";
    
            try{
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
            
            // On insère les tags (si il y en a)
            if(tagsForNews != null){
            sql = "INSERT INTO news_tags (news, tag) VALUES (?, ?)";
    
            try{
                PreparedStatement ps = cnx.prepareStatement(sql);

                for(String idTag : tagsForNews){
                    ps.setLong(1, id);
                    ps.setLong(2, Long.parseLong(idTag));
                    ps.executeUpdate();  
                }
                
            } 
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        }
        
    public List<News> getNewsforTag(long idTag) {
        List<News> liste = new ArrayList();
        
        String sql = "SELECT news.id_news, news.titre, news.content, news.date_pub, news.theme "
                + "FROM news "
                + "INNER JOIN news_tags "
                + "ON news.id_news = news_tags.news "
                + "INNER JOIN tags "
                + "ON news_tags.tag = tags.id_tag "
                + "WHERE news_tags.tag = ?";
                
                try(
                        PreparedStatement ps = cnx.prepareStatement(sql);
                    ){
                    
                    // On valorise l'attribut de requète SQL
                    ps.setLong(1, idTag);
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()) {
                    News news = new News();
                    news.setIdNews(rs.getLong("id_news"));
                    news.setTitre(rs.getString("titre"));
                    news.setContent(rs.getString("content"));
                    //news.getTheme().setIdTheme(rs.getLong("id_theme"));
                    //news.getTheme().setLibelle(rs.getString("libelle"));
                    liste.add(news);
            }
                    
                } 
                catch(SQLException sqle){
                    System.out.println("sqle" + sqle.getMessage());
                }
            
            return liste;
            
        }
    
    public List<News> getNewsforTheme(long idTheme) {
        List<News> liste = new ArrayList();
        
        String sql = "SELECT news.id_news, news.titre, news.content, news.date_pub, news.theme "
                + "FROM news "
                + "INNER JOIN themes "
                + "ON news.theme = themes.id_theme "
                + "WHERE themes.id_theme = ?";
                
                try(
                        PreparedStatement ps = cnx.prepareStatement(sql);
                    ){
                    
                    // On valorise l'attribut de requète SQL
                    ps.setLong(1, idTheme);
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()) {
                    News news = new News();
                    news.setIdNews(rs.getLong("id_news"));
                    news.setTitre(rs.getString("titre"));
                    news.setContent(rs.getString("content"));
                    //news.getTheme().setIdTheme(rs.getLong("id_theme"));
                    //news.getTheme().setLibelle(rs.getString("libelle"));
                    liste.add(news);
            }
                    
                } 
                catch(SQLException sqle){
                    System.out.println("sqle" + sqle.getMessage());
                }
            
            return liste;
            
        }
    
    public List<News> getNewsBySearch(String result) {
        List<News> liste = new ArrayList();
        
        String sql = "SELECT news.id_news, news.titre, news.content, news.date_pub, news.theme "
                + "FROM news "
                + "WHERE news.titre LIKE ?";
                
                try(
                        PreparedStatement ps = cnx.prepareStatement(sql);
                    ){
                    
                    // On valorise l'attribut de requète SQL
                    ps.setString(1, "%" + result + "%");
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()) {
                    News news = new News();
                    news.setIdNews(rs.getLong("id_news"));
                    news.setTitre(rs.getString("titre"));
                    news.setContent(rs.getString("content"));
                    //news.getTheme().setIdTheme(rs.getLong("id_theme"));
                    //news.getTheme().setLibelle(rs.getString("libelle"));
                    liste.add(news);
            }
                    
                } 
                catch(SQLException sqle){
                    System.out.println("sqle" + sqle.getMessage());
                }
            
            return liste;
                    
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
