package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesionAdmin extends JFrame {
    private static InicioSesionAdmin instance = null;
    private int idUsuario;
    private JButton peticionesPelis;
    private JButton solicitudesRegistro;
    private JButton modificarCuentas;
    private JButton eliminarCuentas;
    private JButton actualizarDatosPersonales;
    private JButton volverVistaPrincipal; // Botón para volver a la vista principal

    // Constructor privado que recibe el ID del usuario
    private InicioSesionAdmin(Integer idUsu) {
        this.idUsuario = idUsu;
        initialize();
    }

    // Metodo para obtener la instancia de la clase Singleton
    public static InicioSesionAdmin getInicioSesionAdmin(Integer idUsuario) {
        if (instance == null) {
            instance = new InicioSesionAdmin(idUsuario);
        }
        return instance;
    }

    // Metodo para inicializar los componentes graficos
    private void initialize() {
        // Configuración de la ventana
        setTitle("Bienvenido Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500); // Ajustar altura para más botones
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel superior con el mensaje de bienvenida
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(35, 41, 122));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        JLabel lblMensajeBienvenida = new JLabel("Bienvenido Administrador");
        lblMensajeBienvenida.setForeground(Color.WHITE);
        lblMensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensajeBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblMensajeBienvenida);

        // Panel principal para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10)); // Cambiar a 5 filas para acomodar el nuevo botón
        contentPane.add(panelBotones, BorderLayout.CENTER);

        // Botón "Ver peticiones películas"
        peticionesPelis = new JButton("Ver peticiones películas");
        peticionesPelis.setFont(new Font("Arial", Font.BOLD, 14));
        peticionesPelis.setBackground(new Color(35, 41, 122));
        peticionesPelis.setForeground(Color.WHITE);
        peticionesPelis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PeticionesPelis frame = new PeticionesPelis(1);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Ver peticiones películas");
            }
        });
        panelBotones.add(peticionesPelis);

        // Botón "Ver solicitudes de registro"
        solicitudesRegistro = new JButton("Ver solicitudes de registro");
        solicitudesRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        solicitudesRegistro.setBackground(new Color(35, 41, 122));
        solicitudesRegistro.setForeground(Color.WHITE);
        solicitudesRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SolicitudesRegistro frame = new SolicitudesRegistro(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Ver solicitudes de registro");
            }
        });
        panelBotones.add(solicitudesRegistro);

        // Botón "Modificar Cuentas"
        modificarCuentas = new JButton("Modificar Cuentas");
        modificarCuentas.setFont(new Font("Arial", Font.BOLD, 14));
        modificarCuentas.setBackground(new Color(35, 41, 122));
        modificarCuentas.setForeground(Color.WHITE);
        modificarCuentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VerCuentasParaModificar frame = new VerCuentasParaModificar(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Modificar cuentas de usuarios");
            }
        });
        panelBotones.add(modificarCuentas);

        // Botón "Eliminar Cuentas"
        eliminarCuentas = new JButton("Eliminar Cuentas");
        eliminarCuentas.setFont(new Font("Arial", Font.BOLD, 14));
        eliminarCuentas.setBackground(new Color(35, 41, 122));
        eliminarCuentas.setForeground(Color.WHITE);
        eliminarCuentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EliminarCuentas frame = new EliminarCuentas(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Eliminar cuentas de usuarios");
            }
        });
        panelBotones.add(eliminarCuentas);

        // Botón "Actualizar Datos Personales"
        actualizarDatosPersonales = new JButton("Actualizar Datos Personales");
        actualizarDatosPersonales.setFont(new Font("Arial", Font.BOLD, 14));
        actualizarDatosPersonales.setBackground(new Color(35, 41, 122));
        actualizarDatosPersonales.setForeground(Color.WHITE);
        actualizarDatosPersonales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActualizarDatosPersonales frame = new ActualizarDatosPersonales(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Actualizar datos personales");
            }
        });
        panelBotones.add(actualizarDatosPersonales);

        // Panel inferior para el botón de volver
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(35, 41, 122));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        // Botón para cerrar sesion
        volverVistaPrincipal = new JButton("Cerrar Sesion");
        volverVistaPrincipal.setFont(new Font("Arial", Font.BOLD, 12));
        volverVistaPrincipal.setBackground(Color.WHITE);
        volverVistaPrincipal.setForeground(new Color(35, 41, 122));
        volverVistaPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instance = null; 
                dispose(); 
                System.exit(0);
            }
        });
        panelInferior.add(volverVistaPrincipal);
    }

    // Metodo para mostrar la ventana de inicio de sesion
    public void mostrar() {
        setVisible(true);
    }
}


