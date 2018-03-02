package be.livingsmart.eindwerk;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pieter
 */
public class SmtpAuthenticator extends Authenticator {
    public SmtpAuthenticator() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
     
        String username = "HDRPointOfSale@gmail.com";
        String password = "PointOfSaleHDR2018";
        
        if ((username != null) && (username.length() > 0) && (password != null) 
          && (password.length   () > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}