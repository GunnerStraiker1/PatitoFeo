/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Connection;
import Modelo.Responsable;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Perera
 */
public class DAOResponsable {
    Connection cn = new Connection();
    
    public void insertarResponsable(Responsable responsable){
        Responsable nuevoResponsable = responsable;
        String sqlFuncion = "INSERT INTO responsables (`Nombre`,`Direccion`,`Telefono`,`TelefonoAlt`,`Correo`) VALUES (?,?,?,?,?)";
        java.sql.Connection con;
        
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());    
            PreparedStatement pst = con.prepareStatement(sqlFuncion);
            
            pst.setString(1, nuevoResponsable.getNombre());
            pst.setString(2, nuevoResponsable.getDireccion());
            pst.setLong(3, nuevoResponsable.getTelefono());
            pst.setLong(4, nuevoResponsable.getTelefonoAlter());
            pst.setString(5, nuevoResponsable.getCorreo());
            pst.executeUpdate();
            con.close();
        } catch (SQLException |ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     *
     * @return
     */
    public List<Responsable> consultarResponsables(){
        List<Responsable> listResp = new ArrayList<>();
        java.sql.Connection con;
        String sql ="SELECT * FROM responsables";
        
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                listResp.add(new Responsable(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getLong(5),rs.getString(6)));
            }
            con.close();
        } catch (SQLException |ClassNotFoundException ex) {
            Logger.getLogger(DAOResponsable.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return listResp;
    }
}
