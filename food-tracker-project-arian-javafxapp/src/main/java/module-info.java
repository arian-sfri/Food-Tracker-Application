module main.foodtrackerapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens main.foodtrackerapp to javafx.fxml;
    exports main.foodtrackerapp;
}