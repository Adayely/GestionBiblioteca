package com.example.in.App;

import com.example.in.model.Bibliotecario;
import com.example.in.model.Usuario;
import com.example.in.service.GestorRegistro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaRegistro {

    private GestorRegistro gestorRegistro = GestorRegistro.getInstancia();

    public void mostrar(Stage stage) {
        stage.setTitle("Registrar Persona");

        VBox contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));

        Label titulo = new Label("Registrar Persona");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Tipo de persona
        HBox boxTipo = new HBox(10);
        boxTipo.setAlignment(Pos.CENTER_LEFT);
        Label lblTipo = new Label("Tipo de Persona:");
        ComboBox<String> cbTipoPersona = new ComboBox<>();
        cbTipoPersona.getItems().addAll("Usuario", "Bibliotecario");
        cbTipoPersona.setValue("Usuario");
        cbTipoPersona.setPrefWidth(150);
        boxTipo.getChildren().addAll(lblTipo, cbTipoPersona);

        // Campos comunes
        GridPane gridComun = new GridPane();
        gridComun.setHgap(10);
        gridComun.setVgap(10);

        TextField txtIdPersona = new TextField();
        TextField txtNombre = new TextField();
        TextField txtApellido = new TextField();
        TextField txtCedula = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtCorreo = new TextField();
        TextField txtDireccion = new TextField();

        gridComun.add(new Label("ID Persona:"), 0, 0);
        gridComun.add(txtIdPersona, 1, 0);
        gridComun.add(new Label("Nombre:"), 0, 1);
        gridComun.add(txtNombre, 1, 1);
        gridComun.add(new Label("Apellido:"), 0, 2);
        gridComun.add(txtApellido, 1, 2);
        gridComun.add(new Label("Cédula:"), 0, 3);
        gridComun.add(txtCedula, 1, 3);
        gridComun.add(new Label("Teléfono:"), 0, 4);
        gridComun.add(txtTelefono, 1, 4);
        gridComun.add(new Label("Correo:"), 0, 5);
        gridComun.add(txtCorreo, 1, 5);
        gridComun.add(new Label("Dirección:"), 0, 6);
        gridComun.add(txtDireccion, 1, 6);

        // Campos específicos Usuario
        VBox camposUsuario = new VBox(10);
        Label lblUsuario = new Label("Datos de Usuario");
        lblUsuario.setFont(Font.font("System", FontWeight.BOLD, 14));

        GridPane gridUsuario = new GridPane();
        gridUsuario.setHgap(10);
        gridUsuario.setVgap(10);

        TextField txtIdUsuario = new TextField();
        TextField txtEstado = new TextField();
        CheckBox chkCarnet = new CheckBox();

        gridUsuario.add(new Label("ID Usuario:"), 0, 0);
        gridUsuario.add(txtIdUsuario, 1, 0);
        gridUsuario.add(new Label("Estado:"), 0, 1);
        gridUsuario.add(txtEstado, 1, 1);
        gridUsuario.add(new Label("Carnet:"), 0, 2);
        gridUsuario.add(chkCarnet, 1, 2);

        camposUsuario.getChildren().addAll(lblUsuario, gridUsuario);

        // Campos específicos Bibliotecario
        VBox camposBibliotecario = new VBox(10);
        Label lblBibliotecario = new Label("Datos de Bibliotecario");
        lblBibliotecario.setFont(Font.font("System", FontWeight.BOLD, 14));

        GridPane gridBibliotecario = new GridPane();
        gridBibliotecario.setHgap(10);
        gridBibliotecario.setVgap(10);

        TextField txtIdEmpleado = new TextField();
        TextField txtCargo = new TextField();
        TextField txtHorario = new TextField();

        gridBibliotecario.add(new Label("ID Empleado:"), 0, 0);
        gridBibliotecario.add(txtIdEmpleado, 1, 0);
        gridBibliotecario.add(new Label("Cargo:"), 0, 1);
        gridBibliotecario.add(txtCargo, 1, 1);
        gridBibliotecario.add(new Label("Horario:"), 0, 2);
        gridBibliotecario.add(txtHorario, 1, 2);

        camposBibliotecario.getChildren().addAll(lblBibliotecario, gridBibliotecario);
        camposBibliotecario.setVisible(false);
        camposBibliotecario.setManaged(false);

        // Cambiar campos según tipo
        cbTipoPersona.setOnAction(e -> {
            if (cbTipoPersona.getValue().equals("Usuario")) {
                camposUsuario.setVisible(true);
                camposUsuario.setManaged(true);
                camposBibliotecario.setVisible(false);
                camposBibliotecario.setManaged(false);
            } else {
                camposBibliotecario.setVisible(true);
                camposBibliotecario.setManaged(true);
                camposUsuario.setVisible(false);
                camposUsuario.setManaged(false);
            }
        });

        // Botones
        HBox boxBotones = new HBox(10);
        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefWidth(100);
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefWidth(100);
        boxBotones.getChildren().addAll(btnRegistrar, btnRegresar);

        // Acción registrar
        btnRegistrar.setOnAction(e -> {
            try {
                if (txtIdPersona.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
                        txtApellido.getText().trim().isEmpty() || txtCedula.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Complete todos los campos obligatorios.");
                    alert.showAndWait();
                    return;
                }

                int idPersona = Integer.parseInt(txtIdPersona.getText().trim());
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String cedula = txtCedula.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String correo = txtCorreo.getText().trim();
                String direccion = txtDireccion.getText().trim();

                if (cbTipoPersona.getValue().equals("Usuario")) {
                    if (txtIdUsuario.getText().trim().isEmpty() || txtEstado.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Complete todos los campos de Usuario.");
                        alert.showAndWait();
                        return;
                    }

                    int idUsuario = Integer.parseInt(txtIdUsuario.getText().trim());
                    String estado = txtEstado.getText().trim();
                    boolean carnet = chkCarnet.isSelected();

                    Usuario usuario = new Usuario(idPersona, nombre, apellido, cedula, telefono,
                            correo, direccion, idUsuario, estado, carnet);
                    gestorRegistro.registrarUsuario(usuario);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setContentText("Usuario registrado correctamente.");
                    alert.showAndWait();

                } else {
                    if (txtIdEmpleado.getText().trim().isEmpty() || txtCargo.getText().trim().isEmpty() ||
                            txtHorario.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Complete todos los campos de Bibliotecario.");
                        alert.showAndWait();
                        return;
                    }

                    int idEmpleado = Integer.parseInt(txtIdEmpleado.getText().trim());
                    String cargo = txtCargo.getText().trim();
                    String horario = txtHorario.getText().trim();

                    Bibliotecario bibliotecario = new Bibliotecario(idPersona, nombre, apellido, cedula,
                            telefono, correo, direccion, idEmpleado,
                            cargo, horario);
                    gestorRegistro.registrarBibliotecario(bibliotecario);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setContentText("Bibliotecario registrado correctamente.");
                    alert.showAndWait();
                }

                // Limpiar campos
                txtIdPersona.clear();
                txtNombre.clear();
                txtApellido.clear();
                txtCedula.clear();
                txtTelefono.clear();
                txtCorreo.clear();
                txtDireccion.clear();
                txtIdUsuario.clear();
                txtEstado.clear();
                chkCarnet.setSelected(false);
                txtIdEmpleado.clear();
                txtCargo.clear();
                txtHorario.clear();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Los campos ID deben ser números válidos.");
                alert.showAndWait();
            }
        });

        btnRegresar.setOnAction(e -> {
            PantallaInicioRegistro inicio = new PantallaInicioRegistro();
            inicio.mostrar(stage);
        });

        contenedor.getChildren().addAll(titulo, boxTipo, gridComun, camposUsuario,
                camposBibliotecario, boxBotones);

        ScrollPane scroll = new ScrollPane(contenedor);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 600, 650);
        stage.setScene(scene);
        stage.show();
    }
}