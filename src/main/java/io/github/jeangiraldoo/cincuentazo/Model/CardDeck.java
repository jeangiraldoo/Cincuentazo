package io.github.jeangiraldoo.cincuentazo.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents a deck of cards.
 */
public class CardDeck {
    private List<Card> cards;
    /**
     * Constructs a deck and initializes it with all possible cards.
     */
    public CardDeck() {
        this.cards = new ArrayList<>();
        DeckInit();
    }

    /**
     * Initializes the deck by setting up the ranks/suits to be used and then shuffling them
     */
    private void DeckInit() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"H", "D", "C", "S"}; // Corazones, Diamantes, Tréboles, Picas

        for (String rank : ranks) {
            for (String suit : suits) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards); // Baraja las cartas
    }

    /**
     * Takes out the card at the very top of the deck and returns it
     * @return card at the top of the deck
     */
    public Card takeCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    /**
     * Updates the deck by adding new cards to it
     * @param newCards List of cards to be added to the deck
     */
    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
        Collections.shuffle(cards);
    }

    /**
     * Checks whether or not the deck is empty
     * @return True if there are no longer any cards in the deck, False otherwsie
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the list of cards in the deck.
     *
     * @return The deck's cards.
     */

    public List<Card> getCards() {
        return cards;
    }
}