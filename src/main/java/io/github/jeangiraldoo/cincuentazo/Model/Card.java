package io.github.jeangiraldoo.cincuentazo.Model;

/**
 * Represents a card with a rank, suit, and value.
 */

public class Card {
    private final String rank; // Ej "2", "A", "J"
    private final String suit; // Ej "H", "D", "C", "S"
    private final int value;

    /**
     * Constructs a card with the given rank and suit.
     *
     * @param rank The rank of the card (e.g., "2", "A", "J").
     * @param suit The suit of the card (e.g., "H", "D", "C", "S").
     */

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;

        // Calcula el valor de la carta según el juego
        switch (rank) {
            case "J":
            case "Q":
            case "K":
                this.value = -10;
                break;
            case "A":
                this.value = 1; // Valor inicial, puede cambiar si es necesario
                break;
            case "9":
                this.value = 0;
                break;
            default:
                this.value = Integer.parseInt(rank); // Para "2"-"8", "10"
        }
    }

    /**
     * Returns the rank
     * @return rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the suit
     * @return suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the value of the card
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the path to card's image
     * @return
     */
    public String getImagePath() {
        return "/cards/" + rank + suit + ".jpg"; // Ruta relativa al directorio de recursos
    }
}