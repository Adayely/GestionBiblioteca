package org.example.gestionbiblioteca.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GestionLibros {

    private static final String ARCHIVO_LIBROS = "libros.dat";
    private List<Libro> libros;

    /**
     * Constructor. Carga los libros del archivo .dat.
     * Si el archivo no existe, lo crea vacío.
     */
    public GestionLibros() {
        File archivo = new File(ARCHIVO_LIBROS);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile(); // Crear archivo vacío
                // Guardar una lista vacía para evitar errores de EOF
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                    oos.writeObject(new ArrayList<Libro>());
                }
            } catch (IOException e) {
                System.err.println("Error al crear archivo: " + e.getMessage());
            }
        }
        this.libros = cargarLibros();
    }

    /**
     * Carga la lista de libros desde el archivo .dat.
     */
    @SuppressWarnings("unchecked")
    private List<Libro> cargarLibros() {
        List<Libro> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_LIBROS))) {
            lista = (List<Libro>) ois.readObject();
        } catch (EOFException e) {
            // Archivo vacío, es normal, devolvemos lista vacía
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar libros: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda la lista completa de libros en el archivo .dat.
     */
    private void guardarLibros() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_LIBROS))) {
            oos.writeObject(libros);
        } catch (IOException e) {
            System.err.println("Error al guardar libros: " + e.getMessage());
        }
    }

    /**
     * Agrega un libro a la lista y guarda en el archivo.
     */
    public void agregarLibro(Libro libro) {
        boolean existe = libros.stream().anyMatch(l -> l.getIdLibro() == libro.getIdLibro());
        if (!existe) {
            libros.add(libro);
            guardarLibros();
        }
    }

    /**
     * Elimina un libro por su ID y guarda los cambios.
     */
    public boolean eliminarLibro(int idLibro) {
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getIdLibro() == idLibro) {
                it.remove();
                guardarLibros();
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza solo la disponibilidad de un libro (usado al crear préstamo).
     */
    public boolean actualizarDisponibilidad(int idLibro, boolean disponible) {
        for (Libro l : libros) {
            if (l.getIdLibro() == idLibro) {
                l.setDisponible(disponible);
                guardarLibros();
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza el estado completo de un libro (usado en devoluciones).
     */
    public boolean actualizarEstadoLibro(int idLibro, boolean disponible, String condicion) {
        for (Libro l : libros) {
            if (l.getIdLibro() == idLibro) {
                l.setDisponible(disponible);
                l.setCondicion(condicion);
                guardarLibros(); // Guarda los cambios
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve la lista completa de libros en memoria.
     */
    public List<Libro> getLibros() {
        return libros;
    }

    // --- Métodos de Búsqueda ---

    /**
     * Busca libros que contengan el término en el título.
     * No distingue mayúsculas/minúsculas.
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultado = new ArrayList<>();
        String tituloLower = titulo.toLowerCase();
        for (Libro l : libros) {
            if (l.getTitulo().toLowerCase().contains(tituloLower)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    /**
     * Busca libros que contengan el término en el autor.
     * No distingue mayúsculas/minúsculas.
     */
    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> resultado = new ArrayList<>();
        String autorLower = autor.toLowerCase();
        for (Libro l : libros) {
            if (l.getAutor().toLowerCase().contains(autorLower)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    /**
     * Busca libros que contengan el término en la categoría.
     * No distingue mayúsculas/minúsculas.
     */
    public List<Libro> buscarPorCategoria(String categoria) {
        List<Libro> resultado = new ArrayList<>();
        String categoriaLower = categoria.toLowerCase();
        for (Libro l : libros) {
            if (l.getCategoria().toLowerCase().contains(categoriaLower)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    /**
     * Devuelve una lista de todas las categorías únicas que existen
     * en la lista de libros, ordenadas alfabéticamente.
     */
    public List<String> getCategoriasUnicas() {
        return this.libros.stream()
                .map(Libro::getCategoria)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Borra todos los libros de la lista y guarda.
     */
    public void limpiarLibros() {
        libros.clear();
        guardarLibros();
    }
}
