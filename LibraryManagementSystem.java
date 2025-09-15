import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    // DB Connection
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root"; // change if needed
    private static final String PASS = "password"; // change if needed

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // View Books
    public static void viewBooks() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            System.out.println("\nID | Title | Author | Status");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("title") + " | " +
                                   rs.getString("author") + " | " +
                                   (rs.getBoolean("available") ? "Available" : "Not Available"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lend Book
    public static void lendBook(int userId, int bookId) {
        try (Connection con = getConnection()) {
            PreparedStatement check = con.prepareStatement("SELECT available FROM books WHERE id=?");
            check.setInt(1, bookId);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getBoolean("available")) {
                PreparedStatement lend = con.prepareStatement(
                        "INSERT INTO lending(user_id, book_id, lend_date) VALUES (?, ?, CURDATE())");
                lend.setInt(1, userId);
                lend.setInt(2, bookId);
                lend.executeUpdate();

                PreparedStatement update = con.prepareStatement("UPDATE books SET available=FALSE WHERE id=?");
                update.setInt(1, bookId);
                update.executeUpdate();

                System.out.println("Book lent successfully!");
            } else {
                System.out.println(" Book not available!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return Book
    public static void returnBook(int userId, int bookId) {
        try (Connection con = getConnection()) {
            PreparedStatement check = con.prepareStatement(
                    "SELECT id FROM lending WHERE user_id=? AND book_id=? AND return_date IS NULL");
            check.setInt(1, userId);
            check.setInt(2, bookId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                int lendingId = rs.getInt("id");

                PreparedStatement updateLending = con.prepareStatement(
                        "UPDATE lending SET return_date=CURDATE() WHERE id=?");
                updateLending.setInt(1, lendingId);
                updateLending.executeUpdate();

                PreparedStatement updateBook = con.prepareStatement("UPDATE books SET available=TRUE WHERE id=?");
                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();

                System.out.println(" Book returned successfully!");
            } else {
                System.out.println(" No active lending record found for this book and user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. View Books");
            System.out.println("2. Lend Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    lendBook(userId, bookId);
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    int userIdR = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookIdR = sc.nextInt();
                    returnBook(userIdR, bookIdR);
                    break;
                case 4:
                    System.out.println(" Exiting...");
                    return;
                default:
                    System.out.println(" Invalid option.");
            }
        }
    }
}
