package controladores;

import IGU.Menu2;
import Models.Empleados;
import Models.EmpleadosDAO;
import Models.Usuarios;
import Models.UsuariosDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmpleadosController implements ActionListener, MouseListener, KeyListener {

    private Usuarios usuario;
    private UsuariosDAO usuariodao;
    private Empleados empleados;
    private EmpleadosDAO empleadosdao;
    private Menu2 admin;
    private DefaultTableModel modelotabla;
    private int id_usuario = -1;
    private int id_emp = -1;

    public EmpleadosController(Usuarios usuario, UsuariosDAO usuariodao, Empleados empleados, EmpleadosDAO empleadosdao,
            Menu2 admin) {
        this.usuario = usuario;
        this.usuariodao = usuariodao;
        this.empleados = empleados;
        this.empleadosdao = empleadosdao;
        this.admin = admin;

        // registrar
        this.admin.btnguardar_emp.addActionListener(this);
        // modificar
        this.admin.btnmodificar_emp.addActionListener(this);
        // tabla
        this.admin.jTable_emp.addMouseListener(this);
        // eliminar
        this.admin.btneliminar_emp.addActionListener(this);
        // buscar
        this.admin.txtbuscar_emp.addKeyListener(this);

        // validacion
        this.admin.txtcedula_emp.addKeyListener(this);
        this.admin.txtnombre_emp.addKeyListener(this);
        this.admin.txtapellido_emp.addKeyListener(this);
        this.admin.txtfecha_emp.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == admin.btnguardar_emp) {
            try {
                if (admin.txtcedula_emp.getText().equals("") || admin.txtnombre_emp.getText().equals("")
                        || admin.txtapellido_emp.getText().equals("")
                        || admin.txtdireccion_emp.getText().equals("") || admin.txtfecha_emp.getText().equals("")
                        || admin.txtnombre_user.getText().equals("")
                        || admin.txtpassword.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");

                } else {
                    usuario.setRol(admin.ComboBox_Rol.getSelectedItem().toString());
                    usuario.setNombre_user(admin.txtnombre_user.getText().trim());
                    usuario.setPassword(admin.txtpassword.getText().trim());

                    if (admin.txtcedula_emp.getText().length() < 7 || admin.txtcedula_emp.getText().length() > 9) {
                        JOptionPane.showMessageDialog(null, "La cédula debe tener entre 7 y 9 dígitos");
                    } else if (empleadosdao.existeCedula(admin.txtcedula_emp.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "La cedula ya esta en uso");
                    } else {
                        int usuarioregistrado = usuariodao.registrarusuarios(usuario);

                        if (usuarioregistrado >= 0) {
                            empleados.setCedula(admin.txtcedula_emp.getText().trim());
                            empleados.setNombre(admin.txtnombre_emp.getText().trim());
                            empleados.setApellido(admin.txtapellido_emp.getText().trim());
                            empleados.setDireccion(admin.txtdireccion_emp.getText().trim());
                            empleados.setFecha(Integer.parseInt(admin.txtfecha_emp.getText()));
                            empleados.setId_usuario(usuarioregistrado);

                            if (empleadosdao.registrarempleado(empleados)) {
                                limpiarceldas();
                                inicializartabla_empleado();
                                cargartabla_empleado();
                                JOptionPane.showMessageDialog(null, "Empleado registrado con exito");

                            } else {
                                JOptionPane.showMessageDialog(null, "Error al registrar el empleado");

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "La Cedula Ya Esta Registrada");

                        }

                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una edad correcta");
            }

        } else if (e.getSource() == admin.btnmodificar_emp)

        {
            if (admin.txtcedula_emp.getText().equals("") || admin.txtnombre_emp.getText().equals("")
                    || admin.txtapellido_emp.getText().equals("")
                    || admin.txtdireccion_emp.getText().equals("") || admin.txtfecha_emp.getText().equals("")
                    || admin.txtnombre_user.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");

            } else {
                if (id_emp == -1) {
                    JOptionPane.showMessageDialog(null, "El usuario no existe");
                } else {

                    empleados.setId_emp(id_emp);
                    empleados.setCedula(admin.txtcedula_emp.getText().trim());
                    empleados.setNombre(admin.txtnombre_emp.getText().trim());
                    empleados.setApellido(admin.txtapellido_emp.getText().trim());
                    empleados.setFecha(Integer.parseInt(admin.txtfecha_emp.getText()));
                    empleados.setDireccion(admin.txtdireccion_emp.getText().trim());
                    empleados.setId_usuario(id_usuario);
                    empleados.setNombre_user(admin.txtnombre_user.getText().trim());
                    empleados.setPassword(admin.txtpassword.getText().trim());
                     if (admin.txttelefono_cliente.getText().length() != 11){
                        JOptionPane.showMessageDialog(null, "El numero debe tener 11 dígitos");
                    }

                    if (admin.txtcedula_emp.getText().length() < 6 || admin.txtcedula_emp.getText().length() > 8) {
                        JOptionPane.showMessageDialog(null, "La cédula debe tener entre 6 y 8 dígitos");
                    } else if (empleadosdao.modificarempleado(empleados)) {
                        limpiarceldas();
                        inicializartabla_empleado();
                        cargartabla_empleado();
                        JOptionPane.showMessageDialog(null, "Empleado modificado con exito ");

                    }

                }

            }

        } else if (e.getSource() == admin.btneliminar_emp) {
            if (admin.txtcedula_emp.getText().equals("") || admin.txtnombre_emp.getText().equals("")
                    || admin.txtapellido_emp.getText().equals("")
                    || admin.txtdireccion_emp.getText().equals("") || admin.txtfecha_emp.getText().equals("")
                    || admin.txtnombre_user.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");

            } else {
                int filaseleccionada = admin.jTable_emp.getSelectedRow();
                if (filaseleccionada >= 0) {
                    int id_emp = Integer.parseInt(modelotabla.getValueAt(filaseleccionada, 0).toString());
                    int confirmar = JOptionPane.showConfirmDialog(null, "Esta seguro que desae eliminar el empleado?",
                            "confirmar eliminacion", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        if (empleadosdao.eliminarempleado(id_emp, id_usuario)) {
                            limpiarceldas();
                            inicializartabla_empleado();
                            cargartabla_empleado();
                            JOptionPane.showMessageDialog(null, "Empleado eliminado con exito");

                        }

                    }

                }

            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaseleccionada = admin.jTable_emp.getSelectedRow();

        if (filaseleccionada >= 0) {
            admin.btnguardar_emp.setEnabled(false);
            admin.btnmodificar_emp.setEnabled(true);
            admin.btneliminar_emp.setEnabled(true);

            id_emp = Integer.parseInt(modelotabla.getValueAt(filaseleccionada, 0).toString());
            id_usuario = Integer.parseInt(modelotabla.getValueAt(filaseleccionada, 7).toString());

            admin.txtcedula_emp.setText(modelotabla.getValueAt(filaseleccionada, 1).toString());
            admin.txtnombre_emp.setText(modelotabla.getValueAt(filaseleccionada, 2).toString());
            admin.txtapellido_emp.setText(modelotabla.getValueAt(filaseleccionada, 3).toString());
            admin.txtfecha_emp.setText(modelotabla.getValueAt(filaseleccionada, 4).toString());
            admin.txtdireccion_emp.setText(modelotabla.getValueAt(filaseleccionada, 5).toString());
            admin.txtnombre_user.setText(modelotabla.getValueAt(filaseleccionada, 6).toString());

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (e.getSource() == admin.txtnombre_emp || e.getSource() == admin.txtapellido_emp) {
            if (Character.isDigit(c)) {
                e.consume();
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        } else if (e.getSource() == admin.txtcedula_emp || e.getSource() == admin.txtfecha_emp) {
            if (!Character.isDigit(c)) {
                e.consume();
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == admin.txtbuscar_emp) {
            String buscar = admin.txtbuscar_emp.getText().trim();
            if (buscar.equals("")) {
                limpiarceldas();
                cargartabla_empleado();

            } else {
                cargarbusqueda(buscar);
            }

        }
    }

    public void inicializartabla_empleado() {
        modelotabla = new DefaultTableModel();
        modelotabla.addColumn("id_emp");
        modelotabla.addColumn("cedula");
        modelotabla.addColumn("nombre");
        modelotabla.addColumn("apellido");
        modelotabla.addColumn("edad");
        modelotabla.addColumn("direccion");
        modelotabla.addColumn("nombre_user");
        modelotabla.addColumn("id_usuario");
        admin.jTable_emp.setModel(modelotabla);

        admin.jTable_emp.getColumnModel().getColumn(7).setMinWidth(0);
        admin.jTable_emp.getColumnModel().getColumn(7).setMaxWidth(0);
        admin.jTable_emp.getColumnModel().getColumn(7).setWidth(0);

    }

    public void cargartabla_empleado() {
        modelotabla.setRowCount(0);
        List<Empleados> empleado = empleadosdao.listaempleados();
        for (Empleados e : empleado) {
            modelotabla.addRow(new Object[] {
                    e.getId_emp(),
                    e.getCedula(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getFecha(),
                    e.getDireccion(),
                    e.getNombre_user(),
                    e.getId_usuario()

            });

        }

    }

    public void limpiarceldas() {
        admin.txtcedula_emp.setText("");
        admin.txtnombre_emp.setText("");
        admin.txtapellido_emp.setText("");
        admin.txtdireccion_emp.setText("");
        admin.txtfecha_emp.setText("");
        admin.txtnombre_user.setText("");
        admin.txtpassword.setText("");

        admin.btnguardar_emp.setEnabled(true);
        admin.btnmodificar_emp.setEnabled(false);
        admin.btneliminar_emp.setEnabled(false);

    }

    public void limpiartabla() {
        for (int i = 0; i < modelotabla.getRowCount(); i++) {
            modelotabla.removeRow(i);
            i = i - 1;

        }
    }

    public void cargarbusqueda(String valor) {
        limpiartabla();
        List<Empleados> empleado = empleadosdao.buscarempleEmpleados(valor);
        modelotabla = (DefaultTableModel) admin.jTable_emp.getModel();

        for (Empleados e : empleado) {
            modelotabla.addRow(new Object[] {
                    e.getId_emp(),
                    e.getCedula(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getFecha(),
                    e.getDireccion(),
                    e.getNombre_user(),
                    e.getId_usuario()

            });

        }

    }

}
