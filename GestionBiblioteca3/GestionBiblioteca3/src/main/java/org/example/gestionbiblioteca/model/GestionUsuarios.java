package org.example.gestionbiblioteca.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarios {

    // Nombre del archivo de guardado
    private static final String NOMBRE_ARCHIVO = "usuarios.dat";

    // Lista de usuarios que se maneja en memoria
    private List<Usuario> usuarios;

    /**
     * Constructor. Al iniciar, carga los usuarios del archivo.
     */
    public GestionUsuarios() {
        this.usuarios = cargarUsuarios();
    }

    /**
     * Intenta leer el archivo .dat y devuelve la lista de usuarios.
     * Si no existe, devuelve una lista vacía.
     */
    @SuppressWarnings("unchecked") // Necesario para el casting de (List<Usuario>)
    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuariosCargados = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                usuariosCargados = (List<Usuario>) ois.readObject();
                System.out.println("Usuarios cargados exitosamente desde " + NOMBRE_ARCHIVO);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar usuarios: " + e.getMessage());
                // Si hay un error, empieza con una lista vacía
            }
        }
        return usuariosCargados;
    }

    /**
     * Guarda la lista COMPLETA de usuarios (this.usuarios) en el archivo.
     * Sobrescribe el contenido anterior.
     */
    private void guardarUsuarios() {
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(usuarios);
            System.out.println("Usuarios guardados exitosamente en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Método público para añadir un nuevo usuario.
     * Lo añade a la lista en memoria y luego guarda la lista en disco.
     */
    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        guardarUsuarios(); // Llama a guardar cada vez que se modifica la lista
    }

    /**
     * Método público para obtener la lista de usuarios cargados.
     */
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    // --- ¡NUEVO MÉTODO AÑADIDO! ---
    /**
     * Busca un usuario específico por su número de cédula.
     * @param cedula La cédula a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    public Usuario buscarUsuarioPorCedula(String cedula) {
        for (Usuario u : this.usuarios) {
            // Asumiendo que Persona (clase padre) tiene el método getCedula()
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null; // No se encontró
    }
}
