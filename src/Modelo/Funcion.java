/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Victor Perera
 */
public class Funcion {
    private String claveFuncion;
    private String claveObra;
    private Date fecha;
    private Time HoraInicio;
    private Time HoraFinal;
    private String estado;

    public Funcion(String claveFuncion, String claveObra, Date fecha, Time horaInicio,Time horaFinal,String estado) {
        this.claveFuncion = claveFuncion;
        this.claveObra = claveObra;
        this.fecha = fecha;
        this.HoraInicio = horaInicio;
        this.HoraFinal = horaFinal;
        this.estado = estado;
    }

    public String getClaveFuncion() {
        return claveFuncion;
    }

    public void setClaveFuncion(String claveFuncion) {
        this.claveFuncion = claveFuncion;
    }

    public String getClaveObra() {
        return claveObra;
    }

    public void setClaveObra(String claveObra) {
        this.claveObra = claveObra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Time getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Time HoraInicio) {
        this.HoraInicio = HoraInicio;
    }

    public Time getHoraFinal() {
        return HoraFinal;
    }

    public void setHoraFinal(Time HoraFinal) {
        this.HoraFinal = HoraFinal;
    }
    
    
    
}
