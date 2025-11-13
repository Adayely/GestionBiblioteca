package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane; // <-- IMPORTAR
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.gestionbiblioteca.gestores.GestorPrestamo;
import org.example.gestionbiblioteca.model.Libro;
import org.example.gestionbiblioteca.model.Usuario;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaPrestamo extends Application {

    // ... (Variables de Usuario, Libro, etc. - Sin cambios) ...
    private GestorPrestamo gestorPrestamo;
    private Usuario usuarioValidado;
    private Libro libroSeleccionado;
    private VBox layout;
    private VBox seccionUsuario, seccionLibro, seccionFechas, seccionAccion;
    private TextField txtCedula;
    private Button btnValidarUsuario;
    private Label lblNombreUsuario;
    private ListView<Libro> listViewLibros;

    // --- Controles de búsqueda ---
    private ComboBox<String> cbBuscarPor;
    private TextField txtCriterioLibro;
    private Button btnBuscarLibro;

    // --- ¡NUEVOS COMPONENTES! ---
    private ComboBox<String> cbCategorias; // El nuevo desplegable
    private StackPane inputContenedor;     // Contenedor para intercambiar

    private DatePicker datePickerInicio;
    private DatePicker datePickerFin;
    private Button btnConfirmarPrestamo;
    private TextArea areaResultados;
    private Button btnVolver;


    @Override
    public void start(Stage stage) {
        gestorPrestamo = new GestorPrestamo();
        stage.setTitle("Gestión de Préstamos - Biblioteca");

        // --- Sección Usuario (Paso 1 - Sin cambios) ---
        seccionUsuario = new VBox(5);
        seccionUsuario.getChildren().addAll(
                new Label("Paso 1: Ingrese la Cédula del Usuario:"),
                new HBox(5, new Label("Cédula:"), txtCedula = new TextField(), btnValidarUsuario = new Button("Validar")),
                lblNombreUsuario = new Label()
        );

        // --- 'seccionLibro' (Paso 2 - ¡MODIFICADO!) ---
        seccionLibro = new VBox(5);

        cbBuscarPor = new ComboBox<>();
        cbBuscarPor.getItems().addAll("Todos", "Título", "Autor", "Categoría");
        cbBuscarPor.setValue("Todos");

        // Campo de texto normal
        txtCriterioLibro = new TextField();
        txtCriterioLibro.setPromptText("Ingrese el término");
        txtCriterioLibro.setDisable(true); // Deshabilitado porque "Todos" está seleccionado

        // --- ¡NUEVO! ---
        // 1. Crear el ComboBox de Categorías
        cbCategorias = new ComboBox<>();
        cbCategorias.setPromptText("Seleccione categoría");
        cbCategorias.setVisible(false); // Oculto al inicio
        cbCategorias.setMaxWidth(Double.MAX_VALUE); // Para que ocupe el mismo ancho

        // 2. Llenar el ComboBox con las categorías del gestor
        try {
            cbCategorias.getItems().addAll(gestorPrestamo.getCategoriasUnicas());
        } catch (Exception e) {
            // Si falla, al menos la UI funciona
            System.err.println("Error al cargar categorías: " + e.getMessage());
        }

        // 3. Crear el StackPane para intercambiarlos
        // Apila el campo de texto y el combobox uno encima del otro
        inputContenedor = new StackPane(txtCriterioLibro, cbCategorias);
        inputContenedor.setAlignment(Pos.CENTER_LEFT);

        btnBuscarLibro = new Button("Buscar");

        // 4. Añadir el StackPane AL LUGAR del antiguo txtCriterioLibro
        HBox boxBusquedaLibro = new HBox(5, new Label("Buscar por:"), cbBuscarPor, inputContenedor, btnBuscarLibro);
        boxBusquedaLibro.setAlignment(Pos.CENTER_LEFT);

        seccionLibro.getChildren().addAll(
                new Label("Paso 2: Seleccione un Libro (solo disponibles)"),
                boxBusquedaLibro, // <-- Controles actualizados
                listViewLibros = new ListView<>()
        );

        // ... (CellFactory - Sin cambios) ...
        listViewLibros.setCellFactory(param -> new ListCell<Libro>() {
            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitulo() + " (Autor: " + item.getAutor() + ")");
                }
            }
        });

        // ... (Sección Fechas y Acción - Sin cambios) ...
        seccionFechas = new VBox(5);
        seccionFechas.getChildren().addAll(
                new Label("Paso 3: Seleccione las Fechas"),
                new HBox(5, new Label("Fecha Inicio:"), datePickerInicio = new DatePicker()),
                new HBox(5, new Label("Fecha Fin:"), datePickerFin = new DatePicker())
        );
        seccionAccion = new VBox(5);
        btnConfirmarPrestamo = new Button("Confirmar Préstamo");
        seccionAccion.getChildren().add(btnConfirmarPrestamo);
        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        areaResultados.setPromptText("Aquí se mostrarán los resultados y errores...");
        btnVolver = new Button("⬅ Volver al Menú");

        // Deshabilitar secciones
        seccionLibro.setDisable(true);
        seccionFechas.setDisable(true);
        seccionAccion.setDisable(true);

        // --- Handlers (Eventos) ---

        // --- ¡LÓGICA DE INTERCAMBIO MODIFICADA! ---
        cbBuscarPor.setOnAction(e -> {
            String seleccion = cbBuscarPor.getValue();

            if (seleccion.equals("Categoría")) {
                // Mostrar ComboBox de Categorías, ocultar TextField
                txtCriterioLibro.setVisible(false);
                cbCategorias.setVisible(true);

            } else if (seleccion.equals("Todos")) {
                // Mostrar TextField, ocultar ComboBox de Categorías
                txtCriterioLibro.setVisible(true);
                cbCategorias.setVisible(false);
                // Y Deshabilitar el TextField
                txtCriterioLibro.setDisable(true);
                txtCriterioLibro.clear();

            } else { // Título o Autor
                // Mostrar TextField, ocultar ComboBox de Categorías
                txtCriterioLibro.setVisible(true);
                cbCategorias.setVisible(false);
                // Y Habilitar el TextField
                txtCriterioLibro.setDisable(false);
            }
        });

        btnValidarUsuario.setOnAction(e -> handleValidarUsuario());
        btnBuscarLibro.setOnAction(e -> handleBuscarLibros());
        listViewLibros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> handleLibroSeleccionado(newVal)
        );
        btnConfirmarPrestamo.setOnAction(e -> handleConfirmarPrestamo());
        btnVolver.setOnAction(e -> abrirPantalla("PantallaInicio"));

        // ... (Layout final y Scene - Sin cambios) ...
        layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                seccionUsuario, new Separator(),
                seccionLibro, new Separator(),
                seccionFechas, new Separator(),
                seccionAccion, new Separator(),
                areaResultados, btnVolver
        );

        Scene scene = new Scene(layout, 500, 700);
        stage.setScene(scene);
        stage.show();
    }

    // --- Lógica de los Handlers (Paso a Paso) ---

    private void handleValidarUsuario() {
        // ... (Sin cambios) ...
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            lblNombreUsuario.setText("⚠️ Ingrese una cédula.");
            lblNombreUsuario.setStyle("-fx-text-fill: red;");
            return;
        }

        seccionLibro.setDisable(true);
        seccionFechas.setDisable(true);
        seccionAccion.setDisable(true);
        usuarioValidado = null;
        usuarioValidado = gestorPrestamo.validarUsuarioPorCedula(cedula);

        if (usuarioValidado != null) {
            boolean tieneMultas = gestorPrestamo.usuarioTieneMultasPendientes(usuarioValidado.getCedula());
            if (tieneMultas) {
                lblNombreUsuario.setText("⚠️ No esta registrado, ingreso mal el numero de cedula o tiene multas pendientes");
                lblNombreUsuario.setStyle("-fx-text-fill: red;");
                areaResultados.setText("El usuario debe pagar sus multas en la pantalla de 'Devoluciones y Multas' antes de solicitar nuevos libros.");
                usuarioValidado = null;
            } else {
                lblNombreUsuario.setText("✅ Usuario: " + usuarioValidado.getNombre() + " " + usuarioValidado.getApellido() + " (Sin multas)");
                lblNombreUsuario.setStyle("-fx-text-fill: green;");
                seccionLibro.setDisable(false);
                areaResultados.clear();
            }
        } else {
            lblNombreUsuario.setText("⚠️ No esta registrado, ingreso mal el numero de cedula o tiene multas pendientes");
            lblNombreUsuario.setStyle("-fx-text-fill: red;");
        }
    }


    /**
     * Maneja la búsqueda de libros según el criterio seleccionado (TextField O ComboBox).
     */
    private void handleBuscarLibros() {
        areaResultados.clear();
        listViewLibros.getItems().clear();
        String criterio = cbBuscarPor.getValue();
        List<Libro> resultados = new ArrayList<>();

        try {
            switch (criterio) {
                case "Todos":
                    resultados = gestorPrestamo.buscarLibrosDisponibles();
                    break;

                case "Título":
                    String titulo = txtCriterioLibro.getText().trim();
                    if (titulo.isEmpty()) {
                        areaResultados.appendText("⚠️ Ingrese un Título para buscar.\n");
                        return;
                    }
                    resultados = gestorPrestamo.buscarLibrosPorTitulo(titulo);
                    break;

                case "Autor":
                    String autor = txtCriterioLibro.getText().trim();
                    if (autor.isEmpty()) {
                        areaResultados.appendText("⚠️ Ingrese un Autor para buscar.\n");
                        return;
                    }
                    resultados = gestorPrestamo.buscarLibrosPorAutor(autor);
                    break;

                case "Categoría":
                    // --- ¡MODIFICADO! ---
                    // Lee el valor del ComboBox de Categorías
                    String categoria = cbCategorias.getValue();
                    if (categoria == null || categoria.isEmpty()) {
                        areaResultados.appendText("⚠️ Seleccione una Categoría para buscar.\n");
                        return;
                    }
                    resultados = gestorPrestamo.buscarLibrosPorCategoria(categoria);
                    break;
            }
        } catch (Exception ex) {
            areaResultados.appendText("Error inesperado durante la búsqueda: " + ex.getMessage() + "\n");
            return;
        }

        if (resultados.isEmpty()) {
            areaResultados.appendText("No se encontraron libros disponibles con ese criterio.\n");
        } else {
            listViewLibros.getItems().addAll(resultados);
            areaResultados.appendText("Mostrando " + resultados.size() + " libros encontrados.\n");
        }
    }

    private void handleLibroSeleccionado(Libro libro) {
        // ... (Sin cambios) ...
        if (libro != null) {
            libroSeleccionado = libro;
            seccionFechas.setDisable(false);
            seccionAccion.setDisable(false);
            areaResultados.appendText("Libro seleccionado: " + libro.getTitulo() + "\n");
        } else {
            libroSeleccionado = null;
            seccionFechas.setDisable(true);
            seccionAccion.setDisable(true);
        }
    }

    private void handleConfirmarPrestamo() {
        // ... (Sin cambios) ...
        if (usuarioValidado == null || libroSeleccionado == null ||
                datePickerInicio.getValue() == null || datePickerFin.getValue() == null) {
            areaResultados.appendText("⚠️ Error: Faltan datos (Usuario, Libro o Fechas).\n");
            return;
        }
        if (datePickerFin.getValue().isBefore(datePickerInicio.getValue())) {
            areaResultados.appendText("⚠️ Error: La fecha de fin no puede ser anterior a la de inicio.\n");
            return;
        }
        Date fechaInicio = Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(datePickerFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean exito = gestorPrestamo.crearPrestamo(usuarioValidado, libroSeleccionado, fechaInicio, fechaFin);

        if (exito) {
            areaResultados.appendText("✅ ¡Préstamo creado con éxito!\n");
            areaResultados.appendText("Usuario: " + usuarioValidado.getNombre() + "\n");
            areaResultados.appendText("Libro: " + libroSeleccionado.getTitulo() + "\n");
            resetFormulario();
        } else {
            areaResultados.appendText("⚠️ Error al crear el préstamo (Quizás el libro ya no está disponible).\n");
            handleBuscarLibros();
        }
    }

    private void resetFormulario() {
        // ... (Resets de txtCedula, lblNombreUsuario, etc. - Sin cambios) ...
        txtCedula.clear();
        lblNombreUsuario.setText("");
        listViewLibros.getItems().clear();
        seccionLibro.setDisable(true);
        seccionFechas.setDisable(true);
        seccionAccion.setDisable(true);
        datePickerInicio.setValue(null);
        datePickerFin.setValue(null);
        usuarioValidado = null;
        libroSeleccionado = null;

        // --- ¡MODIFICADO! ---
        // Resetea los controles de búsqueda a su estado inicial
        cbBuscarPor.setValue("Todos");
        txtCriterioLibro.clear();
        txtCriterioLibro.setDisable(true); // Deshabilitado

        cbCategorias.setValue(null); // Limpia la selección

        txtCriterioLibro.setVisible(true); // Muestra el TextField
        cbCategorias.setVisible(false); // Oculta el ComboBox
    }

    private void abrirPantalla(String nombrePantalla) {
        // ... (Sin cambios) ...
        try {
            Class<?> clase = Class.forName("org.example.gestionbiblioteca.pantallas." + nombrePantalla);
            Application app = (Application) clase.getDeclaredConstructor().newInstance();
            Stage nuevoStage = new Stage();
            app.start(nuevoStage);

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