package io.github.jeangiraldoo.cincuentazo.Controller;

import com.sun.security.jgss.GSSUtil;
import io.github.jeangiraldoo.cincuentazo.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private VBox rootNode;
    @FXML
    ComboBox<String> dropDownMenu = new ComboBox<>();

    /**
     * Loads the game's view and sets up its controller to start the game flow
     * @throws IOException
     */
    private void startGame() throws IOException {
        String difficultyChoice = dropDownMenu.getValue();
        int difficulty = dropDownMenu.getItems().indexOf(difficultyChoice) + 1;
        System.out.println("diff init" + difficulty);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/io/github/jeangiraldoo/cincuentazo/gameView.fxml"));
        //FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameView.fxml"));
        loader.setControllerFactory(controllerClass -> {
            try {
                GameController controller = new GameController(); // Create the controller
                controller.setDifficulty(difficulty); // Set the difficulty
                return controller; // Return the controller with the difficulty set
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        Parent root = loader.load();
        GameController controller = loader.getController();
        controller.setDifficulty(difficulty);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Game Screen");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    /**
     * Clears the root node and inserts new nodes related to difficulty settings,
     * allowing the user to select a difficulty level.
     */
    private void on_play_button() {
        rootNode.getChildren().clear();


        Label choiceLabel = new Label("¿Contra cuántos oponentes deseas jugar?");
        choiceLabel.setStyle("-fx-font-family: 'Times New Roman', serif;\n" +
                "    -fx-font-size: 40px;\n" +
                "    -fx-text-fill: #ffd700; /* Dorado */\n" +
                "    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 12, 0.6, 0, 3);\n" +
                "    -fx-alignment: center;");

        dropDownMenu.getItems().clear(); // Asegurarse de que esté limpio antes de agregar ítems
        dropDownMenu.getItems().addAll("Un oponente", "2 oponentes", "3 oponentes");
        dropDownMenu.setStyle("-fx-font-size: 16px; -fx-background-color: lightgreen; -fx-border-color: darkgreen; -fx-padding: 5px;");

        Button startGameButton = new Button("Iniciar juego");
        startGameButton.setStyle("-fx-background-color: linear-gradient(to bottom, #32cd32, #228b22);\n" +
                "    -fx-font-family: 'Verdana', sans-serif;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-border-color: #ffd700; /* Bordes dorados */\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-border-radius: 12px;\n" +
                "    -fx-background-radius: 12px;\n" +
                "    -fx-padding: 10 20;\n" +
                "    -fx-cursor: hand;");
        startGameButton.setOnAction(event -> {
            try {
                startGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        rootNode.getChildren().add(choiceLabel);
        rootNode.getChildren().add(dropDownMenu);
        rootNode.getChildren().add(startGameButton);
    }


    @FXML
    /**
     * Displays information about how to play the game.
     */
    private void on_info_button() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tutorial");
        alert.setHeaderText("Tutorial de Cincuentazo");
        alert.setContentText(
                "Cincuentazo es un juego de cartas de Poker donde los jugadores (humano y máquina) deben sobrevivir utilizando sus cartas.\n\n" +
                        "**Reglas principales**:\n" +
                        "• La suma en la mesa no debe exceder 50 (>50).\n" +
                        "• Cada jugador comienza con 4 cartas y toma nuevas del mazo tras su turno.\n\n" +
                        "**Valores de las cartas**:\n" +
                        "• 2-8 y 10: Suman su número.\n" +
                        "• 9: No afecta la suma.\n" +
                        "• J, Q, K: Restan 10.\n" +
                        "• A: Suma 1 o 10, según convenga.\n\n" +
                        "Recuerda jugar estratégicamente para mantener la suma debajo de 50."
        );
        alert.showAndWait();
    }
}