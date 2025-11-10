package com.example.in.model;

public class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    //dato del chatgpt:
    //private int edad;
    private String cedula;
    private String telefono;
    private String correo;
    private String direccion;

    public Persona() {}

    public Persona(int idPersona, String nombre, String apellido, String cedula, String telefono, String correo, String direccion) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        //dato del chatgpt:
        //this.edad = edad;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public int getIdPersona() {
        return idPersona;
    }
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //dato del chatgpt: public int getEdad() { return edad; }
    //dato del chatgpt: public void setEdad(String edad) { this.edad = edad; }

    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    /*
     * Maria para presidenta de la fis
     * */
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}