package org.totalbeginner.myJDBC;

import java.security.*;
import java.sql.*;

public class login {

    private static ResultSet resultSet = null;

    public static ResultSet getLoginInfo(ResultSet resultSet) throws SQLException {
        
        while (resultSet.next()) {

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");

            System.out.println("username:  " + username + "\npassword:  " + password 
                    + "\nsalt:  " + salt);
            
            
        }
        
        return resultSet;
    }

    private static String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
