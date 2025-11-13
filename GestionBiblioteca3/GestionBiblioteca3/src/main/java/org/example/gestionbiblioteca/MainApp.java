package org.example.gestionbiblioteca;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.gestionbiblioteca.pantallas.PantallaInicio;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        PantallaInicio inicio = new PantallaInicio();
        inicio.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
