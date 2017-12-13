/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DAOFunciones;
import DAO.DAOResponsable;
import DAO.ObrasDAO;
import Interfaz.ObrasFrame;
import Interfaz.MainAdminFrame;
import Interfaz.ModiFrame;
import Interfaz.ResponsableFrame;
import Modelo.Funcion;
import Modelo.Obra;
import Modelo.Responsable;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lu.tudor.santec.jtimechooser.JTimeChooser;

/**
 *
 * @author Victor Perera
 */
public class ObrasCtrl implements ActionListener{
    private ObrasDAO daoObra;
    private ObrasFrame obrasView;
    private Obra obra;
    private List<Obra> obras;
    private List<Funcion> funciones =new ArrayList<>();
    private List<Responsable> listResp;
    private List<JPanel> listPanel = new ArrayList();
    private DAOResponsable daoResp = new DAOResponsable();
    private DAOFunciones daoFuncion = new DAOFunciones();
    private int anteriorNoFuncion;

    public ObrasCtrl(ObrasDAO daoObra, ObrasFrame obrasView) {
        this.daoObra = daoObra;
        this.obrasView = obrasView;
        this.obrasView.setLocationRelativeTo(null);
        this.obrasView.saveBtn.addActionListener(this);
        this.obrasView.addRespBtn.addActionListener(this);
        this.obrasView.btnBack.addActionListener(this);
        this.obrasView.btnaddFuncion.addActionListener(this);
        this.obras = this.daoObra.consultarObras("Total");
        this.obrasView.btnSaveModif.setVisible(false);
        inicializarDatosCreate();
        this.obrasView.setVisible(true);
    }

