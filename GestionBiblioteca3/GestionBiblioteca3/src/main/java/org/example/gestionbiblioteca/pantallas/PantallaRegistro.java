package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.gestionbiblioteca.gestores.GestorRegistro;
import org.example.gestionbiblioteca.model.Bibliotecario;
import org.example.gestionbiblioteca.model.Usuario;

public class PantallaRegistro extends Application {

    private GestorRegistro gestorRegistro = GestorRegistro.getInstancia();

    // Campos de texto promovidos a variables de instancia
    private ComboBox<String> cbTipoPersona;
    private TextField txtNombre, txtApellido, txtCedula, txtTelefono, txtCorreo, txtDireccion;
    private TextField txtIdEmpleado, txtHorario; // 'txtCargo' eliminado

    @Override
    public void start(Stage stage) {
        stage.setTitle("Registrar Persona");

        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label titulo = new Label("Registrar Persona");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Tipo de persona
        HBox boxTipo = new HBox(10);
        boxTipo.setAlignment(Pos.CENTER_LEFT);
        Label lblTipo = new Label("Tipo de Persona:");

        cbTipoPersona = new ComboBox<>();
        cbTipoPersona.getItems().addAll("Usuario", "Bibliotecario");
        cbTipoPersona.setValue("Usuario");
        cbTipoPersona.setPrefWidth(150);
        boxTipo.getChildren().addAll(lblTipo, cbTipoPersona);

        // Campos comunes
        GridPane gridComun = new GridPane();
        gridComun.setHgap(10);
        gridComun.setVgap(10);

        txtNombre = new TextField();
        txtApellido = new TextField();
        txtCedula = new TextField();
        txtTelefono = new TextField();
        txtCorreo = new TextField();
        txtDireccion = new TextField();

        gridComun.add(new Label("Nombre:"), 0, 0);
        gridComun.add(txtNombre, 1, 0);
        gridComun.add(new Label("Apellido:"), 0, 1);
        gridComun.add(txtApellido, 1, 1);
        gridComun.add(new Label("Cédula:"), 0, 2);
        gridComun.add(txtCedula, 1, 2);
        gridComun.add(new Label("Teléfono:"), 0, 3);
        gridComun.add(txtTelefono, 1, 3);
        gridComun.add(new Label("Correo:"), 0, 4);
        gridComun.add(txtCorreo, 1, 4);
        gridComun.add(new Label("Dirección:"), 0, 5);
        gridComun.add(txtDireccion, 1, 5);

        // Campos Bibliotecario
        VBox camposBibliotecario = new VBox(10);
        Label lblBibliotecario = new Label("Datos de Bibliotecario");
        lblBibliotecario.setFont(Font.font("System", FontWeight.BOLD, 14));

        GridPane gridBibliotecario = new GridPane();
        gridBibliotecario.setHgap(10);
        gridBibliotecario.setVgap(10);

        txtIdEmpleado = new TextField();
        txtHorario = new TextField(); // Campo 'Cargo' eliminado

        gridBibliotecario.add(new Label("ID Empleado:"), 0, 0);
        gridBibliotecario.add(txtIdEmpleado, 1, 0);
        gridBibliotecario.add(new Label("Horario:"), 0, 1); // Fila actualizada
        gridBibliotecario.add(txtHorario, 1, 1);

        camposBibliotecario.getChildren().addAll(lblBibliotecario, gridBibliotecario);
        camposBibliotecario.setVisible(false);
        camposBibliotecario.setManaged(false);

        // Cambio tipo
        cbTipoPersona.setOnAction(e -> {
            boolean esBibliotecario = cbTipoPersona.getValue().equals("Bibliotecario");
            camposBibliotecario.setVisible(esBibliotecario);
            camposBibliotecario.setManaged(esBibliotecario);
        });

        // Botones
        HBox boxBotones = new HBox(10);
        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefWidth(100);
        Button btnRegresar = new Button("⬅ Volver");
        btnRegresar.setPrefWidth(100);
        boxBotones.getChildren().addAll(btnRegistrar, btnRegresar);

        // Acción de registro (llama al método actualizado)
        btnRegistrar.setOnAction(e -> registrarPersona());

        btnRegresar.setOnAction(e -> abrirPantalla("PantallaInicioRegistro"));

        contenedor.getChildren().addAll(titulo, boxTipo, gridComun, camposBibliotecario, boxBotones);

        ScrollPane scroll = new ScrollPane(contenedor);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 600, 550);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Intenta registrar una persona.
     * Verifica la cédula duplicada y limpia los campos solo si tiene éxito.
     */
    private void registrarPersona() {
        try {
            String tipo = cbTipoPersona.getValue();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String cedula = txtCedula.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (tipo.equals("Usuario")) {
                Usuario usuario = new Usuario(0, nombre, apellido, cedula, telefono, correo, direccion, 0, "", false);

                // --- CAMBIO: Se lee la respuesta booleana del gestor ---
                boolean exito = gestorRegistro.registrarUsuario(usuario);

                if (exito) {
                    mostrarAlerta("Éxito", "Usuario registrado correctamente.");
                    limpiarCampos(); // Limpia solo si tuvo éxito
                } else {
                    mostrarAlerta("Error", "La cédula " + cedula + " ya se encuentra registrada.");
                }

            } else { // Es Bibliotecario
                int idEmpleado = Integer.parseInt(txtIdEmpleado.getText().trim());
                String horario = txtHorario.getText().trim();

                // Se pasa "" (vacío) para el campo 'cargo' eliminado
                Bibliotecario bibliotecario = new Bibliotecario(0, nombre, apellido, cedula, telefono, correo, direccion, idEmpleado, "", horario);

                // --- CAMBIO: Se lee la respuesta booleana del gestor ---
                boolean exito = gestorRegistro.registrarBibliotecario(bibliotecario);

                if (exito) {
                    mostrarAlerta("Éxito", "Bibliotecario registrado correctamente.");
                    limpiarCampos(); // Limpia solo si tuvo éxito
                } else {
                    mostrarAlerta("Error", "La cédula " + cedula + " ya se encuentra registrada.");
                }
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El campo 'ID Empleado' debe ser un número.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Verifique todos los campos ingresados.");
        }
    }

    /**
     * Limpia todos los campos de entrada del formulario.
     */
    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtIdEmpleado.clear();
        txtHorario.clear();

        cbTipoPersona.setValue("Usuario"); // Resetea el ComboBox
    }

    /**
     * Abre una nueva pantalla de aplicación y cierra la actual.
     */
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

    /**
     * Muestra una alerta simple de tipo Información.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}