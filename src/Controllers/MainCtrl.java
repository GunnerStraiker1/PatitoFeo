/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOAsientos;
import DAO.DAOFunciones;
import DAO.ObrasDAO;
import Interfaz.CancelFrame;
import Interfaz.FuncionesFrame;
import Interfaz.MainAdminFrame;
import Interfaz.MainFrame;
import Modelo.Obra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author Victor Perera
 */
public class MainCtrl implements ActionListener{
    private ObrasDAO daoObra;
    private MainFrame mainView;
    private ArrayList<JButton> botones = new ArrayList();

    public MainCtrl(ObrasDAO daoObra, MainFrame mainView) {
        this.daoObra = daoObra;
        this.mainView = mainView;
        this.mainView.btnAdmin.addActionListener(this);
        this.mainView.btnCancel.addActionListener(this);
        this.mainView.setLocationRelativeTo(null);
        this.mainView.setVisible(true);
        inicializarPanel();
    }
    
    private void inicializarPanel(){
        List<Obra>obrasDisponibles = daoObra.consultarObras("En Cartelera");
        for (int i = 0; i < obrasDisponibles.size(); i++) {
            JButton boton = new JButton("<html>Nombre de Obra: <b>"+obrasDisponibles.get(i).getTituloObra()+"</b><br>Actores: <i>"+ 
                    obrasDisponibles.get(i).getMainActors()+"</i><br>Sinopsis: "+obrasDisponibles.get(i).getDescripcion()+"</html>");
            this.mainView.panel.add(boton);
            botones.add(boton);
            boton.setName(Integer.toString(i));

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Obra obra = obrasDisponibles.get(Integer.parseInt(boton.getName()));
                    System.out.println(boton.getName());
                    FuncionesFrame funcionView = new FuncionesFrame();
                    DAOFunciones daoFuncion = new DAOFunciones();
                    FuncionesCtrl funcionCtrl = new FuncionesCtrl(funcionView, daoFuncion, obra);
                    mainView.dispose();
                }
            });            
            this.mainView.panel.updateUI();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.mainView.btnAdmin){
            MainAdminFrame adminView = new MainAdminFrame();
            AdminCtrl admin = new AdminCtrl(adminView);
            this.mainView.dispose();
        }
        if(e.getSource()==this.mainView.btnCancel){
            CancelFrame cancelView = new CancelFrame();
            DAOAsientos daoAsientos = new DAOAsientos();
            CancelCtrl cancel = new CancelCtrl(cancelView,daoAsientos);
            this.mainView.dispose();
        }
    }
    
    
    
    
}
