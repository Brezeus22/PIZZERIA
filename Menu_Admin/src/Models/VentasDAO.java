package Models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentasDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int registrarventa(int id_cliente, int id_emp) {
        int id_ventagenerado = 0;

        try {
            con = ConexionSQL.getConnection();
            String sql = "INSERT INTO ventas (fecha, id_cliente, id_emp) VALUES (NOW(), ?, ?)";

            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id_cliente);
            ps.setInt(2, id_emp);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id_ventagenerado = rs.getInt(1);

                }

            }
        } catch (SQLException e) {
            System.err.println("Error al registrar la venta" + e.getMessage());
        }

        return id_ventagenerado;
    }

    public boolean registrardetallefactura(int id_venta, int codigo, int cantidad, double total_costo) {

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
            System.err.println("Error al registrar la factura" + e.getMessage());
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

    public List<Object[]> listarventasdetalles(int id_clientefiltro) {
        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT v.id_venta, "
                + "c.nombre, c.apellido, c.id_cliente, "
                + "v.fecha, "
                + "GROUP_CONCAT(p.nombre SEPARATOR ', ') as productos, "
                + "SUM(f.total_costo) as total_pagado "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "INNER JOIN facturas f ON v.id_venta = f.id_venta "
                + "INNER JOIN productos p ON f.codigo = p.codigo ";

        if (id_clientefiltro > 0) {
            sql += "WHERE v.id_cliente = ? ";

        }

        sql += "GROUP BY v.id_venta ORDER BY v.fecha DESC";

        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);

            if (id_clientefiltro > 0) {
                ps.setInt(1, id_clientefiltro);

            }
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[6];

                fila[0] = rs.getString("nombre") + " " + rs.getString("apellido");
                // 1: Productos concatenados
                fila[1] = rs.getString("productos");
                // 2: Fecha
                fila[2] = rs.getString("fecha");
                // 3: Total Pagado
                fila[3] = rs.getDouble("total_pagado");
                // 4: ID Cliente 
                fila[4] = rs.getInt("id_cliente");
                // 5: ID Venta 
                fila[5] = rs.getInt("id_venta");

                lista.add(fila);

            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las ventas"+ e.getMessage());
        }
        return lista;
    }
}
