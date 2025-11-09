package Libro;

public class Libro {
    private int idLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private String estadoFisico;
    private boolean disponible;
    private String estadoCondicion;

    public Libro(int idLibro, String titulo, String autor, String categoria, String estadoFisico, boolean disponible, String estadoCondicion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estadoFisico = estadoFisico;
        this.disponible = disponible;
        this.estadoCondicion = estadoCondicion;
    }

    //public void verificarDisponibilidad() {
    //    System.out.println(disponible ? "El libro está disponible." : "El libro no está disponible.");
    //

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

    public String getEstadoFisico() {
        return estadoFisico;
    }
    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getEstadoCondicion() {
        return estadoCondicion;
    }
    public void setEstadoCondicion(String estadoCondicion) {
        this.estadoCondicion = estadoCondicion;
    }

    @Override
    public String toString() {
        return "ID: " + idLibro +
                " | Título: " + titulo +
                " | Autor: " + autor +
                " | Categoría: " + categoria +
                " | Disponible: " + (disponible ? "Sí" : "No");
    }
}
