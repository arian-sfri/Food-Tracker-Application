package main.foodtrackerapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Food;
import main.Reader;

import java.io.File;
import java.io.IOException;


//Application that tracks food and water consumption, exercise and shows information about user's consumption
 

public class FoodTrackerApp extends Application {
    private static FoodTrackerController ftc;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodTrackerApp.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        ftc = fxmlLoader.getController();
        stage.setTitle("Food Tracker v1.3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        FoodTrackerController.args = args;
        launch();
    }
}