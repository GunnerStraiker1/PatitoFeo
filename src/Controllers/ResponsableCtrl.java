/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOResponsable;
import Interfaz.ResponsableFrame;
import Modelo.Responsable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Perera
 */
public class ResponsableCtrl implements ActionListener {

    private ResponsableFrame respView;
    private DAOResponsable daoResp;

    public ResponsableCtrl(ResponsableFrame respView, DAOResponsable daoResp) {
        this.respView = respView;
        this.daoResp = daoResp;
        this.respView.setLocationRelativeTo(null);
        this.respView.setVisible(true);
        this.respView.btnSave.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.respView.btnSave) {
            String name = this.respView.txtName.getText();
            String dir = this.respView.txtDireccion.getText();
            String tel = this.respView.txtTel.getText();
            String telAlt = this.respView.txtTelAlt.getText();
            String mail = this.respView.txtMail.getText();
            try {
                if (!name.equals("") && !dir.equals("") && !tel.equals("") && !mail.equals("") && !telAlt.equals("")) {
                    Responsable resp = new Responsable(name, dir, Long.valueOf(tel), Long.valueOf(telAlt), mail);
                    this.daoResp.insertarResponsable(resp);
                    JOptionPane.showMessageDialog(respView, "Responsable Guardado");
                } else if (!name.equals("") && !dir.equals("") && !tel.equals("") && !mail.equals("") && telAlt.equals("")) {
                    Responsable resp = new Responsable(name, dir, Long.valueOf(tel), mail);
                    this.daoResp.insertarResponsable(resp);
                    JOptionPane.showMessageDialog(respView, "Responsable Guardado");
                } else {
                    throw new Exception("Datos incompletos");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(respView, ex.getMessage());
            }
        }
    }
}
