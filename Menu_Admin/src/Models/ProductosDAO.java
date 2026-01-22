package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;

public class ProductosDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //metodo para leer todos los productos
    public List<Productos> obtenerproductos() {
        List<Productos> productos = new ArrayList();

        String sql = "SELECT* FROM productos";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Productos objP = new Productos();
                objP.setCodigo(rs.getInt("codigo"));
                objP.setNombre(rs.getString("nombre"));
                objP.setDescripcion(rs.getString("descripcion"));
                objP.setCategoria(rs.getString("categoria"));
                objP.setTamanio(rs.getString("tamanio"));
                objP.setPrecio(rs.getDouble("precio"));
                productos.add(objP);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los productos: " + e.getMessage());
        }
        return productos;
    }

    public List<String> filtarcategoria(String categoria) {
        List<String> producto = new ArrayList<>();
        String sql = "SELECT DISTINCT nombre FROM productos WHERE categoria = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria);
            rs = ps.executeQuery();
            while (rs.next()) {
//                Productos objP = new Productos();
//
//                objP.setNombre(rs.getString("nombre"));
//                objP.setTamanio(rs.getString("tamanio"));
//                objP.setPrecio(rs.getDouble("precio"));
                producto.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error filtrando productos: "+e.getMessage());
        }
        return producto;
    }
    
    //metodo para obtener el precio
    public double obtenerPrecio(String nombre, String tamanio) {
        double precio = 0.0;
        String sql = "SELECT precio FROM productos WHERE nombre = ? AND tamanio = ?";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, tamanio);
            rs = ps.executeQuery();
            if (rs.next()) {
                precio = rs.getDouble("precio");
            }
        } catch (SQLException e) {
            System.err.println("error al obtener el precio del producto"+e.getMessage());
        }
        return precio;
    }

    //metodo para leer un unico producto
    public List<Productos> buscarproducto(String valor) {
        List<Productos> productos = new ArrayList<>();
        Productos producto = null;
        String sql = "SELECT* FROM productos WHERE nombre LIKE ? OR categoria LIKE ? OR codigo LIKE ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,"%" + valor + "%" );
            ps.setString(2, "%" + valor + "%");
            ps.setString(3, "%" + valor + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                producto = new  Productos();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setTamanio(rs.getString("tamanio"));
                producto.setPrecio(rs.getDouble("precio"));
                productos.add(producto);

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto: " + e.getMessage());
        }
        return productos;

    }

    //metodo para registrar un producto
    public boolean registrarproducto(Productos producto) {
        String sql = "INSERT INTO productos(nombre, descripcion, categoria, tamanio, precio) VALUES(?,?,?,?,?)";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getCategoria());
            ps.setString(4, producto.getTamanio());
            ps.setDouble(5, producto.getPrecio());
            ps.execute();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
            return false;
        }

    }

    //metodo para modificar un producto
    public boolean modificarproducto(Productos producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion =?, categoria = ?, tamanio = ?, precio = ? WHERE codigo = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getCategoria());
            ps.setString(4, producto.getTamanio());
            ps.setDouble(5, producto.getPrecio());
            ps.setInt(6, producto.getCodigo());
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error al modificar el producto: " + e.getMessage());
            return false;
        }
    }

    //metodo para eliminar un producto
    public boolean eliminarproducto(int codigo) {
        String sql = "DELETE FROM productos WHERE codigo = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }

    }

}
