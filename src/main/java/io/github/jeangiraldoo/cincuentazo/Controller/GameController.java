package io.github.jeangiraldoo.cincuentazo.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import io.github.jeangiraldoo.cincuentazo.Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/** * Controller class for managing the game logic and UI updates of "El Cincuentazo". */
public class GameController {
    @FXML
    private Pane board;

    @FXML
    private Label gameState;

    @FXML
    private HBox deckContainer;
    private GridPane usedMazoGrid;
    private GridPane remainingMazoGrid;
    private List<HBox> hBoxList = new ArrayList<>();
    private List<VBox> vBoxList = new ArrayList<>();
    private CardDeck remainingMazo;
    private Mesa mesa;
    private ImageView usedMazoImageView;
    private int difficulty;
    private List<Player> jugadores;
    private int turnoActual;

    /** * Initializes the game board and starts the game. */
    public void initialize() {
        iniciarJuego();
    }

    /**
     * Sets up the game's board with its initial state and starts the game
     */
    private void iniciarJuego() {
        VBox humanVBox = createVisualPlayerContainer("Humano", 241.0, 378.0);
        HBox humanHbox = createDeckContainer("humanHbox", 241.0, 378.0, 100.0, 200.0);
        humanVBox.getChildren().add(humanHbox);

        VBox machineOneVBox = createVisualPlayerContainer("Máquina 1", 241.0, 46.0);
        HBox machineOne = createDeckContainer("machineOne", 241.0, 46.0, 100.0, 200.0);
        machineOneVBox.getChildren().add(machineOne);

        VBox machineThreeVBox = createVisualPlayerContainer("Máquina 3", 14.0, 214.0);
        HBox machineThree = createDeckContainer("machineThree", 14.0, 214.0, 100.0, 200.0);
        machineThreeVBox.getChildren().add(machineThree);

        VBox machineTwoVBox = createVisualPlayerContainer("Máquina 2", 465.0, 214.0);
        HBox machineTwo = createDeckContainer("machineTwo", 465.0, 214.0, 100.0, 202.0);
        machineTwoVBox.getChildren().add(machineTwo);

        deckContainer = createDeckContainer("mainDeck", 241.0, 214.0, 100.0, 200.0);
        usedMazoGrid = new GridPane();
        remainingMazoGrid = new GridPane();
        String usedMazoMsg = "Estas son las cartas que han sido usadas:";
        String remainingMazoMsg = "Estas son las cartas que aún no han sido usasdas:";
        usedMazoImageView = getClickableDeck("Cartas usadas", usedMazoMsg, usedMazoGrid);
        ImageView remainingMazoImageView = getClickableDeck("Cartas sin usar", remainingMazoMsg, remainingMazoGrid);
        deckContainer.getChildren().add(usedMazoImageView);
        deckContainer.getChildren().add(remainingMazoImageView);

        hBoxList.add(machineOne);
        hBoxList.add(machineTwo);
        hBoxList.add(machineThree);
        vBoxList.add(machineOneVBox);
        vBoxList.add(machineTwoVBox);
        vBoxList.add(machineThreeVBox);

        // Inicializar el mazo
         remainingMazo = new CardDeck();

        // Seleccionar la carta inicial para la mesa
        Card cartaInicial = remainingMazo.takeCard();
        usedMazoImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cartaInicial.getImagePath()))));
        mesa = new Mesa(cartaInicial);

        // Inicializar jugadores
        jugadores = new ArrayList<>();
        Player human = new Player("Humano");
        human.setLayout(humanHbox);
        human.setPlayerContainer(humanVBox);
        jugadores.add(human);

        for (int i = 0; i < difficulty; i++) {
            Player machine = new Player("Máquina " + (i + 1));
            machine.setLayout(hBoxList.get(i));
            machine.setPlayerContainer(vBoxList.get(i));
            jugadores.add(machine);
        }
        // Añadir solo las VBox de los jugadores que jugarán
        for (int i = 0; i < jugadores.size(); i++) {
            board.getChildren().add(jugadores.get(i).getContainer());
        }
        board.getChildren().add(deckContainer);
        Button infoButton = new Button("¿Cómo jugar?");
        infoButton.setStyle("-fx-background-color: linear-gradient(to bottom, #32cd32, #228b22);\n" +
                "    -fx-font-family: 'Verdana', sans-serif;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-border-color: #ffd700; /* Bordes dorados */\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-border-radius: 12px;\n" +
                "    -fx-background-radius: 12px;\n" +
                "    -fx-padding: 10 20;\n" +
                "    -fx-cursor: hand;");
        infoButton.setOnAction(event -> on_info_button());
        board.getChildren().add(infoButton);

        // Repartir cartas iniciales
        for (Player jugador : jugadores) {
            for (int j = 0; j <4; j++) {
                jugador.recibirCarta(remainingMazo.takeCard());
            }
        }

        turnoActual = 0; // Empieza el jugador humano
        for(Player jugador: jugadores){
            actualizarCartasJugador(jugador);
        }
        actualizarEstado();
        actualizarVista();
    }
    /** * Returns an ImageView representing a clickable deck for either used or remaining cards.
     * * Clicking the deck performs the appropriate action. * * @param title the title of the deck
     * * @param message the message describing the deck * @param grid the grid pane to display the cards
     * * @return the ImageView representing the clickable deck */

    private ImageView getClickableDeck(String title, String message, GridPane grid) {
        grid.setHgap(10);
        grid.setVgap(10);

        Alert remainingMazoAlert = new Alert(Alert.AlertType.INFORMATION);
        remainingMazoAlert.setTitle(title);
        remainingMazoAlert.setHeaderText(message);
        remainingMazoAlert.getDialogPane().setContent(grid);

        Image reverseCardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cards/reverseCard.jpg")));
        ImageView reverseCardImageView = new ImageView(reverseCardImage);
        reverseCardImageView.setFitHeight(100);
        reverseCardImageView.setFitWidth(50);

        // Evento para mostrar las cartas del mazo (alert)
        reverseCardImageView.setOnMouseClicked(event -> {
            if (title.equals("Cartas sin usar")) {
                tomarCartaDelMazo();
            } else {
                remainingMazoAlert.showAndWait();
            }
        });

        return reverseCardImageView;
    }

    /** * Allows the human player to take a card from the remaining deck. */

    private void tomarCartaDelMazo() {
        // Obtener el jugador humano (primer jugador)
        Player jugadorHumano = jugadores.get(0);

        // Validar si hay cartas en el mazo
        if (remainingMazo.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mazo vacío");
            alert.setHeaderText("El mazo está vacío.");
            alert.setContentText("No hay cartas disponibles para tomar.");
            alert.showAndWait();
            return;
        }

        // Tomar una carta del mazo
        Card cartaNueva = remainingMazo.takeCard();

        // Añadir la carta al jugador humano
        jugadorHumano.recibirCarta(cartaNueva);

        // Actualizar la vista del jugador humano
        actualizarCartasJugador(jugadorHumano);

        // Mostrar mensaje de confirmación
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Carta tomada");
        alert.setHeaderText("¡Has tomado una carta!");
        alert.setContentText("La carta fue añadida a tu mano.");
        alert.showAndWait();

        // Actualizar el mazo restante en la interfaz
        updateRemainingMazo();
    }
    /** * Creates a visual container for a player with the specified name and position.
     * * * @param name the name of the player
     * * @param posX the x position of the container
     * * @param posY the y position of the container
     * * @return the VBox container for the player */

    private VBox createVisualPlayerContainer(String name, double posX, double posY){
        VBox playerContainer = new VBox();
        playerContainer.setLayoutX(posX);
        playerContainer.setLayoutY(posY);
        Label playerLabel = new Label(name);
        playerLabel.setStyle("-fx-font-family: 'Times New Roman', serif;\n" +
                "    -fx-font-size: 15px;\n" +
                "    -fx-text-fill: #ffd700; /* Dorado */\n" +
                "    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 12, 0.6, 0, 3);\n" +
                "    -fx-alignment: center;");
        playerContainer.getChildren().add(playerLabel);

        return playerContainer;
    }
    /** * Creates a visual container (HBox) for a player's deck with the specified parameters. *
     * * @param id the identifier for the container * @param posX the x position of the container * @param posY the y position of the container *
     * @param height the height of the container * @param width the width of the container *
     *  @return the HBox container for the player's deck */

    private HBox createDeckContainer(String id, double posX, double posY, double height, double width){
        HBox playerDeckContainer = new HBox();
        playerDeckContainer.setAlignment(Pos.CENTER);
        playerDeckContainer.setId("machineOne");
        playerDeckContainer.setLayoutX(posX);
        playerDeckContainer.setLayoutY(posY);
        playerDeckContainer.setPrefHeight(height);
        playerDeckContainer.setPrefWidth(width);
        playerDeckContainer.setStyle("-fx-background-color:  #006400");

        return playerDeckContainer;
    }
    /**
     * Updates the view based on the current state of the game
     */
    private void actualizarVista() {
        updateUsedMazo();
        updateRemainingMazo();
        //actualizarCartasJugador();
        //actualizarEstado();
    }

    /**
     * Updates the table based on the current state of the game
     */
    private void updateUsedMazo() {
        usedMazoGrid.getChildren().clear();
        int numColumns = 10;
        List<Card> cards = mesa.getCartasMesa();
        for (int i = 0; i < cards.size(); i++) {
            int row = i / numColumns;
            int col = i % numColumns;
            Card card = cards.get(i);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(75);
            usedMazoGrid.add(imageView, col, row);
        }
    }

    /** * Updates the visual representation of the remaining deck on the game board.
     * * Clears the existing grid and repopulates it with images of the remaining cards. */

    private void updateRemainingMazo(){
        remainingMazoGrid.getChildren().clear();
        int numColumns = 10;
        List<Card> cards = remainingMazo.getCards();
        for (int i = 0; i < cards.size(); i++) {
            int row = i / numColumns;
            int col = i % numColumns;
            Card card = cards.get(i);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(75);
            remainingMazoGrid.add(imageView, col, row);
        }
    }

    /** * Updates the visual representation of a player's deck on the game board.
     * * Clears the player's current deck layout and repopulates it with images of the player's cards.
     * * @param jugador the player whose deck is being updated */

    private void actualizarCartasJugador(Player jugador) {
//        for (int i = 0; i < jugadores.size(); i++) {
//            Player jugador = jugadores.get(i);
//            jugador.clearLayout();
//
//            for (Card carta : jugador.getDeck()) {
//                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
//                ImageView imageView = new ImageView(image);
//                imageView.setFitWidth(50);  // Ancho de la carta
//                imageView.setFitHeight(75); // Alto de la carta
//                if (i == 0){
//                    imageView.setOnMouseClicked(event -> jugarCarta(carta));
//                }
//                jugador.insertIntoLayout(imageView);
//            }
//        }
        jugador.clearLayout();

        for (Card carta : jugador.getDeck()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);  // Ancho de la carta
            imageView.setFitHeight(75); // Alto de la carta
            if (jugador.getName().equals("Humano")){
                imageView.setOnMouseClicked(event -> jugarCarta(carta));
            }
            jugador.insertIntoLayout(imageView);
        }

    }

    /**
     * Displays the player that can play in the current turn
     */
    private void actualizarEstado() {
        Player jugadorActual = jugadores.get(turnoActual);
        System.out.println("turno actual: " + jugadorActual.getName());
        gameState.setText("Turno: " + jugadorActual.getName() + " | Suma Mesa: " + mesa.getSumaMesa());
    }

    /** * Handles the action of playing a card during a player's turn. *
     *  * @param carta the card being played */

    private void jugarCarta(Card carta) {
        Player jugadorActual = jugadores.get(turnoActual);

        // Validar si la carta puede ser jugada
        if (mesa.getSumaMesa() + carta.getValue() > 50) {
            jugadorActual.eliminar();
            if (jugadorActual.getName().equals("Humano")) {
                mostrarMensajeFin("¡Has sido eliminado! Perdiste el juego.");
                avanzarTurno();

            } else {
                verificarEstadoDelJuego();
                return;
            }
        } else {
            // Jugar la carta

            mesa.agregarCarta(jugadorActual.jugarCarta(carta));
            System.out.println("Mesa updated: " + mesa.getSumaMesa());
            actualizarCartasJugador(jugadorActual);
            actualizarEstado();

            // Verificar si queda un único jugador
            if (jugadores.stream().filter(j -> !j.isEliminate()).count() == 1) {
                mostrarMensajeFin("¡" + jugadorActual.getName() + " ganó el juego!");
                return;
            }

            // Reemplazar la carta después de un delay (solo para jugador humano)
            if (jugadorActual.getName().equals("Humano")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000); // Retraso de 2 segundos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        if (!remainingMazo.isEmpty()) {
                            jugadorActual.recibirCarta(remainingMazo.takeCard());
                        } else {
                            remainingMazo.addCards(mesa.reiniciarMazo());
                            if (!remainingMazo.isEmpty()) {
                                jugadorActual.recibirCarta(remainingMazo.takeCard());
                            }
                        }
                        actualizarCartasJugador(jugadorActual);
                        actualizarEstado();
                        avanzarTurno();
                    });
                }).start();
            } else {
                if (!remainingMazo.isEmpty()) {
                    jugadorActual.recibirCarta(remainingMazo.takeCard());
                } else {
                    remainingMazo.addCards(mesa.reiniciarMazo());
                    if (!remainingMazo.isEmpty()) {
                        jugadorActual.recibirCarta(remainingMazo.takeCard());
                    }
                }
            }
        }

        // Avanzar el turno después de un delay para la máquina
        if (!jugadorActual.getName().equals("Humano")) {
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Retraso de 2 segundos para el turno de la máquina
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    actualizarCartasJugador(jugadorActual);
                    usedMazoImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath()))));
                    actualizarEstado();
                    avanzarTurno();
                });
            }).start();
        } else {
//            actualizarEstado();
//            actualizarCartasJugador(jugadorActual);
        }

        // Actualizar la imagen de la última carta usada
        usedMazoImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath()))));
    }

    /**

     * Check  if there is only one player left in the game and ends it if that is the case
     */
    private void verificarEstadoDelJuego() {
        List<Player> jugadoresActivos = new ArrayList<>();
        for (Player jugador : jugadores) {
            if (!jugador.isEliminate()) {
                jugadoresActivos.add(jugador);
            }
        }

        if (jugadoresActivos.size() == 1) {
            Player ganador = jugadoresActivos.get(0);
            mostrarMensajeGanador(ganador);
            finalizarJuego();
        }
    }



    /**
     * Ends the game and stop any pendent action
     */
    private void finalizarJuego() {
        Platform.exit(); // Cierra la aplicación
        // O puedes limpiar el tablero si deseas reiniciar el juego:
        // board.getChildren().clear();
    }


    /**
     * Shows a message with the game winner
     *
     * @param ganador the player who won the game.
     */
    private void mostrarMensajeGanador(Player ganador) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Juego terminado!");
        alert.setHeaderText("Tenemos un ganador");
        alert.setContentText("El ganador es: " + ganador.getName());
        alert.showAndWait();
    }




    /**
     * Upates the game's state by setting which player can play in the next turn
     */
    private void avanzarTurno() {
        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } while (jugadores.get(turnoActual).isEliminate());

        // Si es turno de una máquina, jugar automáticamente
        if (!jugadores.get(turnoActual).getName().equals("Humano")) {
            jugarTurnoMaquina();
        }
        actualizarEstado();
        verificarEstadoDelJuego();
        actualizarVista();
    }

    /**
     * Encapsulates the actions to be done by the machine when it is its turn
     */
    private void jugarTurnoMaquina() {
        Player maquina = jugadores.get(turnoActual);
        for (Card carta : maquina.getDeck()) {
            if (mesa.getSumaMesa() + carta.getValue() <= 50) {
                actualizarEstado();
                jugarCarta(carta);
                return;
            }
        }

        // Si no puede jugar ninguna carta, queda eliminada
        maquina.eliminar();
        maquina.clearContainer();
        avanzarTurno();
    }

    /**
     * Displays a message once the game ends
     * @param mensaje Message to be displayed
     */
    private void mostrarMensajeFin(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Juego terminado!");
        alert.setHeaderText("GAME OVER");
        alert.setContentText("PERDISTE  ");
        alert.showAndWait();
    }

    /**
     * Sets the difficulty of the game based on how many machines the human player will play against
     * @param difficulty Number of machines to play against
     */
    public void setDifficulty(Integer difficulty){
        this.difficulty = difficulty;
    }
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
