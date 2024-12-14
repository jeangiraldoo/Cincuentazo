package io.github.jeangiraldoo.cincuentazo.Model;

import static org.junit.jupiter.api.Assertions.*;

class MesaTest {
    void getSumaTest(){
        Card carta = new Card("J", "C");
        Mesa mesa = new Mesa(carta);
        assertEquals(-10, mesa.getSumaMesa());
    }
}