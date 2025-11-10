package Libro;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        GestorLibro gestor = new GestorLibro();

        // Agregar algunos libros de ejemplo
        gestor.registrarLibro(1, "Cien años de soledad", "Gabriel García Márquez", "Novela", "Bueno", true, "Excelente");
        gestor.registrarLibro(2, "El Principito", "Antoine de Saint-Exupéry", "Infantil", "Bueno", true, "Excelente");
        gestor.registrarLibro(3, "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", "Usado", false, "Regular");

        // Mostrar la pantalla principal
        PantallaLibro pantalla = new PantallaLibro();
        pantalla.mostrar(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

