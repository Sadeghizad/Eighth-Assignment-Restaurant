package ap.restaurant.controllers;

import ap.restaurant.database.MenuItemDAO;
import ap.restaurant.entities.CartItem;
import ap.restaurant.entities.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML private ListView<MenuItem> menuList;
    @FXML private ListView<CartItem> cartList;
    @FXML private Label totalLabel;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button checkoutButton;
    @FXML private TextField quantityField;

    private ObservableList<MenuItem> menuItems;
    private ObservableList<CartItem> cartItems;
    private Map<Integer, CartItem> cartMap; // For tracking quantity

    private int userId; // should be passed from Login

    public void setUserId(int id) {
        this.userId = id;
    }

    @FXML
    public void initialize() {
        cartMap = new HashMap<>();
        cartItems = FXCollections.observableArrayList();
        cartList.setItems(cartItems);

        menuItems = FXCollections.observableArrayList(MenuItemDAO.getAll());
        menuList.setItems(menuItems);

        menuList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getPrice() + " Toman");
                }
            }
        });

        cartList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMenuItem().getName() + " × " + item.getQuantity() + " | " +item.getSubtotal() + " Toman");
                }
            }
        });

        updateTotal();
    }

    @FXML
    private void onAddClick() {
        MenuItem selected = menuList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int id = selected.getId();
            int quantity = Integer.parseInt(quantityField.getText());
            if (cartMap.containsKey(id)) {
                CartItem existing = cartMap.get(id);
                cartMap.put(id, new CartItem(existing.getMenuItem(), existing.getQuantity() + quantity));
            } else {
                cartMap.put(id, new CartItem(selected, quantity));
            }
            refreshCart();
        }
    }

    @FXML
    private void onRemoveClick() {
        CartItem selected = cartList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int id = selected.getMenuItem().getId();
            if (cartMap.containsKey(id)) {
                CartItem current = cartMap.get(id);
                if (current.getQuantity() <= 1) {
                    cartMap.remove(id);
                } else {
                    cartMap.put(id, new CartItem(current.getMenuItem(), current.getQuantity() - 1));
                }
                refreshCart();
            }
        }
    }

    private void refreshCart() {
        cartItems.setAll(cartMap.values());
        updateTotal();
    }

    private void updateTotal() {
        int total = cartItems.stream().mapToInt(CartItem::getSubtotal).sum();
        totalLabel.setText("Total: " + total + " Toman");
    }

    @FXML
    private void onCheckoutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ap/restaurant/fxml/checkout.fxml"));
            Parent root = loader.load();

            // Pass data
            CheckoutController controller = loader.getController();
            controller.setData(cartItems, userId);

            Stage stage = (Stage) cartList.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
