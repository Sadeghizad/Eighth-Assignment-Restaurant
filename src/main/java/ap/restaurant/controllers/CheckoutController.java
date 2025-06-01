package ap.restaurant.controllers;

import ap.restaurant.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CheckoutController {

    @FXML private TableView<?> receiptTable;
    @FXML private Label totalPriceLabel;

    @FXML
    public void initialize() {
        // TODO: Fill table with order data from cart
        // TODO: Display total cost
    }

    @FXML
    private void onPlaceOrderClick() {
        // TODO: Save order to DB using OrderDAO
        // Show confirmation
        showAlert("✅ Order placed.");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
