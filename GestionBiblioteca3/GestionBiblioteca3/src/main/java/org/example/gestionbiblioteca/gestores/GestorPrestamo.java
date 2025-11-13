package org.example.gestionbiblioteca.gestores;

import org.example.gestionbiblioteca.model.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GestorPrestamo {

    // ... (Variables y constructor sin cambios) ...
    private GestionUsuarios gestionUsuarios;
    private GestionLibros gestionLibros;
    private GestionPrestamos gestionPrestamos;
    private GestionMulta gestionMulta;

    public GestorPrestamo() {
        this.gestionUsuarios = new GestionUsuarios();
        this.gestionLibros = new GestionLibros();
        this.gestionPrestamos = new GestionPrestamos();
        this.gestionMulta = new GestionMulta();
    }

    // ... (validarUsuarioPorCedula, usuarioTieneMultasPendientes - Sin cambios) ...
    public Usuario validarUsuarioPorCedula(String cedula) {
        return gestionUsuarios.buscarUsuarioPorCedula(cedula);
    }
    public boolean usuarioTieneMultasPendientes(String cedula) {
        List<Multa> multas = gestionMulta.getMultas();
        for (Multa m : multas) {
            if (m.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    // --- Punto 2: Búsqueda de Libros (Con el nuevo método) ---

    public List<Libro> buscarLibrosDisponibles() {
        return gestionLibros.getLibros().stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorCategoria(String categoria) {
        return gestionLibros.buscarPorCategoria(categoria).stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return gestionLibros.buscarPorTitulo(titulo).stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return gestionLibros.buscarPorAutor(autor).stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());
    }

    // --- ¡NUEVO MÉTODO AÑADIDO! ---
    /**
     * Pide a GestionLibros la lista de categorías únicas.
     */
    public List<String> getCategoriasUnicas() {
        return gestionLibros.getCategoriasUnicas();
    }


    // ... (crearPrestamo, devolverLibro, getPrestamosActivos, etc. - Sin cambios) ...
    public boolean crearPrestamo(Usuario usuario, Libro libro, Date fechaInicio, Date fechaFin) {
        if (!libro.isDisponible()) {
            return false;
        }

        int nuevoId = generarSiguienteIdPrestamo();
        Prestamo nuevoPrestamo = new Prestamo(nuevoId, fechaInicio, fechaFin, "Activo", usuario, libro);
        gestionPrestamos.agregarPrestamoYGuardar(nuevoPrestamo);
        gestionLibros.actualizarDisponibilidad(libro.getIdLibro(), false);
        return true;
    }

    public void devolverLibro(int idPrestamo) {
        boolean prestamoEncontrado = false;
        Libro libroADevolver = null;

        for (Prestamo p : gestionPrestamos.getPrestamos()) {
            if (p.getIdPrestamo() == idPrestamo && p.getEstado().equals("Activo")) {
                p.setEstado("Devuelto");
                p.setFechaFin(new Date());
                libroADevolver = p.getLibro();
                prestamoEncontrado = true;
                break;
            }
        }

        if (prestamoEncontrado) {
            gestionPrestamos.guardarListaCompleta();
            if (libroADevolver != null) {
                gestionLibros.actualizarDisponibilidad(libroADevolver.getIdLibro(), true);
            }
        }
    }

    public List<Prestamo> getPrestamosActivos() {
        return gestionPrestamos.getPrestamos().stream()
                .filter(p -> p.getEstado().equals("Activo"))
                .collect(Collectors.toList());
    }

    private int generarSiguienteIdPrestamo() {
        int maxId = 0;
        for (Prestamo p : gestionPrestamos.getPrestamos()) {
            if (p.getIdPrestamo() > maxId) {
                maxId = p.getIdPrestamo();
            }
        }
        return maxId + 1;
    }
}