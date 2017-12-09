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
public class Obra {
    private String claveObra;
    private int responsable;
    private String tituloObra;
    private String descripcion;
    private String mainActors;
    private String estado;
    private int duracion;
    private double precioBase;

    public Obra(String claveObra, int responsable, String tituloObra, String descripcion, String mainActors, String estado, int duracion, double precioBase) {
        this.claveObra = claveObra;
        this.responsable = responsable;
        this.tituloObra = tituloObra;
        this.descripcion = descripcion;
        this.mainActors = mainActors;
        this.estado = estado;
        this.duracion = duracion;
        this.precioBase = precioBase;
    }

    
    public String getClaveObra() {
        return claveObra;
    }

    public void setClaveObra(String claveObra) {
        this.claveObra = claveObra;
    }

    public int getResponsable() {
        return responsable;
    }

    public void setResponsable(int responsable) {
        this.responsable = responsable;
    }

    public String getTituloObra() {
        return tituloObra;
    }

    public void setTituloObra(String tituloObra) {
        this.tituloObra = tituloObra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
    
    
    
}
