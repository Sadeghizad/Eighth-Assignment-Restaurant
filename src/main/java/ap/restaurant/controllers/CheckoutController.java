package ap.restaurant.controllers;

import ap.restaurant.database.OrderDAO;
import ap.restaurant.database.OrderDetailDAO;
import ap.restaurant.entities.CartItem;
import ap.restaurant.entities.Order;
import ap.restaurant.entities.OrderDetail;
import ap.restaurant.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class CheckoutController {

    @FXML private TableView<CartItem> receiptTable;
    @FXML private TableColumn<CartItem, String> nameCol;
    @FXML private TableColumn<CartItem, Integer> qtyCol;
    @FXML private TableColumn<CartItem, Integer> priceCol;
    @FXML private TableColumn<CartItem, Integer> subtotalCol;
    @FXML private Label totalPriceLabel;

    private List<CartItem> cart;
    private int userId;

    public void setData(List<CartItem> cart, int userId) {
        this.cart = cart;
        this.userId = userId;
        receiptTable.setItems(FXCollections.observableArrayList(cart));
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int total = cart.stream().mapToInt(CartItem::getSubtotal).sum();
        totalPriceLabel.setText(total + " Toman");
    }

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMenuItem().getName()));
        qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getMenuItem().getPrice()).asObject());
        subtotalCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSubtotal()).asObject());
    }

    @FXML
    private void onPlaceOrderClick() {
        int totalPrice = cart.stream().mapToInt(CartItem::getSubtotal).sum();
        Order order = new Order(userId, totalPrice);
        int orderId = OrderDAO.save(order);

        if (orderId > 0) {
            for (CartItem item : cart) {
                OrderDetail detail = new OrderDetail(
                        orderId,
                        item.getMenuItem().getId(),
                        item.getQuantity(),
                        item.getMenuItem().getPrice()
                );
                OrderDetailDAO.save(detail);
            }

            showAlert("✅ Order placed.");
            cart.clear();
        } else {
            showAlert("❌ Failed to place order.");
        }
    }

    @FXML
    private void onLogoutClick() {
        Stage stage = (Stage) receiptTable.getScene().getWindow();
        SceneManager.switchScene(stage, "/ap/restaurant/fxml/Login.fxml");
    }

    @FXML
    private void onOrderAgainClick() {
        Stage stage = (Stage) receiptTable.getScene().getWindow();
        SceneManager.switchScene(stage, "/ap/restaurant/fxml/main.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
