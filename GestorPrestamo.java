import java.util.ArrayList;
import java.util.Date;

public class GestorPrestamo {
    private ArrayList<Prestamo> prestamos = new ArrayList<>();
    private int contador = 1;

    public void crearPrestamo(Usuario usuario, Libro libro) {
        Prestamo prestamo = new Prestamo(contador++, new Date(), null, "Activo", usuario, libro);
        prestamos.add(prestamo);
    }

    public void devolverLibro(int id) {
        for (Prestamo p : prestamos) {
            if (p.getIdPrestamo() == id) {
                p.setEstado("Devuelto");
                p.setFechaFin(new Date());
            }
        }
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
}
