package org.example.gestionbiblioteca.model;

import java.io.Serializable;

public class Libro implements Serializable{
    private static final long serialVersionUID = 1L;

    private int idLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private String estado;
    private boolean disponible;
    private String condicion;

    public Libro(int idLibro, String titulo, String autor, String categoria, String estado, boolean disponible, String condicion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = estado;
        this.disponible = disponible;
        this.condicion = condicion;
    }

    // Getters y setters
    public int getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getCondicion() {
        return condicion;
    }
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return "ID: " + idLibro +
                " | Título: " + titulo +
                " | Autor: " + autor +
                " | Categoría: " + categoria +
                " | Estado: " + estado +
                " | Condición: " + condicion +
                " | Disponible: " + (disponible ? "Sí" : "No");
    }
}
