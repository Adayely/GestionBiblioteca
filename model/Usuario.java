package com.example.in.model;

public class Usuario extends Persona {
    private int idUsuario;
    private String estado;
    private boolean carnet;

    public Usuario() {}

    public Usuario(int idPersona, String nombre, String apellido, String cedula, String telefono,
                   String correo, String direccion, int idUsuario, String estado, boolean carnet) {
        super(idPersona, nombre, apellido, cedula, telefono, correo, direccion);
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.carnet = carnet;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isCarnet() {
        return carnet;
    }
    public void setCarnet(boolean carnet) {
        this.carnet = carnet;
    }
}