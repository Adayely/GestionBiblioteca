package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PantallaInicio extends Application {

    @Override
    public void start(Stage stage) {
        // --- Título ---
        Text titulo = new Text("📚 Sistema de Gestión de Biblioteca");
        titulo.setFont(Font.font("Arial", 20));
        titulo.setFill(Color.WHITE);

        // --- Botones principales ---
        Button btnRegistrar = new Button("Registrar");
        Button btnPrestamo = new Button("Solicitar Préstamo");
        Button btnLibros = new Button("Libros");
        Button btnMulta = new Button("Consultar Multa");

        // --- Estilo común ---
        Button[] botones = {btnRegistrar, btnPrestamo, btnLibros, btnMulta};
        for (Button b : botones) {
            b.setPrefWidth(250);
            b.setStyle(
                    "-fx-background-color: #ffffff; " +
                            "-fx-font-size: 14px; " +
                            "-fx-text-fill: #333333; " +
                            "-fx-font-weight: bold;"
            );
        }

        // --- Contenedor principal ---
        VBox layout = new VBox(15, titulo, btnRegistrar, btnPrestamo, btnLibros, btnMulta);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #2b5876, #4e4376);");

        Scene scene = new Scene(layout, 400, 350);

        // --- Acciones de los botones ---
        btnRegistrar.setOnAction(e -> abrirPantalla("PantallaInicioRegistro"));
        btnPrestamo.setOnAction(e -> abrirPantalla("PantallaPrestamo"));
        btnLibros.setOnAction(e -> abrirPantalla("PantallaLibro"));
        btnMulta.setOnAction(e -> abrirPantalla("PantallaMulta"));

        // --- Mostrar ventana ---
        stage.setTitle("Menú Principal - Biblioteca");
        stage.setScene(scene);
        stage.show();
    }

    // --- Método genérico para abrir pantallas ---
    private void abrirPantalla(String nombrePantalla) {
        try {
            Class<?> clase = Class.forName("org.example.gestionbiblioteca.pantallas." + nombrePantalla);
            Application app = (Application) clase.getDeclaredConstructor().newInstance();
            Stage nuevoStage = new Stage();
            app.start(nuevoStage);

            // Cierra la ventana actual
            for (javafx.stage.Window window : javafx.stage.Window.getWindows()) {
                if (window.isShowing()) {
                    ((Stage) window).close();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
