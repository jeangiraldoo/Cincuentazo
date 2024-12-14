package io.github.jeangiraldoo.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that initializes and launches the Cincuentazo application.
 * @authors  Jean Pierre Giraldo - Emerson Albornoz
 * @version 1.0
 * @since 2024-12-04
 */

public class Main extends Application {

    /**
     * Starts the JavaFX application by setting up the primary stage and loading the initial FXML layout.
     *
     * @param stage the primary stage for this application
     * @throws IOException if there is an error loading the FXML file
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Cincuentazo v1.0");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command-line arguments (not used in this application)
     */

    public static void main(String[] args) {
        launch();
    }
}