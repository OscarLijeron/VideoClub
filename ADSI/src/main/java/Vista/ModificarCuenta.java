package Vista;

import javax.swing.*;

import Controladores.VideoClub;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarCuenta extends JFrame {
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtContraseña;
    private JTextField txtCorreo;
    private JButton btnModificar;
    private JButton btnVolver;

    public ModificarCuenta(Integer pIdAdmin,Integer pIdUsuario) {
        setTitle("Modificar Datos Cuenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel superior para el título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(35, 41, 122));
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("Modificar Datos Cuenta");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(lblTitulo);

        // Panel central para los campos de texto
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(4, 2, 10, 10));
        contentPane.add(panelCampos, BorderLayout.CENTER);

        // Etiqueta y campo de texto para Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(txtNombre);

        // Etiqueta y campo de texto para Contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setHorizontalAlignment(SwingConstants.RIGHT);
        lblContraseña.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(lblContraseña);

        txtContraseña = new JTextField();
        txtContraseña.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(txtContraseña);

        // Etiqueta y campo de texto para Correo
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampos.add(txtCorreo);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        // Botón para solicitar registro
        btnModificar = new JButton("Actualizar Datos");
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setBackground(new Color(35, 41, 122));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String contraseña = txtContraseña.getText();
                String correo = txtCorreo.getText();

                if (nombre.isEmpty() || contraseña.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                } else {
                    Integer resultado = VideoClub.getGestorGeneral().actualizarDatosPersonales(pIdUsuario,nombre,contraseña,correo);
                    if (resultado==0){
                        JOptionPane.showMessageDialog(null, "Datos actualizados.");
                    }
                    if (resultado==1){
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                    }
                    if (resultado==2){
                        JOptionPane.showMessageDialog(null, "Ya existe una solicitud usuario con esos datos.");
                    }
                    if (resultado==3){
                        JOptionPane.showMessageDialog(null, "Ya existe un usuario con esos datos.");
                    }
                    if (resultado==4){
                        JOptionPane.showMessageDialog(null, "No se han realizado cambios porque los datos son iguales a los que ya tenía.");
                    }
                }
            }
        });
        panelBotones.add(btnModificar);

        // Botón para volver a la página anterior
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                VerCuentasParaModificar frame = new VerCuentasParaModificar(pIdAdmin); // Cambia a la clase correspondiente
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Volviendo al catálogo de cuentas");
                
            }
        });
        panelBotones.add(btnVolver);
    }
}