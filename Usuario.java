public class Usuario extends Persona {
    private int idUsuario;
    private String estado;
    private boolean carnet;

    public Usuario(int idPersona, String nombre, String apellido, String edad, String telefono, String correo, String direccion,
                   int idUsuario, String estado, boolean carnet) {
        super(idPersona, nombre, apellido, edad, telefono, correo, direccion);
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.carnet = carnet;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getEstado() { return estado; }
    public boolean isCarnet() { return carnet; }
}
