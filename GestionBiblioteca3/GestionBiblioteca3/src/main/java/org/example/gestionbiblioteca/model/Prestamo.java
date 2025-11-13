package org.example.gestionbiblioteca.model;

import java.io.Serializable; // <--- 1. Importar
import java.util.Date;

public class Prestamo implements Serializable { // <--- 2. Implementar

    // 3. Añadir ID de versión (diferente a los otros)
    private static final long serialVersionUID = 4L;

    private int idPrestamo;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private Usuario usuario;
    private Libro libro; // (Recuerda hacer 'Libro' Serializable también)

    public Prestamo(int idPrestamo, Date fechaInicio, Date fechaFin, String estado, Usuario usuario, Libro libro) {
        this.idPrestamo = idPrestamo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuario = usuario;
        this.libro = libro;
    }

    // --- Getters y Setters (sin cambios) ---
    public int getIdPrestamo() { return idPrestamo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public Usuario getUsuario() { return usuario; }
    public Libro getLibro() { return libro; }
}
