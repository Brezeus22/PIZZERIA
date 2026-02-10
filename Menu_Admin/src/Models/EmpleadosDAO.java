package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;

public class EmpleadosDAO {

    Connection con = null;
    PreparedStatement ps = null;
    PreparedStatement psuser = null;
    ResultSet rs = null;

    // metodo para leer todos los empleados
    public List<Empleados> listaempleados() {
        List<Empleados> empleados = new ArrayList();
        String sql = " SELECT e.*, u.nombre_user AS nombre_user FROM empleados e INNER JOIN usuarios u ON e.id_usuario = u.id_usuario ORDER BY e.id_emp ASC;";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Empleados objE = new Empleados();
                objE.setId_emp(rs.getInt("id_emp"));
                objE.setCedula(rs.getString("cedula"));
                objE.setNombre(rs.getString("nombre"));
                objE.setApellido(rs.getString("apellido"));
                objE.setFecha(rs.getInt("fecha"));
                objE.setDireccion(rs.getString("direccion"));
                objE.setId_usuario(rs.getInt("id_usuario"));
                objE.setNombre_user(rs.getString("nombre_user"));

                empleados.add(objE);

            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar a los empleados: " + e.getMessage());
        }
        return empleados;

    }

    //metodo para leer un unico empleado
    public Empleados mostrarempleado(int id_emp) {
        Empleados empleado = new Empleados();
        String sql = "SELECT* FROM empleados WHERE id_emp = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                empleado.setId_emp(rs.getInt("id_emp"));
                empleado.setCedula(rs.getString("cedula"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apllido"));
                empleado.setFecha(rs.getInt("fecha"));
                empleado.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar al empleado: " + e.getMessage());
        }
        return empleado;
    }

    //metodo para registrar un empleado
    public boolean registrarempleado(Empleados empleado) {
        String sql = "INSERT INTO empleados(cedula, nombre, apellido, fecha, direccion, id_usuario) VALUES(?,?,?,?,?,?)";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setInt(4, empleado.getFecha());
            ps.setString(5, empleado.getDireccion());
            ps.setInt(6, empleado.getId_usuario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar el empleado: " + e.getMessage());
            return false;
        }

    }

    //metodo para modificar un empleado 
    public boolean modificarempleado(Empleados empleado) {
        String sql = "UPDATE empleados SET cedula = ?, nombre = ?, apellido = ?, fecha = ?, direccion = ? WHERE id_emp =?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setInt(4, empleado.getFecha());
            ps.setString(5, empleado.getDireccion());
            ps.setInt(6, empleado.getId_emp());
            ps.executeUpdate();

            String sqlUser = "UPDATE usuarios SET nombre_user=? ";
            if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
                sqlUser += ", password=? ";
            }
            sqlUser += "WHERE id_usuario=?";

            psuser = con.prepareStatement(sqlUser);
            psuser.setString(1, empleado.getNombre_user());

            int index = 2;
            if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
                psuser.setString(index++, empleado.getPassword());
            }
            psuser.setInt(index, empleado.getId_usuario());
            psuser.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al modificar al empleado: " + e.getMessage());
            return false;
        }

    }

    //metodo para eliminar un empleado
    public boolean eliminarempleado(int id_emp, int id_usuario) {
        String sql = "DELETE FROM empleados WHERE id_emp = ?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_emp);
            ps.execute();
            //return true;

            String sqlUser = "DELETE FROM usuarios WHERE id_usuario = ?";
            psuser = con.prepareStatement(sqlUser);
            psuser.setInt(1, id_usuario);
            psuser.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar al empleado: " + e.getMessage());
            return false;
        }

    }

    public List<Empleados> buscarempleEmpleados(String valor) {
        List<Empleados> empleados = new ArrayList<>();
        String sql = "SELECT e.*, u.nombre_user "
                + "FROM empleados e "
                + "INNER JOIN usuarios u ON e.id_usuario = u.id_usuario "
                + "WHERE e.nombre LIKE ? OR e.cedula LIKE ? OR e.fecha LIKE ?";
        Empleados empleado = null;

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + valor + "%");
            ps.setString(2, "%" + valor + "%");
            ps.setString(3, "%" + valor + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                empleado = new Empleados();
                empleado.setId_emp(rs.getInt("id_emp"));
                empleado.setCedula(rs.getString("cedula"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setFecha(rs.getInt("fecha"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setNombre_user(rs.getString("nombre_user"));
                empleado.setId_usuario(rs.getInt("id_usuario"));
                empleados.add(empleado);

            }

        } catch (SQLException e) {
            System.err.println("error al encontrar el empleado" + e.getMessage());
        }

        return empleados;
    }
    
    // metodo para contar empleados 
    public int contarEmpleados() {
        String sql = "SELECT COUNT(*) FROM empleados";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar empleados: " + e.getMessage());
        }
        return 0;
    }
    
    // metodo para verificar si existe cedula
    public boolean existeCedula(String cedula) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE cedula = ?";
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
    
    
}
