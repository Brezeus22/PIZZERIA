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
    ResultSet rs = null;

    // metodo para leer todos los empleados
    public List<Empleados> listaempleados() {
        List<Empleados> empleados = new ArrayList();
        String sql = "SELECT* FROM empleados";

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
                objE.setEdad(rs.getInt("edad"));
                objE.setDireccion(rs.getString("direccion"));

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
                empleado.setEdad(rs.getInt("edad"));
                empleado.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar al empleado: " + e.getMessage());
        }
        return empleado;
    }

    //metodo para registrar un empleado
    public boolean registrarempleado(Empleados empleado) {
        String sql = "INSERT INTO empleados(cedula, nombre, apellido, edad, direccion, id_usuario) VALUES(?,?,?,?,?,?)";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setInt(4, empleado.getEdad());
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
        String sql = "UPDATE empleados SET cedula = ?, nombre = ?, apellido = ?, edad = ?, direccion = ? WHERE id_emp =?";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setInt(4, empleado.getEdad());
            ps.setString(5, empleado.getDireccion());
            ps.setInt(6, empleado.getId_emp());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al modificar al empleado: "+e.getMessage());
            return false;
        }

    }
    
    //metodo para eliminar un empleado
    public boolean eliminarempleado(int id_emp){
        String sql = "DELETE FROM empleados WHERE id_emp = ?";
        
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_emp);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar al empleado: "+e.getMessage());
            return false;
        }
    
    }
}
