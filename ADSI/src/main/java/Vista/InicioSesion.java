package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesion extends JFrame {
    private static InicioSesion instance = null;
    private int idUsuario;
    private JButton verAlquilados;
    private JButton alquilarPelicula;

    // Constructor privado que recibe el nombre del usuario
    private InicioSesion(Integer idUsu) {
        this.idUsuario = idUsu;
        initialize();
    }

    // Metodo para obtener la instancia de la clase Singleton
    public static InicioSesion getInicioSesion(Integer idUsuario) {
        if (instance == null) {
            instance = new InicioSesion(idUsuario);
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
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));
        contentPane.add(panelBotones, BorderLayout.CENTER);

        // Boton "Ver Alquilados"
        verAlquilados = new JButton("Ver Alquilados");
        verAlquilados.setFont(new Font("Arial", Font.BOLD, 14));
        verAlquilados.setBackground(new Color(35, 41, 122));
        verAlquilados.setForeground(Color.WHITE);
        verAlquilados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AlquileresUsuario vistaMisAlq = AlquileresUsuario.getAlquileresUsuario(idUsuario);
                vistaMisAlq.mostrar();
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Mostrar peliculas alquiladas");
            }
        });
        panelBotones.add(verAlquilados);

        // Boton "Alquilar Pelicula"
        alquilarPelicula = new JButton("Alquilar Pelicula");
        alquilarPelicula.setFont(new Font("Arial", Font.BOLD, 14));
        alquilarPelicula.setBackground(new Color(35, 41, 122));
        alquilarPelicula.setForeground(Color.WHITE);
        alquilarPelicula.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AlquilarPelicula vistaAlqPeli = AlquilarPelicula.getAlquilarPelicula(idUsuario);
                vistaAlqPeli.mostrar();
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Proceso de alquiler de pelicula");
            }
        });
        panelBotones.add(alquilarPelicula);
    }

    // Metodo para mostrar la ventana de inicio de sesion
    public void mostrar() {
        setVisible(true);
    }
}
