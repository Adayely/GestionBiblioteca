import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Instanciamos el gestor de préstamos (control de la lógica)
        GestorPrestamo gestor = new GestorPrestamo();

        // Creamos y mostramos la pantalla principal (interfaz)
        PantallaPrestamo pantalla = new PantallaPrestamo(gestor);
        pantalla.mostrar(stage);
    }

    public static void main(String[] args) {
        // Método de inicio de JavaFX (reemplaza el main clásico)
        launch(args);
    }
}
