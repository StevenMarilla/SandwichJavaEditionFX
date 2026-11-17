module sandwichjavaeditionfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens app to javafx.fxml;
    opens model to javafx.base, javafx.fxml;
    exports app;
    exports model;
}