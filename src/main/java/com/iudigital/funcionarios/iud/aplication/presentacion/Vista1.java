/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.iudigital.funcionarios.iud.aplication.presentacion;

import com.iudigital.funcionarios.iud.aplication.controller.FamiliarController;
import com.iudigital.funcionarios.iud.aplication.controller.FuncionarioController;
import com.iudigital.funcionarios.iud.aplication.controller.InfoAcademicaController;
import com.iudigital.funcionarios.iud.aplication.domain.Familiar;
import com.iudigital.funcionarios.iud.aplication.domain.Funcionario;
import com.iudigital.funcionarios.iud.aplication.domain.InfoAcademica;
import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andri
 */
public class Vista1 extends javax.swing.JFrame {

    DefaultTableModel modeloTabla = new DefaultTableModel();

    /**
     * Creates new form Vista1
     */
    public Vista1() {
        initComponents();

        String encabezados[] = {"ID", "TIPO DOCUMENTO", "IDENTIFICACION", "NOMBRES",
            "APELLIDOS", "ESTADO CIVIL", "SEXO", "DIRECCION", "TELEFONO", "FECHA NACIMIENTO"};

        modeloTabla.setColumnIdentifiers(encabezados);
        funcionariosTabla.setModel(modeloTabla);

        listarFuncionarios();
        limpiarTabla(familiaresTabla);
        txtEditIdentificacion.setCaretColor(Color.red);

    }

