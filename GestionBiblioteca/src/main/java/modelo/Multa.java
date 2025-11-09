
package modelo;

public class Multa {
    private String cedula;
    private int diasRetraso;
    private boolean danado;
    private boolean perdido;
    private double total;

    public Multa(String cedula, int dias, boolean danado, boolean perdido, double total) {
        this.cedula = cedula;
        this.diasRetraso = dias;
        this.danado = danado;
        this.perdido = perdido;
        this.total = total;
    }

    public String getCedula() { return cedula; }
    public int getDiasRetraso() { return diasRetraso; }
    public boolean isDanado() { return danado; }
    public boolean isPerdido() { return perdido; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Cédula: " + cedula + " | Días: " + diasRetraso +
                " | Dañado: " + danado + " | Perdido: " + perdido +
                " | Total: $" + total;
    }
}
