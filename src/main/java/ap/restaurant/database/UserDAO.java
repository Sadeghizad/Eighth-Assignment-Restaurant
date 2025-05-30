package ap.restaurant.database;

import ap.restaurant.entities.User;
import ap.restaurant.utils.PasswordUtil;

import java.sql.*;

public class UserDAO {
    public static boolean signUp(User user) {
        String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordUtil.hash(user.getPassword()));
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Signup failed: " + e.getMessage());
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
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
    }
}
