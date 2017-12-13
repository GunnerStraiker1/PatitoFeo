/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.ObrasDAO;
import Interfaz.ObrasFrame;
import Interfaz.MainAdminFrame;
import Interfaz.MainFrame;
import Interfaz.ModiFrame;
import Interfaz.ReportFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Victor Perera
 */
public class AdminCtrl implements ActionListener{
    private MainAdminFrame adminView;

    public AdminCtrl(MainAdminFrame adminView) {
        this.adminView = adminView;
        this.adminView.btnCreate.addActionListener(this);
        this.adminView.btnModi.addActionListener(this);
        this.adminView.btnReporte.addActionListener(this);
        this.adminView.btnDailyReport.addActionListener(this);
        this.adminView.btnMenu.addActionListener(this);
        this.adminView.setLocationRelativeTo(null);
        this.adminView.setVisible(true);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.adminView.btnCreate) {
            ObrasFrame createView = new ObrasFrame();
            ObrasDAO obrasDao = new ObrasDAO();
            ObrasCtrl obrasCtrl = new ObrasCtrl(obrasDao, createView);
            this.adminView.dispose();
        }
        if(e.getSource()== this.adminView.btnModi){
            ModiFrame modifFrame = new ModiFrame();
            ObrasDAO obrasDao = new ObrasDAO();
            ModifObrasCtrl modifCtrl = new ModifObrasCtrl(modifFrame, obrasDao);
            this.adminView.dispose();
        }
        if(e.getSource() == this.adminView.btnDailyReport){
            ReportFrame reportView = new ReportFrame();
            ReportCtrl reportCtrl = new ReportCtrl(reportView, "Diario");
            this.adminView.dispose();
        }
        if(e.getSource() == this.adminView.btnReporte){
            ReportFrame reportView = new ReportFrame();
            ReportCtrl reportCtrl = new ReportCtrl(reportView, "General");
            this.adminView.dispose();
        }
        if(e.getSource() == this.adminView.btnMenu){
            MainFrame main = new MainFrame();
            ObrasDAO daoObra = new ObrasDAO();
            MainCtrl mainCtrl = new MainCtrl(daoObra, main);
            this.adminView.dispose();
        }
    }
    
    
}
