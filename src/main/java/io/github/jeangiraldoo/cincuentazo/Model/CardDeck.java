package io.github.jeangiraldoo.cincuentazo.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
        DeckInit();
    }

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

    public Card takeCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
        Collections.shuffle(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}