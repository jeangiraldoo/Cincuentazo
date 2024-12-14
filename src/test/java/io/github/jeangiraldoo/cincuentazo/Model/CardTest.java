package io.github.jeangiraldoo.cincuentazo.Model;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    void getValueTest1(){
        Card carta = new Card("J", "C");
        assertEquals(-10, carta.getValue());
    }

    void getValueTest2(){
        Card carta = new Card("9", "C");
        assertEquals(0, carta.getValue());
    }

}