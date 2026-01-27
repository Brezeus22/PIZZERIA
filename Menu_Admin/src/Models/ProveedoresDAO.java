package Models;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProveedoresDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // metodo para leer todos los proveedores
    public List<Proveedores> obtenertodoslosProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();

        String sql = "SELECT* FROM proveedores";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedores objP = new Proveedores();
                objP.setId_proveedor(rs.getInt("id_proveedor"));
                objP.setNombre(rs.getString("nombre"));
                objP.setTelefono(rs.getInt("telefono"));
                objP.setDireccion(rs.getString("direccion"));
                objP.setDescripcion(rs.getString("descripcion"));

                proveedores.add(objP);

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los proveedores: " + e.getMessage());
        }
        return proveedores;

    }

    // metodo para leer un proveedor
    public Proveedores obtenerProveedor(int id_proveedor) {
        Proveedores Proveedor = null;
        String sql = "SELECT* FROM proveedores WHERE id_proveedor = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            rs = ps.executeQuery();

            if (rs.next()) {
                Proveedor = new Proveedores();
                Proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                Proveedor.setNombre(rs.getString("nombre"));
                Proveedor.setTelefono(rs.getInt("telefono"));
                Proveedor.setDireccion(rs.getString("direccion"));
                Proveedor.setDescripcion(rs.getString("descripcion"));

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el proveedor por ID: " + e.getMessage());
        }
        return Proveedor;

    }

    // metodo para registrar a los proveedores
    public boolean registrarProveedor(Proveedores Proveedores) {
        String sql = "INSERT INTO proveedores(nombre, telefono, direccion, descripcion) VALUES(?,?,?,?)";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, Proveedores.getNombre());
            ps.setInt(2, Proveedores.getTelefono());
            ps.setString(3, Proveedores.getDireccion());
            ps.setString(4, Proveedores.getDescripcion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al proveedor" + e);
            return false;
        }

    }

    // metodo para modificar un proveedor
    public boolean modificarproveedor(Proveedores proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, telefono = ?, direccion = ?, descripcion =? WHERE id_proveedor = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setInt(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getDescripcion());
            ps.setInt(5, proveedor.getId_proveedor());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar datos de el Proveedor: " + e.getMessage());
            return false;
        }

    }

    // metodo para eliminar proveedores
    public boolean eliminarproveedor(int id_proveedor) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el proveedor" + e.getMessage());
            return false;
        }
    }

    // metodo para contar proveedores
    public int contarProveedores() {
        String sql = "SELECT COUNT(*) FROM proveedores";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar proveedores: " + e.getMessage());
        }
        return 0;
    }
}
