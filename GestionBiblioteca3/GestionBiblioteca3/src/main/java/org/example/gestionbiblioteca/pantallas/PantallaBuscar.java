package org.example.gestionbiblioteca.pantallas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.gestionbiblioteca.gestores.GestorRegistro;
import org.example.gestionbiblioteca.model.Bibliotecario;
import org.example.gestionbiblioteca.model.Persona;
import org.example.gestionbiblioteca.model.Usuario;

public class PantallaBuscar extends Application {

    private GestorRegistro gestorRegistro = GestorRegistro.getInstancia();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Buscar Persona");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Buscar Persona");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Opciones de búsqueda (sin "ID")
        HBox boxOpciones = new HBox(10);
        boxOpciones.setAlignment(Pos.CENTER_LEFT);
        Label lblBuscarPor = new Label("Buscar por:");
        ToggleGroup grupo = new ToggleGroup();
        RadioButton rbNombre = new RadioButton("Nombre");
        RadioButton rbCedula = new RadioButton("Cédula");
        rbNombre.setToggleGroup(grupo);
        rbCedula.setToggleGroup(grupo);
        rbCedula.setSelected(true);
        boxOpciones.getChildren().addAll(lblBuscarPor, rbNombre, rbCedula);

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

        // Acción de búsqueda
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
            }
            // Lógica de búsqueda por ID eliminada

            if (personaEncontrada != null) {
                StringBuilder info = new StringBuilder();
                info.append("=== DATOS PERSONALES ===\n");

                // Se quita ID Persona (marcado en amarillo en ambas imágenes)
                // info.append("ID Persona: ").append(personaEncontrada.getIdPersona()).append("\n");

                info.append("Nombre: ").append(personaEncontrada.getNombre()).append("\n");
                info.append("Apellido: ").append(personaEncontrada.getApellido()).append("\n");
                info.append("Cédula: ").append(personaEncontrada.getCedula()).append("\n");
                info.append("Teléfono: ").append(personaEncontrada.getTelefono()).append("\n");
                info.append("Correo: ").append(personaEncontrada.getCorreo()).append("\n");
                info.append("Dirección: ").append(personaEncontrada.getDireccion()).append("\n\n");

                if (personaEncontrada instanceof Usuario usuario) {
                    // Se elimina la información específica de Usuario
                } else if (personaEncontrada instanceof Bibliotecario bibliotecario) {
                    info.append("=== DATOS DE BIBLIOTECARIO ===\n");

                    // --- CAMBIO ---
                    // Se vuelve a mostrar el ID Empleado
                    info.append("ID Empleado: ").append(bibliotecario.getIdEmpleado()).append("\n");

                    // Se elimina Cargo (marcado en amarillo)
                    // info.append("Cargo: ").append(bibliotecario.getCargo()).append("\n");

                    // Se mantiene Horario (no estaba marcado en amarillo)
                    info.append("Horario: ").append(bibliotecario.getHorario()).append("\n");
                }

                txtResultados.setText(info.toString());
            } else {
                txtResultados.setText("No se encontró ninguna persona con ese criterio.");
            }
        });

        // Botón regresar
        Button btnRegresar = new Button("⬅ Volver");
        btnRegresar.setPrefWidth(150);
        btnRegresar.setOnAction(e -> abrirPantalla("PantallaInicioRegistro"));

        root.getChildren().addAll(titulo, boxOpciones, boxBusqueda, lblResultados, txtResultados, btnRegresar);

        Scene scene = new Scene(root, 700, 550);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirPantalla(String nombrePantalla) {
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