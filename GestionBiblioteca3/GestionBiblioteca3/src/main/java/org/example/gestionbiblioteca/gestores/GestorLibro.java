package org.example.gestionbiblioteca.gestores;
import org.example.gestionbiblioteca.model.Libro;
import org.example.gestionbiblioteca.model.GestionLibros;
import java.util.List;

public class GestorLibro {
    private GestionLibros gestion;

    public GestorLibro() {
        this.gestion = new GestionLibros();
    }

    // Metodo para limpiar todos los libros
    public void limpiarLibros() {
        gestion.limpiarLibros();
    }

    //registrar libro y guardarlo en la lista
    public void registrarLibro(int idLibro, String titulo, String autor, String categoria, String estado, boolean disponible, String condicion) {
        Libro l = new Libro(idLibro, titulo, autor, categoria, estado, disponible, condicion);
        gestion.agregarLibro(l);
        System.out.println("Libro registrado: " + titulo + " con ID: " + idLibro);
    }

    public boolean eliminarLibro(int idLibro) {
        return gestion.eliminarLibro(idLibro);
    }

    public boolean actualizarLibro(int idLibro, boolean disponible) {
        return gestion.actualizarDisponibilidad(idLibro, disponible);
    }

    public List<Libro> getListaLibros() {
        return gestion.getLibros();
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return gestion.buscarPorTitulo(titulo);
    }

    public List<Libro> buscarPorAutor(String autor) {
        return gestion.buscarPorAutor(autor);
    }

    public List<Libro> buscarPorCategoria(String categoria) {
        return gestion.buscarPorCategoria(categoria);
    }

}
