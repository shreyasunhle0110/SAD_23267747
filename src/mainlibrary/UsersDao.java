/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Data Access Object for user-related operations.
 * Handles user validation, existence checks, and user creation.
 * 
 * @author bikash
 */
public class UsersDao {
    private static final Logger logger = LogManager.getLogger(UsersDao.class);

    /**
     * Validates a user's credentials by comparing the provided password
     * with the hashed password stored in the database.
     * 
     * @param name     The username to validate.
     * @param password The password to validate.
     * @return True if the credentials are valid, false otherwise.
     * @throws SQLException If a database access error occurs.
     * @see <a href="https://cwe.mitre.org/data/definitions/89.html">CWE-89: SQL Injection</a>
     */
    public static boolean validate(String name, String password) {
        boolean status = false;
        String query = "SELECT UserPass FROM Users WHERE UserName = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("UserPass");
                    status = BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            logger.error("Error during user validation: {}", e.getMessage());
        }
        return status;
    }

    /**
     * Checks if a user with the given username already exists in the database.
     *
     * @param userName The username to check.
     * @return True if the user exists, false otherwise.
     */
    public static boolean CheckIfAlready(String userName) {
        boolean status = false;
        String query = "SELECT 1 FROM Users WHERE UserName = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error during user existence check: " + e.getMessage());
        }
        return status;
    }

    

    /**
     * Adds a user to the database with hashed password.
     * 
     * @param username The username of the user.
     * @param password The plaintext password of the user.
     * @param email    The email of the user.
     * @param fullName The full name of the user.
     * @return True if the operation is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean addUser(String username, String password, String email, String fullName) throws SQLException {
        String hashedPassword = hashPassword(password);
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Users (UserName, UserPass, Email, FullName) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, email);
            ps.setString(4, fullName);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Hashes a password using SHA-256.
     * 
     * @param password The plaintext password to hash.
     * @return The hashed password as a hexadecimal string.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Validates a user's credentials by comparing the provided password
     * with the hashed password stored in the database.
     * 
     * @param username The username to validate.
     * @param password The plaintext password to validate.
     * @return True if the credentials are valid, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean validateUser(String username, String password) throws SQLException {
        String hashedPassword = hashPassword(password);
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE UserName = ? AND UserPass = ?")) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
