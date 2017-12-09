/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.ObrasDAO;
import Interfaz.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Victor Perera
 */
public class MainCtrl implements ActionListener{
    private ObrasDAO daoObra;
    private MainFrame mainView;

    public MainCtrl(ObrasDAO daoObra, MainFrame mainView) {
        this.daoObra = daoObra;
        this.mainView = mainView;
    }
    
    private void inicializarPanel(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.mainView.btnAdmin){
            
        }
    }
    
    
    
    
}
