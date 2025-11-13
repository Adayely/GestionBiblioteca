package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaInicioRegistro extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema de Gestión de Biblioteca");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Sistema de Gestión de Biblioteca");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefSize(200, 40);
        btnBuscar.setFont(Font.font(14));
        btnBuscar.setOnAction(e -> abrirPantalla("PantallaBuscar"));

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefSize(200, 40);
        btnRegistrar.setFont(Font.font(14));
        btnRegistrar.setOnAction(e -> abrirPantalla("PantallaRegistro"));

        Button btnVolver = new Button("⬅ Volver al Menú Principal");
        btnVolver.setPrefSize(200, 40);
        btnVolver.setFont(Font.font(14));
        btnVolver.setOnAction(e -> abrirPantalla("PantallaInicio"));

        root.getChildren().addAll(titulo, btnBuscar, btnRegistrar, btnVolver);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

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
