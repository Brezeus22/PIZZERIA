/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import IGU.Menu2;
import Models.Clientes;
import Models.ClientesDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Personal
 */
public class ClientesController implements ActionListener, MouseListener, KeyListener {

    private Clientes clientes;
    private ClientesDAO clientesdao;
    private Menu2 admin;
    private DefaultTableModel modeloTabla;
    private int idClienteSeleccionado = 0;

    public ClientesController(Clientes clientes, ClientesDAO clientesdao, Menu2 admin) {
        this.clientes = clientes;
        this.clientesdao = clientesdao;
        this.admin = admin;

        // registrar
        this.admin.btnguardar_clliente.addActionListener(this);
        // tabla
        this.admin.table_cliente.addMouseListener(this);
        // buscar
        this.admin.txtbuscar_cliente.addKeyListener(this);
        // modificar
        this.admin.btnmodificar_cliente.addActionListener(this);
        // eliminar
        this.admin.btneliminar_cliente.addActionListener(this);
        // validacion
        this.admin.txtnom_cliente.addKeyListener(this);
        this.admin.txtapellido_clliente.addKeyListener(this);
        this.admin.txtcedula_cliente.addKeyListener(this);
        this.admin.txttelefono_cliente.addKeyListener(this);
        this.admin.txtdireccion_cliente.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == admin.btnguardar_clliente) {
            try {
                if (admin.txtnom_cliente.getText().equals("") || admin.txtapellido_clliente.getText().equals("")
                        || admin.txtcedula_cliente.getText().equals("")
                        || admin.txttelefono_cliente.getText().equals("")
                        || admin.txtdireccion_cliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");

                } else {
                    clientes.setNombre(admin.txtnom_cliente.getText().trim());
                    clientes.setApellido(admin.txtapellido_clliente.getText().trim());
                    clientes.setCedula(admin.txtcedula_cliente.getText());
                    clientes.setTelefono(admin.txttelefono_cliente.getText());
                    clientes.setDireccion(admin.txtdireccion_cliente.getText().trim());

                    if (admin.txtcedula_cliente.getText().length() < 7
                            || admin.txtcedula_cliente.getText().length() > 9) {
                        JOptionPane.showMessageDialog(null, "La cédula debe tener entre 7 y 9 dígitos");
                    } else if (clientesdao.existeCedula(admin.txtcedula_cliente.getText())) {
                        JOptionPane.showMessageDialog(null, "La cédula ya está registrada.");
                    } else if (clientesdao.registrarcliente(clientes)) {

                        inicializartabla();
                        cargartabla();
                        limpiarceldas();
                        JOptionPane.showMessageDialog(null, "El cliente ha sido registrado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el cliente");

                    }

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La cedula no cumple el Formato", "error de formato",
                        JOptionPane.ERROR_MESSAGE);

            }

        } else if (e.getSource() == admin.btnmodificar_cliente) {
            try {
                if (admin.txtnom_cliente.getText().equals("") || admin.txtapellido_clliente.getText().equals("")
                        || admin.txtcedula_cliente.getText().equals("")
                        || admin.txttelefono_cliente.getText().equals("")
                        || admin.txtdireccion_cliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");

                } else {
                    clientes.setId_cliente(idClienteSeleccionado);
                    clientes.setNombre(admin.txtnom_cliente.getText().trim());
                    clientes.setApellido(admin.txtapellido_clliente.getText().trim());
                    clientes.setCedula(admin.txtcedula_cliente.getText());
                    clientes.setTelefono(admin.txttelefono_cliente.getText());
                    clientes.setDireccion(admin.txtdireccion_cliente.getText().trim());

                    if (admin.txtcedula_cliente.getText().length() < 7
                            || admin.txtcedula_cliente.getText().length() > 9) {
                        JOptionPane.showMessageDialog(null, "La cédula debe tener entre 7 y 9 dígitos");
                    } else if (clientesdao.modificarcliente(clientes)) {

                        inicializartabla();
                        cargartabla();
                        limpiarceldas();

                        JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar el cliente");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar valores válidos", "error de formato",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == admin.btneliminar_cliente) {
            int filaseleccionada = admin.table_cliente.getSelectedRow();
            if (filaseleccionada >= 0) {
                int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el cliente?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    if (clientesdao.eliminarcliente(idClienteSeleccionado)) {
                        inicializartabla();
                        cargartabla();
                        limpiarceldas();
                        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");

                    }
                } else {
                    inicializartabla();
                    cargartabla();
                    limpiarceldas();

                }

            }
        }
    }

    public void inicializartabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("id");
        modeloTabla.addColumn("nombre");
        modeloTabla.addColumn("apellido");
        modeloTabla.addColumn("cedula");
        modeloTabla.addColumn("telefono");
        modeloTabla.addColumn("direccion");
        admin.table_cliente.setModel(modeloTabla);

    }

    public void cargartabla() {
        modeloTabla.setRowCount(0);
        List<Clientes> clientes = clientesdao.obtenertodoslosclientes();
        for (Clientes c : clientes) {
            modeloTabla.addRow(new Object[] {
                    c.getId_cliente(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getCedula(),
                    c.getTelefono(),
                    c.getDireccion(), });

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fliasaleccionada = admin.table_cliente.getSelectedRow();

        if (fliasaleccionada >= 0) {
            admin.btnguardar_clliente.setEnabled(false);
            admin.btnmodificar_cliente.setEnabled(true);
            admin.btneliminar_cliente.setEnabled(true);

            idClienteSeleccionado = (int) modeloTabla.getValueAt(fliasaleccionada, 0);
            admin.txtnom_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 1).toString());
            admin.txtapellido_clliente.setText(modeloTabla.getValueAt(fliasaleccionada, 2).toString());
            admin.txtcedula_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 3).toString());
            admin.txttelefono_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 4).toString());
            admin.txtdireccion_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 5).toString());

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
        if (e.getSource() == admin.txtnom_cliente || e.getSource() == admin.txtapellido_clliente) {
            if (Character.isDigit(c)) {
                e.consume();
                java.awt.Toolkit.getDefaultToolkit().beep();

            }

        } else if (e.getSource() == admin.txttelefono_cliente || e.getSource() == admin.txtcedula_cliente) {
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
        if (e.getSource() == admin.txtbuscar_cliente) {
            String busqueda = admin.txtbuscar_cliente.getText().trim();
            if (busqueda.equals("")) {
                limpiartabla();
                cargartabla();

            } else {
                cargarBusqueda(busqueda);
            }

        }
    }

    public void cargarBusqueda(String valor) {
        limpiartabla();
        List<Clientes> cliente = clientesdao.buscarClientes(valor);
        modeloTabla = (DefaultTableModel) admin.table_cliente.getModel();

        for (Clientes c : cliente) {
            modeloTabla.addRow(new Object[] {
                    c.getId_cliente(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getCedula(),
                    c.getTelefono(),
                    c.getDireccion()
            });

        }

    }

    public void limpiartabla() {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            modeloTabla.removeRow(i);
            i = i - 1;

        }
    }

    public void limpiarceldas() {
        admin.txtnom_cliente.setText("");
        admin.txtapellido_clliente.setText("");
        admin.txtcedula_cliente.setText("");
        admin.txttelefono_cliente.setText("");
        admin.txtdireccion_cliente.setText("");
        idClienteSeleccionado = 0;

        admin.btnguardar_clliente.setEnabled(true);
        admin.btnmodificar_cliente.setEnabled(false);
        admin.btneliminar_cliente.setEnabled(false);

    }

}
