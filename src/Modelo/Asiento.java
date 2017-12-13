/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author Victor Perera
 */
public class Asiento {
    private String nombre;
    private String area;
    private double precio;
    private java.sql.Timestamp fechaCompra;
    private String clvVenta;
    private String clvFuncion;

    public Asiento(String nombre, String area) {
        this.nombre = nombre;
        this.area = area;
    }

    public Asiento(String nombre, String area, double precio) {
        this.nombre = nombre;
        this.area = area;
        this.precio = precio;
    }

    public Asiento(String nombre, String area, double precio, String clvVenta) {
        this.nombre = nombre;
        this.area = area;
        this.precio = precio;
        this.clvVenta = clvVenta;
    }

    public Asiento(String nombre, String area, double precio, Timestamp fechaCompra, String clvVenta) {
        this.nombre = nombre;
        this.area = area;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.clvVenta = clvVenta;
    }

    public Asiento(String nombre, String area, double precio, Timestamp fechaCompra, String clvVenta, String clvFuncion) {
        this.nombre = nombre;
        this.area = area;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.clvVenta = clvVenta;
        this.clvFuncion = clvFuncion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getClvVenta() {
        return clvVenta;
    }

    public void setClvVenta(String clvVenta) {
        this.clvVenta = clvVenta;
    }

    public java.sql.Timestamp getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(java.sql.Timestamp fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getClvFuncion() {
        return clvFuncion;
    }

    public void setClvFuncion(String clvFuncion) {
        this.clvFuncion = clvFuncion;
    }
    
    
    
}
