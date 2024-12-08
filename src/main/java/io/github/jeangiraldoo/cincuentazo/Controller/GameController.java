package io.github.jeangiraldoo.cincuentazo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import io.github.jeangiraldoo.cincuentazo.Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {

    @FXML
    private Label gameState;

    @FXML
    private HBox humanHbox;

    @FXML
    private HBox machineOne;

    @FXML
    private HBox machineThree;

    @FXML
    private HBox machineTwo;

    @FXML
    private HBox mainDeck;

    private CardDeck mazo;
    private Mesa mesa;
    private int difficulty;
    private List<Player> jugadores;
    private int turnoActual;

    public void initialize() {
        iniciarJuego();
    }

    private void iniciarJuego() {
        // Inicializar el mazo
        System.out.println(difficulty);
        mazo = new CardDeck();

        // Seleccionar la carta inicial para la mesa
        Card cartaInicial = mazo.takeCard();
        mesa = new Mesa(cartaInicial);

        // Inicializar jugadores
        jugadores = new ArrayList<>();
        jugadores.add(new Player("Humano"));
        for (int i = 1; i <= 3; i++) {
            jugadores.add(new Player("Máquina " + i));
        }

        // Repartir cartas iniciales
        for (Player jugador : jugadores) {
            for (int j = 0; j < 4; j++) {
                jugador.recibirCarta(mazo.takeCard());
            }
        }

        turnoActual = 0; // Empieza el jugador humano
        actualizarVista();
    }

    private void actualizarVista() {
        actualizarMesa();
        actualizarCartasHumanas();
        actualizarCartasMaquinas();
        actualizarEstado();
    }


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


    private void actualizarCartasHumanas() {
        humanHbox.getChildren().clear();
        Player humano = jugadores.get(0);

        for (Card carta : humano.getDeck()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);  // Ancho de la carta
            imageView.setFitHeight(75); // Alto de la carta

            imageView.setOnMouseClicked(event -> jugarCarta(carta));
            humanHbox.getChildren().add(imageView);
        }
    }


    private void actualizarCartasMaquinas() {
        actualizarCartasMaquina(machineOne, 1);
        actualizarCartasMaquina(machineTwo, 2);
        actualizarCartasMaquina(machineThree, 3);
    }

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

    private void mostrarMensajeFin(String mensaje) {
        gameState.setText(mensaje);
        humanHbox.getChildren().clear();
        machineOne.getChildren().clear();
        machineTwo.getChildren().clear();
        machineThree.getChildren().clear();
    }

    public void setDifficulty(Integer difficulty){
        this.difficulty = difficulty;
    }
}
