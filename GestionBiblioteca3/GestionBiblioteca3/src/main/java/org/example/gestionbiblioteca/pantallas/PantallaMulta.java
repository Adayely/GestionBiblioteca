package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.gestionbiblioteca.gestores.GestorMulta;
import org.example.gestionbiblioteca.model.Multa;
import org.example.gestionbiblioteca.model.Prestamo;

public class PantallaMulta extends Application {

    // El coordinador de lógica (el GestorMulta refactorizado)
    private GestorMulta gestorMulta;

    // --- Componentes UI ---
    // Sección 1: Búsqueda de Préstamos
    private TextField txtCedulaBuscar;
    private Button btnBuscarPrestamos;
    private ListView<Prestamo> listaPrestamosActivos; // 👈 Lo ajustaremos

    // Sección 2: Devolución
    private ComboBox<String> comboCondicion;
    private Button btnRegistrarDevolucion;

    // Sección 3: Multas Pendientes
    private ListView<Multa> listaMultasPendientes; // 👈 Lo ajustaremos
    private Button btnPagarMulta;

    private Button btnVolver;
    private TextArea areaResultados; // Para mostrar mensajes

    // Variables de estado
    private Prestamo prestamoSeleccionado;
    private Multa multaSeleccionada;

    @Override
    public void start(Stage stage) {
        gestorMulta = new GestorMulta();
        stage.setTitle("Devolución de Libros y Gestión de Multas");

        // --- Layout Principal ---
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // --- Arriba: Búsqueda y Devolución (Puntos 1, 2, 3) ---
        VBox topVBox = new VBox(10);

        // Fila 1: Búsqueda por Cédula
        HBox busquedaBox = new HBox(10);
        busquedaBox.getChildren().addAll(
                new Label("Cédula Usuario:"),
                txtCedulaBuscar = new TextField(),
                btnBuscarPrestamos = new Button("Buscar Préstamos Activos")
        );
        // Hacemos que el TextField ocupe el espacio extra
        HBox.setHgrow(txtCedulaBuscar, Priority.ALWAYS);

        // Fila 2: Lista de préstamos activos
        listaPrestamosActivos = new ListView<>();
        // 🛠️ AJUSTE 1: Reducir el alto de la lista de Préstamos para ahorrar espacio.
        listaPrestamosActivos.setPrefHeight(150);

        // Usamos un CellFactory para mostrar el Préstamo de forma legible
        listaPrestamosActivos.setCellFactory(param -> new ListCell<Prestamo>() {
            @Override
            protected void updateItem(Prestamo p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setText(null);
                } else {
                    setText("ID: " + p.getIdPrestamo() + " | Usuario: " + p.getUsuario().getNombre() + " | Libro: " + p.getLibro().getTitulo());
                }
            }
        });

        // Fila 3: Acción de Devolución
        HBox devolucionBox = new HBox(10);
        comboCondicion = new ComboBox<>();
        comboCondicion.getItems().addAll("Bueno", "Dañado", "Perdido");
        comboCondicion.setValue("Bueno"); // Valor por defecto

        btnRegistrarDevolucion = new Button("Registrar Devolución");
        btnRegistrarDevolucion.setDisable(true); // Deshabilitado hasta seleccionar

        devolucionBox.getChildren().addAll(new Label("Condición del libro:"), comboCondicion, btnRegistrarDevolucion);

        topVBox.getChildren().addAll(busquedaBox, new Label("**Préstamos Activos del Usuario:**"), listaPrestamosActivos, new Separator(), devolucionBox);
        root.setTop(topVBox);

        // --- Centro: Multas Pendientes (Puntos 4 y 5) ---
        VBox centerVBox = new VBox(10);
        centerVBox.setPadding(new Insets(15, 0, 15, 0));

        listaMultasPendientes = new ListView<>();
        // 🛠️ AJUSTE 2: Reducir el alto de la lista de Multas para ahorrar espacio.
        listaMultasPendientes.setPrefHeight(150);

