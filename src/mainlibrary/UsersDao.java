/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.security.SecureRandom;
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
     * Adds a new user to the database with a hashed password.
     * 
     * @param user      The username of the new user.
     * @param userPass  The plaintext password of the new user.
     * @param userEmail The email of the new user.
     * @param date      The registration date of the new user.
     * @return The number of rows affected by the insert operation.
     * @throws SQLException If a database access error occurs.
     * @see <a href="https://cwe.mitre.org/data/definitions/256.html">CWE-256: Plaintext Storage of Passwords</a>
     */
    public static int addUser(String user, String userPass, String userEmail, String date) {
        int status = 0;
        String query = "INSERT INTO Users (UserName, UserPass, Email, RegDate) VALUES (?, ?, ?, ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            String hashedPassword = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
            ps.setString(1, user);
            ps.setString(2, hashedPassword);
            ps.setString(3, userEmail);
            ps.setString(4, date);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error during user addition: {}", e.getMessage());
        }
        return status;
    }

    /**
     * Adds a user to the database.
     * 
     * @param userName The username of the user.
     * @param password The password of the user.
     * @param email    The email of the user.
     * @param date     The registration date of the user.
     * @return 1 if the operation is successful, 0 otherwise.
     */
    public static int AddUser(String userName, String password, String email, String date) {
        try {
            // Database connection and insertion logic
            // Replace with actual database code
            System.out.println("Adding user: " + userName);
            return 1; // Return 1 for success
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 for failure
        }
    }
}
