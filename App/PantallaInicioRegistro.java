package com.example.in.App;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaInicioRegistro {

    public void mostrar(Stage stage) {
        stage.setTitle("Sistema de Gestión Biblioteca");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Sistema de Gestión Biblioteca");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefSize(200, 40);
        btnBuscar.setFont(Font.font(14));
        btnBuscar.setOnAction(e -> {
            PantallaBuscar buscar = new PantallaBuscar();
            buscar.mostrar(stage);
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefSize(200, 40);
        btnRegistrar.setFont(Font.font(14));
        btnRegistrar.setOnAction(e -> {
            PantallaRegistro registro = new PantallaRegistro();
            registro.mostrar(stage);
        });

        root.getChildren().addAll(titulo, btnBuscar, btnRegistrar);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}