package Conexion_MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionSQL {
    private static final String URL = "jdbc:mysql://localhost:3306/pizzeria_bd";
    private static final String USER = "root"; // Usuario por defecto de XAMPP MySQL
    private static final String PASSWORD = "";
    
    
    public static Connection getConnection(){
        
        Connection connection=null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexion exitosa con la base de datos");
        } catch (ClassNotFoundException e) {
             JOptionPane.showMessageDialog(null, "Error: No se encontró el driver de la base de datos. " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
             System.err.println("Error driver no encontrado"+e.getMessage());
        } catch (SQLException e) {
               JOptionPane.showMessageDialog(null, "Error al conectar la base de datos. "+ e.getMessage(),"Error de conexion ",JOptionPane.ERROR_MESSAGE);
               System.err.println("Error al conectar la base de datos "+e.getMessage());
        }
        return connection;
    } 
    
     public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}
