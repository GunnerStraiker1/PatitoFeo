/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author Victor Perera
 */
public class Connection {
    private String driver;
    private String url;
    private String usuario;
    private String contraseña;

    public Connection() {
        this.driver = "com.mysql.jdbc.Driver";
        this.url ="jdbc:mysql://localhost:3306/patitoFeo?zeroDateTimeBehavior=convertToNull";
        this.usuario = "root";
        this.contraseña = "";
    }
    
       public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}
