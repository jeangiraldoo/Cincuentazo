package io.github.jeangiraldoo.cincuentazo.Model;

public class Card {
    private final String rank; // Ej. "2", "A", "J"
    private final String suit; // Ej. "H", "D", "C", "S"
    private final int value;

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

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return "/cards/" + rank + suit + ".jpg"; // Ruta relativa al directorio de recursos
    }
}