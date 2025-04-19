/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author bikash
 */
public class UsersDao {

    public static boolean validate(String name, String password) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();
            String select = "SELECT UserPass FROM Users WHERE UserName = ?";
            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("UserPass");
                status = BCrypt.checkpw(password, hashedPassword);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean CheckIfAlready(String UserName) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();
            String select = "select * from Users where UserName= ?";
            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, UserName);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int AddUser(String User, String UserPass, String UserEmail, String Date) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            String hashedPassword = BCrypt.hashpw(UserPass, BCrypt.gensalt(12));
            String insert = "INSERT INTO Users (UserName, UserPass, Email, RegDate) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1, User);
            ps.setString(2, hashedPassword);
            ps.setString(3, UserEmail);
            ps.setString(4, Date);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

}
