package io.github.jeangiraldoo.cincuentazo.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {
    @Test
    void isEliminateTest(){
        Player jugador = new Player("Jean");
        jugador.eliminar();
        assertTrue(jugador.isEliminate());
    }
  
}