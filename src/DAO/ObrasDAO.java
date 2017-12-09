/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Connection;
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
public class ObrasDAO {
    Connection cn = new Connection();
    
        public void insertarObra(Obra obra) {
        Obra obraNueva = obra;
        String sqlObra = "INSERT INTO obras (`ClaveObra`,`Responsable`,`Titulo`,`Descripcion`,`Actores`,`Estado`,`Duracion` ,`Precio`) VALUES (?,?,?,?,?,?,?,?)";
        java.sql.Connection con;
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());                 
            
//            Se crea la obra en tabla Obras de la Base de Datos
            PreparedStatement pstObra = con.prepareStatement(sqlObra);
            pstObra.setString(1, obraNueva.getClaveObra());
            pstObra.setInt(2, obraNueva.getResponsable());
            pstObra.setString(3, obraNueva.getTituloObra());
            pstObra.setString(4, obraNueva.getDescripcion());
            pstObra.setString(5, obraNueva.getMainActors());
            pstObra.setString(6, obraNueva.getEstado());
            pstObra.setInt(7, obraNueva.getDuracion());
            pstObra.setDouble(8, obraNueva.getPrecioBase());
            pstObra.executeUpdate();           
            con.close();
        } catch (SQLException|ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
      
    public List<Obra> consultarObras(String tipoObra) {
        List<Obra> listObras = new ArrayList<>();
        java.sql.Connection con;
        String sql = "";
        switch (tipoObra) {
            case "Total":
                sql = "SELECT * FROM obras";
                break;
            case "En Cartelera":
                sql = "SELECT * FROM obras WHERE  Estado = \"En Cartelera\"";
                break;
            case "Proximamente":
                sql = "SELECT * FROM obras WHERE  Estado = \"Proximamente\"";
                break;
            case "Pasada":
                sql = "SELECT * FROM obras WHERE  Estado = \"Pasada\"";
                break;
        }
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listObras.add(new Obra(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getDouble(8)));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return listObras;
    }
}
