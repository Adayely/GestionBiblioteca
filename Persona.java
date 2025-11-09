public class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String edad;
    private String telefono;
    private String correo;
    private String direccion;

    public Persona(int idPersona, String nombre, String apellido, String edad, String telefono, String correo, String direccion) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }
}
