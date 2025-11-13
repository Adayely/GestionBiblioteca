package org.example.gestionbiblioteca.model; // O donde pongas tus gestores

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase maneja la persistencia (guardado y carga) de la lista
 * de multas en el archivo 'multas.dat'.
 */
public class GestionMulta {

    private static final String NOMBRE_ARCHIVO = "multas.dat";
    private List<Multa> multas;

    /**
     * Constructor: Carga las multas guardadas del archivo.
     */
    public GestionMulta() {
        this.multas = cargarMultas();
    }

    /**
     * Lee el archivo .dat y devuelve la lista de multas.
     */
    @SuppressWarnings("unchecked")
    private List<Multa> cargarMultas() {
        List<Multa> multasCargadas = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                multasCargadas = (List<Multa>) ois.readObject();
                System.out.println("Multas cargadas exitosamente desde " + NOMBRE_ARCHIVO);

            } catch (IOException | ClassNotFoundException e) {
                // EOFException (archivo vacío) o ClassNotFound, empezamos lista vacía
                System.err.println("Error al cargar multas (puede ser archivo vacío): " + e.getMessage());
            }
        }
        return multasCargadas;
    }

    /**
     * Guarda la lista COMPLETA de multas en el archivo.
     * Este método debe llamarse después de CUALQUIER cambio.
     */
    public void guardarListaCompleta() {
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(multas);
            System.out.println("Multas guardadas exitosamente en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("Error al guardar multas: " + e.getMessage());
        }
    }

    /**
     * Añade una multa a la lista y guarda la lista completa.
     */
    public void agregarMultaYGuardar(Multa multa) {
        this.multas.add(multa);
        guardarListaCompleta(); // Guarda automáticamente al añadir
    }

    /**
     * Devuelve la lista de multas en memoria.
     */
    public List<Multa> getMultas() {
        return this.multas;
    }
}
