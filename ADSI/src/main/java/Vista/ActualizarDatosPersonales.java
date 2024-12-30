package Vista;

import javax.swing.*;

import Controladores.VideoClub;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualizarDatosPersonales extends JFrame {
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtContraseña;
    private JTextField txtCorreo;
    private JButton btnSolicitarRegistro;
    private JButton btnVolver;

    public ActualizarDatosPersonales(Integer pIdUsuario) {
        setTitle("Actualizar Datos Personales");
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

        JLabel lblTitulo = new JLabel("Actualizar Datos Personales");
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
        btnSolicitarRegistro = new JButton("Actualizar Datos");
        btnSolicitarRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        btnSolicitarRegistro.setBackground(new Color(35, 41, 122));
        btnSolicitarRegistro.setForeground(Color.WHITE);
        btnSolicitarRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String contraseña = txtContraseña.getText();
                String correo = txtCorreo.getText();

                if (nombre.isEmpty() || contraseña.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                } else {
                    VideoClub.getGestorGeneral().recuperarBD();
                    VideoClub.getGestorGeneral().actualizarDatosPersonales(pIdUsuario,nombre,correo,contraseña);
                    JOptionPane.showMessageDialog(null, "Datos actualizados.");
                }
            }
        });
        panelBotones.add(btnSolicitarRegistro);

        // Botón para volver a la página inicial
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes añadir la lógica para volver a la pagina de iniciar sesion
                
            }
        });
        panelBotones.add(btnVolver);
    }
}
