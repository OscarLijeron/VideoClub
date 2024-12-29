package Vista;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {
    private JPanel contentPane;
    private JPanel panel;
    private JButton InicioS;
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
        InicioS = new JButton("Inicio de Sesion");
        InicioS.setFont(new Font("Arial", Font.BOLD, 14));
        InicioS.setBackground(new Color(35, 41, 122));
        InicioS.setForeground(Color.WHITE);
        InicioS.setPreferredSize(new Dimension(150, 40));
        InicioS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Capturar el nombre de usuario y comprobar nombre de usuario y contraseña en la base de datos
                String usuarioNombre = usuario.getText();

                // Verificar que el campo de usuario no esté vacío
                if (usuarioNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un usuario.");
                } else {
                    // Crear y mostrar la nueva vista de InicioSesion

                    InicioSesion vistaSes = InicioSesion.getInicioSesion(usuarioNombre);
                    vistaSes.mostrar();

                    // Cerrar la vista actual
                    setVisible(false);
                    dispose();
                }
            }
        });
        botonesPanel.add(InicioS);

        // Botón de Registro
        Registro = new JButton("Registro");
        Registro.setFont(new Font("Arial", Font.BOLD, 14));
        Registro.setBackground(new Color(35, 41, 122));
        Registro.setForeground(Color.WHITE);
        Registro.setPreferredSize(new Dimension(150, 40));
        Registro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar el código para el registro
            }
        });
        botonesPanel.add(Registro);

        // Botón de Ver Catalogo
        VerCatalogo = new JButton("Ver Catalogo");
        VerCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
        VerCatalogo.setBackground(new Color(35, 41, 122));
        VerCatalogo.setForeground(Color.WHITE);
        VerCatalogo.setPreferredSize(new Dimension(150, 40));
        VerCatalogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar el código para ver el catálogo
            }
        });
        botonesPanel.add(VerCatalogo);

    }
}       