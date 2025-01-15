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
    private JButton solicitudesRegistro; // Nuevo botón

    // Constructor privado que recibe el nombre del usuario
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
        // Configuracion de la ventana
        setTitle("Bienvenido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel superior con el mensaje de bienvenida
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(35, 41, 122));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        JLabel lblMensajeBienvenida = new JLabel("Bienvenido");
        lblMensajeBienvenida.setForeground(Color.WHITE);
        lblMensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensajeBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblMensajeBienvenida);

        // Panel principal para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10)); // Cambiar a 3 filas para acomodar el nuevo botón
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
                SolicitudesRegistro frame = new SolicitudesRegistro(idUsuario); // Cambia a la clase correspondiente
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Ver solicitudes de registro");
            }
        });
        panelBotones.add(solicitudesRegistro);
    }

    // Metodo para mostrar la ventana de inicio de sesion
    public void mostrar() {
        setVisible(true);
    }
}