package org.example.gestionbiblioteca.model;

import java.io.Serializable; // <--- 1. Importar

public class Bibliotecario extends Persona implements Serializable { // <--- 2. Implementar

    // 3. Añadir un ID de versión (diferente a los otros)
    private static final long serialVersionUID = 3L;

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

    // --- Getters y Setters (sin cambios) ---
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

    // --- Métodos (sin cambios) ---
    public void buscarLibro() {
        System.out.println("Buscando libro en el sistema...");
    }
}
