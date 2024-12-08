module io.github.jeangiraldoo.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.security.jgss;

    opens io.github.jeangiraldoo.cincuentazo.Model to javafx.graphics;
    opens io.github.jeangiraldoo.cincuentazo.Controller to javafx.fxml;

    opens io.github.jeangiraldoo.cincuentazo to javafx.fxml;

    exports io.github.jeangiraldoo.cincuentazo;
    exports io.github.jeangiraldoo.cincuentazo.Model;
    exports io.github.jeangiraldoo.cincuentazo.Controller;
}
