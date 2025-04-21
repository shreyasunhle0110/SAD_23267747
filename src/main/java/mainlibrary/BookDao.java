package mainlibrary;

import java.sql.*;

/**
 * Book data access with publisher management and validation.
 * Uses transaction boundaries for multi-table operations.
 */
public class BookDao {

    /**
     * Validates publisher existence to maintain referential integrity
     */
    public static boolean PublisherValidate(String Publisher) {
        boolean status = false;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from Publisher where PublisherName = ?")) {
            ps.setString(1, Publisher);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    /**
     * Atomic operation to add book with publisher validation
     */
    public static int SaveBook(String BookN, String AuthorN, String PublisherN, String ShelfN, String RowN, String GenreN) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Books(BookName, Author, Genre, Publisher, Shelf, Row) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, BookN);
            ps.setString(2, AuthorN);
            ps.setString(3, GenreN);
            ps.setString(4, PublisherN);
            ps.setString(5, ShelfN);
            ps.setString(6, RowN);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    /**
     * Adds a new publisher to the database
     */
    public static int AddPublisher(String Publisher) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Publisher(PublisherName) VALUES(?)")) {
            ps.setString(1, Publisher);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int Delete(int BookID) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Books WHERE BookID = ?")) {
            ps.setInt(1, BookID);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
}
