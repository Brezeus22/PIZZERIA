package Models;

import java.sql.Connection;
import Conexion_MySQL.ConexionSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // metodo para leer todos los clientes
    public List<Clientes> obtenertodoslosclientes() {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT* FROM clientes";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Clientes objC = new Clientes();
                objC.setId_cliente(rs.getInt("id_cliente"));
                objC.setCedula(rs.getString("cedula"));
                objC.setNombre(rs.getString("nombre"));
                objC.setApellido(rs.getString("apellido"));
                objC.setTelefono(rs.getString("telefono"));
                objC.setDireccion(rs.getString("direccion"));

                clientes.add(objC);

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los clientes: " + e.getMessage());
        }
        return clientes;

    }

    // metodo para leer un cliente
    public Clientes obtenerCliente(int id_cliente) {
        Clientes cliente = null;
        String sql = "SELECT* FROM clientes WHERE id_cliente = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_cliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Clientes();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));

            }
        } catch (SQLException e) {
            System.err.println("Error al obtener al cliente por el ID" + e.getMessage());

        }
        return cliente;
    }

    // metodo para buscar por cedula
    public Clientes buscarcedula(String cedula) {
        String sql = "SELECT* FROM clientes WHERE cedula = ?";
        Clientes cliente = null;

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Clientes();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));

            }
        } catch (SQLException e) {
            System.err.println("Error al buscar al cliente: " + e.getMessage());
        }
        return cliente;

    }

    // metodo para registrar un cliente
    public boolean registrarcliente(Clientes cliente) {
        String sql = "INSERT INTO clientes(cedula, nombre, apellido, telefono, direccion) VALUES(?,?,?,?,?)";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.execute();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al registar el nuevo cliente: " + e.getMessage());
            return false;
        }

    }

    // metodo para modificar un cliente
    public boolean modificarcliente(Clientes cliente) {
        String sql = "UPDATE clientes SET cedula = ?, nombre = ?, apellido = ?, telefono = ?, direccion = ? WHERE id_cliente = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getId_cliente());
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error al modificar al cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean existeCedula(String cedula) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ?";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cédula: " + e.getMessage());
        }
        return false;
    }

    // metodo para eliminar un cliente
    public boolean eliminarcliente(int id_cliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_cliente);
            ps.execute();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar al cliente: " + e.getMessage());
            return false;
        }

    }

    // metodo para contar clientes
    public int contarClientes() {
        String sql = "SELECT COUNT(*) FROM clientes";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar clientes: " + e.getMessage());
        }
        return 0;
    }

    // metodo para buscar clientes
    public List<Clientes> buscarClientes(String valor) {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR apellido LIKE ? OR cedula LIKE ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + valor + "%");
            ps.setString(2, "%" + valor + "%");
            ps.setString(3, "%" + valor + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Clientes objC = new Clientes();
                objC.setId_cliente(rs.getInt("id_cliente"));
                objC.setCedula(rs.getString("cedula"));
                objC.setNombre(rs.getString("nombre"));
                objC.setApellido(rs.getString("apellido"));
                objC.setTelefono(rs.getString("telefono"));
                objC.setDireccion(rs.getString("direccion"));

                clientes.add(objC);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes: " + e.getMessage());
        }
        return clientes;
    }
}