    void listarFuncionarios() {

        FuncionarioController funcionario = new FuncionarioController();
        try {
            List<Funcionario> funcionarios = funcionario.obtenerFuncionariosVista();
            modeloTabla = (DefaultTableModel) funcionariosTabla.getModel();
            Object[] vectorFuncionario = new Object[10];
            funcionarios.forEach(f -> {
                vectorFuncionario[0] = f.getId();
                vectorFuncionario[1] = f.getTipo_documento();
                vectorFuncionario[2] = f.getFuncionario_identificacion();
                vectorFuncionario[3] = f.getNombres();
                vectorFuncionario[4] = f.getApellidos();
                vectorFuncionario[5] = f.getEstado_civil();
                vectorFuncionario[6] = f.getSexo();
                vectorFuncionario[7] = f.getDireccion();
                vectorFuncionario[8] = f.getTelefono();
                vectorFuncionario[9] = f.getFecha_nacimiento();

                modeloTabla.addRow(vectorFuncionario);
                funcionariosTabla.setEnabled(false);

            });

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    private void limpiarTabla(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    void crearFuncionario() {

        FuncionarioController funcionarioControl = new FuncionarioController();
        Funcionario funcionario = new Funcionario();
        int tipoDocumento = 0;
        int estadoCivil = 0;
        if (boxTipoDocumento.getSelectedItem() == "CC") {
            tipoDocumento = 1;
        } else {
            tipoDocumento = 2;
        }

        if (boxEstadoCivil.getSelectedItem() == "Soltero") {
            estadoCivil = 1;
        } else {
            estadoCivil = 2;
        }
        funcionario.setId_tipo_documento(tipoDocumento);

        funcionario.setFuncionario_identificacion(txtNumeroIdentificacion.getText());
        funcionario.setNombres(txtNombres.getText());
        funcionario.setApellidos(txtApellidos.getText());
        funcionario.setId_estado_civil(estadoCivil);
        funcionario.setSexo(String.valueOf(boxSexo.getSelectedItem()));
        funcionario.setDireccion(txtDireccion.getText());
        funcionario.setTelefono(txtTelefono.getText());
        funcionario.setFecha_nacimiento(txtFechaNacimiento.getText());

        try {
            funcionarioControl.crearFuncionario(funcionario);
            String mensaje = "¡Funcionario creado!";
            JOptionPane.showMessageDialog(null, mensaje, "Guardado", JOptionPane.WARNING_MESSAGE);
            txtNumeroIdentificacion.setText("");
            txtNombres.setText("");
            txtApellidos.setText("");
            boxSexo.setSelectedIndex(0);
            txtDireccion.setText("");
            txtFechaNacimiento.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        limpiarTabla(funcionariosTabla);
        listarFuncionarios();

    }

    void buscarFuncionarioByIdentificacion() {
        FuncionarioController funcionarioControl = new FuncionarioController();
        String identificacion = txtBuscaFuncionario.getText();
        Funcionario funcionario = new Funcionario();
        try {
            funcionario = funcionarioControl.obtenerFuncionario(identificacion);

            if (funcionario.getFuncionario_identificacion() == null) {
                botonGuardarCambios.setEnabled(false);
                botonEliminarFuncionario.setEnabled(false);
                boxEditTipoDocumento.setSelectedIndex(0);
                txtId.setText(null);
                txtEditIdentificacion.setText("");
                txtEditNombres.setText("");
                txtEditApellidos.setText("");
                boxEditEstadoCivil.setSelectedIndex(0);
                boxEditSexo.setSelectedIndex(0);
                txtEditDireccion.setText("");
                txtEditTelefono.setText("");
                txtEditFechaNacimiento.setText("");
                deshabilitarEdicionFuncionario();
                String mensaje = "¡Error Funcionario no encontrado!";
                JOptionPane.showMessageDialog(null, mensaje, "Funcionario no Encontrado", JOptionPane.WARNING_MESSAGE);
            } else {
                
                botonEliminarFuncionario.setEnabled(true);
                int tipoDocumento = 0;
                int estadoCivil = 0;
                int sexo = 0;
                if (funcionario.getId_tipo_documento() == 1) {
                    tipoDocumento = 0;
                } else {
                    tipoDocumento = 1;
                }
                if (funcionario.getId_estado_civil() == 1) {
                    estadoCivil = 0;
                } else {
                    estadoCivil = 1;
                }
                if (funcionario.getSexo().equals("M")) {
                    sexo = 0;
                } else {
                    sexo = 1;
                }
                boxEditTipoDocumento.setSelectedIndex(tipoDocumento);
                txtId.setText(Integer.toString(funcionario.getId()));
                txtEditIdentificacion.setText(funcionario.getFuncionario_identificacion());
                txtEditNombres.setText(funcionario.getNombres());
                txtEditApellidos.setText(funcionario.getApellidos());
                boxEditEstadoCivil.setSelectedIndex(estadoCivil);
                boxEditSexo.setSelectedIndex(sexo);
                txtEditDireccion.setText(funcionario.getDireccion());
                txtEditTelefono.setText(funcionario.getTelefono());
                txtEditFechaNacimiento.setText(funcionario.getFecha_nacimiento());
                

            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    void editarFuncionario() {

        FuncionarioController funcionarioControl = new FuncionarioController();
        Funcionario funcionario = new Funcionario();
        int tipoDocumento = 0;
        int estadoCivil = 0;
        if (boxEditTipoDocumento.getSelectedItem() == "CC") {
            tipoDocumento = 1;
        } else {
            tipoDocumento = 2;
        }

        if (boxEditEstadoCivil.getSelectedItem() == "Soltero") {
            estadoCivil = 1;
        } else {
            estadoCivil = 2;
        }
        funcionario.setId(Integer.parseInt(txtId.getText()));
        funcionario.setId_tipo_documento(tipoDocumento);

        funcionario.setFuncionario_identificacion(txtEditIdentificacion.getText());

        funcionario.setNombres(txtEditNombres.getText());

        funcionario.setApellidos(txtEditApellidos.getText());

        funcionario.setId_estado_civil(estadoCivil);

        funcionario.setSexo(String.valueOf(boxEditSexo.getSelectedItem()));

        funcionario.setDireccion(txtEditDireccion.getText());

        funcionario.setTelefono(txtEditTelefono.getText());

        funcionario.setFecha_nacimiento(txtEditFechaNacimiento.getText());

        try {
            funcionarioControl.actualiziarFuncionario(funcionario.getId(), funcionario);

            String mensaje = "¡Funcionario Actualizado!";
            JOptionPane.showMessageDialog(null, mensaje, "Guardado", JOptionPane.WARNING_MESSAGE);

            txtBuscaFuncionario.setText("");
            boxEditTipoDocumento.setSelectedIndex(0);
            txtId.setText("");
            txtEditIdentificacion.setText("");
            txtEditNombres.setText("");
            txtEditApellidos.setText("");
            boxEditEstadoCivil.setSelectedIndex(0);
            boxEditSexo.setSelectedIndex(0);
            txtEditDireccion.setText("");
            txtEditTelefono.setText("");
            txtEditFechaNacimiento.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        limpiarTabla(funcionariosTabla);
        listarFuncionarios();

    }

    void listarFamiliaresByFuncionario(String funcionario_identificacion) {

        FamiliarController familiar = new FamiliarController();
        try {
            List<Familiar> familiares = familiar.obtenerFamiliares(funcionario_identificacion);
            modeloTabla = (DefaultTableModel) familiaresTabla.getModel();
            Object[] vectorFamiliar = new Object[6];
            limpiarTabla(familiaresTabla);
            familiares.forEach(f -> {
                vectorFamiliar[0] = f.getId();
                vectorFamiliar[1] = f.getFuncionario_identificacion();
                vectorFamiliar[2] = f.getNombres();
                vectorFamiliar[3] = f.getApellidos();
                vectorFamiliar[4] = f.getCedula();
                vectorFamiliar[5] = f.getRol();
                String encabezados[] = {"Id Familiar", "Identificacion del Funcionario", "Nombres", "Apellidos", "Cedula Familiar", "Rol"};
                modeloTabla.setColumnIdentifiers(encabezados);
                modeloTabla.addRow(vectorFamiliar);
                familiaresTabla.setEnabled(false);
                jTabbedPane1.setSelectedIndex(1);

            });

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    void habilitarEdicionFuncionario() {
        boxEditTipoDocumento.setEnabled(true);
        boxEditEstadoCivil.setEnabled(true);
        boxEditSexo.setEnabled(true);
        txtEditIdentificacion.setEnabled(true);
        txtEditNombres.setEnabled(true);
        txtEditApellidos.setEnabled(true);
        txtEditDireccion.setEnabled(true);
        txtEditTelefono.setEnabled(true);
        jCalendar2.setEnabled(true);
        botonGuardarCambios.setEnabled(true);

    }

    void deshabilitarEdicionFuncionario() {
        boxEditTipoDocumento.setEnabled(false);
        boxEditEstadoCivil.setEnabled(false);
        boxEditSexo.setEnabled(false);
        txtEditIdentificacion.setEnabled(false);
        txtEditNombres.setEnabled(false);
        txtEditApellidos.setEnabled(false);
        txtEditDireccion.setEnabled(false);
        txtEditTelefono.setEnabled(false);
        jCalendar2.setEnabled(false);
        botonGuardarCambios.setEnabled(false);

    }

    void buscarFuncionarioByIdentificacion2() {
        FuncionarioController funcionarioControl = new FuncionarioController();
        String identificacion = txtBuscarFuncionarioFamiliar.getText();
        Funcionario funcionario = new Funcionario();
        try {
            funcionario = funcionarioControl.obtenerFuncionario(identificacion);

            if (funcionario.getFuncionario_identificacion() == null) {
                System.out.println("Error Funcionario no encontrado");
                String mensaje = "¡Error Funcionario no encontrado!";
                JOptionPane.showMessageDialog(null, mensaje, "Funcionario no Encontrado", JOptionPane.WARNING_MESSAGE);

                txtNombresFamiliar.setEnabled(false);
                txtApellidosFamiliar.setEnabled(false);
                txtCedulaFamiliar.setEnabled(false);
                boxRolFamiliar.setEnabled(false);
                botonGuardarFamiliar.setEnabled(false);
                txtIdFuncionario.setText("");
                txtCedulaFuncionarioFamiliar.setText("");
            } else {
                txtIdFuncionario.setText(Integer.toString(funcionario.getId()));
                txtCedulaFuncionarioFamiliar.setText(funcionario.getFuncionario_identificacion());
                txtNombresFamiliar.setEnabled(true);
                txtApellidosFamiliar.setEnabled(true);
                txtCedulaFamiliar.setEnabled(true);
                boxRolFamiliar.setEnabled(true);
                botonGuardarFamiliar.setEnabled(true);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    void crearFamiliar() {

        FamiliarController familiarControl = new FamiliarController();
        Familiar familiar = new Familiar();

        familiar.setFuncionario_identificacion(txtCedulaFuncionarioFamiliar.getText());
        familiar.setNombres(txtNombresFamiliar.getText());
        familiar.setApellidos(txtApellidosFamiliar.getText());
        familiar.setCedula(txtCedulaFamiliar.getText());
        familiar.setRol(String.valueOf(boxRolFamiliar.getSelectedItem()));

        try {
            familiarControl.crearFamiliar(familiar);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        limpiarTabla(familiaresTabla);
        listarFamiliaresByFuncionario(txtCedulaFuncionarioFamiliar.getText());

        String mensaje = "¡Familiar guardado!";
        JOptionPane.showMessageDialog(null, mensaje, "Guardado", JOptionPane.WARNING_MESSAGE);

        txtNombresFamiliar.setText("");
        txtApellidosFamiliar.setText("");
        txtCedulaFamiliar.setText("");

    }

    void listarInfoAcademicaFuncionario(String identificacion) {

        InfoAcademicaController infoAcademicaControl = new InfoAcademicaController();

        try {
            InfoAcademica infoAcademica = infoAcademicaControl.obtenerInfoAcademicaFuncionario(identificacion);
            modeloTabla = (DefaultTableModel) infoAcademicaTabla.getModel();
            Object[] vectorInfoAcademica = new Object[6];
            limpiarTabla(infoAcademicaTabla);

            vectorInfoAcademica[0] = infoAcademica.getId();
            vectorInfoAcademica[1] = infoAcademica.getFuncionario_identificacion();
            vectorInfoAcademica[2] = infoAcademica.getUniversidad();
            vectorInfoAcademica[3] = infoAcademica.getNivel_estudio();
            vectorInfoAcademica[4] = infoAcademica.getTitulo();

            String encabezados[] = {"Id InfoAcademica", "Identificacion del Funcionario", "Universidad", "Nivel de Estudio", "Titulo"};
            modeloTabla.setColumnIdentifiers(encabezados);
            modeloTabla.addRow(vectorInfoAcademica);
            infoAcademicaTabla.setEnabled(false);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    void getInfoAcademicaFuncionario(String identificacion) {

        InfoAcademicaController infoAcademicaControl = new InfoAcademicaController();

        try {
            InfoAcademica infoAcademica = infoAcademicaControl.obtenerInfoAcademicaFuncionario(identificacion);
            modeloTabla = (DefaultTableModel) infoAcademicaTabla.getModel();
            Object[] vectorInfoAcademica = new Object[6];
            limpiarTabla(infoAcademicaTabla);

            vectorInfoAcademica[0] = infoAcademica.getId();
            vectorInfoAcademica[1] = infoAcademica.getFuncionario_identificacion();
            vectorInfoAcademica[2] = infoAcademica.getUniversidad();
            vectorInfoAcademica[3] = infoAcademica.getNivel_estudio();
            vectorInfoAcademica[4] = infoAcademica.getTitulo();

            String encabezados[] = {"Id InfoAcademica", "Identificacion del Funcionario", "Universidad", "Nivel de Estudio", "Titulo"};
            modeloTabla.setColumnIdentifiers(encabezados);
            modeloTabla.addRow(vectorInfoAcademica);
            infoAcademicaTabla.setEnabled(false);

            if (infoAcademica.getFuncionario_identificacion() == null) {
                botonGuardarInfoAcademica.setEnabled(true);
                botonActualizarInfoAcademica.setEnabled(false);
                txtUniversidad.setText("");
                boxNivelEstudio.setSelectedIndex(0);
                txtTitulo.setText("");
            } else {
                txtUniversidad.setText(infoAcademica.getUniversidad());
                boxNivelEstudio.setSelectedItem(infoAcademica.getNivel_estudio());
                txtTitulo.setText(infoAcademica.getTitulo());
                botonActualizarInfoAcademica.setEnabled(true);
                botonGuardarInfoAcademica.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    void guardarInfoAcademica() {

        InfoAcademicaController infoAcademicaControl = new InfoAcademicaController();

        InfoAcademica infoAcademica = new InfoAcademica();

        infoAcademica.setFuncionario_identificacion(txtCedulaFuncionarioFamiliar.getText());
        infoAcademica.setNivel_estudio(String.valueOf(boxNivelEstudio.getSelectedItem()));
        infoAcademica.setUniversidad(txtUniversidad.getText());
        infoAcademica.setTitulo(txtTitulo.getText());

        try {
            infoAcademicaControl.crearInfoAcademicaFuncionario(infoAcademica);
            String mensaje = "¡InfoAcademica guardada!";
            JOptionPane.showMessageDialog(null, mensaje, "Guardado", JOptionPane.WARNING_MESSAGE);
            jTabbedPane1.setSelectedIndex(2);
            botonGuardarInfoAcademica.setEnabled(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    void actualizarInfoAcademica() {

        InfoAcademicaController infoAcademicaControl = new InfoAcademicaController();

        InfoAcademica infoAcademica = new InfoAcademica();

        infoAcademica.setFuncionario_identificacion(txtCedulaFuncionarioFamiliar.getText());
        infoAcademica.setNivel_estudio(String.valueOf(boxNivelEstudio.getSelectedItem()));
        infoAcademica.setUniversidad(txtUniversidad.getText());
        infoAcademica.setTitulo(txtTitulo.getText());

        try {
            infoAcademicaControl.actualiziarInfoAcademicaFuncionario(infoAcademica);
            String mensaje = "¡InfoAcademica actualizada!";
            JOptionPane.showMessageDialog(null, mensaje, "Actualizado", JOptionPane.WARNING_MESSAGE);
            jTabbedPane1.setSelectedIndex(2);
            botonGuardarInfoAcademica.setEnabled(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    void eliminarFuncionario(int id_funcionario) {

        FuncionarioController funcionarioControl = new FuncionarioController();

        try {

            funcionarioControl.eliminarFuncionario(id_funcionario);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        funcionariosTabla = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        familiaresTabla = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        infoAcademicaTabla = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        boxTipoDocumento = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNumeroIdentificacion = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        boxEstadoCivil = new javax.swing.JComboBox<>();
        boxSexo = new javax.swing.JComboBox<>();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        txtFechaNacimiento = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        botonGuardarFuncionario = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscaFuncionario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtEditIdentificacion = new javax.swing.JTextField();
        txtEditNombres = new javax.swing.JTextField();
        txtEditApellidos = new javax.swing.JTextField();
        txtEditTelefono = new javax.swing.JTextField();
        txtEditDireccion = new javax.swing.JTextField();
        boxEditTipoDocumento = new javax.swing.JComboBox<>();
        boxEditEstadoCivil = new javax.swing.JComboBox<>();
        boxEditSexo = new javax.swing.JComboBox<>();
        txtEditFechaNacimiento = new javax.swing.JTextField();
        jCalendar2 = new com.toedter.calendar.JCalendar();
        botonGuardarCambios = new javax.swing.JButton();
        botonEditarFuncionario = new javax.swing.JButton();
        botonEliminarFuncionario = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtBuscarFuncionarioFamiliar = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        txtIdFuncionario = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtNombresFamiliar = new javax.swing.JTextField();
        txtApellidosFamiliar = new javax.swing.JTextField();
        txtCedulaFamiliar = new javax.swing.JTextField();
        boxRolFamiliar = new javax.swing.JComboBox<>();
        botonGuardarFamiliar = new javax.swing.JButton();
        txtCedulaFuncionarioFamiliar = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtUniversidad = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        boxNivelEstudio = new javax.swing.JComboBox<>();
        botonGuardarInfoAcademica = new javax.swing.JButton();
        botonActualizarInfoAcademica = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        funcionariosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        funcionariosTabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(funcionariosTabla);

        jTabbedPane1.addTab("Funcionarios", jScrollPane1);

        familiaresTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(familiaresTabla);

        jTabbedPane1.addTab("Familiares", jScrollPane2);

        infoAcademicaTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(infoAcademicaTabla);

        jTabbedPane1.addTab("InfoAcademica", jScrollPane3);

        jLabel1.setText("Tipo Documento");

        boxTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CC", "CE" }));
        boxTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTipoDocumentoActionPerformed(evt);
            }
        });

        jLabel2.setText("Numero de Identificacion");

        jLabel3.setText("Nombres");

        jLabel4.setText("Apellidos");

        jLabel5.setText("Estado Civil");

        jLabel6.setText("Sexo");

        jLabel7.setText("Direccion");

        jLabel8.setText("Telefono");

        jLabel9.setText("Fecha de Nacimiento");

        boxEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado" }));

        boxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });

        txtFechaNacimiento.setEditable(false);

        botonGuardarFuncionario.setText("Guardar Funcionario");
        botonGuardarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boxTipoDocumento, 0, 180, Short.MAX_VALUE)
                                    .addComponent(txtNumeroIdentificacion)
                                    .addComponent(txtNombres)
                                    .addComponent(txtApellidos)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(148, 148, 148)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(boxSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(boxEstadoCivil, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(84, 84, 84)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(123, 123, 123)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txtDireccion)))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonGuardarFuncionario)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNumeroIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(boxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonGuardarFuncionario))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Nuevo Funcionario", jPanel2);

        jLabel10.setText("Cedula del Funcionario");

        txtBuscaFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscaFuncionarioActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setText("Tipo Documento");

        jLabel12.setText("Numero de Identificacion");

        jLabel13.setText("Nombres");

        jLabel14.setText("Apellidos");

        jLabel15.setText("Estado Civil");

        jLabel16.setText("Sexo");

        jLabel17.setText("Direccion");

        jLabel18.setText("Telefono");

        jLabel19.setText("Fecha de Nacimiento");

        jLabel20.setText("ID");

        txtId.setEditable(false);

        txtEditIdentificacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEditIdentificacion.setEnabled(false);

        txtEditNombres.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEditNombres.setEnabled(false);
        txtEditNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditNombresActionPerformed(evt);
            }
        });

        txtEditApellidos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEditApellidos.setEnabled(false);

        txtEditTelefono.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEditTelefono.setEnabled(false);

        txtEditDireccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEditDireccion.setEnabled(false);

        boxEditTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CC", "CE" }));
        boxEditTipoDocumento.setEnabled(false);

        boxEditEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado" }));
        boxEditEstadoCivil.setEnabled(false);

        boxEditSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        boxEditSexo.setEnabled(false);

        txtEditFechaNacimiento.setEditable(false);

        jCalendar2.setEnabled(false);
        jCalendar2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar2PropertyChange(evt);
            }
        });

        botonGuardarCambios.setText("Guardar Cambios");
        botonGuardarCambios.setEnabled(false);
        botonGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarCambiosActionPerformed(evt);
            }
        });

        botonEditarFuncionario.setText("Editar Funcionario");
        botonEditarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarFuncionarioActionPerformed(evt);
            }
        });

        botonEliminarFuncionario.setText("Eliminar Funcionario");
        botonEliminarFuncionario.setEnabled(false);
        botonEliminarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBuscaFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel16)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(boxEditSexo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(boxEditEstadoCivil, javax.swing.GroupLayout.Alignment.LEADING, 0, 181, Short.MAX_VALUE)
                                .addComponent(txtEditApellidos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEditNombres, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(boxEditTipoDocumento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEditIdentificacion, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEditDireccion)
                                .addComponent(txtEditTelefono)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEditFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(botonEditarFuncionario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonGuardarCambios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(botonEliminarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(104, 104, 104))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscaFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(txtEditFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boxEditTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtEditIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtEditNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(11, 11, 11)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(boxEditEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(boxEditSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEditDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtEditTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtEditApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 130, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonEditarFuncionario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonGuardarCambios)
                            .addComponent(botonEliminarFuncionario))
                        .addContainerGap())))
        );

        jTabbedPane3.addTab("Consultar Funcionario", jPanel3);

        jLabel21.setText("Cedula Funcionario");

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel22.setText("Id Funcionario");

        txtIdFuncionario.setEditable(false);

        jLabel23.setText("Nombres");

        jLabel24.setText("Apellidos");

        jLabel25.setText("Cedula");

        jLabel26.setText("Rol");

        txtNombresFamiliar.setEnabled(false);

        txtApellidosFamiliar.setEnabled(false);

        txtCedulaFamiliar.setEnabled(false);

        boxRolFamiliar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PADRE", "MADRE", "HIJO", "HIJA", "HERMANO", "HERMANA", "ESPOSO", "ESPOSA" }));
        boxRolFamiliar.setEnabled(false);

        botonGuardarFamiliar.setText("Guardar Familiar");
        botonGuardarFamiliar.setEnabled(false);
        botonGuardarFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarFamiliarActionPerformed(evt);
            }
        });

        txtCedulaFuncionarioFamiliar.setEditable(false);
        txtCedulaFuncionarioFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaFuncionarioFamiliarActionPerformed(evt);
            }
        });

        jLabel27.setText("Cedula Funcionario");

        jLabel28.setText("AGREGAR FAMILIAR");

        jLabel29.setText("EDITAR INFORMACION ACADEMICA");

        jLabel30.setText("Universidad");

        jLabel31.setText("Nivel de Estudio");

        jLabel32.setText("Titulo");

        boxNivelEstudio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TECNICA", "TECNOLOGIA", "PREGRADO", "ESPECIALISTA", "DOCTORADO" }));

        botonGuardarInfoAcademica.setText("Guardar InfoAcademica");
        botonGuardarInfoAcademica.setEnabled(false);
        botonGuardarInfoAcademica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarInfoAcademicaActionPerformed(evt);
            }
        });

        botonActualizarInfoAcademica.setText("Actualizar InfoAcademica");
        botonActualizarInfoAcademica.setEnabled(false);
        botonActualizarInfoAcademica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarInfoAcademicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarFuncionarioFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3))
                            .addComponent(botonGuardarFamiliar)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel28)
                                .addGap(232, 232, 232)
                                .addComponent(jLabel29)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel27))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCedulaFuncionarioFamiliar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(txtIdFuncionario)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(24, 24, 24)
                                        .addComponent(txtNombresFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(99, 99, 99)
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(173, 173, 173))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCedulaFamiliar, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                    .addComponent(txtApellidosFamiliar)
                                    .addComponent(boxRolFamiliar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(105, 105, 105)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(botonGuardarInfoAcademica)
                                        .addGap(18, 18, 18)
                                        .addComponent(botonActualizarInfoAcademica)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(boxNivelEstudio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTitulo)
                                            .addComponent(txtUniversidad))))))
                        .addGap(271, 271, 271))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtBuscarFuncionarioFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedulaFuncionarioFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtNombresFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txtUniversidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtApellidosFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(boxNivelEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtCedulaFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(boxRolFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGuardarFamiliar)
                    .addComponent(botonGuardarInfoAcademica)
                    .addComponent(botonActualizarInfoAcademica))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Info Adicional Funcionario", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane3)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane3)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonActualizarInfoAcademicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarInfoAcademicaActionPerformed
        // TODO add your handling code here:
        actualizarInfoAcademica();
        listarInfoAcademicaFuncionario(txtCedulaFuncionarioFamiliar.getText());
    }//GEN-LAST:event_botonActualizarInfoAcademicaActionPerformed

    private void botonGuardarInfoAcademicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarInfoAcademicaActionPerformed
        // TODO add your handling code here:
        guardarInfoAcademica();
        listarInfoAcademicaFuncionario(txtCedulaFuncionarioFamiliar.getText());
    }//GEN-LAST:event_botonGuardarInfoAcademicaActionPerformed

    private void txtCedulaFuncionarioFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaFuncionarioFamiliarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaFuncionarioFamiliarActionPerformed

    private void botonGuardarFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarFamiliarActionPerformed
        // TODO add your handling code here:
        crearFamiliar();
    }//GEN-LAST:event_botonGuardarFamiliarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        buscarFuncionarioByIdentificacion2();
        String funcionario_identificacion = txtBuscarFuncionarioFamiliar.getText();
        listarFamiliaresByFuncionario(funcionario_identificacion);
        getInfoAcademicaFuncionario(funcionario_identificacion);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void botonEditarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarFuncionarioActionPerformed
        // TODO add your handling code here:
        habilitarEdicionFuncionario();
        buscarFuncionarioByIdentificacion();
        
    }//GEN-LAST:event_botonEditarFuncionarioActionPerformed

    private void botonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCambiosActionPerformed
        // TODO add your handling code here:
        editarFuncionario();
        deshabilitarEdicionFuncionario();
        limpiarTabla(funcionariosTabla);
        listarFuncionarios();
    }//GEN-LAST:event_botonGuardarCambiosActionPerformed

    private void jCalendar2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar2PropertyChange
        // TODO add your handling code here:
        if (evt.getOldValue() != null) {
            SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
            txtEditFechaNacimiento.setText(ff.format(jCalendar2.getCalendar().getTime()));
        }
    }//GEN-LAST:event_jCalendar2PropertyChange

    private void txtEditNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditNombresActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        deshabilitarEdicionFuncionario();
        buscarFuncionarioByIdentificacion();        
        String funcionario_identificacion = txtBuscaFuncionario.getText();
        listarFamiliaresByFuncionario(funcionario_identificacion);
        listarInfoAcademicaFuncionario(funcionario_identificacion);
        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBuscaFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaFuncionarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscaFuncionarioActionPerformed

    private void botonGuardarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarFuncionarioActionPerformed
        // TODO add your handling code here:
        crearFuncionario();
    }//GEN-LAST:event_botonGuardarFuncionarioActionPerformed

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        // TODO add your handling code here:
        if (evt.getOldValue() != null) {
            SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
            txtFechaNacimiento.setText(ff.format(jCalendar1.getCalendar().getTime()));
        }
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void boxTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTipoDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTipoDocumentoActionPerformed

    private void botonEliminarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarFuncionarioActionPerformed
        // TODO add your handling code here:

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar el funcionario?\n"
                        + "Esta accion eliminará tambien la informacion academica y de familiares del funcionario",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            // Lógica para eliminar el registro de la base de datos
            // ...
            eliminarFuncionario(Integer.parseInt(txtId.getText()));
            String funcionario_identificacion = txtBuscaFuncionario.getText();
            
            String mensaje = "¡Funcionario Eliminado!";
            JOptionPane.showMessageDialog(null, mensaje, "Eliminado", JOptionPane.WARNING_MESSAGE);
            botonEliminarFuncionario.setEnabled(false);
            boxEditTipoDocumento.setSelectedIndex(0);
            txtId.setText(null);
            txtEditIdentificacion.setText("");
            txtEditNombres.setText("");
            txtEditApellidos.setText("");
            boxEditEstadoCivil.setSelectedIndex(0);
            boxEditSexo.setSelectedIndex(0);
            txtEditDireccion.setText("");
            txtEditTelefono.setText("");
            txtEditFechaNacimiento.setText("");
            limpiarTabla(funcionariosTabla);
            listarFamiliaresByFuncionario(funcionario_identificacion);
            getInfoAcademicaFuncionario(funcionario_identificacion);            
            listarFuncionarios();
        }


    }//GEN-LAST:event_botonEliminarFuncionarioActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizarInfoAcademica;
    private javax.swing.JButton botonEditarFuncionario;
    private javax.swing.JButton botonEliminarFuncionario;
    private javax.swing.JButton botonGuardarCambios;
    private javax.swing.JButton botonGuardarFamiliar;
    private javax.swing.JButton botonGuardarFuncionario;
    private javax.swing.JButton botonGuardarInfoAcademica;
    private javax.swing.JComboBox<String> boxEditEstadoCivil;
    private javax.swing.JComboBox<String> boxEditSexo;
    private javax.swing.JComboBox<String> boxEditTipoDocumento;
    private javax.swing.JComboBox<String> boxEstadoCivil;
    private javax.swing.JComboBox<String> boxNivelEstudio;
    private javax.swing.JComboBox<String> boxRolFamiliar;
    private javax.swing.JComboBox<String> boxSexo;
    private javax.swing.JComboBox<String> boxTipoDocumento;
    private javax.swing.JTable familiaresTabla;
    private javax.swing.JTable funcionariosTabla;
    private javax.swing.JTable infoAcademicaTabla;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JCalendar jCalendar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtApellidosFamiliar;
    private javax.swing.JTextField txtBuscaFuncionario;
    private javax.swing.JTextField txtBuscarFuncionarioFamiliar;
    private javax.swing.JTextField txtCedulaFamiliar;
    private javax.swing.JTextField txtCedulaFuncionarioFamiliar;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEditApellidos;
    private javax.swing.JTextField txtEditDireccion;
    private javax.swing.JTextField txtEditFechaNacimiento;
    private javax.swing.JTextField txtEditIdentificacion;
    private javax.swing.JTextField txtEditNombres;
    private javax.swing.JTextField txtEditTelefono;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdFuncionario;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNombresFamiliar;
    private javax.swing.JTextField txtNumeroIdentificacion;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtUniversidad;
    // End of variables declaration//GEN-END:variables
}
