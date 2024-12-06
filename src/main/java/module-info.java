module io.github.jeangiraldoo.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.jeangiraldoo.cincuentazo to javafx.fxml;
    exports io.github.jeangiraldoo.cincuentazo;
}