package Libro;

import java.util.ArrayList;
import java.util.List;

public class GestorLibro {
    private List<Libro> listaLibros;

    public GestorLibro() {
        this.listaLibros = new ArrayList<>();
    }

    //registrar libro y guardarlo en la lista
    public void registrarLibro(int idLibro, String titulo, String autor, String categoria, String estadoFisico, boolean disponible, String estadoCondicion) {
        Libro l = new Libro(idLibro, titulo, autor, categoria, estadoFisico, disponible, estadoCondicion);
        listaLibros.add(l);
        System.out.println("Libro registrado: " + titulo + "con ID: " + idLibro);
    }

    //mostrar libro
    public void mostrarLibro(Libro libro) {
        if (listaLibros.contains(libro)) {
            System.out.println("Detalles del libro: " +
                    "ID: " + libro.getIdLibro() +
                    " | Título: " + libro.getTitulo() +
                    " | Autor: " + libro.getAutor() +
                    " | Categoría: " + libro.getCategoria() +
                    " | Estado físico: " + libro.getEstadoFisico() +
                    " | Disponible: " + (libro.isDisponible() ? "Sí" : "No") +
                    " | Condición: " + libro.getEstadoCondicion());
        } else {
            System.out.println("El libro no se encuentra registrado en la biblioteca.");
        }
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : listaLibros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                resultado.add(l);
            }
        }
        return resultado;
        //System.out.println("Buscando libro por título: " + titulo);
    }

    public List<Libro> buscarPorAutor(String autor) {
        //System.out.println("Buscando libros del autor: " + autor);
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : listaLibros) {
            if (l.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public List<Libro> buscarPorCategoria(String categoria) {
        //System.out.println("Buscando libros de categoría: " + categoria);
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : listaLibros) {
            if (l.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public boolean actualizarLibro(int idLibro, boolean disponible) {
        //System.out.println("Libro actualizado: " + libro.getTitulo());
        for (Libro l : listaLibros) {
            if (l.getIdLibro() == idLibro) {
                l.setDisponible(disponible);  // Solo cambiamos la disponibilidad
                System.out.println("Disponibilidad actualizada para el libro: " + l.getTitulo() +
                        " | Disponible: " + (disponible ? "Sí" : "No"));
                return true;
            }
        }
        return false; // Libro no encontrado
    }

    public boolean eliminarLibro(int idLibro) {
        //System.out.println("Libro eliminado: " + libro.getTitulo());
        for (Libro l : listaLibros) {
            if (l.getIdLibro() == idLibro) {
                listaLibros.remove(l);
                System.out.println("Libro eliminado: " + l.getTitulo());
                return true;
            }
        }
        return false;
    }

    // Getter de lista de libros
    public List<Libro> getListaLibros() {
        return listaLibros;
    }
}