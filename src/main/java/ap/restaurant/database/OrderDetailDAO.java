package ap.restaurant.database;

import ap.restaurant.entities.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAO {
    public static void save(OrderDetail detail) {
        String sql = "INSERT INTO OrderDetails (orderId, menuItemId, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detail.getOrderId());
            stmt.setInt(2, detail.getMenuItemId());
            stmt.setInt(3, detail.getQuantity());
            stmt.setInt(4, detail.getPrice());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
