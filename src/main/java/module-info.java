module modular {
    requires javafx.controls;
    requires javafx.fxml;
    exports org.example;
    exports org.example.gui;
    exports org.example.math;
    opens org.example to javafx.fxml;
}
