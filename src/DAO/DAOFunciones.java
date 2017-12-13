/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Connection;
import Modelo.Funcion;
import Modelo.Obra;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Perera
 */
public class DAOFunciones {
    Connection cn = new Connection();

    public void insertarFuncion(Funcion funcion) {
        java.sql.Connection con;
        Funcion nuevaFuncion = funcion;
        String sql = "INSERT  INTO funciones (`ClaveFuncion`,`ClaveObra`,`Fecha`,`HoraInicial`,`HoraFinal`,`Estado`) VALUES (?,?,?,?,?,?)";

        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pstFuncion = con.prepareStatement(sql);
            pstFuncion.setString(1, nuevaFuncion.getClaveFuncion());
            pstFuncion.setString(2, nuevaFuncion.getClaveObra());
            pstFuncion.setDate(3, nuevaFuncion.getFecha());
            pstFuncion.setTime(4, nuevaFuncion.getHoraInicio());
            pstFuncion.setTime(5, nuevaFuncion.getHoraFinal());
            pstFuncion.setString(6, nuevaFuncion.getEstado());
            pstFuncion.executeUpdate();
            crearTablaFuncion(nuevaFuncion.getClaveFuncion());
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void crearTablaFuncion(String clv) {
        String nombre = clv;
        java.sql.Connection con;
        String jqNEW = "CREATE TABLE " + nombre + ""
                + "(Asiento VARCHAR(3), Area VARCHAR(10), Price Double"
                +  ", Fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, ClaveVenta VARCHAR(9))";
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            Statement st = con.createStatement();
            st.executeUpdate(jqNEW);
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Funcion> consultarFunciones() {
        List<Funcion> funciones = new ArrayList<>();
        java.sql.Connection con;
        int i = 0;
        String sql = "SELECT * FROM funciones";

        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                funciones.add(new Funcion(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6)));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return funciones;
    }

    public List<Funcion> consultarFuncionesDeObra(Obra obra, String tipo) {
        Obra obraConsulta = obra;
        String sql = null;
        List<Funcion> listFuncion = new ArrayList<>();
        java.sql.Connection con;
        switch (tipo) {
            case "Disponible":
                sql = "SELECT * FROM funciones WHERE ClaveObra= \"" + obraConsulta.getClaveObra() + "\" AND Estado= \"Disponible\"";
                break;
            case "Vendida":
                sql = "SELECT * FROM funciones WHERE ClaveObra= \"" + obraConsulta.getClaveObra() + "\" AND Estado= \"Vendida\"";
                break;
            case "Cancelada":
                sql = "SELECT * FROM funciones WHERE ClaveObra= \"" + obraConsulta.getClaveObra() + "\" AND Estado= \"Cancelada\"";
                break;
            case "Total":
                sql = "SELECT * FROM funciones WHERE ClaveObra= \"" + obraConsulta.getClaveObra() + "\"";
                break;
        }
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listFuncion.add(new Funcion(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6)));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return listFuncion;
    }
    
    public void modificarFuncion(Funcion funcion){
            Funcion estadoFuncion = funcion;
            java.sql.Connection con;
            String sql ="UPDATE funciones SET Fecha =?, HoraInicial=?, HoraFinal=?, Estado=? WHERE ClaveFuncion=?";
        try {    
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, estadoFuncion.getFecha());
            pst.setTime(2, estadoFuncion.getHoraInicio());
            pst.setTime(3, estadoFuncion.getHoraFinal());
            pst.setString(4, estadoFuncion.getEstado());
            pst.setString(5, estadoFuncion.getClaveFuncion());
            pst.executeUpdate();
            con.close();
        } catch (SQLException|ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
