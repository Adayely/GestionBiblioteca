import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaPrestamo {
    private GestorPrestamo gestorPrestamo;

    public PantallaPrestamo(GestorPrestamo gestorPrestamo) {
        this.gestorPrestamo = gestorPrestamo;
    }

    public void mostrar(Stage stage) {
        // ---- Controles ----
        Label lblUsuario = new Label("Nombre de Usuario:");
        TextField txtUsuario = new TextField();

        Label lblLibro = new Label("Título del Libro:");
        TextField txtLibro = new TextField();

        Button btnCrear = new Button("Crear Préstamo");
        Button btnDevolver = new Button("Devolver Préstamo");
        Button btnMostrar = new Button("Mostrar Préstamos Activos");

        Label lblIdDevolver = new Label("ID Préstamo a devolver:");
        TextField txtIdDevolver = new TextField();

        TextArea areaResultados = new TextArea();
        areaResultados.setEditable(false);

        // ---- Acciones ----
        btnCrear.setOnAction(e -> {
            if (txtUsuario.getText().isEmpty() || txtLibro.getText().isEmpty()) {
                areaResultados.appendText("⚠️ Debes ingresar usuario y libro.\n");
                return;
            }

            Usuario usuario = new Usuario(1, txtUsuario.getText(), "Apellido", "20", "0999999999", "correo@ejemplo.com", "Direccion", 1, "Activo", true);
            Libro libro = new Libro(1, txtLibro.getText(), "Autor", "Categoría", true, "Bueno", "N/A");
            gestorPrestamo.crearPrestamo(usuario, libro);
            areaResultados.appendText(" Préstamo creado: " + libro.getTitulo() + "\n");
        });

        btnDevolver.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdDevolver.getText());
                gestorPrestamo.devolverLibro(id);
                areaResultados.appendText(" Préstamo " + id + " devuelto.\n");
            } catch (NumberFormatException ex) {
                areaResultados.appendText(" ID no válido.\n");
            }
        });

        btnMostrar.setOnAction(e -> {
            areaResultados.clear();
            for (Prestamo p : gestorPrestamo.getPrestamos()) {
                areaResultados.appendText("ID: " + p.getIdPrestamo() + " | "
                        + p.getUsuario().getNombre() + " - "
                        + p.getLibro().getTitulo() + " (" + p.getEstado() + ")\n");
            }
        });

        // ---- Layout ----
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                lblUsuario, txtUsuario,
                lblLibro, txtLibro,
                btnCrear,
                lblIdDevolver, txtIdDevolver, btnDevolver,
                btnMostrar,
                new Label("Préstamos activos:"),
                areaResultados
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setTitle("Gestión de Préstamos - Biblioteca");
        stage.setScene(scene);
        stage.show();
    }
}
