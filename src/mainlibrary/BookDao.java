package mainlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static mainlibrary.AppConstants.*;

public class BookDao {
    public static int save(String callno, String name, String author, String publisher, int quantity) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into books(callno,name,author,publisher,quantity) values(?,?,?,?,?)")) {
            ps.setString(1, callno);
            ps.setString(2, name);
            ps.setString(3, author);
            ps.setString(4, publisher);
            ps.setInt(5, quantity);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

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

    public static int AddPublisher(String Publisher) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into Publisher(PublisherName) values(?)")) {
            ps.setString(1, Publisher);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

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
