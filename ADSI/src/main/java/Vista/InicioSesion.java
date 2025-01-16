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
    private JButton catalogoAmpliado;
    private JButton pedirSolicitudPeli;
    private JButton actualizarDatosPersonales;
    private JButton volverVistaPrincipal; // Botón para volver a la vista principal

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
        setBounds(100, 100, 450, 400); // Ajustamos la altura para acomodar el botón adicional
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
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10)); // Cambiamos a 5 filas para acomodar el botón adicional
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

        // Boton "Explorar catalogo ampliado"
        catalogoAmpliado = new JButton("Catalogo Ampliado");
        catalogoAmpliado.setFont(new Font("Arial", Font.BOLD, 14));
        catalogoAmpliado.setBackground(new Color(35, 41, 122));
        catalogoAmpliado.setForeground(Color.WHITE);
        catalogoAmpliado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CatalogoAmpliado frame = new CatalogoAmpliado(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Explorar Catalogo Ampliado");
            }
        });
        panelBotones.add(catalogoAmpliado);

        // Boton "Solicitar Pelicula"
        pedirSolicitudPeli = new JButton("Solicitar Pelicula");
        pedirSolicitudPeli.setFont(new Font("Arial", Font.BOLD, 14));
        pedirSolicitudPeli.setBackground(new Color(35, 41, 122));
        pedirSolicitudPeli.setForeground(Color.WHITE);
        pedirSolicitudPeli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PedirSolicitudPeli frame = new PedirSolicitudPeli(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Solicitar Pelicula");
            }
        });
        panelBotones.add(pedirSolicitudPeli);

        // Boton "Actualizar Datos Personales"
        actualizarDatosPersonales = new JButton("Actualizar Datos Personales");
        actualizarDatosPersonales.setFont(new Font("Arial", Font.BOLD, 14));
        actualizarDatosPersonales.setBackground(new Color(35, 41, 122));
        actualizarDatosPersonales.setForeground(Color.WHITE);
        actualizarDatosPersonales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActualizarDatosPersonales frame = new ActualizarDatosPersonales(idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Actualizar tus datos personales");
            }
        });
        panelBotones.add(actualizarDatosPersonales);

        // Panel inferior para el botón de volver
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(35, 41, 122));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        // Botón "Volver a la vista principal"
        volverVistaPrincipal = new JButton("Cerrar Sesion");
        volverVistaPrincipal.setFont(new Font("Arial", Font.BOLD, 12));
        volverVistaPrincipal.setBackground(Color.WHITE);
        volverVistaPrincipal.setForeground(new Color(35, 41, 122));
        volverVistaPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VistaPrincipal vistaInicial = new VistaPrincipal();
                vistaInicial.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Volviendo a la vista principal...");
                
            }
        });
        panelInferior.add(volverVistaPrincipal);
    }

    // Metodo para mostrar la ventana de inicio de sesion
    public void mostrar() {
        setVisible(true);
    }
}
