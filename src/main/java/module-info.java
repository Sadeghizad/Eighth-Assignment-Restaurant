module ap.restaurant.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports ap.restaurant.main;
    exports ap.restaurant.entities;

    opens ap.restaurant.main to javafx.fxml;
}
