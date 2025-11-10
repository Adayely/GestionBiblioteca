package com.example.in.service;

import com.example.in.model.Persona;
import com.example.in.model.Usuario;
import com.example.in.model.Bibliotecario;

import java.util.ArrayList;
import java.util.List;

public class GestorRegistro {

    private static GestorRegistro instancia;

    private List<Persona> listaPersonas;

    private GestorRegistro() {
        this.listaPersonas = new ArrayList<>();
    }

    public static GestorRegistro getInstancia() {
        if (instancia == null) {
            instancia = new GestorRegistro();
        }
        return instancia;
    }

    // ===================== REGISTRAR USUARIO =====================
    public void registrarUsuario(Usuario usuario) {
        this.listaPersonas.add(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombre());
    }

    // ===================== REGISTRAR BIBLIOTECARIO =====================
    public void registrarBibliotecario(Bibliotecario bibliotecario) {
        this.listaPersonas.add(bibliotecario);
        System.out.println("Bibliotecario registrado: " + bibliotecario.getNombre());
    }

    // ===================== BUSCAR PERSONA POR CÉDULA =====================
    public Persona buscarPersonaPorCedula(String cedula) {
        for (Persona p : listaPersonas) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    // ===================== OBTENER LISTA DE PERSONAS =====================
    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }
}