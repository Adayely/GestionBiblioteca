public class Libro {
    private int idLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private boolean disponible;
    private String estadoFisico;
    private String estadoCondicion;

    public Libro(int idLibro, String titulo, String autor, String categoria, boolean disponible, String estadoFisico, String estadoCondicion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.disponible = disponible;
        this.estadoFisico = estadoFisico;
        this.estadoCondicion = estadoCondicion;
    }

    public int getIdLibro() { return idLibro; }
    public String getTitulo() { return titulo; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
