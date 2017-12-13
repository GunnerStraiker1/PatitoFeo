/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOAsientos;
import DAO.DAOFunciones;
import DAO.ObrasDAO;
import Interfaz.AsientosFrame;
import Interfaz.FuncionesFrame;
import Interfaz.MainFrame;
import Modelo.Funcion;
import Modelo.Obra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Perera
 */
public class FuncionesCtrl implements ActionListener{
    private FuncionesFrame funcionView;
    private DAOFunciones daoFuncion;
    private List<JButton> botones = new ArrayList<>();

    public FuncionesCtrl(FuncionesFrame funcionView, DAOFunciones daoFuncion, Obra obra) {
        this.funcionView = funcionView;
        this.daoFuncion = daoFuncion;
        this.funcionView.btnBack.addActionListener(this);
        this.funcionView.setLocationRelativeTo(null);
        this.funcionView.setVisible(true);
        inicializarPanel(obra);
    }

    protected void inicializarPanel(Obra obra){
        List<Funcion> funcionesDisponibles = this.daoFuncion.consultarFuncionesDeObra(obra, "Disponible");
        if (funcionesDisponibles.size() != 0) {
            for (int i = 0; i < funcionesDisponibles.size(); i++) {

                JButton boton = new JButton("<html>Dia: <b>" + funcionesDisponibles.get(i).getFecha().toString() + "</b><br>Hora: <i>"
                        + funcionesDisponibles.get(i).getHoraInicio().toString() + "</i>" + "</html>");
                this.funcionView.panelFuncion.add(boton);
                botones.add(boton);
                boton.setName(Integer.toString(i));

                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Funcion funcion = funcionesDisponibles.get(Integer.parseInt(boton.getName()));
                        System.out.println(boton.getName());
                        AsientosFrame asientosView = new AsientosFrame();
                        DAOAsientos daoAsientos = new DAOAsientos();
                        AsientosCtrl asientosCtrl = new AsientosCtrl(asientosView, daoAsientos,obra, funcion);
                        funcionView.dispose();
                    }
                });
                this.funcionView.panelFuncion.updateUI();
            }
        } else {
            JOptionPane.showMessageDialog(funcionView, "No hay funciones disponibles para esta obra");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.funcionView.btnBack){
            ObrasDAO daoObras = new ObrasDAO();
            MainFrame main = new MainFrame();
            MainCtrl mainCtrl = new MainCtrl(daoObras, main);
            this.funcionView.dispose();
        }
    }
    
    
}
