/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.*;
import Interfaz.*;
import Modelo.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Perera
 */
public class ModifObrasCtrl implements ActionListener{
    private ModiFrame modifView;
    private ObrasDAO daoObra;
    private List<Obra> obras;
    public ModifObrasCtrl(ModiFrame modifView, ObrasDAO daoObra) {
        this.modifView = modifView;
        this.daoObra = daoObra;
        this.modifView.btnModif.addActionListener(this);
        this.modifView.btnBack.addActionListener(this);
        limpiarTabla();
        crearTablaObras();
        this.modifView.setLocationRelativeTo(null);
        this.modifView.setVisible(true);
    }

    private void crearTablaObras() {
        DefaultTableModel modelo = (DefaultTableModel) this.modifView.tabla.getModel();
        Object[] registros = new Object[7];
        String nombreResp;
        DAOResponsable daoResp = new DAOResponsable();
        List<Responsable> listResp = daoResp.consultarResponsables();
        obras = daoObra.consultarObras("Total");
        for (Obra obra : obras) {
            for (Responsable resp : listResp) {
                if (resp.getId() == obra.getResponsable()) {
                    registros[0] = resp.getNombre();
                }
            }
            registros[1] = obra.getTituloObra();
            registros[2] = obra.getDescripcion();
            registros[3] = obra.getMainActors();
            registros[4] = obra.getDuracion();
            registros[5] = obra.getEstado();
            registros[6] = obra.getPrecioBase();
            modelo.addRow(registros);
        }
        this.modifView.tabla.setModel(modelo);
    }

    private void limpiarTabla() {
        DefaultTableModel tb = (DefaultTableModel) this.modifView.tabla.getModel();
        int a = this.modifView.tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
        this.modifView.tabla.setModel(tb);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.modifView.btnModif) {
            int row= this.modifView.tabla.getSelectedRow();
            if (row!=-1) {
                Obra obraModif = obras.get(this.modifView.tabla.getSelectedRow());
                ObrasFrame obrasView = new ObrasFrame();
                ObrasCtrl obraCtrl = new ObrasCtrl(daoObra, obrasView, obraModif);
                this.modifView.dispose();
            }
            else
                JOptionPane.showMessageDialog(modifView, "No se ha seleccionado una obra");
                        
        }
        if(e.getSource() == this.modifView.btnBack){
            MainAdminFrame adminFrame = new MainAdminFrame();
            AdminCtrl admin = new AdminCtrl(adminFrame);
            this.modifView.dispose();
        }
    }
    
}
