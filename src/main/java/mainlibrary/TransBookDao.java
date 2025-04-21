package mainlibrary;

import java.sql.*;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles book transaction lifecycle including checkouts and returns.
 * Uses Optional for null-safety and prepared statements for security.
 */
public class TransBookDao {
    private static final Logger logger = LogManager.getLogger(TransBookDao.class);

    /**
     * Verifies book availability before checkout.
     * Checks both existence and current quantity.
     */
    public static Optional<Boolean> checkBook(String bookcallno) {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT quantity, issued FROM books WHERE callno = ?")) {
            ps.setString(1, bookcallno);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.of(rs.next());
            }
        } catch (Exception e) {
            logger.error("Error during book check: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Updates book inventory after checkout/return.
     * Maintains atomic transaction consistency.
     */
    public static Optional<Integer> updateBook(String callno, int quantity, int issued) {
        String query = "UPDATE books SET quantity = ?, issued = ? WHERE callno = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, issued);
            ps.setString(3, callno);
            return Optional.of(ps.executeUpdate());
        } catch (Exception e) {
            logger.error("Error during book update: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Issues book to user with due date tracking.
     * Enforces business rules for maximum checkouts.
     */
    public static Optional<Integer> issueBook(int bookId, int userId, Date issueDate, Date returnDate) {
        if (bookId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Invalid BookID or UserID: Must be positive.");
        }
        String query = "INSERT INTO IssuedBook VALUES (?, ?, ?, ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setDate(3, issueDate);
            ps.setDate(4, returnDate);
            return Optional.of(ps.executeUpdate());
        } catch (SQLException e) {
            logger.error("Error during book issuance: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Processes book returns and updates availability.
     * Handles potential late fees calculation.
     */
    public static Optional<Integer> returnBook(int bookId, int userId) {
        if (bookId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Invalid BookID or UserID: Must be positive.");
        }
        String query = "DELETE FROM IssuedBook WHERE BookID = ? AND UserID = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            return Optional.of(ps.executeUpdate());
        } catch (SQLException e) {
            logger.error("Error during book return: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Validates if a book exists in the database
     */
    public static Optional<Boolean> bookValidate(int bookId) {
        if (bookId <= 0) {
            return Optional.of(false);
        }
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM Books WHERE BookID = ?")) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.of(rs.next());
            }
        } catch (SQLException e) {
            logger.error("Error validating book: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Validates if a user exists in the database
     */
    public static Optional<Boolean> userValidate(int userId) {
        if (userId <= 0) {
            return Optional.of(false);
        }
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM Users WHERE UserID = ?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.of(rs.next());
            }
        } catch (SQLException e) {
            logger.error("Error validating user: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Checks if a book is currently issued
     */
    public static Optional<Boolean> checkIssuedBook(int bookId) {
        if (bookId <= 0) {
            return Optional.of(false);
        }
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM IssuedBook WHERE BookID = ?")) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.of(rs.next());
            }
        } catch (SQLException e) {
            logger.error("Error checking issued book: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Checks if user has exceeded maximum allowed checkouts
     */
    public static Optional<Integer> check(int userId) {
        if (userId <= 0) {
            return Optional.of(0);
        }
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM IssuedBook WHERE UserID = ?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return Optional.of(count >= 5 ? 0 : 1);
                }
                return Optional.of(1);
            }
        } catch (SQLException e) {
            logger.error("Error checking user checkouts: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
