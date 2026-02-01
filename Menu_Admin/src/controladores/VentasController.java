package controladores;

import IGU.Menu2;
import Models.Clientes;
import Models.ClientesDAO;
import Models.VentasDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class VentasController implements ActionListener, MouseListener, KeyListener {

    private VentasDAO ventasDao;
    private ClientesDAO clientesDao;
    private Menu2 admin;
    private DefaultTableModel modelo;

    public VentasController(VentasDAO ventasDao, ClientesDAO clientesDao, Menu2 admin) {
        this.ventasDao = ventasDao;
        this.clientesDao = clientesDao;
        this.admin = admin;

        this.admin.jComboBox_cliente.addActionListener(this);
        this.admin.jTable_ventas.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == admin.jComboBox_cliente) {
            // Obtener el cliente seleccionado
            ClienteItem item = (ClienteItem) admin.jComboBox_cliente.getSelectedItem();
            if (item != null) {
                // Filtrar tabla por el ID del cliente seleccionado
                listarVentas(item.getId());
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaseleccionada = admin.jTable_ventas.getSelectedRow();

        if (filaseleccionada >= 0) {
            String nombreCliente = admin.jTable_ventas.getValueAt(filaseleccionada, 0).toString();
            String fecha = admin.jTable_ventas.getValueAt(filaseleccionada, 2).toString();
            String total = admin.jTable_ventas.getValueAt(filaseleccionada, 3).toString();
            
            admin.txt_fecha.setText(fecha);
            admin.txt_total_pagar.setText(total);
            
            seleccionarClienteEnCombo(nombreCliente);

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
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void iniciar() {
        llenarComboClientes();
        listarVentas(0); // 0 = Traer todos sin filtro
    }
    
    
    private void llenarComboClientes() {
        List<Clientes> lista = clientesDao.obtenertodoslosclientes();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        // Agregamos una opción por defecto
        model.addElement(new ClienteItem(0, "Todos los clientes"));
        
        for (Clientes c : lista) {
            // Usamos una clase auxiliar para guardar ID y Nombre juntos
            model.addElement(new ClienteItem(c.getId_cliente(), c.getNombre() + " " + c.getApellido()));
        }
        admin.jComboBox_cliente.setModel(model);
    }
    
    private void listarVentas(int id_cliente) {
        List<Object[]> ventas = ventasDao.listarventasdetalles(id_cliente);
        modelo = (DefaultTableModel) admin.jTable_ventas.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Object[] v : ventas) {
            // Solo agregamos las columnas visibles: Nombre, Producto, Fecha, Total
            modelo.addRow(new Object[]{v[0], v[1], v[2], v[3]});
        }
    }
    
    private void seleccionarClienteEnCombo(String nombreCompleto) {
        // Evitamos que se dispare el evento del actionListener al cambiar programáticamente
        admin.jComboBox_cliente.removeActionListener(this);
        
        for (int i = 0; i < admin.jComboBox_cliente.getItemCount(); i++) {
            Object item = admin.jComboBox_cliente.getItemAt(i);
            if (item.toString().equals(nombreCompleto)) {
                admin.jComboBox_cliente.setSelectedIndex(i);
                break;
            }
        }
        
        // Reactivamos el listener
        admin.jComboBox_cliente.addActionListener(this);
    }
    
    class ClienteItem {
        private int id;
        private String nombre;

        public ClienteItem(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() { return id; }
        
        @Override
        public String toString() {
            return nombre; // Esto es lo que se ve en el ComboBox
        }
    }

}