    public ObrasCtrl(ObrasDAO daoObra, ObrasFrame obrasView, Obra obra) {
        this.obra=obra;
        this.daoObra = daoObra;
        this.obrasView = obrasView;
        this.obrasView.setLocationRelativeTo(null);
        this.obrasView.saveBtn.setVisible(false);
        this.obrasView.addRespBtn.addActionListener(this);
        this.obrasView.btnBack.addActionListener(this);
        this.obrasView.btnaddFuncion.addActionListener(this);
        this.obrasView.btnSaveModif.addActionListener(this);
        this.obrasView.setVisible(true);
        inicializarDatosModif();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.obrasView.saveBtn) {
            String clvObra = this.obrasView.claveObraTxt.getText();
            int noCbxResp = this.obrasView.respCmbx.getSelectedIndex();
            int noResp = listResp.get(noCbxResp).getId();
            String titulo = this.obrasView.titleTxt.getText();
            String description = this.obrasView.descripTxt.getText();
            String actores = this.obrasView.actorsTxt.getText();
            String estado = this.obrasView.estadoCmbx.getSelectedItem().toString();
            if (listPanel.isEmpty()) {
                try {
                    Obra nuevaObra = crearObra(clvObra, noResp, titulo, description, actores, estado);
                    this.daoObra.insertarObra(nuevaObra);
                    JOptionPane.showMessageDialog(obrasView, "Obra Guardada");
                    inicializarDatosCreate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(obrasView, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                try {
                    Obra nuevaObra = crearObra(clvObra, noResp, titulo, description, actores, estado);
                    crearFunciones(clvObra, nuevaObra.getDuracion());
                    verificarFunciones(nuevaObra);
                    this.daoObra.insertarObra(nuevaObra);
                    for (Funcion funcion : funciones) {
                        daoFuncion.insertarFuncion(funcion);
                    }
                    JOptionPane.showMessageDialog(obrasView, "Obra con Funciones Guardada");
                    this.obras = this.daoObra.consultarObras("Total");
                    inicializarDatosCreate();
                    listPanel.clear();
                    funciones.clear();
                    this.obrasView.panel.removeAll();
                    this.obrasView.panel.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(obrasView, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        if(e.getSource() == this.obrasView.btnSaveModif){
            String clvObra = this.obrasView.claveObraTxt.getText();
            int noCbxResp = this.obrasView.respCmbx.getSelectedIndex();
            int noResp = listResp.get(noCbxResp).getId();
            String titulo = this.obrasView.titleTxt.getText();
            String description = this.obrasView.descripTxt.getText();
            String actores = this.obrasView.actorsTxt.getText();
            String estado = this.obrasView.estadoCmbx.getSelectedItem().toString();
            if (listPanel.isEmpty()) {
                try {
                    Obra nuevaObra = crearObra(clvObra, noResp, titulo, description, actores, estado);
                    this.daoObra.modificarObra(nuevaObra);
                    JOptionPane.showMessageDialog(obrasView, "Obra Modificada");
                    ModiFrame modiView = new ModiFrame();
                    ObrasDAO daoObra = new ObrasDAO();
                    ModifObrasCtrl modif = new ModifObrasCtrl(modiView, daoObra);
                    this.obrasView.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(obrasView, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                try {
                    Obra nuevaObra = crearObra(clvObra, noResp, titulo, description, actores, estado);
                    crearFunciones(clvObra, nuevaObra.getDuracion());
                    verificarFunciones(nuevaObra);
                    this.daoObra.modificarObra(nuevaObra);
                    for (int i=0;i<funciones.size();i++) {
                        if(i<anteriorNoFuncion)
                            daoFuncion.modificarFuncion(funciones.get(i));
                        else
                            daoFuncion.insertarFuncion(funciones.get(i));
                    }
                    JOptionPane.showMessageDialog(obrasView, "Obra con Funciones Modificada");
                    this.obrasView.dispose();
                    ModiFrame modiView = new ModiFrame();
                    ObrasDAO daoObra = new ObrasDAO();
                    ModifObrasCtrl modif = new ModifObrasCtrl(modiView, daoObra);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(obrasView, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
        }
        
        if(e.getSource() == this.obrasView.addRespBtn){
            ResponsableFrame respView = new ResponsableFrame();
            ResponsableCtrl respCtrl = new ResponsableCtrl(respView, daoResp);
            
        }
        if(e.getSource() == this.obrasView.btnaddFuncion){
            crearCamposFuncion();
        }
        
        if(e.getSource() == this.obrasView.btnBack){
            MainAdminFrame adminFrame = new MainAdminFrame();
            AdminCtrl admin = new AdminCtrl(adminFrame);
            this.obrasView.dispose();
        }
    }
    
    private void crearClaveObra() {
        if (this.obras.isEmpty()) {
            this.obrasView.claveObraTxt.setText("A0001");
            this.obrasView.claveObraTxt.setEditable(false);
        } else {
            this.obrasView.claveObraTxt.setEditable(true);
            Obra lastObra = this.obras.get(this.obras.size() - 1);
            String nuevaClv = "A";
            String clv = lastObra.getClaveObra();
            String noClv = clv.substring(1);
            int numero = Integer.parseInt(noClv);
            numero++;
            noClv = String.valueOf(numero);
            numero = 4 - noClv.length();
            for (int i = 0; i < numero; i++) {
                nuevaClv = nuevaClv.concat("0");
            }
            String concat = nuevaClv.concat(noClv);
            System.out.println(concat);
            this.obrasView.claveObraTxt.setText("");
            this.obrasView.claveObraTxt.setText(concat);
            this.obrasView.claveObraTxt.setEditable(false);
        }
    }

    private void inicializarDatosCreate() {
        this.obrasView.respCmbx.removeAllItems();
        listResp = daoResp.consultarResponsables();
        for (Responsable resp : listResp) {
            this.obrasView.respCmbx.addItem(resp.getNombre());
        }
        this.obrasView.duracionTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        this.obrasView.txtPrecio.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        crearClaveObra();
        this.obrasView.titleTxt.setText("");
        this.obrasView.descripTxt.setText("");
        this.obrasView.actorsTxt.setText("");
        this.obrasView.duracionTxt.setText("");
        this.obrasView.txtPrecio.setText("");
    }

    private void verificarFunciones(Obra nuevaObra) throws Exception {
        List<Funcion> funcionesAlmacendas = daoFuncion.consultarFunciones();
        for (Funcion funcion : funciones) {
            for (Funcion funcionBD : funcionesAlmacendas) {
                System.out.println(funcionBD.getHoraInicio());
                System.out.println(funcionBD.getHoraFinal());
                System.out.println(funcion.getHoraInicio());
                if (funcionBD.getFecha().equals(funcion.getFecha()) &&( (funcion.getHoraInicio().before(funcionBD.getHoraFinal()) && funcion.getHoraInicio().after(funcionBD.getHoraInicio()))
                        || (funcion.getHoraFinal().before(funcionBD.getHoraFinal()) && funcion.getHoraFinal().after(funcionBD.getHoraInicio())))) {
                    throw new Exception("Hora Coincidente con una funcion de otra Obra");
                } else if (funcion.getFecha().equals(funcionBD.getFecha()) && funcion.getHoraInicio().equals(funcionBD.getHoraInicio())) {
                    throw new Exception("Misma hora que otra funcion de otra obra");
                }
            }

            for (Funcion funcionAux : funciones) {
                if (!funcion.getClaveFuncion().equals(funcionAux.getClaveFuncion())) {
                    if (funcionAux.getFecha().equals(funcion.getFecha())
                            && ((funcion.getHoraInicio().before(funcionAux.getHoraFinal()) && funcion.getHoraInicio().after(funcionAux.getHoraInicio()))
                            || funcion.getHoraInicio().equals(funcionAux.getHoraInicio()))) {
                        throw new Exception("Hora Coincidente con una funcion de la Obra");
                    }
                }
            }
        }
    }

    private Obra crearObra(String clvObra, int noResp, String titulo, String description, String actores, String estado) throws Exception {
        Obra nuevaObra = null;
        if (!(titulo.isEmpty() && description.isEmpty() && actores.isEmpty()
                && this.obrasView.duracionTxt.getText().isEmpty() && this.obrasView.txtPrecio.getText().isEmpty())) {
            int duracion = Integer.valueOf(this.obrasView.duracionTxt.getText());
            double precio = Double.valueOf(this.obrasView.txtPrecio.getText());
            nuevaObra = new Obra(clvObra, noResp, titulo, description, actores, estado, duracion, precio);
            return nuevaObra;
        } else {
            throw new Exception("Datos incompletos");
        }
    }

    private void crearCamposFuncion() {
        JPanel panelSon = new JPanel();
        panelSon.setLayout(new GridLayout(0, 2));
        panelSon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel labDate = new JLabel("Fecha");
        JLabel labTime = new JLabel("Horario");
        JLabel labState = new JLabel("Estado");
        JLabel labClaveFunc = new JLabel("Clave de Funcion");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setMinSelectableDate(new java.util.Date());
        dateChooser.setName("Date " + (listPanel.size() + 1));
        JTimeChooser timeChooser = new JTimeChooser();
        timeChooser.setName("Time " + (listPanel.size() + 1));
        JComboBox cmbxEstado = new JComboBox();
        cmbxEstado.setName("Estado " + (listPanel.size() + 1));
        JTextField txtClaveFunc = new JTextField();
        txtClaveFunc.setName("TxtClave " + (listPanel.size() + 1));

        cmbxEstado.addItem("Disponible");
        cmbxEstado.addItem("Cancelada");
        cmbxEstado.addItem("Vendida");

        panelSon.add(labDate);
        panelSon.add(dateChooser);
        panelSon.add(labTime);
        panelSon.add(timeChooser);
        panelSon.add(labState);
        panelSon.add(cmbxEstado);
        panelSon.add(labClaveFunc);
        panelSon.add(txtClaveFunc);
        panelSon.updateUI();

        panelSon.setName(String.valueOf((listPanel.size() + 1)));
        listPanel.add(panelSon);
        this.obrasView.panel.add(panelSon);
        this.obrasView.panel.updateUI();
    }

    private void establecerCamposFuncion() {
        funciones = daoFuncion.consultarFuncionesDeObra(obra, "Total");
        anteriorNoFuncion = funciones.size();
        for (int i = 0; i < funciones.size(); i++) {
            JPanel panelSon = new JPanel();
            panelSon.setLayout(new GridLayout(0, 2));
            panelSon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel labDate = new JLabel("Fecha");
            JLabel labTime = new JLabel("Horario");
            JLabel labState = new JLabel("Estado");
            JLabel labClaveFunc = new JLabel("Clave de Funcion");
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setMinSelectableDate(new java.util.Date());
            dateChooser.setDate(funciones.get(i).getFecha());
            dateChooser.setName("Date " + (listPanel.size() + 1));
            JTimeChooser timeChooser = new JTimeChooser();
            timeChooser.setName("Time " + (listPanel.size() + 1));
            timeChooser.setTime(funciones.get(i).getHoraInicio());
            JComboBox cmbxEstado = new JComboBox();
            cmbxEstado.setName("Estado " + (listPanel.size() + 1));
            JTextField txtClaveFunc = new JTextField();
            txtClaveFunc.setName("TxtClave " + (listPanel.size() + 1));
            txtClaveFunc.setText(funciones.get(i).getClaveFuncion());
            txtClaveFunc.setEditable(false);

            cmbxEstado.addItem("Disponible");
            cmbxEstado.addItem("Cancelada");
            cmbxEstado.addItem("Vendida");
            cmbxEstado.setSelectedItem(funciones.get(i).getEstado());

            panelSon.add(labDate);
            panelSon.add(dateChooser);
            panelSon.add(labTime);
            panelSon.add(timeChooser);
            panelSon.add(labState);
            panelSon.add(cmbxEstado);
            panelSon.add(labClaveFunc);
            panelSon.add(txtClaveFunc);
            panelSon.updateUI();

            panelSon.setName(String.valueOf((listPanel.size() + 1)));
            listPanel.add(panelSon);
            this.obrasView.panel.add(panelSon);
            this.obrasView.panel.updateUI();
        }
    }

    private void crearFunciones(String clvObra, int duracion) throws Exception {
        funciones.clear();
        String claveFuncion = null, estado = null;
        java.sql.Time horario = null;
        java.sql.Date fecha = null;
        for (JPanel panelFuncion : listPanel) {
            for (Component component : panelFuncion.getComponents()) {
                if (component instanceof JDateChooser) {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaString = formato.format(((JDateChooser) component).getDate());
                    fecha = java.sql.Date.valueOf(fechaString);
                } else if (component instanceof JTimeChooser) {
                    horario = java.sql.Time.valueOf(((JTimeChooser) component).getFormatedTime());
                } else if (component instanceof JComboBox) {
                    estado = ((JComboBox) component).getSelectedItem().toString();
                } else if (component instanceof JTextField) {
                    claveFuncion = ((JTextField) component).getText();
                }
            }

            if (claveFuncion.length() == 6) {
                java.sql.Time horaFinal = getAddSubtractTime(horario, (duracion + 30));
                funciones.add(new Funcion(claveFuncion, clvObra, fecha, horario, horaFinal, estado));
            } else {
                throw new Exception("Clave no válida");
            }
        }
    }

    private static java.sql.Time getAddSubtractTime(java.sql.Time time, int minutes) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time.getTime());
        cal.add(cal.MINUTE, minutes);
        return new Time(cal.getTimeInMillis());
    }

    private void inicializarDatosModif() {
        this.obrasView.respCmbx.removeAllItems();
        listResp = daoResp.consultarResponsables();
        for (Responsable resp : listResp) {
            this.obrasView.respCmbx.addItem(resp.getNombre());
            if (resp.getId() == obra.getResponsable()) {
                this.obrasView.respCmbx.setSelectedItem(resp.getNombre());
            }
        }
        this.obrasView.duracionTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        this.obrasView.txtPrecio.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        this.obrasView.claveObraTxt.setText(obra.getClaveObra());
        this.obrasView.claveObraTxt.setEditable(false);
        this.obrasView.titleTxt.setText(obra.getTituloObra());
        this.obrasView.descripTxt.setText(obra.getDescripcion());
        this.obrasView.actorsTxt.setText(obra.getMainActors());
        this.obrasView.duracionTxt.setText(String.valueOf(obra.getDuracion()));
        this.obrasView.estadoCmbx.setSelectedItem(obra.getEstado());
        this.obrasView.txtPrecio.setText(String.valueOf(obra.getPrecioBase()));
        establecerCamposFuncion();
    }
}
