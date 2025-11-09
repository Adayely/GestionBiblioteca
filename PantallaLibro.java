package Libro;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class PantallaLibro {
    private GestorLibro gestor;

    public PantallaLibro() {
        this.gestor = new GestorLibro();
    }

    // Componentes GUI
    private TextField txtId, txtTitulo, txtAutor, txtCategoria, txtEstadoFisico, txtEstadoCondicion, txtBuscar;
    private CheckBox chkDisponible;
    //private Button btnRegistrar, btnActualizar, btnEliminar, btnMostrar, btnBuscar;
    private ComboBox<String> comboBusqueda;
    private TextArea areaResultados;

    public void mostrar(Stage stage) {
        stage.setTitle("Gestión de Libros");

        // Campos del libro
        GridPane gridCampos = new GridPane();
        gridCampos.setPadding(new Insets(10));
        gridCampos.setHgap(10);
        gridCampos.setVgap(10);

        txtId = new TextField();
        txtTitulo = new TextField();
        txtAutor = new TextField();
        txtCategoria = new TextField();
        txtEstadoFisico = new TextField();
        txtEstadoCondicion = new TextField();
        chkDisponible = new CheckBox("Disponible");

        gridCampos.add(new Label("ID:"), 0, 0);
        gridCampos.add(txtId, 1, 0);
        gridCampos.add(new Label("Título:"), 0, 1);
        gridCampos.add(txtTitulo, 1, 1);
        gridCampos.add(new Label("Autor:"), 0, 2);
        gridCampos.add(txtAutor, 1, 2);
        gridCampos.add(new Label("Categoría:"), 0, 3);
        gridCampos.add(txtCategoria, 1, 3);
        gridCampos.add(new Label("Estado físico:"), 0, 4);
        gridCampos.add(txtEstadoFisico, 1, 4);
        gridCampos.add(new Label("Condición:"), 0, 5);
        gridCampos.add(txtEstadoCondicion, 1, 5);
        gridCampos.add(chkDisponible, 1, 6);

        // Área de resultados
        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        areaResultados.setPrefHeight(200);

        // Panel de búsqueda
        HBox panelBusqueda = new HBox(10);
        comboBusqueda = new ComboBox<>();
        comboBusqueda.getItems().addAll("Título", "Autor", "Categoría");
        comboBusqueda.setValue("Título");

        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar...");

        Button btnBuscar = new Button("Buscar");
        panelBusqueda.getChildren().addAll(new Label("Buscar por:"), comboBusqueda, txtBuscar, btnBuscar);
        panelBusqueda.setPadding(new Insets(10));

        // Botones principales
        HBox botones = new HBox(10);
        Button btnRegistrar = new Button("Registrar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnMostrar = new Button("Mostrar Todos");

        botones.getChildren().addAll(btnRegistrar, btnActualizar, btnEliminar, btnMostrar);
        botones.setPadding(new Insets(10));

        // Eventos
        btnRegistrar.setOnAction(e -> registrarLibro());
        btnActualizar.setOnAction(e -> actualizarLibro());
        btnEliminar.setOnAction(e -> eliminarLibro());
        btnMostrar.setOnAction(e -> mostrarLibros());
        btnBuscar.setOnAction(e -> buscarLibros());

        // Estructura general
        VBox root = new VBox(10, gridCampos, panelBusqueda, botones, areaResultados);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void registrarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            gestor.registrarLibro(id, txtTitulo.getText(), txtAutor.getText(),
                    txtCategoria.getText(), txtEstadoFisico.getText(),
                    chkDisponible.isSelected(), txtEstadoCondicion.getText());
            mostrarAlerta("Libro registrado correctamente.");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("El ID debe ser un número entero.");
        }
    }

    private void actualizarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean actualizado = gestor.actualizarLibro(id, chkDisponible.isSelected());
            mostrarAlerta(actualizado ? "Libro actualizado." : "No se encontró el libro.");
        } catch (NumberFormatException ex) {
            mostrarAlerta("El ID debe ser un número entero.");
        }
    }

    private void eliminarLibro() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean eliminado = gestor.eliminarLibro(id);
            mostrarAlerta(eliminado ? "Libro eliminado." : "No se encontró el libro.");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            mostrarAlerta("El ID debe ser un número entero.");
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

    private void buscarLibros() {
        areaResultados.clear();
        String criterio = comboBusqueda.getValue();
        String valor = txtBuscar.getText().trim();

        if (valor.isEmpty()) {
            mostrarAlerta("Ingrese un valor para buscar.");
            return;
        }

        List<Libro> resultados = switch (criterio) {
            case "Autor" -> gestor.buscarPorAutor(valor);
            case "Categoría" -> gestor.buscarPorCategoria(valor);
            default -> gestor.buscarPorTitulo(valor);
        };

        if (resultados.isEmpty()) {
            areaResultados.setText("No se encontraron resultados.");
        } else {
            resultados.forEach(l -> areaResultados.appendText(l + "\n"));
        }
    }

    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtCategoria.clear();
        txtEstadoFisico.clear();
        txtEstadoCondicion.clear();
        chkDisponible.setSelected(false);
        txtBuscar.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
