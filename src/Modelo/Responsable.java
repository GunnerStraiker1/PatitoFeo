/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Victor Perera
 */
public class Responsable {
    private int id;
    private String nombre;
    private String direccion;
    private long telefono;
    private long telefonoAlter;
    private String correo;
    
    public Responsable(String nombre, String direccion, long telefono, long telefonoAlter, String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.telefonoAlter = telefonoAlter;
        this.correo = correo;
    }

    public Responsable(String nombre, String direccion, long telefono, String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    public Responsable(int id, String nombre, String direccion, long telefono, long telefonoAlter, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.telefonoAlter = telefonoAlter;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getTelefonoAlter() {
        return telefonoAlter;
    }

    public void setTelefonoAlter(long telefonoAlter) {
        this.telefonoAlter = telefonoAlter;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}
