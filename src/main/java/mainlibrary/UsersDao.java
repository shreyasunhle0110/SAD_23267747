package mainlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages user authentication and storage with secure password handling.
 * Implements BCrypt hashing and SQL injection prevention.
 */
public class UsersDao {
    private static final Logger logger = LogManager.getLogger(UsersDao.class);

    /**
     * Validates user credentials against stored BCrypt hash.
     * Prevents timing attacks by using constant-time comparison.
     * 
     * @param name     The username to validate.
     * @param password The password to validate.
     * @return True if the credentials are valid, false otherwise.
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
     * Prevents duplicate usernames to maintain unique user identifiers.
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
            logger.error("Error during user existence check: {}", e.getMessage());
        }
        return status;
    }

    /**
     * Creates new user with BCrypt hashed password. 
     * Stores email and registration date for account management.
     * 
     * @param username The username of the user.
     * @param password The plaintext password of the user.
     * @param email    The email of the user.
     * @param fullName The full name of the user.
     * @return True if the operation is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean addUser(String username, String password, String email, String fullName) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Users (UserName, UserPass, Email, FullName) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, email);
            ps.setString(4, fullName);
            return ps.executeUpdate() > 0;
        }
    }
}
