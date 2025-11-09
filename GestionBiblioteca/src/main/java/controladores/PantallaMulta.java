package controladores;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.GestorMulta;

public class PantallaMulta {

    private TextField txtCedula;
    private TextField txtDias;
    private CheckBox chkDanado;
    private CheckBox chkPerdido;
    private ListView<String> listaMultas;

    private GestorMulta gestorMulta;

    public PantallaMulta() {
        gestorMulta = new GestorMulta();
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Multas");

        // Top: Datos y registro
        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(10));

        HBox fila1 = new HBox(10);
        fila1.getChildren().addAll(
                new Label("Cédula Usuario:"),
                txtCedula = new TextField()
        );

        HBox fila2 = new HBox(10);
        txtDias = new TextField();
        chkDanado = new CheckBox("Libro Dañado");
        chkPerdido = new CheckBox("Libro Perdido");
        Button btnRegistrar = new Button("Registrar Multa");

        fila2.getChildren().addAll(
                new Label("Días de retraso:"),
                txtDias,
                chkDanado,
                chkPerdido,
                btnRegistrar
        );

        topBox.getChildren().addAll(fila1, fila2);

        // Centro: Lista de multas
        listaMultas = new ListView<>();

        // Abajo: botones
        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(10));

        Button btnPagar = new Button("Pagar Multa");
        Button btnActualizar = new Button("Actualizar");

        bottomBox.getChildren().addAll(btnPagar, btnActualizar);

        // BorderPane principal
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(listaMultas);
        root.setBottom(bottomBox);

        // Eventos
        btnRegistrar.setOnAction(e -> registrarMulta());
        btnPagar.setOnAction(e -> pagarMulta());
        btnActualizar.setOnAction(e -> actualizar());

        // Mostrar ventana
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    private void registrarMulta() {
        String cedula = txtCedula.getText();
        int dias = Integer.parseInt(txtDias.getText());
        boolean danado = chkDanado.isSelected();
        boolean perdido = chkPerdido.isSelected();

        String multa = gestorMulta.generarMulta(cedula, dias, danado, perdido);
        listaMultas.getItems().add(multa);
    }

    private void pagarMulta() {
        String seleccion = listaMultas.getSelectionModel().getSelectedItem();
        if (seleccion == null) return;

        String msg = gestorMulta.registrarPagoMulta(seleccion);
        listaMultas.getItems().remove(seleccion);
    }

    private void actualizar() {
        listaMultas.getItems().setAll(gestorMulta.cargarMultas());
    }
}
