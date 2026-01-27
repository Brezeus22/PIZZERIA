package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Conexion_MySQL.ConexionSQL;

public class VentasDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

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
