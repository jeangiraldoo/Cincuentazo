package io.github.jeangiraldoo.cincuentazo.Model;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the table in the game, containing cards and their cumulative value.
 */
public class Mesa {
    private List<Card> cartasMesa;
    private int sumaMesa;

    /**
     * Constructs the table with an initial card.
     *
     * @param cartaInicial The initial card on the table.
     */
    public Mesa(Card cartaInicial) {
        this.cartasMesa = new ArrayList<>();
        this.cartasMesa.add(cartaInicial);
        this.sumaMesa = cartaInicial.getValue();
    }

    /**
     * Returns the current sum of the cards on the table.
     *
     * @return The sum of the cards' values.
     */


    public int getSumaMesa() {
        return sumaMesa;
    }

    /**
     * Adds a card to the table and updates the sum.
     *
     * @param carta The card to add.
     */

    public void agregarCarta(Card carta) {
        System.out.println("Score increased!");
        cartasMesa.add(carta);
        sumaMesa += carta.getValue();
    }

    /**
     * Returns the list of cards currently on the table.
     *
     * @return The cards on the table.
     */

    public List<Card> getCartasMesa() {
        return cartasMesa;
    }

    /**
     * Resets the table, keeping the last card and returning the rest for a new deck.
     *
     * @return A list of cards to be returned to the deck.
     */

    public List<Card> reiniciarMazo() {
        List<Card> cartasParaMazo = new ArrayList<>(cartasMesa);
        cartasParaMazo.remove(cartasMesa.size() - 1); // Dejar la última carta en la mesa
        cartasMesa.clear();
        return cartasParaMazo;
    }
}