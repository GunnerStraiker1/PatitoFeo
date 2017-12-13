/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOAsientos;
import Interfaz.AsientosFrame;
import Interfaz.VentaFrame;
import Modelo.Asiento;
import Modelo.Funcion;
import Modelo.Obra;
import java.awt.Color;
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
public class AsientosCtrl implements ActionListener{
    private AsientosFrame asientosView;
    private DAOAsientos daoAsiento;
    private List<Asiento> listAsientos;
    private List<JButton> buttons = new ArrayList<>();
    private int noAsientosSelect;
    private Obra obra;
    private Funcion funcion;
    private List<Asiento> asientosSelect = new ArrayList<>();

    public AsientosCtrl(AsientosFrame asientosView, DAOAsientos daoAsiento,Obra obra, Funcion funcion) {
        this.asientosView = asientosView;
        this.daoAsiento = daoAsiento;
        this.obra = obra;
        this.asientosView.setLocationRelativeTo(null);
        this.asientosView.buy.addActionListener(this);
        listAsientos = daoAsiento.consultarAsientosGral();
        this.funcion= funcion;
        this.noAsientosSelect=0;
        crearPanelAsientos(obra);
        this.asientosView.setVisible(true);        
    }

    public AsientosFrame getAsientosView() {
        return asientosView;
    }

    public void setAsientosView(AsientosFrame asientosView) {
        this.asientosView = asientosView;
    }

    public DAOAsientos getDaoAsiento() {
        return daoAsiento;
    }

    public void setDaoAsiento(DAOAsientos daoAsiento) {
        this.daoAsiento = daoAsiento;
    }

    public List<Asiento> getListAsientos() {
        return listAsientos;
    }

    public void setListAsientos(List<Asiento> listAsientos) {
        this.listAsientos = listAsientos;
    }

    public List<JButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<JButton> buttons) {
        this.buttons = buttons;
    }

    public int getNoAsientosSelect() {
        return noAsientosSelect;
    }

    public void setNoAsientosSelect(int noAsientosSelect) {
        this.noAsientosSelect = noAsientosSelect;
    }
        
    private void crearPanelAsientos(Obra obra) {
        for (int i = 0; i < listAsientos.size(); i++) {

            JButton lab = new JButton();

            lab.setText(listAsientos.get(i).getNombre());
            if (listAsientos.get(i).getArea().equalsIgnoreCase("ORO")) {
                lab.setBackground(Color.ORANGE);
            } else if (listAsientos.get(i).getArea().equalsIgnoreCase("AZUL")) {
                lab.setBackground(Color.blue);
            } else if (listAsientos.get(i).getArea().equalsIgnoreCase("GRIS")) {
                lab.setBackground(Color.LIGHT_GRAY);
            } else if (listAsientos.get(i).getArea().equalsIgnoreCase("AMARILLO")) {
                lab.setBackground(Color.YELLOW);
            } else if (listAsientos.get(i).getArea().equalsIgnoreCase("VERDE")) {
                lab.setBackground(Color.GREEN);
            }
            this.asientosView.panel.add(lab);        

            lab.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if (!lab.getBackground().equals(Color.BLACK)) {
                            if (getNoAsientosSelect() < Integer.parseInt(asientosView.noBoletos.getText())) {
                            seleccionarAsiento(lab, obra);
                            setNoAsientosSelect(getNoAsientosSelect() + 1);
                        } else {
                                JOptionPane.showMessageDialog(asientosView, "Has alcanzado tu limite de asientos");
                            
                        }
                    }
                    else{
                            deseleccionarAsiento(lab);
                            setNoAsientosSelect(getNoAsientosSelect() - 1);
                        
                    }
                }
            });
            buttons.add(lab);  
            this.asientosView.panel.updateUI();

        }
        System.out.println(buttons.size());
        daoAsiento.visualizarAsientosComprados(buttons, funcion);
        this.asientosView.panel.updateUI();
    }
    
    private void seleccionarAsiento(JButton boton, Obra obra){
            Color bg = boton.getBackground();
            String palco = null;
            double porcentaje=0;
            if (bg.equals(Color.BLUE)) {
                palco = "Plata";
                porcentaje=.75;
            }
            else if(bg.equals(Color.YELLOW)){
                palco = "Oro";
                porcentaje=.9;
            }
            else if(bg.equals(Color.GREEN)){
                palco = "Diamante";
                porcentaje=1;
            }
            else if(bg.equals(Color.LIGHT_GRAY)){
                palco = "Lata";
                porcentaje=.5;
            }
            else if(bg.equals(Color.ORANGE)){
                palco = "Cobre";
                porcentaje=.6;
            }
            double precio = daoAsiento.consultarPrecio(obra);
        
            Asiento asientoSelect = new Asiento(boton.getText(),palco,(precio*porcentaje));
//            System.out.println(asi.getNombre());
            asientosSelect.add(asientoSelect);
            
            boton.setBackground(Color.BLACK);
            boton.setForeground(Color.BLACK);
    }
    
    public void deseleccionarAsiento(JButton sit){
        for (int j = 0; j < asientosSelect.size(); j++) {
            if(asientosSelect.get(j).getNombre().equals(sit.getText())){
                if(asientosSelect.get(j).getArea().equalsIgnoreCase("Plata")){
                   sit.setBackground(Color.BLUE); 
                }
                else if(asientosSelect.get(j).getArea().equalsIgnoreCase("Oro")){
                   sit.setBackground(Color.YELLOW); 
                }
                else if(asientosSelect.get(j).getArea().equalsIgnoreCase("Diamante")){
                   sit.setBackground(Color.GREEN); 
                }
                else if(asientosSelect.get(j).getArea().equalsIgnoreCase("Lata")){
                   sit.setBackground(Color.LIGHT_GRAY); 
                }
                else if(asientosSelect.get(j).getArea().equalsIgnoreCase("Cobre")){
                   sit.setBackground(Color.ORANGE); 
                }
                sit.setEnabled(true);
                asientosSelect.remove(j);
                //i--;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.asientosView.buy) {
            try{
            if(asientosSelect.size()==Integer.parseInt(this.asientosView.noBoletos.getText())){
                daoAsiento.comprarAsientos(funcion, asientosSelect);
                JOptionPane.showMessageDialog(asientosView, "Seleccion realizada con éxito");
                this.asientosView.dispose();
                VentaFrame ventaView = new VentaFrame();
                for (int i = 0; i < asientosSelect.size(); i++) {
                    asientosSelect.get(i).setClvVenta(asientosSelect.get(0).getNombre()+funcion.getClaveFuncion());
                }
                VentaCtrl ventaCtrl = new VentaCtrl(ventaView, asientosSelect);
            }
            else{
                throw new Exception("Número de boletos seleccionados incorrecto");
            }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(asientosView, ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
}
