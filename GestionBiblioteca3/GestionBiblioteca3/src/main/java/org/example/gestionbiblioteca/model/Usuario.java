package org.example.gestionbiblioteca.model;

import java.io.Serializable; // <--- CAMBIO

public class Usuario extends Persona implements Serializable { // <--- CAMBIO

    // --- CAMBIO ---
    private static final long serialVersionUID = 2L;

    private int idUsuario;
    private String estado;
    private boolean carnet;

    // ¡CAMBIO/CORRECCIÓN! Se usa 'cedula' para coincidir con Persona
    public Usuario(int idPersona, String nombre, String apellido, String cedula, String telefono, String correo, String direccion,
                   int idUsuario, String estado, boolean carnet) {

        // Se pasa 'cedula' al constructor de Persona
        super(idPersona, nombre, apellido, cedula, telefono, correo, direccion);

        this.idUsuario = idUsuario;
        this.estado = estado;
        this.carnet = carnet;
    }

    // --- Getters (sin cambios) ---
    public int getIdUsuario() { return idUsuario; }
    public String getEstado() { return estado; }
    public boolean isCarnet() { return carnet; }
}
