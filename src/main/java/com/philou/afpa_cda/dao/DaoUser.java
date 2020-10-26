/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.dao;

import com.philou.afpa_cda.beans.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 77011-53-17
 */
public class DaoUser implements Dao<User> {

    private Connection cnx;

    public DaoUser(Connection con) {
        this.cnx = con;
    }

    @Override
    public List<User> liste() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(User obj) {
       String sql = "INSERT INTO users (lastname, firstname, adresse, email, username, password) VALUES (?,?,?,?,?,?)";

        try(PreparedStatement ps = cnx.prepareStatement(sql);){
            
        ps.setString(1, obj.getLastName());
        ps.setString(2, obj.getFirstName());
        ps.setString(3, obj.getAdresse());
        ps.setString(4, obj.getEmail());
        ps.setString(5, obj.getUsername());
        ps.setString(6, obj.getPassword());

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
    public User find(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User checkLogin(String username, String password) {
        User user = new User();

        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        try (
                PreparedStatement ps = cnx.prepareStatement(sql);) {

            // On valorise l'attribut de requète SQL
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                user.setIdUser(rs.getLong("id_user"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAdresse(rs.getString("adresse"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle" + sqle.getMessage());
        }
        return user;

    }

}
