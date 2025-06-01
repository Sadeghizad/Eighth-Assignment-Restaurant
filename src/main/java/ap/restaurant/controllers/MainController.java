package ap.restaurant.controllers;

import ap.restaurant.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MainController {

    @FXML private ListView<String> menuList;
    @FXML private ListView<String> cartList;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Label totalLabel;

    @FXML
    public void initialize() {
        // Sample data, should be replaced with database content
        menuList.getItems().addAll("Pizza - 80", "Burger - 60", "Sushi - 90");
    }

    @FXML
    private void onAddClick() {
        String selected = menuList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cartList.getItems().add(selected);
            // TODO: update total price
        }
    }

    @FXML
    private void onRemoveClick() {
        int selected = cartList.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            cartList.getItems().remove(selected);
            // TODO: update total price
        }
    }

    @FXML
    private void onCheckoutClick() {
        Stage stage = (Stage) cartList.getScene().getWindow();
        SceneManager.switchScene(stage, "/ap/restaurant/fxml/checkout.fxml");
    }

}
