package io.github.jeangiraldoo.cincuentazo.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CincuentazoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/jeangiraldoo/cincuentazo/gameView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cincuentazo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}