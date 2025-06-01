package ap.restaurant.database;

import ap.restaurant.entities.User;
import ap.restaurant.utils.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public static boolean signUp(User user) {
        String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, PasswordUtil.hash(user.getPassword()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Sign-up failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean login(String username, String password) {
        String sql = "SELECT password FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return storedHash.equals(PasswordUtil.hash(password));
            }

        } catch (SQLException e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
        return false;
    }

    public static int getUserId(String username) {
        String sql = "SELECT id FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
