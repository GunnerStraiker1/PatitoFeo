/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.*;
import Interfaz.MainAdminFrame;
import Interfaz.ReportFrame;
import Modelo.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author Victor Perera
 */
public class ReportCtrl implements ActionListener{
    private java.sql.Timestamp fecha;
    private ObrasDAO daoObras = new ObrasDAO();
    private DAOFunciones daoFunciones = new DAOFunciones();
    private DAOAsientos daoAsientos = new DAOAsientos();
    private ReportFrame reportView;

    public ReportCtrl(ReportFrame reportView,String tipoReporte) {
        if(tipoReporte.equalsIgnoreCase("General")){
            this.reportView = reportView;
            this.reportView.lblTitle.setText("Reporte "+ tipoReporte);
            this.reportView.btnBack.addActionListener(this);
            crearReporteGral();
            this.reportView.setLocationRelativeTo(null);
            this.reportView.setVisible(true);
        }
        else if(tipoReporte.equalsIgnoreCase("Diario")){
            this.reportView = reportView;
            this.reportView.lblTitle.setText("Reporte "+ tipoReporte);
            this.reportView.btnBack.addActionListener(this);
            this.fecha = new Timestamp(System.currentTimeMillis());
            crearReporteDiario();
            this.reportView.setLocationRelativeTo(null);
            this.reportView.setVisible(true);
        }        
    }
    
    public void crearReporteGral() {
        List<Obra> listObras = daoObras.consultarObras("En Cartelera");
        List<Funcion> listFuncion = daoFunciones.consultarFunciones();
        List<Asiento> listAsientosCancelados = daoAsientos.consultarReembolsos();

        for (int i = 0; i < listObras.size(); i++) {

            JLabel titulo = new JLabel();
            JLabel obra1 = new JLabel();
            JLabel obra2 = new JLabel();
            JLabel obra3 = new JLabel();
            titulo.setText(listObras.get(i).getTituloObra());
            this.reportView.panel.add(titulo);
            this.reportView.panel.add(obra1);
            this.reportView.panel.add(obra2);
            this.reportView.panel.add(obra3);
            for (int j = 0; j < listFuncion.size(); j++) {
                double diamante = 0, lata = 0, cobre = 0, plata = 0, oro = 0, subtotal = 0, total = 0, reembolso = 0;
                if (listFuncion.get(j).getClaveObra().equals(listObras.get(i).getClaveObra())) {
                    JLabel fechafuncion = new JLabel();
                    JLabel horaFuncion = new JLabel();
                    JLabel funcion1 = new JLabel();
                    JLabel funcion2 = new JLabel();
                    fechafuncion.setText(listFuncion.get(j).getFecha().toString());
                    horaFuncion.setText(listFuncion.get(j).getHoraInicio().toString());
                    this.reportView.panel.add(fechafuncion);
                    this.reportView.panel.add(horaFuncion);
                    this.reportView.panel.add(funcion1);
                    this.reportView.panel.add(funcion2);

                    List<Asiento> boletos = daoAsientos.consultarBoletosComprados(listFuncion.get(j).getClaveFuncion());
                    for (int k = 0; k < boletos.size(); k++) {
                        if ("Diamante".equals(boletos.get(k).getArea())) {
                            diamante = diamante + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Plata".equals(boletos.get(k).getArea())) {
                            plata = plata + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Oro".equals(boletos.get(k).getArea())) {
                            oro = oro + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Cobre".equals(boletos.get(k).getArea())) {
                            cobre = cobre + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Lata".equals(boletos.get(k).getArea())) {
                            lata = lata + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        }
                    }
                    JLabel obj1 = new JLabel();
                    JLabel obj2 = new JLabel();
                    JLabel lblDiamante = new JLabel();
                    JLabel diamantePrecio = new JLabel();
                    lblDiamante.setText("Diamante");
                    diamantePrecio.setText(String.valueOf(diamante));
                    this.reportView.panel.add(obj1);
                    this.reportView.panel.add(obj2);
                    this.reportView.panel.add(lblDiamante);
                    this.reportView.panel.add(diamantePrecio);
                    JLabel obj3 = new JLabel();
                    JLabel obj4 = new JLabel();
                    JLabel lblOro = new JLabel();
                    JLabel OroPrecio = new JLabel();
                    JLabel obj5 = new JLabel();
                    JLabel obj6 = new JLabel();
                    JLabel lblPlata = new JLabel();
                    JLabel PlataPrecio = new JLabel();
                    JLabel obj7 = new JLabel();
                    JLabel obj8 = new JLabel();
                    JLabel lblCobre = new JLabel();
                    JLabel CobrePrecio = new JLabel();
                    JLabel obj9 = new JLabel();
                    JLabel obj10 = new JLabel();
                    JLabel lblLata = new JLabel();
                    JLabel LataPrecio = new JLabel();
                    JLabel obj11 = new JLabel();
                    JLabel obj12 = new JLabel();
                    JLabel lblSubtotal = new JLabel();
                    JLabel subtotalPrecio = new JLabel();
                    JLabel obj13 = new JLabel();
                    JLabel obj14 = new JLabel();
                    JLabel lblReembolso = new JLabel();
                    JLabel reembolsoPrecio = new JLabel();
                    JLabel obj15 = new JLabel();
                    JLabel obj16 = new JLabel();
                    JLabel lblTotal = new JLabel();
                    JLabel TotalPrecio = new JLabel();
                    lblOro.setText("Oro");
                    lblPlata.setText("Plata");
                    lblCobre.setText("Cobre");
                    lblLata.setText("Lata");
                    lblSubtotal.setText("Subtotal");
                    OroPrecio.setText(String.valueOf(oro));
                    PlataPrecio.setText(String.valueOf(plata));
                    CobrePrecio.setText(String.valueOf(cobre));
                    LataPrecio.setText(String.valueOf(lata));
                    subtotalPrecio.setText(String.valueOf(subtotal));

                    this.reportView.panel.add(obj3);
                    this.reportView.panel.add(obj4);
                    this.reportView.panel.add(lblOro);
                    this.reportView.panel.add(OroPrecio);

                    this.reportView.panel.add(obj5);
                    this.reportView.panel.add(obj6);
                    this.reportView.panel.add(lblPlata);
                    this.reportView.panel.add(PlataPrecio);

                    this.reportView.panel.add(obj7);
                    this.reportView.panel.add(obj8);
                    this.reportView.panel.add(lblCobre);
                    this.reportView.panel.add(CobrePrecio);

                    this.reportView.panel.add(obj9);
                    this.reportView.panel.add(obj10);
                    this.reportView.panel.add(lblLata);
                    this.reportView.panel.add(LataPrecio);

                    this.reportView.panel.add(obj11);
                    this.reportView.panel.add(obj12);
                    this.reportView.panel.add(lblSubtotal);
                    this.reportView.panel.add(subtotalPrecio);

                    for (int k = 0; k < listAsientosCancelados.size(); k++) {
                        if (listAsientosCancelados.get(k).getClvFuncion().equals(listFuncion.get(j).getClaveFuncion())) {
                            reembolso = reembolso + listAsientosCancelados.get(k).getPrecio();
                        }
                    }
                    lblReembolso.setText("Cancelados");
                    reembolsoPrecio.setText(String.valueOf(reembolso));
                    this.reportView.panel.add(obj13);
                    this.reportView.panel.add(obj14);
                    this.reportView.panel.add(lblReembolso);
                    this.reportView.panel.add(reembolsoPrecio);

                    lblTotal.setText("Total");
                    TotalPrecio.setText(String.valueOf(subtotal - reembolso));
                    this.reportView.panel.add(obj15);
                    this.reportView.panel.add(obj16);
                    this.reportView.panel.add(lblTotal);
                    this.reportView.panel.add(TotalPrecio);

                }
            }
            this.reportView.panel.updateUI();
        }
    }
    
