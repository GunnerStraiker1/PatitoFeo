/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Connection;
import Modelo.Asiento;
import Modelo.Funcion;
import Modelo.Obra;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Perera
 */
public class DAOAsientos {
    Connection cn = new Connection();
    
    public List<Asiento> consultarAsientosGral() {
        List<Asiento> asientosGral = new ArrayList<>();
        String sql = "SELECT nombre, palco FROM `asientos`" ;
        java.sql.Connection con;   
        
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Asiento asiento = new Asiento(rs.getString(1),rs.getString(2));
                asientosGral.add(asiento);
            }
            con.close();
        } catch (ClassNotFoundException|SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return asientosGral;
    }
    
    public double consultarPrecio(Obra obra) {
        double precio = 0;
        String sql = "SELECT precio FROM `obras` WHERE ClaveObra='" + obra.getClaveObra() + "'";
        java.sql.Connection con;
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                precio = rs.getDouble(1);
            }
            return precio;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOAsientos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAsientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return precio;
    }
    
    public void comprarAsientos(Funcion funcion, List<Asiento> asientos){
        String jq = "INSERT INTO "+funcion.getClaveFuncion()+"(`Asiento`,`Area`,`Price`,`ClaveVenta`) VALUES (?,?,?,?)";
        java.sql.Connection con;
             try {
                Class.forName(cn.getDriver());
                con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
                 
                 for (int j = 0; j < asientos.size(); j++) {
                    PreparedStatement pst = con.prepareStatement(jq);
                    pst.setString(1, asientos.get(j).getNombre());
                    pst.setString(2, asientos.get(j).getArea());
                    pst.setDouble(3, asientos.get(j).getPrecio());
                    pst.setString(4, asientos.get(0).getNombre()+funcion.getClaveFuncion());
                    pst.executeUpdate();                    
                 }
            } catch (SQLException ex) {
                Logger.getLogger(DAOAsientos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOAsientos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void visualizarAsientosComprados(List<JButton> buttons, Funcion funcion){
        String sq = "SELECT * FROM " + funcion.getClaveFuncion();
        java.sql.Connection con;
        try {            
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sq);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
//                asientosOcupados.add(rs0.getString(1));
                for (int i = 0; i < buttons.size(); i++) {
                    if (buttons.get(i).getText().equalsIgnoreCase(rs.getString(1))) {
                        buttons.get(i).setBackground(Color.BLACK);
                        System.out.println(buttons.get(i).getActionListeners()[0].toString());
                        buttons.get(i).removeActionListener(buttons.get(i).getActionListeners()[0]);
                        //buttons.get(i).removeActionListener(buttons.get(i).getActionListeners()[0]);
                    }
                }
            }           
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();  
        }
    }
    
    public List<Asiento> consultarBoletosComprados(String clvVenta, String funcion) {
        List<Asiento> asientosComprados= new ArrayList<>();
        String sql = "SELECT * FROM `"+funcion + "` WHERE ClaveVenta='" + clvVenta + "'";
        java.sql.Connection con;
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                asientosComprados.add(new Asiento(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getTimestamp(4), rs.getString(5)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Funcion no existe");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return asientosComprados;
    }
        public List<Asiento> consultarBoletosComprados(String funcion) {
        List<Asiento> asientosComprados= new ArrayList<>();
        String sql = "SELECT * FROM `"+funcion;
        java.sql.Connection con;
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                asientosComprados.add(new Asiento(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getTimestamp(4), rs.getString(5)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Funcion no existe");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return asientosComprados;
    }
    
        public void cancelarAsientos(String clvFuncion, List<Asiento> asientos){
        String jq = "DELETE FROM "+clvFuncion.toUpperCase()+" WHERE Asiento= ?";
        java.sql.Connection con;
             try {
                Class.forName(cn.getDriver());
                con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
                 
                 for (int j = 0; j < asientos.size(); j++) {
                    PreparedStatement pst = con.prepareStatement(jq);
                    pst.setString(1, asientos.get(j).getNombre());
                    pst.executeUpdate();                    
                 }
                 añadirReembolso(asientos,clvFuncion);
            } catch (SQLException | ClassNotFoundException ex) {
                 System.out.println(ex.getMessage());
                 ex.printStackTrace();
            }
    }
        private void añadirReembolso(List<Asiento> asientos,String clvFuncion){
        String jq = "INSERT INTO Reembolsos (`Nombre`,`Area`,`Precio`,`ClaveVenta`,`ClaveFuncion`) VALUES (?,?,?,?,?)";
        java.sql.Connection con;
             try {
                Class.forName(cn.getDriver());
                con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
                 
                 for (int j = 0; j < asientos.size(); j++) {
                    PreparedStatement pst = con.prepareStatement(jq);
                    pst.setString(1, asientos.get(j).getNombre());
                    pst.setString(2, asientos.get(j).getArea());
                    pst.setDouble(3, asientos.get(j).getPrecio());
                    pst.setString(4, asientos.get(0).getClvVenta());
                    pst.setString(5, clvFuncion);
                    pst.executeUpdate();                    
                 }
            } catch (SQLException | ClassNotFoundException ex) {
                 System.out.println(ex.getMessage());
                 ex.printStackTrace();
            }
    }
        public List<Asiento> consultarReembolsos() {
        List<Asiento> asientosReembolso = new ArrayList<>();
        String sql = "SELECT * FROM `Reembolsos`" ;
        java.sql.Connection con;   
        
        try {
            Class.forName(cn.getDriver());
            con = DriverManager.getConnection(cn.getUrl(), cn.getUsuario(), cn.getContraseña());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Asiento asiento = new Asiento(rs.getString(1),rs.getString(2),rs.getDouble(3), rs.getTimestamp(4),rs.getString(5),rs.getString(6));
                asientosReembolso.add(asiento);
            }
            con.close();
        } catch (ClassNotFoundException|SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return asientosReembolso;
    }
}
