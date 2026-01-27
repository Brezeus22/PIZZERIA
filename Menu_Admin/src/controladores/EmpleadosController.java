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

    public EmpleadosController(Usuarios usuario, UsuariosDAO usuariodao, Empleados empleados, EmpleadosDAO empleadosdao,
            Menu2 admin) {
        this.usuario = usuario;
        this.usuariodao = usuariodao;
        this.empleados = empleados;
        this.empleadosdao = empleadosdao;
        this.admin = admin;

        // registrar
        this.admin.btnguardar_emp.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == admin.btnguardar_emp) {
            try {
                if (admin.txtcedula_emp.getText().equals("") || admin.txtnombre_emp.getText().equals("")
                        || admin.txtapellido_emp.getText().equals("")
                        || admin.txtdireccion_emp.getText().equals("") || admin.txtedad_emp.getText().equals("")
                        || admin.txtnombre_user.getText().equals("")
                        || admin.txtpassword.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");

                } else {
                    empleados.setCedula(admin.txtcedula_emp.getText().trim());
                    empleados.setNombre(admin.txtnombre_emp.getText().trim());
                    empleados.setApellido(admin.txtapellido_emp.getText().trim());
                    empleados.setDireccion(admin.txtdireccion_emp.getText().trim());
                    empleados.setEdad(Integer.parseInt(admin.txtedad_emp.getText()));
                    usuario.setRol(admin.ComboBox_Rol.getSelectedItem().toString());
                    usuario.setNombre_user(admin.txtnombre_user.getText().trim());
                    usuario.setPassword(admin.txtpassword.getText().trim());

                    if (empleadosdao.existeCedula(admin.txtcedula_emp.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "La cédula ya está registrada.");
                    } else if (empleadosdao.registrarempleado(empleados)) {
                        inicializartabla();
                        cargartabla();
                        JOptionPane.showMessageDialog(null, "Empleado registrado con exito");

                    }

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error al registrar el empleado");
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void inicializartabla() {
        modelotabla = new DefaultTableModel();
        modelotabla.addColumn("cedula");
        modelotabla.addColumn("nombre");
        modelotabla.addColumn("apellido");
        modelotabla.addColumn("edad");
        modelotabla.addColumn("direccion");
        admin.jTable_emp.setModel(modelotabla);

    }

    public void cargartabla() {
        modelotabla.setRowCount(0);
        List<Empleados> empleado = empleadosdao.listaempleados();
        for (Empleados e : empleado) {
            modelotabla.addRow(new Object[] {
                    e.getCedula(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getEdad(),
                    e.getDireccion()

            });

        }

    }

}
