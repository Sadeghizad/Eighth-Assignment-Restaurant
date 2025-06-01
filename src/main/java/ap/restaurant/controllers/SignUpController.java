package ap.restaurant.controllers;

import ap.restaurant.database.UserDAO;
import ap.restaurant.entities.User;
import ap.restaurant.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailField;

    @FXML
    private void onSignUpClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String email = emailField.getText();

        if (!password.equals(confirm)) {
            showAlert("Passwords don't match!");
            return;
        }

        User user = new User(username, password, email);
        boolean success = UserDAO.signUp(user);

        if (success) {
            showAlert("Account created successfully!");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            SceneManager.switchScene(stage, "/ap/restaurant/fxml/login.fxml");
        } else {
            showAlert("Username already exists.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
