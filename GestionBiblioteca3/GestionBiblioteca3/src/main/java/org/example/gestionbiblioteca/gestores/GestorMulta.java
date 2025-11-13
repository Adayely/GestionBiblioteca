package org.example.gestionbiblioteca.gestores;

import org.example.gestionbiblioteca.model.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Coordinador central para DEVOLUCIONES y MULTAS.
 * Esta clase reemplaza al antiguo GestorMulta.
 * Utiliza las clases de Gestión (Persistencia) para operar.
 */
public class GestorMulta {

    // Los gestores de persistencia
    private GestionPrestamos gestionPrestamos;
    private GestionLibros gestionLibros;
    private GestionMulta gestionMulta; // La NUEVA clase de persistencia

    // Constantes de multas
    private static final double PRECIO_DIA_RETRASO = 0.50;
    private static final double PRECIO_DANADO = 5.00;
    private static final double PRECIO_PERDIDO = 20.00;

    public GestorMulta() {
        // Inicializa los 3 gestores de persistencia
        this.gestionPrestamos = new GestionPrestamos();
        this.gestionLibros = new GestionLibros();
        this.gestionMulta = new GestionMulta();
    }

    // --- Punto 1: Validación de Préstamos por Cédula ---
    public List<Prestamo> buscarPrestamosActivosPorCedula(String cedula) {
        return gestionPrestamos.getPrestamos().stream()
                .filter(p -> p.getUsuario().getCedula().equals(cedula) && p.getEstado().equals("Activo"))
                .collect(Collectors.toList());
    }

    // --- Puntos 3, 4 y 6: Lógica de Devolución y Multa ---
    /**
     * Procesa la devolución de un libro.
     * 1. Calcula la multa (si aplica).
     * 2. Actualiza el estado del libro (Punto 3).
     * 3. Guarda la multa (Punto 4).
     * 4. Actualiza el estado del préstamo a "Devuelto" (Punto 6).
     * @param prestamo El préstamo que se está cerrando.
     * @param condicion "Bueno", "Dañado" o "Perdido".
     * @return La Multa generada (o null si no se generó multa).
     */
    public Multa devolverLibro(Prestamo prestamo, String condicion) {
        Date hoy = new Date();
        Date fechaDebeDevolver = prestamo.getFechaFin(); // Esta es la fecha LÍMITE

        // 1. Calcular días de retraso
        int diasRetraso = 0;
        if (hoy.after(fechaDebeDevolver)) {
            long diffMillis = hoy.getTime() - fechaDebeDevolver.getTime();
            // Aseguramos que cuente el día parcial como 1
            diasRetraso = (int) TimeUnit.MILLISECONDS.toDays(diffMillis) + 1;
        }

        // 2. Determinar flags de condición
        boolean danado = condicion.equals("Dañado");
        boolean perdido = condicion.equals("Perdido");

        // 3. Actualizar el LIBRO en GestionLibros (Punto 3)
        Libro libro = prestamo.getLibro();
        if (perdido) {
            // Si se pierde, no está disponible y su condición es "Perdido"
            gestionLibros.actualizarEstadoLibro(libro.getIdLibro(), false, "Perdido");
        } else {
            // Si está "Bueno" o "Dañado", vuelve a estar disponible
            gestionLibros.actualizarEstadoLibro(libro.getIdLibro(), true, condicion);
        }

        // 4. Actualizar el PRÉSTAMO a "Devuelto" (Punto 6)
        prestamo.setEstado("Devuelto");
        prestamo.setFechaFin(hoy); // Sobrescribimos la fecha límite con la fecha REAL de devolución
        gestionPrestamos.guardarListaCompleta(); // Guarda el cambio de estado

        // 5. Generar y guardar la MULTA (Punto 4)
        Multa nuevaMulta = null;
        if (diasRetraso > 0 || danado || perdido) {
            double total = (diasRetraso * PRECIO_DIA_RETRASO);
            if (danado) total += PRECIO_DANADO;
            if (perdido) total += PRECIO_PERDIDO;

            nuevaMulta = new Multa(
                    prestamo.getUsuario().getCedula(),
                    diasRetraso,
                    danado,
                    perdido,
                    total
            );

            gestionMulta.agregarMultaYGuardar(nuevaMulta);
        }

        return nuevaMulta; // Devuelve la multa para que la pantalla la muestre
    }

    // --- Punto 5: Gestión de Pagos ---

    /**
     * Carga todas las multas pendientes desde el archivo.
     */
    public List<Multa> getMultasPendientes() {
        return gestionMulta.getMultas();
    }

    /**
     * Registra el pago de una multa, eliminándola de la lista persistente.
     * @param multa El objeto Multa a pagar (debe ser el seleccionado de la lista).
     * @return true si el pago (eliminación) fue exitoso.
     */
    public boolean pagarMulta(Multa multa) {
        List<Multa> multasActuales = gestionMulta.getMultas();

        // Removemos el objeto específico de la lista
        boolean eliminado = multasActuales.remove(multa);

        if (eliminado) {
            // Guardamos la lista modificada (sin la multa pagada)
            gestionMulta.guardarListaCompleta();
        }
        return eliminado;
    }
}