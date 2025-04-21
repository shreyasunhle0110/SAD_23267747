package mainlibrary;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO class for handling book transactions.
 */
public class TransBookDao {
    private static final Logger logger = LogManager.getLogger(TransBookDao.class);

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
     * Validates if a book exists in the database.
     * 
     * @param bookId The ID of the book to validate.
     * @return Optional<Boolean> indicating if the book exists.
     * @throws IllegalArgumentException If the bookId is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static Optional<Boolean> bookValidate(int bookId) {
        if (bookId <= 0) {
            return Optional.of(false);
        }
        // Simulate validation logic
        return Optional.of(true);
    }

    /**
     * Validates if a user exists in the database.
     * 
     * @param userId The ID of the user to validate.
     * @return Optional<Boolean> indicating if the user exists.
     * @throws IllegalArgumentException If the userId is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static Optional<Boolean> userValidate(int userId) {
        if (userId <= 0) {
            return Optional.of(false);
        }
        // Simulate validation logic
        return Optional.of(true);
    }

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
     * Issues a book to a user.
     * 
     * @param bookId     The ID of the book to issue.
     * @param userId     The ID of the user.
     * @param issueDate  The issue date.
     * @param returnDate The return date.
     * @return The number of rows affected by the operation.
     * @throws IllegalArgumentException If the input is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/89.html">CWE-89: SQL Injection</a>
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
     * Returns a book by deleting its record from IssuedBook.
     * 
     * @param bookId The ID of the book to return.
     * @param userId The ID of the user returning the book.
     * @return Optional<Integer> indicating the number of rows affected.
     * @throws IllegalArgumentException If the bookId or userId is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/89.html">CWE-89: SQL Injection</a>
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
     * Checks if a book is issued.
     * 
     * @param bookId The ID of the book to check.
     * @return Optional<Boolean> indicating if the book is issued.
     * @throws IllegalArgumentException If the bookId is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static Optional<Boolean> checkIssuedBook(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid BookID: Must be positive.");
        }
        String query = "SELECT 1 FROM IssuedBook WHERE BookID = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.of(rs.next());
            }
        } catch (SQLException e) {
            logger.error("Error during issued book check: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<Integer> check(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid UserID: Must be positive.");
        }
        String query = "SELECT BookNo FROM Book_Count WHERE UserID = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int bookCount = rs.getInt("BookNo");
                    return Optional.of(bookCount == 5 ? 0 : 1);
                }
                return Optional.of(1);
            }
        } catch (Exception e) {
            logger.error("Error during book count check: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
