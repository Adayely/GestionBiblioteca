package org.example.gestionbiblioteca.model;

import java.io.Serializable; // <--- 1. IMPORTAR

public class Multa implements Serializable { // <--- 2. IMPLEMENTAR

    // --- 3. AÑADIR ID DE VERSIÓN ---
    private static final long serialVersionUID = 5L; // (Usamos 5L para que sea único)

    private String cedula;
    private int diasRetraso;
    private boolean danado;
    private boolean perdido;
    private double total;

    // (El resto de la clase no cambia)
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

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setPerdido(boolean perdido) {
        this.perdido = perdido;
    }

    public void setDiasRetraso(int diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public void setDanado(boolean danado) {
        this.danado = danado;
    }
}
