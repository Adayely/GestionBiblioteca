package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import org.example.gestionbiblioteca.gestores.GestorLibro;
import org.example.gestionbiblioteca.model.Libro;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class PantallaLibro extends Application {

    private GestorLibro gestor;

    // Componentes GUI (Sin campos de búsqueda)
    private TextField txtId, txtTitulo, txtAutor;
    private ComboBox<String> comboCategoria, comboEstado, comboCondicion;
    private TextArea areaResultados;

    @Override
    public void start(Stage stage) {
        gestor = new GestorLibro();
        stage.setTitle("Gestión de Libros");

        // --- Campos del libro ---
        GridPane gridCampos = new GridPane();
        gridCampos.setPadding(new Insets(10));
        gridCampos.setHgap(10);
        gridCampos.setVgap(10);

        txtId = new TextField();
        txtTitulo = new TextField();
        txtAutor = new TextField();

        // ComboBox de Categoría
        comboCategoria = new ComboBox<>();
        comboCategoria.getItems().addAll("Acción", "Clásico", "Fantasía", "Ficción", "Infantil", "Juvenil", "Misterio", "Romance", "Suspenso", "Terror");
        comboCategoria.setValue("Acción");

        // ComboBox de Estado físico
        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Excelente", "Bueno", "Regular", "Malo", "Usado");
        comboEstado.setValue("Excelente");

        // ComboBox de Condición
        comboCondicion = new ComboBox<>();
        comboCondicion.getItems().addAll("Nuevo", "Dado de baja", "Donado", "Reacondicionado");
        comboCondicion.setValue("Nuevo");

        // Agregar al grid
        gridCampos.add(new Label("ID:"), 0, 0);
        gridCampos.add(txtId, 1, 0);
        gridCampos.add(new Label("Título:"), 0, 1);
        gridCampos.add(txtTitulo, 1, 1);
        gridCampos.add(new Label("Autor:"), 0, 2);
        gridCampos.add(txtAutor, 1, 2);
        gridCampos.add(new Label("Categoría:"), 0, 3);
        gridCampos.add(comboCategoria, 1, 3);
        gridCampos.add(new Label("Estado:"), 0, 4);
        gridCampos.add(comboEstado, 1, 4);
        gridCampos.add(new Label("Condición:"), 0, 5);
        gridCampos.add(comboCondicion, 1, 5);

        // --- Área de resultados ---
        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        areaResultados.setPrefHeight(200);

        // --- Panel de búsqueda (ELIMINADO) ---

        // --- Botones principales ---
        HBox botones = new HBox(10);
        Button btnRegistrar = new Button("Registrar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnMostrar = new Button("Mostrar todos");
        Button btnLimpiar = new Button("Limpiar libros");
        Button btnVolver = new Button("⬅ Volver al Menú"); // Botón añadido

        botones.getChildren().addAll(btnRegistrar, btnActualizar, btnEliminar, btnMostrar, btnLimpiar, btnVolver);
        botones.setPadding(new Insets(10));

        // --- Eventos ---
        btnRegistrar.setOnAction(e -> registrarLibro());
        btnActualizar.setOnAction(e -> actualizarLibro());
        btnEliminar.setOnAction(e -> eliminarLibro());
        btnMostrar.setOnAction(e -> mostrarLibros());
        // El evento de btnBuscar fue eliminado
        btnLimpiar.setOnAction(e -> {
            gestor.limpiarLibros();
            mostrarAlerta("Información", "Todos los libros han sido eliminados.");
            mostrarLibros(); // Refresca la lista
        });
        btnVolver.setOnAction(e -> abrirPantalla("PantallaInicio")); // Evento añadido

        // --- Estructura general (Actualizada sin panelBusqueda) ---
        VBox root = new VBox(10, gridCampos, botones, areaResultados);
        root.setPadding(new Insets(15));

        // --- Mostrar ventana ---
        Scene scene = new Scene(root, 600, 500); // Se redujo un poco la altura
        stage.setScene(scene);
        stage.show();
    }

    private void registrarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            gestor.registrarLibro(id, txtTitulo.getText(), txtAutor.getText(),
                    comboCategoria.getValue(), comboEstado.getValue(),
                    true, comboCondicion.getValue());
            mostrarAlerta("Información", "Libro registrado correctamente.");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error", "El ID debe ser un número entero.");
        }
    }

    private void actualizarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean actualizado = gestor.actualizarLibro(id, true);
            mostrarAlerta("Información", actualizado ? "Libro actualizado." : "No se encontró el libro.");
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error", "El ID debe ser un número entero.");
        }
    }

    private void eliminarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean eliminado = gestor.eliminarLibro(id);
            mostrarAlerta("Información", eliminado ? "Libro eliminado." : "No se encontró el libro.");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error", "El ID debe ser un número entero.");
        }
    }

    private void mostrarLibros() {
        areaResultados.clear();
        List<Libro> libros = gestor.getListaLibros();
        if (libros.isEmpty()) {
            areaResultados.setText("No hay libros registrados.");
        } else {
            libros.forEach(l -> areaResultados.appendText(l + "\n"));
        }
    }

    // --- Método buscarLibros() (ELIMINADO) ---

    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        // txtBuscar.clear(); // <-- Eliminado
        comboCategoria.setValue("Ficción");
        comboEstado.setValue("Bueno");
        comboCondicion.setValue("Disponible");
    }

    // Método de alerta estandarizado
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Método de navegación añadido
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