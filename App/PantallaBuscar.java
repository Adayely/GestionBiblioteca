package com.example.in.App;

import com.example.in.model.Bibliotecario;
import com.example.in.model.Persona;
import com.example.in.model.Usuario;
import com.example.in.service.GestorRegistro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaBuscar {

    private GestorRegistro gestorRegistro = GestorRegistro.getInstancia();

    public void mostrar(Stage stage) {
        stage.setTitle("Buscar Persona");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Buscar Persona");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Opciones de búsqueda
        HBox boxOpciones = new HBox(10);
        boxOpciones.setAlignment(Pos.CENTER_LEFT);
        Label lblBuscarPor = new Label("Buscar por:");

        ToggleGroup grupo = new ToggleGroup();
        RadioButton rbNombre = new RadioButton("Nombre");
        RadioButton rbCedula = new RadioButton("Cédula");
        RadioButton rbId = new RadioButton("ID");
        rbNombre.setToggleGroup(grupo);
        rbCedula.setToggleGroup(grupo);
        rbId.setToggleGroup(grupo);
        rbCedula.setSelected(true);

        boxOpciones.getChildren().addAll(lblBuscarPor, rbNombre, rbCedula, rbId);

        // Campo de búsqueda
        HBox boxBusqueda = new HBox(10);
        boxBusqueda.setAlignment(Pos.CENTER_LEFT);
        Label lblCriterio = new Label("Criterio:");
        TextField txtCriterio = new TextField();
        txtCriterio.setPrefWidth(250);
        txtCriterio.setPromptText("Ingrese el criterio de búsqueda");
        Button btnBuscar = new Button("Buscar");

        boxBusqueda.getChildren().addAll(lblCriterio, txtCriterio, btnBuscar);

        // Área de resultados
        Label lblResultados = new Label("Resultados:");
        lblResultados.setFont(Font.font("System", FontWeight.BOLD, 14));
        TextArea txtResultados = new TextArea();
        txtResultados.setEditable(false);
        txtResultados.setPrefHeight(300);
        txtResultados.setWrapText(true);

        // Botón buscar acción
        btnBuscar.setOnAction(e -> {
            String criterio = txtCriterio.getText().trim();

            if (criterio.isEmpty()) {
                txtResultados.setText("Por favor, ingrese un criterio de búsqueda.");
                return;
            }

            Persona personaEncontrada = null;

            if (rbCedula.isSelected()) {
                personaEncontrada = gestorRegistro.buscarPersonaPorCedula(criterio);
            } else if (rbNombre.isSelected()) {
                for (Persona p : gestorRegistro.getListaPersonas()) {
                    if (p.getNombre().equalsIgnoreCase(criterio)) {
                        personaEncontrada = p;
                        break;
                    }
                }
            } else if (rbId.isSelected()) {
                try {
                    int id = Integer.parseInt(criterio);
                    for (Persona p : gestorRegistro.getListaPersonas()) {
                        if (p.getIdPersona() == id) {
                            personaEncontrada = p;
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    txtResultados.setText("El ID debe ser un número válido.");
                    return;
                }
            }

            if (personaEncontrada != null) {
                StringBuilder info = new StringBuilder();
                info.append("=== DATOS PERSONALES ===\n");
                info.append("ID Persona: ").append(personaEncontrada.getIdPersona()).append("\n");
                info.append("Nombre: ").append(personaEncontrada.getNombre()).append("\n");
                info.append("Apellido: ").append(personaEncontrada.getApellido()).append("\n");
                info.append("Cédula: ").append(personaEncontrada.getCedula()).append("\n");
                info.append("Teléfono: ").append(personaEncontrada.getTelefono()).append("\n");
                info.append("Correo: ").append(personaEncontrada.getCorreo()).append("\n");
                info.append("Dirección: ").append(personaEncontrada.getDireccion()).append("\n\n");

                if (personaEncontrada instanceof Usuario) {
                    Usuario usuario = (Usuario) personaEncontrada;
                    info.append("=== DATOS DE USUARIO ===\n");
                    info.append("ID Usuario: ").append(usuario.getIdUsuario()).append("\n");
                    info.append("Estado: ").append(usuario.getEstado()).append("\n");
                    info.append("Carnet: ").append(usuario.isCarnet() ? "Sí" : "No").append("\n");
                } else if (personaEncontrada instanceof Bibliotecario) {
                    Bibliotecario bibliotecario = (Bibliotecario) personaEncontrada;
                    info.append("=== DATOS DE BIBLIOTECARIO ===\n");
                    info.append("ID Empleado: ").append(bibliotecario.getIdEmpleado()).append("\n");
                    info.append("Cargo: ").append(bibliotecario.getCargo()).append("\n");
                    info.append("Horario: ").append(bibliotecario.getHorario()).append("\n");
                }

                txtResultados.setText(info.toString());
            } else {
                txtResultados.setText("No se encontró ninguna persona con ese criterio.");
            }
        });

        // Botón regresar
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefWidth(150);
        btnRegresar.setOnAction(e -> {
            PantallaInicioRegistro inicio = new PantallaInicioRegistro();
            inicio.mostrar(stage);
        });

        root.getChildren().addAll(titulo, boxOpciones, boxBusqueda, lblResultados,
                txtResultados, btnRegresar);

        Scene scene = new Scene(root, 700, 550);
        stage.setScene(scene);
        stage.show();
    }
}