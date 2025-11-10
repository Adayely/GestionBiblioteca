package com.example.in.model;

public class Bibliotecario extends Persona {
    private int idEmpleado;
    private String cargo;
    private String horario;
 /*
    private GestorPrestamo gestorPrestamo;
    private GestorLibro gestorLibro;

*/
 public Bibliotecario(int idPersona, String nombre, String apellido, String cedula,
                      String telefono, String correo, String direccion,
                      int idEmpleado, String cargo, String horario) {

     super(idPersona, nombre, apellido, cedula, telefono, correo, direccion);

     this.idEmpleado = idEmpleado;
     this.cargo = cargo;
     this.horario = horario;
 }


    public int getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void buscarLibro() {
        System.out.println("Buscando libro en el sistema...");
    }
}