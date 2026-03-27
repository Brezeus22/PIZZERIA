//package controladores;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.*;
//import IGU.Menu2.java;
//
//    
//
//    public class AdminController extends JFrame {
//    // El panel que contendrá a los demás
//    JPanel panel_admin;
//    CardLayout cardLayout;
//    
//    public AdminController() {
//        setLayout(new BorderLayout());
//
//        // 1. Creamos el Layout y el contenedor
//        cardLayout = new CardLayout();
//        panel_admin = new JPanel(cardLayout);
//
//        // 2. Creamos los paneles que queremos mostrar
//        JPanel panelHome = new JPanel();
//
//        JPanel panelVentas = new JPanel();
//
//        // 3. Los añadimos al contenedor principal con un NOMBRE (Key)
//         panel_admin.add(panelHome, "home");
//         panel_admin.add(panelVentas, "ventas");
////        Menu2.add(panelPerfil, "clientes");
////        Menu2.add(panelPerfil, "empleados");
////        Menu2.add(panelPerfil, "productos");
//
//        // 4. Creamos los botones del menú
//        JButton jButton_HOME = new JButton("Ir a Inicio");
//        JButton jButton_VENTAS = new JButton("Ir a Perfil");
//
//        // 5. Configurar las acciones (Aquí es donde evitamos el switch)
//        jButton_HOME.addActionListener(e -> cardLayout.show( panel_admin, "inicio"));
//        jButton_VENTAS.addActionListener(e -> cardLayout.show( panel_admin, "perfil"));
//
//        // Organizar la interfaz
//        JPanel menuLateral = new JPanel();
//        menuLateral.add(jButton_HOME);
//        menuLateral.add(jButton_VENTAS);
//
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AdminController().setVisible(true));
//    }
//}
