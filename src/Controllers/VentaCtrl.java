/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Interfaz.VentaFrame;
import Modelo.Asiento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Victor Perera
 */
public class VentaCtrl implements ActionListener{
        private VentaFrame ventaView;

    public VentaCtrl(VentaFrame ventaView, List<Asiento> asientos) {
        this.ventaView = ventaView;
        this.ventaView.btnSalir.addActionListener(this);
        this.ventaView.lbltotal.setText(String.valueOf(suma(asientos)));
        this.ventaView.clvVenta.setText(asientos.get(0).getClvVenta());
        this.ventaView.setLocationRelativeTo(null);
        this.ventaView.setVisible(true);
    }
    
    private double suma(List<Asiento> asientos){
        double pago = 0;
        for (int i = 0; i < asientos.size(); i++) {
            pago = pago+asientos.get(i).getPrecio();
        }
        return pago;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.ventaView.btnSalir) {
            this.ventaView.dispose();
            System.exit(0);
        }
    }
    
}
