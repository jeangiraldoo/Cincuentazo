package io.github.jeangiraldoo.cincuentazo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import io.github.jeangiraldoo.cincuentazo.Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {
    @FXML
    private Pane board;

    @FXML
    private Label gameState;

    @FXML
    private HBox mainDeck;
    private List<HBox> hBoxList = new ArrayList<>();
    private CardDeck mazo;
    private Mesa mesa;
    private int difficulty;
    private List<Player> jugadores;
    private int turnoActual;

    public void initialize() {
        iniciarJuego();
    }

    /**
     * Sets up the game's board with its initial state and starts the game
     */
    private void iniciarJuego() {
        HBox humanHbox = new HBox();
        humanHbox.setId("humanHbox");
        humanHbox.setLayoutX(241.0);
        humanHbox.setLayoutY(378.0);
        humanHbox.setPrefHeight(100.0);
        humanHbox.setPrefWidth(200.0);
        humanHbox.setStyle("-fx-background-color: #2E8B57;");

        HBox machineOne = new HBox();
        machineOne.setId("machineOne");
        machineOne.setLayoutX(241.0);
        machineOne.setLayoutY(46.0);
        machineOne.setPrefHeight(100.0);
        machineOne.setPrefWidth(200.0);
        machineOne.setStyle("-fx-background-color: #2E8B57;");

        HBox machineThree = new HBox();
        machineThree.setId("machineThree");
        machineThree.setLayoutX(14.0);
        machineThree.setLayoutY(214.0);
        machineThree.setPrefHeight(100.0);
        machineThree.setPrefWidth(200.0);
        machineThree.setStyle("-fx-background-color: #2E8B57;");

        HBox machineTwo = new HBox();
        machineTwo.setId("machineTwo");
        machineTwo.setLayoutX(465.0);
        machineTwo.setLayoutY(214.0);
        machineTwo.setPrefHeight(100.0);
        machineTwo.setPrefWidth(202.0);
        machineTwo.setStyle("-fx-background-color: #2E8B57;");

        mainDeck = new HBox();
        mainDeck.setId("mainDeck");
        mainDeck.setLayoutX(241.0);
        mainDeck.setLayoutY(214.0);
        mainDeck.setPrefHeight(100.0);
        mainDeck.setPrefWidth(200.0);

        hBoxList.add(machineOne);
        hBoxList.add(machineTwo);
        hBoxList.add(machineThree);


        // Inicializar el mazo
        System.out.println(difficulty);
        mazo = new CardDeck();

        // Seleccionar la carta inicial para la mesa
        Card cartaInicial = mazo.takeCard();
        mesa = new Mesa(cartaInicial);

        // Inicializar jugadores
        jugadores = new ArrayList<>();
        Player human = new Player("Humano");
        human.setLayout(humanHbox);
        jugadores.add(human);

        for (int i = 0; i < difficulty; i++) {
            Player machine = new Player("Máquina " + (i + 1));
            machine.setLayout(hBoxList.get(i));
            jugadores.add(machine);
        }
        // Añadir solo las HBox de los jugadores que jugarán
        for (int i = 0; i < jugadores.size(); i++) {
            board.getChildren().add(jugadores.get(i).getLayout());
        }
        board.getChildren().add(mainDeck);

        // Repartir cartas iniciales
        for (Player jugador : jugadores) {
            for (int j = 0; j <4; j++) {
                jugador.recibirCarta(mazo.takeCard());
            }
        }

        turnoActual = 0; // Empieza el jugador humano
        actualizarVista();
    }

    /**
     * Updates the view based on the current state of the game
     */
    private void actualizarVista() {
        actualizarMesa();
        actualizarCartasHumanas();
        actualizarCartasMaquinas();
        actualizarEstado();
    }

    /**
     * Updates the table based on the current state of the game
     */
    private void actualizarMesa() {
        mainDeck.getChildren().clear();
        for (Card carta : mesa.getCartasMesa()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(75);

            mainDeck.getChildren().add(imageView);
        }
    }

    /**
     * Updates the deck used by the human player
     */
    private void actualizarCartasHumanas() {
        Player jugador = jugadores.get(0);
        jugador.clearLayout();

        for (Card carta : jugador.getDeck()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);  // Ancho de la carta
            imageView.setFitHeight(75); // Alto de la carta

            imageView.setOnMouseClicked(event -> jugarCarta(carta));
            jugador.insertIntoLayout(imageView);
            //humanHbox.getChildren().add(imageView);
        }
    }

    /**
     * Updates the decks used by all of the machines in the game
     */
    private void actualizarCartasMaquinas() {
        for (int i = 1; i < jugadores.size(); i++) {
            Player maquina = jugadores.get(i);
            actualizarCartasMaquina(maquina.getLayout(), i);
        }
    }

    /**
     * Updates the deck of a specific machine
     * @param maquinaHbox The machine's layout
     * @param indiceJugador The machine's index
     */
    private void actualizarCartasMaquina(HBox maquinaHbox, int indiceJugador) {
        maquinaHbox.getChildren().clear();
        Player maquina = jugadores.get(indiceJugador);

        for (Card carta : maquina.getDeck()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(75);

            maquinaHbox.getChildren().add(imageView); // Sin evento de clic
        }
    }

    /**
     * Displays the player that can play in the current turn
     */
    private void actualizarEstado() {
        Player jugadorActual = jugadores.get(turnoActual);
        gameState.setText("Turno: " + jugadorActual.getName() + " | Suma Mesa: " + mesa.getSumaMesa());
    }

    private void jugarCarta(Card carta) {
        Player jugadorActual = jugadores.get(turnoActual);

        // Validar si la carta puede ser jugada
        if (mesa.getSumaMesa() + carta.getValue() > 50) {
            jugadorActual.eliminar();
            if (jugadorActual.getName().equals("Humano")) {
                mostrarMensajeFin("¡Has sido eliminado! Perdiste el juego.");
                return;
            }
        } else {
            // Jugar la carta
            mesa.agregarCarta(jugadorActual.jugarCarta(carta));

            // Reemplazar la carta
            if (!mazo.isEmpty()) {
                jugadorActual.recibirCarta(mazo.takeCard());
            } else {
                mazo.addCards(mesa.reiniciarMazo());
                if (!mazo.isEmpty()) {
                    jugadorActual.recibirCarta(mazo.takeCard());
                }
            }
        }

        // Verificar si queda un único jugador
        if (jugadores.stream().filter(j -> !j.isEliminate()).count() == 1) {
            mostrarMensajeFin("¡" + jugadorActual.getName() + " ganó el juego!");
            return;
        }

        avanzarTurno();
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

        actualizarVista();
    }

    /**
     * Encapsulates the actions to be done by the machine when it is its turn
     */
    private void jugarTurnoMaquina() {
        Player maquina = jugadores.get(turnoActual);
        for (Card carta : maquina.getDeck()) {
            if (mesa.getSumaMesa() + carta.getValue() <= 50) {
                jugarCarta(carta);
                return;
            }
        }

        // Si no puede jugar ninguna carta, queda eliminada
        maquina.eliminar();
        avanzarTurno();
    }

    /**
     * Displays a message once the game ends
     * @param mensaje Message to be displayed
     */
    private void mostrarMensajeFin(String mensaje) {
        gameState.setText(mensaje);
        for (int i = 0; i < jugadores.size(); i++) {
            Player jugador = jugadores.get(i);
            jugador.clearLayout();
        }
    }

    /**
     * Sets the difficulty of the game based on how many machines the human player will play against
     * @param difficulty Number of machines to play against
     */
    public void setDifficulty(Integer difficulty){
        this.difficulty = difficulty;
    }
}
