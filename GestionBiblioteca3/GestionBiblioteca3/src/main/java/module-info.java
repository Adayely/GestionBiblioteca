module org.example.gestionbiblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.gestionbiblioteca to javafx.fxml;
    exports org.example.gestionbiblioteca;
}