    public void crearReporteDiario() {
        System.out.println(this.fecha.toLocalDateTime().toLocalDate());
        List<Obra> listObras = daoObras.consultarObras("En Cartelera");
        List<Funcion> listFuncion = daoFunciones.consultarFunciones();
        List<Asiento> listAsientosCancelados = daoAsientos.consultarReembolsos();

        for (int i = 0; i < listObras.size(); i++) {

            JLabel titulo = new JLabel();
            JLabel obra1 = new JLabel();
            JLabel obra2 = new JLabel();
            JLabel obra3 = new JLabel();
            titulo.setText(listObras.get(i).getTituloObra());
            this.reportView.panel.add(titulo);
            this.reportView.panel.add(obra1);
            this.reportView.panel.add(obra2);
            this.reportView.panel.add(obra3);
            for (int j = 0; j < listFuncion.size(); j++) {
                double diamante = 0, lata = 0, cobre = 0, plata = 0, oro = 0, subtotal = 0, total = 0, reembolso = 0;
                if (listFuncion.get(j).getClaveObra().equals(listObras.get(i).getClaveObra())) {
                    JLabel fechafuncion = new JLabel();
                    JLabel horaFuncion = new JLabel();
                    JLabel funcion1 = new JLabel();
                    JLabel funcion2 = new JLabel();
                    fechafuncion.setText(listFuncion.get(j).getFecha().toString());
                    horaFuncion.setText(listFuncion.get(j).getHoraInicio().toString());
                    this.reportView.panel.add(fechafuncion);
                    this.reportView.panel.add(horaFuncion);
                    this.reportView.panel.add(funcion1);
                    this.reportView.panel.add(funcion2);

                    List<Asiento> boletos = daoAsientos.consultarBoletosComprados(listFuncion.get(j).getClaveFuncion());
                    for (int k = 0; k < boletos.size(); k++) {
                        if ("Diamante".equals(boletos.get(k).getArea()) && (boletos.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            diamante = diamante + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Plata".equals(boletos.get(k).getArea()) && (boletos.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            plata = plata + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Oro".equals(boletos.get(k).getArea()) && (boletos.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            oro = oro + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Cobre".equals(boletos.get(k).getArea()) && (boletos.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            cobre = cobre + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        } else if ("Lata".equals(boletos.get(k).getArea()) && (boletos.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            lata = lata + boletos.get(k).getPrecio();
                            subtotal = subtotal + boletos.get(k).getPrecio();
                        }
                    }
                    JLabel obj1 = new JLabel();
                    JLabel obj2 = new JLabel();
                    JLabel lblDiamante = new JLabel();
                    JLabel diamantePrecio = new JLabel();
                    lblDiamante.setText("Diamante");
                    diamantePrecio.setText(String.valueOf(diamante));
                    this.reportView.panel.add(obj1);
                    this.reportView.panel.add(obj2);
                    this.reportView.panel.add(lblDiamante);
                    this.reportView.panel.add(diamantePrecio);
                    JLabel obj3 = new JLabel();
                    JLabel obj4 = new JLabel();
                    JLabel lblOro = new JLabel();
                    JLabel OroPrecio = new JLabel();
                    JLabel obj5 = new JLabel();
                    JLabel obj6 = new JLabel();
                    JLabel lblPlata = new JLabel();
                    JLabel PlataPrecio = new JLabel();
                    JLabel obj7 = new JLabel();
                    JLabel obj8 = new JLabel();
                    JLabel lblCobre = new JLabel();
                    JLabel CobrePrecio = new JLabel();
                    JLabel obj9 = new JLabel();
                    JLabel obj10 = new JLabel();
                    JLabel lblLata = new JLabel();
                    JLabel LataPrecio = new JLabel();
                    JLabel obj11 = new JLabel();
                    JLabel obj12 = new JLabel();
                    JLabel lblSubtotal = new JLabel();
                    JLabel subtotalPrecio = new JLabel();
                    JLabel obj13 = new JLabel();
                    JLabel obj14 = new JLabel();
                    JLabel lblReembolso = new JLabel();
                    JLabel reembolsoPrecio = new JLabel();
                    JLabel obj15 = new JLabel();
                    JLabel obj16 = new JLabel();
                    JLabel lblTotal = new JLabel();
                    JLabel TotalPrecio = new JLabel();
                    lblOro.setText("Oro");
                    lblPlata.setText("Plata");
                    lblCobre.setText("Cobre");
                    lblLata.setText("Lata");
                    lblSubtotal.setText("Subtotal");
                    OroPrecio.setText(String.valueOf(oro));
                    PlataPrecio.setText(String.valueOf(plata));
                    CobrePrecio.setText(String.valueOf(cobre));
                    LataPrecio.setText(String.valueOf(lata));
                    subtotalPrecio.setText(String.valueOf(subtotal));

                    this.reportView.panel.add(obj3);
                    this.reportView.panel.add(obj4);
                    this.reportView.panel.add(lblOro);
                    this.reportView.panel.add(OroPrecio);

                    this.reportView.panel.add(obj5);
                    this.reportView.panel.add(obj6);
                    this.reportView.panel.add(lblPlata);
                    this.reportView.panel.add(PlataPrecio);

                    this.reportView.panel.add(obj7);
                    this.reportView.panel.add(obj8);
                    this.reportView.panel.add(lblCobre);
                    this.reportView.panel.add(CobrePrecio);

                    this.reportView.panel.add(obj9);
                    this.reportView.panel.add(obj10);
                    this.reportView.panel.add(lblLata);
                    this.reportView.panel.add(LataPrecio);

                    this.reportView.panel.add(obj11);
                    this.reportView.panel.add(obj12);
                    this.reportView.panel.add(lblSubtotal);
                    this.reportView.panel.add(subtotalPrecio);

                    for (int k = 0; k < listAsientosCancelados.size(); k++) {
                        if (listAsientosCancelados.get(k).getClvFuncion().equals(listFuncion.get(j).getClaveFuncion()) && (listAsientosCancelados.get(k).getFechaCompra().toLocalDateTime().toLocalDate().equals(this.fecha.toLocalDateTime().toLocalDate()))) {
                            reembolso = reembolso + listAsientosCancelados.get(k).getPrecio();
                        }
                    }
                    lblReembolso.setText("Cancelados");
                    reembolsoPrecio.setText(String.valueOf(reembolso));
                    this.reportView.panel.add(obj13);
                    this.reportView.panel.add(obj14);
                    this.reportView.panel.add(lblReembolso);
                    this.reportView.panel.add(reembolsoPrecio);

                    lblTotal.setText("Total");
                    TotalPrecio.setText(String.valueOf(subtotal - reembolso));
                    this.reportView.panel.add(obj15);
                    this.reportView.panel.add(obj16);
                    this.reportView.panel.add(lblTotal);
                    this.reportView.panel.add(TotalPrecio);

                }
            }
            this.reportView.panel.updateUI();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.reportView.btnBack){
            MainAdminFrame adminFrame = new MainAdminFrame();
            AdminCtrl admin = new AdminCtrl(adminFrame);
            this.reportView.dispose();
        }
    }

}
