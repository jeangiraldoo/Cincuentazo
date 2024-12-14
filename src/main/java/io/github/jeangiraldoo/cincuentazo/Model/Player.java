package io.github.jeangiraldoo.cincuentazo.Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */

public class Player {
    private String name;
    private VBox container;
    private HBox layout;
    private List<Card> deck;
    private boolean eliminate;

    /**
     * Constructs a player with the specified name.
     *
     * @param nombre The player's name.
     */
    public Player(String nombre) {
        this.name = nombre;
        this.deck = new ArrayList<>();
        this.eliminate = false;
    }

    /**
     * Returns the player's name
     * @return String representing the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's deck
     * @return List<Card> The player's deck
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Checks if the player was eliminated/lost the game
     * @return True if the player lost, False otherwise
     */
    public boolean isEliminate() {
        return eliminate;
    }

    /**
     * Sets the 'eliminate' state to false indicating that the player lost
     */
    public void eliminar() {
        eliminate = true;
    }

    /**
     * Receives a new card to be added to the deck
     * @param carta Card to be added to the deck
     */
    public void recibirCarta(Card carta) {
        if (deck.size() < 4) {
            deck.add(carta);
        }
    }

    /**
     * Uses one of the cards in the deck and returns it so that it goes back to the table
     * @param carta card used by the player
     * @return card used by the player
     */
    public Card jugarCarta(Card carta) {
        if (deck.contains(carta)) {
            deck.remove(carta); // Elimina la carta jugada del mazo
            return carta;
        }
        throw new IllegalArgumentException("La carta no pertenece al jugador");
    }

    /**
     * Checks if the player can play or not
     * @param sumaMesa
     * @return True if the player can play, False otherwise.
     */
    public boolean puedeJugar(int sumaMesa) {
        return deck.stream().anyMatch(c -> sumaMesa + c.getValue() <= 50);
    }

    /**
     * Sets the container that arranges everything related to a player on the board
     * @param container VBox that arranges player-specific elements
     */
    public void setPlayerContainer(VBox container){
        this.container = container;
    }

    /**
     * Returns the player's container
     * @return VBox container that arranges the player's elements
     */
    public VBox getContainer(){
        return container;
    }
    /**
     * Sets the layout to be used by the player
     * @param layout layout which will arrange the player's cards
     */
    public void setLayout(HBox layout){
        this.layout = layout;
    }

    /**
     * Removes all children nodes from the layout
     */
    public void clearLayout(){
        layout.getChildren().clear();
    }

    /**
     * Clears all child nodes from the container.
     */

    public void clearContainer(){
        container.getChildren().clear();
    }

    /**
     * Inserts an image into the layout
     * @param image The image to be displayed in the layout
     */
    public void insertIntoLayout(ImageView image){
        layout.getChildren().add(image);
    }

    /**
     * Returns the layout used by the player
     * @return HBox representing the player's deck
     */
    public HBox getLayout(){
        return this.layout;
    }

}