        // El toString() de Multa ya es legible

        centerVBox.getChildren().addAll(new Separator(), new Label("**Multas Pendientes (Todos los usuarios):**"), listaMultasPendientes);
        root.setCenter(centerVBox);

        // --- Abajo: Acciones y Resultados ---
        VBox bottomVBox = new VBox(10);
        btnPagarMulta = new Button("Pagar Multa Seleccionada");
        btnPagarMulta.setDisable(true); // Deshabilitado

        btnVolver = new Button("⬅ Volver al Menú");
        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        // Dejamos un alto reducido para los resultados
        areaResultados.setPrefHeight(75);

        bottomVBox.getChildren().addAll(btnPagarMulta, areaResultados, btnVolver);
        root.setBottom(bottomVBox);

        // --- Cargar datos iniciales ---
        cargarMultasPendientes();

        // --- Eventos ---
        btnBuscarPrestamos.setOnAction(e -> handleBuscarPrestamos());
        btnRegistrarDevolucion.setOnAction(e -> handleRegistrarDevolucion());
        btnPagarMulta.setOnAction(e -> handlePagarMulta());
        btnVolver.setOnAction(e -> abrirPantalla("PantallaInicio"));

        // Listeners para habilitar/deshabilitar botones
        listaPrestamosActivos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    prestamoSeleccionado = newVal;
                    btnRegistrarDevolucion.setDisable(newVal == null);
                });

        listaMultasPendientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    multaSeleccionada = newVal;
                    btnPagarMulta.setDisable(newVal == null);
                });

        // --- Mostrar ventana ---
        // 🛠️ AJUSTE 3: Reducir el tamaño inicial de la ventana para que se ajuste mejor al contenido.
        stage.setScene(new Scene(root, 650, 650));
        stage.show();
    }

    // --- Lógica de Handlers ---
    // ... (El resto de tus métodos se mantienen igual) ...

    private void handleBuscarPrestamos() {
        String cedula = txtCedulaBuscar.getText().trim();
        if (cedula.isEmpty()) {
            areaResultados.setText("Error: Ingrese una cédula.");
            return;
        }
        listaPrestamosActivos.getItems().setAll(gestorMulta.buscarPrestamosActivosPorCedula(cedula));
        areaResultados.setText("Búsqueda de préstamos para " + cedula + " completada.");
    }

    private void handleRegistrarDevolucion() {
        if (prestamoSeleccionado == null) {
            areaResultados.setText("Error: No hay ningún préstamo seleccionado.");
            return;
        }

        String condicion = comboCondicion.getValue();

        // Usamos el coordinador
        Multa multaGenerada = gestorMulta.devolverLibro(prestamoSeleccionado, condicion);

        if (multaGenerada != null) {
            areaResultados.setText("¡Devolución registrada! Se generó una multa de $" + multaGenerada.getTotal());
        } else {
            areaResultados.setText("¡Devolución registrada! No se generó multa.");
        }

        // Recargar ambas listas
        handleBuscarPrestamos(); // Para quitar el préstamo devuelto de la lista activa
        cargarMultasPendientes(); // Para mostrar la nueva multa si se generó

        prestamoSeleccionado = null;
        btnRegistrarDevolucion.setDisable(true);
    }

    private void handlePagarMulta() {
        if (multaSeleccionada == null) {
            areaResultados.setText("Error: No hay ninguna multa seleccionada.");
            return;
        }

        boolean exito = gestorMulta.pagarMulta(multaSeleccionada);

        if (exito) {
            areaResultados.setText("Multa pagada y eliminada.");
        } else {
            areaResultados.setText("Error al procesar el pago.");
        }

        // Recargar lista de multas
        cargarMultasPendientes();
        multaSeleccionada = null;
        btnPagarMulta.setDisable(true);
    }

    private void cargarMultasPendientes() {
        listaMultasPendientes.getItems().setAll(gestorMulta.getMultasPendientes());
    }

    // --- Métodos de utilidad (sin cambios) ---
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