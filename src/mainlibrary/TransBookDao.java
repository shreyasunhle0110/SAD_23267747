package mainlibrary;

import java.sql.*;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransBookDao {
    private static final Logger logger = LogManager.getLogger(TransBookDao.class);

    public static boolean checkBook(String bookcallno) {
        boolean status = false;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT quantity, issued FROM books WHERE callno = ?");
            ps.setString(1, bookcallno);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            logger.error("Error during book check: {}", e.getMessage());
        }
        return status;
    }

    /**
     * Validates if a book exists in the database.
     * 
     * @param bookId The ID of the book to validate.
     * @return True if the book exists, false otherwise.
     * @throws IllegalArgumentException If the bookId is invalid.
     * @see <a href="https://cwe.mitre.org/data/definitions/20.html">CWE-20: Improper Input Validation</a>
     */
    public static boolean bookValidate(int bookId) {
        if (!String.valueOf(bookId).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid BookID: Must be numeric.");
        }
        boolean status = false;
        String query = "SELECT * FROM Books WHERE BookID = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (SQLException e) {
            logger.error("Error during book validation: {}", e.getMessage());
        }
        return status;
    }

    public static boolean UserValidate(int userId) {
        if (!String.valueOf(userId).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid UserID: Must be numeric.");
        }
        boolean status = false;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE UserID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            logger.error("Error during user validation: {}", e.getMessage());
        }
        return status;
    }

    public static int updatebook(String callno, int quantity, int issued) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE books SET quantity = ?, issued = ? WHERE callno = ?");
            ps.setInt(1, quantity);
            ps.setInt(2, issued);
            ps.setString(3, callno);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            logger.error("Error during book update: {}", e.getMessage());
        }
        return status;
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
    public static int issueBook(int bookId, int userId, Date issueDate, Date returnDate) {
        if (!String.valueOf(bookId).matches("\\d+") || !String.valueOf(userId).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid BookID or UserID: Must be numeric.");
        }
        int status = 0;
        String query = "INSERT INTO IssuedBook VALUES (?, ?, ?, ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setDate(3, issueDate);
            ps.setDate(4, returnDate);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error during book issuance: {}", e.getMessage());
        }
        return status;
    }

    public static int ReturnBook(int bookId, int userId) {
        if (!String.valueOf(bookId).matches("\\d+") || !String.valueOf(userId).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid BookID or UserID: Must be numeric.");
        }
        int status = 0;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM IssuedBook WHERE BookID = ? AND UserID = ?");
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            logger.error("Error during book return: {}", e.getMessage());
        }
        return status;
    }

    public static boolean CheckIssuedBook(int bookId) {
        if (!String.valueOf(bookId).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid BookID: Must be numeric.");
        }
        boolean status = false;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM IssuedBook WHERE BookID = ?");
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            logger.error("Error during issued book check: {}", e.getMessage());
        }
        return status;
    }

    public static int Check(int UserID) {
        if (!String.valueOf(UserID).matches("\\d+")) {
            throw new IllegalArgumentException("Invalid UserID: Must be numeric.");
        }
        boolean status = false;
        int num = 0;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Book_Count UserID = ?");
            ps.setInt(2, UserID);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            num = rs.getInt("BookNo");
            con.close();
        } catch (Exception e) {
            logger.error("Error during book count check: {}", e.getMessage());
        }
        if (num == 5) {
            return 0;
        } else {
            return 1;
        }
    }
}
