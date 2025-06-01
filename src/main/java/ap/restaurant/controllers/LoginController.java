package ap.restaurant.controllers;

import ap.restaurant.database.UserDAO;
import ap.restaurant.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void onLoginClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean success = UserDAO.login(username, password);
        if (success) {
            showAlert("Login successful!");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            SceneManager.switchScene(stage, "/ap/restaurant/fxml/main.fxml");
        } else {
            showAlert("Invalid credentials.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
