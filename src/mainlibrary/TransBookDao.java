package mainlibrary;

import java.sql.*;
import javax.swing.JTextField;

public class TransBookDao {

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
            System.out.println(e);
        }
        return status;
    }

    public static boolean BookValidate(String BookID) {
        boolean status = false;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Books WHERE BookID = ?");
            ps.setString(1, BookID);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean UserValidate(int userId) {
        boolean status = false;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE UserID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println(e);
        }
        return status;
    }

    public static int IssueBook(int bookId, int userId, Date issueDate, Date returnDate) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO IssuedBook VALUES (?, ?, ?, ?)");
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setDate(3, issueDate);
            ps.setDate(4, returnDate);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int ReturnBook(int bookId, int userId) {
        int status = 0;
        try {
            Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM IssuedBook WHERE BookID = ? AND UserID = ?");
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean CheckIssuedBook(int bookId) {
        boolean status = false;
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM IssuedBook WHERE BookID = ?");
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int Check(int UserID) {
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
            System.out.println(e);
        }
        if (num == 5) {
            return 0;
        } else {
            return 1;
        }
    }

}
