package controladores;

import IGU.Menu2;
import Models.Productos;
import Models.ProductosDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductosControllers implements ActionListener, MouseListener, KeyListener {

    private Productos productos;
    private ProductosDAO productosdao;
    private Menu2 admin;
    private DefaultTableModel modeloTabla;

    public ProductosControllers(Productos productos, ProductosDAO productosdao, Menu2 admin) {
        this.productos = productos;
        this.productosdao = productosdao;
        this.admin = admin;

        //registrar
        this.admin.btnguardar_productos.addActionListener(this);
        //tabla
        this.admin.tabla_producto.addMouseListener(this);
        //buscar
        this.admin.txtbuscar__producto.addKeyListener(this);
        //modificar
        this.admin.btnmodificar_productos.addActionListener(this);
        //eliminar
        this.admin.btneliminar_productos.addActionListener(this);
        // validacion
        this.admin.txtnombre_producto.addKeyListener(this);
        this.admin.txtdescripcion.addKeyListener(this);
        this.admin.txtprecio.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == admin.btnguardar_productos) {
            try {
                if (admin.txtnombre_producto.getText().equals("") || admin.txtprecio.getText().equals("") || admin.txtprecio.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "debe rellenar todos los campos");

                } else {
                    productos.setNombre(admin.txtnombre_producto.getText().trim());
                    productos.setDescripcion(admin.txtdescripcion.getText().trim());
                    productos.setCategoria(admin.ComboBoxcategoria.getSelectedItem().toString());
                    productos.setTamanio(admin.comboxtamanio.getSelectedItem().toString());
                    productos.setPrecio(Double.parseDouble(admin.txtprecio.getText()));

                    if (productosdao.registrarproducto(productos)) {

                        inicializartabla();
                        cargartabla();
                        limpiarceldas();
                        JOptionPane.showMessageDialog(null, "El producto ha sido registrado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el producto");

                    }

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El precio debe ser un numero valido", "error de formato", JOptionPane.ERROR_MESSAGE);

            }

        } else if (e.getSource() == admin.btnmodificar_productos) {
            try {
                if (admin.txtcodigo.getText().equals("") || admin.txtnombre_producto.getText().equals("") || admin.txtprecio.getText().equals("") || admin.txtprecio.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "debe rellenar todos los campos");

                } else {
                    productos.setCodigo(Integer.parseInt(admin.txtcodigo.getText()));
                    productos.setNombre(admin.txtnombre_producto.getText().trim());
                    productos.setDescripcion(admin.txtdescripcion.getText().trim());
                    productos.setCategoria((String) admin.ComboBoxcategoria.getSelectedItem());
                    productos.setTamanio((String) admin.comboxtamanio.getSelectedItem());
                    productos.setPrecio(Double.parseDouble(admin.txtprecio.getText()));

                    if (productosdao.modificarproducto(productos)) {

                        inicializartabla();
                        cargartabla();
                        limpiarceldas();

                        JOptionPane.showMessageDialog(null, "producto modificado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "error al modificar el producto");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un valor numerico en el precio", "error de formato", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == admin.btneliminar_productos) {
            int filaseleccionada = admin.tabla_producto.getSelectedRow();
            if (filaseleccionada >= 0) {
                int codigo = (int) modeloTabla.getValueAt(filaseleccionada, 0);
                int confirmar = JOptionPane.showConfirmDialog(null, "esta seguro que desea eliminar el producto?", "confirmar eliminacion", JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    if(productosdao.eliminarproducto(codigo)){
                    inicializartabla();
                    cargartabla();
                    limpiarceldas();
                    JOptionPane.showMessageDialog(null, "producto eliminado correctamente");
                
                } else {
                    JOptionPane.showMessageDialog(null, "error al eliminar el producto");

                }
            }
                else{
                    inicializartabla();
                    cargartabla();
                    limpiarceldas();
                
                }

            }
        }
    }

    public void inicializartabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("codigo");
        modeloTabla.addColumn("nombre");
        modeloTabla.addColumn("descripcion");
        modeloTabla.addColumn("categoria");
        modeloTabla.addColumn("tamanio");
        modeloTabla.addColumn("precio");
        admin.tabla_producto.setModel(modeloTabla);

    }

    public void cargartabla() {
        modeloTabla.setRowCount(0);
        List<Productos> productos = productosdao.obtenerproductos();
        for (Productos p : productos) {
            modeloTabla.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria(),
                p.getTamanio(),
                p.getPrecio(),});

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fliasaleccionada = admin.tabla_producto.getSelectedRow();

        if (fliasaleccionada >= 0) {
            admin.btnguardar_productos.setEnabled(false);
            admin.btnmodificar_productos.setEnabled(true);
            admin.btneliminar_productos.setEnabled(true);

            admin.txtcodigo.setText(modeloTabla.getValueAt(fliasaleccionada, 0).toString());
            admin.txtnombre_producto.setText(modeloTabla.getValueAt(fliasaleccionada, 1).toString());
            admin.txtdescripcion.setText(modeloTabla.getValueAt(fliasaleccionada, 2).toString());
            admin.ComboBoxcategoria.setSelectedItem(modeloTabla.getValueAt(fliasaleccionada, 3));
            admin.comboxtamanio.setSelectedItem(modeloTabla.getValueAt(fliasaleccionada, 4));
            admin.txtprecio.setText(modeloTabla.getValueAt(fliasaleccionada, 5).toString());

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
        if(e.getSource() == admin.txtnombre_producto || e.getSource() == admin.txtdescripcion){
            if(Character.isDigit(c)){
                e.consume();
                java.awt.Toolkit.getDefaultToolkit().beep();
            
            }
        
        }
        else if(e.getSource() == admin.txtprecio ){
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
        if (e.getSource() == admin.txtbuscar__producto) {
            String busqueda = admin.txtbuscar__producto.getText().trim();
            if (busqueda.equals("")) {
                limpiartabla();
                cargartabla();

            } else {
                cargarbusqueda(busqueda);
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
        admin.txtcodigo.setText("");
        admin.txtnombre_producto.setText("");
        admin.txtdescripcion.setText("");
        admin.txtprecio.setText("");

        admin.btnguardar_productos.setEnabled(true);
        admin.btnmodificar_productos.setEnabled(false);
        admin.btneliminar_productos.setEnabled(false);

    }

    public void cargarbusqueda(String valor) {
        limpiartabla();
        List<Productos> productos = productosdao.buscarproducto(valor);
        modeloTabla = (DefaultTableModel) admin.tabla_producto.getModel();

        for (Productos p : productos) {
            modeloTabla.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria(),
                p.getTamanio(),
                p.getPrecio(),});

        }

    }

}
