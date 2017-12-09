
import Controllers.*;
import DAO.*;
import Interfaz.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor Perera
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObrasFrame view = new ObrasFrame();
        ObrasDAO dao = new ObrasDAO();
        ObrasCtrl ctrl = new ObrasCtrl(dao,view);
                
    }
    
}
