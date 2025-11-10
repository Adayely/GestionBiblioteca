import java.util.Date;

public class Prestamo {
    private int idPrestamo;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private Usuario usuario;
    private Libro libro;

    public Prestamo(int idPrestamo, Date fechaInicio, Date fechaFin, String estado, Usuario usuario, Libro libro) {
        this.idPrestamo = idPrestamo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuario = usuario;
        this.libro = libro;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public Usuario getUsuario() { return usuario; }
    public Libro getLibro() { return libro; }

    public static class Bibliotecario extends Persona {
        private int idEmpleado;
        private String cargo;
        private String horario;

        public Bibliotecario(int idPersona, String nombre, String apellido, String cedula, String telefono,
                             String correo, String direccion, int idEmpleado, String cargo, String horario) {
            super(idPersona, nombre, apellido, cedula, telefono, correo, direccion);
            this.idEmpleado = idEmpleado;
            this.cargo = cargo;
            this.horario = horario;
        }

        public void buscarLibro() {
            System.out.println("Buscando libro...");
        }
    }
}
