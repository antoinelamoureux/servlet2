/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philou.afpa_cda.controllers;

import com.philou.afpa_cda.beans.User;
import com.philou.afpa_cda.dao.DaoUser;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 77011-53-17
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private Connection cnx;
    private DaoUser daoUser;
    private User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        request.getRequestDispatcher("login/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lastName = request.getParameter("lastname");
        String firstName = request.getParameter("firstname");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String passwordHash = hashPassword(password);
       
        user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setAdresse(adresse);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordHash);
        
        daoUser = new DaoUser(cnx);
        daoUser.insert(user);
        
        request.getRequestDispatcher("login").forward(request, response);
    }

    public String hashPassword(String password) {
        String passwordHash = "";
        
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            passwordHash = new String (hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passwordHash;
    }
    
}
