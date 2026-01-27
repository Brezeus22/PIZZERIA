package Models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;
import java.sql.Statement;

public class VentasDAO {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public int registrarventa(int id_cliente, int id_emp){
        int id_ventagenerado = 0;
        
        try {
            con = ConexionSQL.getConnection();
            String sql = "INSERT INTO ventas (fecha, id_cliente, id_emp) VALUES (NOW(), ?, ?)";
            
            ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id_cliente);
            ps.setInt(2, id_emp);
            
            int resultado = ps.executeUpdate();
            
            if(resultado > 0){
                rs = ps.getGeneratedKeys();
                if(rs.next()){
                    id_ventagenerado = rs.getInt(1);
                
                }
            
            
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar la venta"+ e.getMessage());
        }
    
    
    return id_ventagenerado;
    }
    
    public boolean registrardetallefactura(int id_venta, int codigo, int cantidad, double total_costo){
        
        try {
            con = ConexionSQL.getConnection();
            
            String sql = "INSERT INTO facturas (cantidad, total_costo, id_venta, codigo) VALUES (?, ?, ?, ?)";
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setDouble(2, total_costo);
            ps.setInt(3, id_venta);
            ps.setInt(4, codigo);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar la factura"+e.getMessage());
            return false;
        }
    
    
    
    }
    
        // Metodo para contar el total de ventas realizadas
    public int contarVentas() {
        String sql = "SELECT COUNT(*) FROM ventas";
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar ventas: " + e.getMessage());
        }
        return 0;
    }
}

