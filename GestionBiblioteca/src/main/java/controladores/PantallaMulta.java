package controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.*;

public class PantallaMulta {

    @FXML private TextField txtCedula;
    @FXML private TextField txtDias;
    @FXML private CheckBox chkDanado;
    @FXML private CheckBox chkPerdido;
    @FXML private ListView<String> listaMultas;

    private GestorMulta gestor = new GestorMulta();

    @FXML
    public void initialize() {
        actualizar();
    }

    @FXML
    private void registrarMulta() {
        try {
            String cedula = txtCedula.getText();
            int dias = Integer.parseInt(txtDias.getText());
            boolean danado = chkDanado.isSelected();
            boolean perdido = chkPerdido.isSelected();

            double total = gestor.calcularMulta(dias, danado, perdido);

            Multa m = new Multa(cedula, dias, danado, perdido, total);
            gestor.guardarMulta(m);

            actualizar();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Datos inválidos.");
            a.show();
        }
    }

    @FXML
    private void pagarMulta() {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Función de pago en desarrollo.");
        a.show();
    }

    @FXML
    private void actualizar() {
        listaMultas.getItems().clear();
        gestor.cargarMultas().forEach(m -> listaMultas.getItems().add(m.toString()));
    }
}
