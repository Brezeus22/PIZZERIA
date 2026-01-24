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
    
      public ClientesController(Clientes clientes, ClientesDAO clientesdao, Menu2 admin) {
        this.clientes = clientes;
        this.clientesdao = clientesdao;
        this.admin = admin;

        //registrar
    this.admin.btnguardar_clliente.addActionListener(this);
        //tabla
        this.admin.table_cliente.addMouseListener(this);
        //buscar
        this.admin.txtbuscar_cliente.addKeyListener(this);
        //modificar
        this.admin.btnmodificar_cliente.addActionListener(this);
        //eliminar
        this.admin.btneliminar_cliente.addActionListener(this);
        // validacion
        this.admin.txtnombre_cliente.addKeyListener(this);
        this.admin.txtapellido_clliente.addKeyListener(this);
        this.admin.txtcedula_cliente.addKeyListener(this);
    }
     public void actionPerformed(ActionEvent e) {
     
          if (e.getSource() == admin.btnguardar_clliente) {
            try {
                if (admin.txtnom_cliente.getText().equals("")|| admin.txtapellido_clliente.getText().equals("")|| admin.txtcedula_cliente.getText().equals("")|| admin.txttelefono_cliente.getText().equals("") || admin.txtdireccion_cliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");

                } else {
                    clientes.setNombre(admin.txtnom_cliente.getText().trim());
                    clientes.setApellido(admin.txtapellido_clliente.getText().trim());
                    clientes.setCedula(admin.txtcedula_cliente.getText());
                    clientes.setTelefono(admin.txttelefono_cliente.getText());
                    clientes.setDireccion(admin.txtdireccion_cliente.getText().trim());
                    
        

                    if (clientesdao.registrarcliente(clientes)) {
                        
                        inicializartabla();
                        cargartabla();
                        limpiarceldas();
                        JOptionPane.showMessageDialog(null, "El producto ha sido registrado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el producto");

                    }

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La cedula no cumple el Formato", "error de formato", JOptionPane.ERROR_MESSAGE);

            }

        } 
     
     }

    public void inicializartabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("nombre");
        modeloTabla.addColumn("apellido");
        modeloTabla.addColumn("cedula");
        modeloTabla.addColumn("telefono");
        modeloTabla.addColumn("direccion");
        admin.tabla_producto.setModel(modeloTabla);

    }

    public void cargartabla() {
        modeloTabla.setRowCount(0);
        List<Clientes> clientes = clientesdao.obtenertodoslosclientes();
        for (Clientes c : clientes) {
            modeloTabla.addRow(new Object[]{
                c.getNombre(),
                c.getApellido(),
                c.getCedula(),
                c.getTelefono(),
                c.getDireccion(),});

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fliasaleccionada = admin.table_cliente.getSelectedRow();

        if (fliasaleccionada >= 0) {
            admin.btnguardar_clliente.setEnabled(false);
            admin.btnmodificar_cliente.setEnabled(true);
            admin.btneliminar_cliente.setEnabled(true);

           
            admin.txtnom_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 0).toString());
            admin.txtapellido_clliente.setText(modeloTabla.getValueAt(fliasaleccionada, 1).toString());
            admin.txtcedula_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 2).toString());
            admin.txttelefono_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 3).toString());
            admin.txtdireccion_cliente.setText(modeloTabla.getValueAt(fliasaleccionada, 4).toString());

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
        if(e.getSource() == admin.txtnom_cliente || e.getSource() == admin.txtapellido_clliente ||e.getSource() == admin.txtdireccion_cliente){
            if(Character.isDigit(c)){
                e.consume();
                java.awt.Toolkit.getDefaultToolkit().beep();
            
            }
        
        }
        else if(e.getSource() == admin.txttelefono_cliente || e.getSource() == admin.txtcedula_cliente ){
            if(!Character.isDigit(c) && c != '.'){
                e.consume();
            }
        
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == admin.txtbuscar_cliente) {
            String busqueda = admin.txtbuscar__producto.getText().trim();
            if (busqueda.equals("")) {
                limpiartabla();
                cargartabla();

            } else {
            
            }

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

        admin.btnguardar_clliente.setEnabled(true);
        admin.btnmodificar_cliente.setEnabled(false);
        admin.btneliminar_cliente.setEnabled(false);

    }


       
}
