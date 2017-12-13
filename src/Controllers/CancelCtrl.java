/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOAsientos;
import DAO.ObrasDAO;
import Interfaz.CancelFrame;
import Interfaz.MainFrame;
import Modelo.Asiento;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Victor Perera
 */
public class CancelCtrl implements ActionListener{
    private CancelFrame cancelView;
    private DAOAsientos daoAsientos;
    private List<Asiento> asientos;
    private String clvFuncion;
    private List<Asiento> asientosSelected = new ArrayList<>();
    private List<JRadioButton> botones = new ArrayList();

    public CancelCtrl(CancelFrame cancelView, DAOAsientos daoAsientos) {
        this.cancelView = cancelView;
        this.daoAsientos = daoAsientos;
        this.cancelView.btnCancel.setVisible(false);
        this.cancelView.btnCancel.addActionListener(this);
        this.cancelView.btnIngresar.addActionListener(this);
        this.cancelView.setLocationRelativeTo(null);
        this.cancelView.setVisible(true);
    }
    
    private void crearPanel(String clv){
        this.setClvFuncion(clv.substring(clv.length()-6));
        this.asientos = daoAsientos.consultarBoletosComprados(clv, clvFuncion);
        for (int i = 0; i < asientos.size(); i++) {
            JRadioButton boton = new JRadioButton();
            JTextField nombre = new JTextField(); JTextField palco = new JTextField();
            JTextField precio = new JTextField(); 
            
            nombre.setText(this.asientos.get(i).getNombre());
            nombre.setFont(new Font("Tahoma",0,18));
            nombre.setBackground(Color.WHITE);
            nombre.setEditable(false);
            this.cancelView.panel.add(nombre);
            
            palco.setText(this.asientos.get(i).getArea());
            palco.setFont(new Font("Tahoma",0,18));
            palco.setBackground(Color.WHITE);
            palco.setEditable(false);
            this.cancelView.panel.add(palco);
            
            precio.setText(String.valueOf(this.asientos.get(i).getPrecio()));
            precio.setFont(new Font("Tahoma",0,18));
            precio.setBackground(Color.WHITE);
            precio.setEditable(false);
            this.cancelView.panel.add(precio);
            
            boton.setName(String.valueOf(i));
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     if(boton.isSelected()){
                        nombre.setBackground(Color.BLUE);
                        palco.setBackground(Color.BLUE);
                        precio.setBackground(Color.BLUE);
                        asientosSelected.add(asientos.get(Integer.parseInt(boton.getName())));
                    }
                    else{
                        nombre.setBackground(Color.WHITE);
                        palco.setBackground(Color.WHITE);
                        precio.setBackground(Color.WHITE);
                        asientosSelected.remove(asientos.get(Integer.parseInt(boton.getName())));
                    }
                }
            });
            this.cancelView.panel.add(boton);
            this.cancelView.panel.updateUI();
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== this.cancelView.btnIngresar){
            try{
                String clvCompra = this.cancelView.txtClvCompra.getText();
                if((clvCompra.length()!=8 )&& (clvCompra.length()!=9)){
                    throw new Exception("No existe esta funcion, revise su Clave");
                }
                else{
                    crearPanel(clvCompra);
                    this.cancelView.btnCancel.setVisible(true);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(cancelView, ex.getMessage());
            }
        }
        if (e.getSource() == this.cancelView.btnCancel) {
            try {
                if (asientosSelected.isEmpty()) {
                    throw new Exception("No se ha seleccionado un boleto");
                } else {
                    this.daoAsientos.cancelarAsientos(clvFuncion, asientosSelected);
                    JOptionPane.showMessageDialog(cancelView, "Asiento(s) Cancelado(s) \nPase a la taquilla con con su clave de Compra");
                    ObrasDAO daoObra = new ObrasDAO();
                    MainFrame mainView = new MainFrame();
                    MainCtrl main = new MainCtrl(daoObra, mainView);
                    this.cancelView.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(cancelView, ex.getMessage());
            }
        }
        if (e.getSource() == this.cancelView.btnBack) {
            ObrasDAO daoObra = new ObrasDAO();
            MainFrame mainView = new MainFrame();
            MainCtrl main = new MainCtrl(daoObra, mainView);
            this.cancelView.dispose();
        }
    }

    public void setClvFuncion(String clvCompra) {
        this.clvFuncion = clvCompra;
    }
    
    
    
}
