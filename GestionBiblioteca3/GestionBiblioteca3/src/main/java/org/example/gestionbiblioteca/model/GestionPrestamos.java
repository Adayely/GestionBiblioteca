package org.example.gestionbiblioteca.model; // O donde pongas tus gestores

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionPrestamos {

    private static final String NOMBRE_ARCHIVO = "prestamos.dat";

    private List<Prestamo> prestamos;

    /**
     * Constructor: Carga los préstamos guardados del archivo.
     */
    public GestionPrestamos() {
        this.prestamos = cargarPrestamos();
    }

    /**
     * Lee el archivo .dat y devuelve la lista de préstamos.
     */
    @SuppressWarnings("unchecked")
    private List<Prestamo> cargarPrestamos() {
        List<Prestamo> prestamosCargados = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                prestamosCargados = (List<Prestamo>) ois.readObject();
                System.out.println("Préstamos cargados exitosamente desde " + NOMBRE_ARCHIVO);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar préstamos: " + e.getMessage());
            }
        }
        return prestamosCargados;
    }

    /**
     * Guarda la lista COMPLETA de préstamos en el archivo.
     * Este método es crucial y debe llamarse después de CUALQUIER cambio.
     */
    public void guardarListaCompleta() {
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(prestamos);
            System.out.println("Préstamos guardados exitosamente en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
        }
    }

    /**
     * Añade un préstamo a la lista y guarda la lista completa.
     */
    public void agregarPrestamoYGuardar(Prestamo prestamo) {
        this.prestamos.add(prestamo);
        guardarListaCompleta(); // Guarda automáticamente al añadir
    }

    /**
     * Devuelve la lista de préstamos en memoria.
     */
    public List<Prestamo> getPrestamos() {
        return this.prestamos;
    }
}
