package ap.restaurant.database;

import ap.restaurant.entities.Order;

import java.sql.*;

public class OrderDAO {
    public static int save(Order order) {
        String sql = "INSERT INTO Orders (userId, createdAt, totalPrice) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getUserId());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getCreatedAt()));
            stmt.setInt(3, order.getTotalPrice());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
