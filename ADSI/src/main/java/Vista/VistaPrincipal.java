package Vista;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {
    private JPanel contentPane;
    private JPanel panel;
    private JButton InicioSesion;
    private JButton Registro;
    private JButton VerCatalogo;
    private JPanel arriba;
    private JLabel titulo;
    private JTextField usuario;
    private JTextField contraseña;

    public VistaPrincipal() {
        setTitle("the BRO's");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel superior
        arriba = new JPanel();
        arriba.setBackground(new Color(35, 41, 122)); // Color de fondo
        contentPane.add(arriba, BorderLayout.NORTH);
        
        // Titulo con estilo
        titulo = new JLabel("Bienvenido a nuestro videoclub");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE); // Texto blanco
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        arriba.add(titulo);

        // Panel principal
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Aumentamos el espaciado entre los componentes
        panel.setBackground(new Color(240, 240, 240)); // Color de fondo suave
        contentPane.add(panel, BorderLayout.CENTER);

        // Etiqueta y campo de usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblUsuario);

        usuario = new JTextField();
        usuario.setColumns(10);
        usuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(usuario);

        // Etiqueta y campo de contraseña
        JLabel lblContrasea = new JLabel("Contraseña");
        lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
        lblContrasea.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblContrasea);

        contraseña = new JTextField();
        contraseña.setColumns(10);
        contraseña.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(contraseña);

        // Botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout());
        botonesPanel.setBackground(new Color(240, 240, 240));
        panel.add(botonesPanel);

        // Botón de Inicio de Sesión
        InicioSesion = new JButton("Inicio de Sesion");
        InicioSesion.setFont(new Font("Arial", Font.BOLD, 14));
        InicioSesion.setBackground(new Color(35, 41, 122)); // Fondo color azul oscuro
        InicioSesion.setForeground(Color.WHITE); // Texto blanco
        InicioSesion.setPreferredSize(new Dimension(150, 40)); // Tamaño personalizado
        InicioSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aquí va el código que se ejecutará al pulsar el botón de inicio de sesión
            }
        });
        botonesPanel.add(InicioSesion);

        // Botón de Registro
        Registro = new JButton("Registro");
        Registro.setFont(new Font("Arial", Font.BOLD, 14));
        Registro.setBackground(new Color(35, 41, 122)); // Fondo color azul oscuro
        Registro.setForeground(Color.WHITE); // Texto blanco
        Registro.setPreferredSize(new Dimension(150, 40)); // Tamaño personalizado
        Registro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aquí va el código que se ejecutará al pulsar el botón de registro
            }
        });
        botonesPanel.add(Registro);

        // Botón de Ver Catalogo
        VerCatalogo = new JButton("Ver Catalogo");
        VerCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
        VerCatalogo.setBackground(new Color(35, 41, 122)); // Fondo color azul oscuro
        VerCatalogo.setForeground(Color.WHITE); // Texto blanco
        VerCatalogo.setPreferredSize(new Dimension(150, 40)); // Tamaño personalizado
        VerCatalogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aquí va el código que se ejecutará al pulsar el botón de ver catalogo
            }
        });
        botonesPanel.add(VerCatalogo);
    }
}

