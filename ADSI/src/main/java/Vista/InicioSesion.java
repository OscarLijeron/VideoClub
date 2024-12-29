package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Usuario;
import Controladores.GestorUsuarios;

public class InicioSesion extends JFrame {
    private static InicioSesion instance = null;
    private Usuario usuario;

    private JButton verAlquilados;
    private JButton alquilarPelicula;

    // Constructor privado que recibe el nombre del usuario
    private InicioSesion(String nombreUsu) {
        this.usuario = GestorUsuarios.getGestorUsuarios().obtenerUsuarioPorNombre(nombreUsu);
        initialize();
    }

    // Método para obtener la instancia de la clase Singleton
    public static InicioSesion getInicioSesion(String nombreUsu) {
        if (instance == null) {
            instance = new InicioSesion(nombreUsu);
        }
        return instance;
    }

    // Método para inicializar los componentes gráficos
    private void initialize() {
        // Configuración de la ventana
        setTitle("Bienvenido, " + usuario.getNombre());
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

        JLabel lblMensajeBienvenida = new JLabel("Bienvenido, " + usuario.getNombre());
        lblMensajeBienvenida.setForeground(Color.WHITE);
        lblMensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensajeBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblMensajeBienvenida);

        // Panel principal para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));
        contentPane.add(panelBotones, BorderLayout.CENTER);

        // Botón "Ver Alquilados"
        verAlquilados = new JButton("Ver Alquilados");
        verAlquilados.setFont(new Font("Arial", Font.BOLD, 14));
        verAlquilados.setBackground(new Color(35, 41, 122));
        verAlquilados.setForeground(Color.WHITE);
        verAlquilados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la funcionalidad para ver los alquileres

                JOptionPane.showMessageDialog(null, "Mostrar películas alquiladas");
            }
        });
        panelBotones.add(verAlquilados);

        // Botón "Alquilar Película"
        alquilarPelicula = new JButton("Alquilar Película");
        alquilarPelicula.setFont(new Font("Arial", Font.BOLD, 14));
        alquilarPelicula.setBackground(new Color(35, 41, 122));
        alquilarPelicula.setForeground(Color.WHITE);
        alquilarPelicula.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la funcionalidad para alquilar una película
                JOptionPane.showMessageDialog(null, "Proceso de alquiler de película");
            }
        });
        panelBotones.add(alquilarPelicula);
    }

    // Método para mostrar la ventana de inicio de sesión
    public void mostrar() {
        setVisible(true);
    }
}
