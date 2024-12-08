package io.github.jeangiraldoo.cincuentazo.Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> deck;
    private boolean eliminate;

    public Player(String nombre) {
        this.name = nombre;
        this.deck = new ArrayList<>();
        this.eliminate = false;
    }

    public String getName() {
        return name;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public boolean isEliminate() {
        return eliminate;
    }

    public void eliminar() {
        eliminate = true;
    }

    public void recibirCarta(Card carta) {
        if (deck.size() < 4) {
            deck.add(carta);
        }
    }

    public Card jugarCarta(Card carta) {
        if (deck.contains(carta)) {
            deck.remove(carta); // Elimina la carta jugada del mazo
            return carta;
        }
        throw new IllegalArgumentException("La carta no pertenece al jugador");
    }

    public boolean puedeJugar(int sumaMesa) {
        return deck.stream().anyMatch(c -> sumaMesa + c.getValue() <= 50);
    }
}
