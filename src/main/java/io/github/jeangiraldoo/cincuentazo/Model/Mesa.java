package io.github.jeangiraldoo.cincuentazo.Model;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private List<Card> cartasMesa;
    private int sumaMesa;

    public Mesa(Card cartaInicial) {
        this.cartasMesa = new ArrayList<>();
        this.cartasMesa.add(cartaInicial);
        this.sumaMesa = cartaInicial.getValue();
    }

    public int getSumaMesa() {
        return sumaMesa;
    }

    public void agregarCarta(Card carta) {
        cartasMesa.add(carta);
        sumaMesa += carta.getValue();
    }

    public List<Card> getCartasMesa() {
        return cartasMesa;
    }

    public List<Card> reiniciarMazo() {
        List<Card> cartasParaMazo = new ArrayList<>(cartasMesa);
        cartasParaMazo.remove(cartasMesa.size() - 1); // Dejar la última carta en la mesa
        cartasMesa.clear();
        return cartasParaMazo;
    }
}