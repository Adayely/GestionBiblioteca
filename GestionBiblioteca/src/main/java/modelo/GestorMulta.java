package modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorMulta {

    private List<String> multas = new ArrayList<>();

    public String generarMulta(String cedula, int dias, boolean danado, boolean perdido) {
        double total = dias * 0.50;

        if (danado) total += 5.0;
        if (perdido) total += 20.0;

        String multa = "Cédula: " + cedula + " | Total: $" + total;
        multas.add(multa);
        return multa;
    }

    public String registrarPagoMulta(String multa) {
        multas.remove(multa);
        return "Pagada: " + multa;
    }

    public List<String> cargarMultas() {
        return multas;
    }
}
