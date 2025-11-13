package org.example.gestionbiblioteca.gestores;

import org.example.gestionbiblioteca.model.Bibliotecario;
import org.example.gestionbiblioteca.model.GestionBibliotecarios;
import org.example.gestionbiblioteca.model.GestionUsuarios;
import org.example.gestionbiblioteca.model.Persona;
import org.example.gestionbiblioteca.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestorRegistro {
    private static GestorRegistro instancia;

    private GestionUsuarios gestionUsuarios;
    private GestionBibliotecarios gestionBibliotecarios;

    private GestorRegistro() {
        this.gestionUsuarios = new GestionUsuarios();
        this.gestionBibliotecarios = new GestionBibliotecarios();
    }

    public static GestorRegistro getInstancia() {
        if (instancia == null) {
            instancia = new GestorRegistro();
        }
        return instancia;
    }

    // ===================== REGISTRAR USUARIO =====================
    // --- CAMBIO: Ahora devuelve 'boolean' en lugar de 'void' ---
    public boolean registrarUsuario(Usuario usuario) {

        // --- CAMBIO: Se añade la validación ---
        // Buscamos si la cédula ya existe ANTES de agregar
        if (this.buscarPersonaPorCedula(usuario.getCedula()) != null) {
            // Si la encontramos, no registramos y devolvemos 'false'
            return false;
        }

        // Si 'buscarPersonaPorCedula' devolvió null, la cédula es única.
        this.gestionUsuarios.agregarUsuario(usuario);
        System.out.println("Usuario registrado y guardado: " + usuario.getNombre());
        // Devolvemos 'true' para indicar que el registro fue exitoso
        return true;
    }

    // ===================== REGISTRAR BIBLIOTECARIO =====================
    // --- CAMBIO: Ahora devuelve 'boolean' en lugar de 'void' ---
    public boolean registrarBibliotecario(Bibliotecario bibliotecario) {

        // --- CAMBIO: Se añade la validación ---
        if (this.buscarPersonaPorCedula(bibliotecario.getCedula()) != null) {
            // Si la encontramos, no registramos y devolvemos 'false'
            return false;
        }

        this.gestionBibliotecarios.agregarBibliotecario(bibliotecario);
        System.out.println("Bibliotecario registrado y guardado: " + bibliotecario.getNombre());
        // Devolvemos 'true' para indicar que el registro fue exitoso
        return true;
    }

    // ===================== BUSCAR PERSONA POR CÉDULA =====================
    public Persona buscarPersonaPorCedula(String cedula) {
        // Primero busca en usuarios
        for (Usuario u : gestionUsuarios.getUsuarios()) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }

        // Si no lo encuentra, busca en bibliotecarios
        for (Bibliotecario b : gestionBibliotecarios.getBibliotecarios()) {
            if (b.getCedula().equals(cedula)) {
                return b;
            }
        }

        return null; // No se encontró en ninguna lista
    }

    // ===================== OBTENER LISTA DE PERSONAS =====================
    public List<Persona> getListaPersonas() {
        List<Persona> listaCombinada = new ArrayList<>();
        listaCombinada.addAll(gestionUsuarios.getUsuarios());
        listaCombinada.addAll(gestionBibliotecarios.getBibliotecarios());
        return listaCombinada;
    }

    // --- MÉTODOS ADICIONALES (ÚTILES) ---
    public List<Usuario> getListaUsuarios() {
        return gestionUsuarios.getUsuarios();
    }
    public List<Bibliotecario> getListaBibliotecarios() {
        return gestionBibliotecarios.getBibliotecarios();
    }
}