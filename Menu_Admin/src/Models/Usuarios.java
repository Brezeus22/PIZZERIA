package Models;

public class Usuarios {
    private int id_usuario;
    private String rol;
    private String nombre_user;
    private String password;
    private int id_emp;

    public Usuarios() {
    }

    public Usuarios(int id_usuario, String rol, String nombre_user, String password, int id_emp) {
        this.id_usuario = id_usuario;
        this.rol = rol;
        this.nombre_user = nombre_user;
        this.password = password;
        this.id_emp = id_emp;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre_user() {
        return nombre_user;
    }

    public void setNombre_user(String nombre_user) {
        this.nombre_user = nombre_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
