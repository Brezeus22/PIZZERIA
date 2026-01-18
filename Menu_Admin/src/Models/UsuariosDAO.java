package Models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import Conexion_MySQL.ConexionSQL;
import java.sql.SQLException;

public class UsuariosDAO {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public int  registrarusuarios(Usuarios usuario){
        String sql = "INSERT INTO usuarios(rol, nombre_user, password) VALUES(?,?,?)";
        int idgenerado = 0;
        
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getRol());
            ps.setString(2, usuario.getNombre_user());
            ps.setString(3,usuario.getPassword());
           int filas = ps.executeUpdate();
           
           if(filas > 0){
               rs = ps.getGeneratedKeys();
               if(rs.next()){
                   idgenerado = rs.getInt(1);
               }
           }
           
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario: "+ e.getMessage());
        }
        return idgenerado;
    
    }
    
    //metodo de login 
    public Usuarios login(String nombre_user, String password){
        Usuarios usuario = null;
        String sql = "SELECT* FROM usuarios WHERE nombre_user = ? AND password = ?";
        
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre_user);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if(rs.next()){
                usuario = new Usuarios();
                usuario.setId_usuario( rs.getInt("id_usuario"));
                usuario.setRol(rs.getString("rol"));
                usuario.setNombre_user(rs.getString("nombre_user"));
                usuario.setPassword(rs.getString("password"));
            
            }
        } catch (SQLException e) {
            System.err.println("Error al iniciar sesion: "+ e.getMessage());
        }
        return usuario;
    
    }
    
    
    // metodo para modificar un usuario
    public boolean modificarusuario(Usuarios usuario){
        String sql = "UPDATE usuarios SET nombre_user = ?, password = ? WHERE id_usuario = ?";
        
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombre_user());
            ps.setString(2, usuario.getPassword());
            ps.setInt(3, usuario.getId_usuario());
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar al usuario: "+e.getMessage());
            return false;
        }
    
    
    }
    
    //metodo para eliminar un usuario
    public boolean eliminarusuario(int id_usuario){
        String sql = "DELETE FROM usuarios WHERE id_usuario";
        
        try {
            con = ConexionSQL.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: "+ e.getMessage());
            return false;
        }
    
    }
}
