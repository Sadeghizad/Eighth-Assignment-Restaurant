module ap.restaurant.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports ap.restaurant.restaurant;
    exports ap.restaurant.entities;

    opens ap.restaurant.restaurant to javafx.fxml;
}
