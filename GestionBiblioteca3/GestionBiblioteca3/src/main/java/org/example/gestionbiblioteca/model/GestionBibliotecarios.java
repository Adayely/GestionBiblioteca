package org.example.gestionbiblioteca.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionBibliotecarios {

    // 1. Nombre de archivo diferente
    private static final String NOMBRE_ARCHIVO = "bibliotecarios.dat";

    // 2. La lista ahora es de tipo Bibliotecario
    private List<Bibliotecario> bibliotecarios;

    /**
     * Constructor. Al iniciar, carga los bibliotecarios del archivo.
     */
    public GestionBibliotecarios() {
        // 3. Llama al método de cargar bibliotecarios
        this.bibliotecarios = cargarBibliotecarios();
    }

    /**
     * Intenta leer el archivo .dat y devuelve la lista de bibliotecarios.
     * Si no existe, devuelve una lista vacía.
     */
    @SuppressWarnings("unchecked")
    private List<Bibliotecario> cargarBibliotecarios() {
        // 4. Tipo de lista actualizado
        List<Bibliotecario> bibliotecariosCargados = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                // 5. Casting actualizado
                bibliotecariosCargados = (List<Bibliotecario>) ois.readObject();
                System.out.println("Bibliotecarios cargados exitosamente desde " + NOMBRE_ARCHIVO);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar bibliotecarios: " + e.getMessage());
            }
        }
        return bibliotecariosCargados;
    }

    /**
     * Guarda la lista COMPLETA de bibliotecarios (this.bibliotecarios) en el archivo.
     * Sobrescribe el contenido anterior.
     */
    private void guardarBibliotecarios() {
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // 6. Guarda la lista de bibliotecarios
            oos.writeObject(bibliotecarios);
            System.out.println("Bibliotecarios guardados exitosamente en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("Error al guardar bibliotecarios: " + e.getMessage());
        }
    }

    /**
     * Método público para añadir un nuevo bibliotecario.
     * Lo añade a la lista en memoria y luego guarda la lista en disco.
     */
    public void agregarBibliotecario(Bibliotecario bibliotecario) {
        // 7. Parámetro de tipo Bibliotecario
        this.bibliotecarios.add(bibliotecario);
        guardarBibliotecarios(); // Llama a guardar
    }

    /**
     * Método público para obtener la lista de bibliotecarios cargados.
     */
    public List<Bibliotecario> getBibliotecarios() {
        // 8. Devuelve la lista de bibliotecarios
        return this.bibliotecarios;
    }

    // ... (Aquí puedes añadir más métodos como eliminarBibliotecario, etc.)
}
