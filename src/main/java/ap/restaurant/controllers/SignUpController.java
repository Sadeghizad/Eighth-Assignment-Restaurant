package ap.restaurant.controllers;

import ap.restaurant.database.UserDAO;
import ap.restaurant.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            // TODO: Load login scene
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
