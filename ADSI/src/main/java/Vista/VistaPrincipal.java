package Vista;
// en esta vista tienen que aparecer 3 botones: inicio de sesion, registro y ver catalogo (no el catalogo ampliado) y 2 cajas de texto para introducir el usuario y la contraseña
// hazme esta vista que se llamara VistaPrincipal
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controladores.VideoClub;

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

        arriba = new JPanel();
        contentPane.add(arriba, BorderLayout.NORTH);

        titulo = new JLabel("Bienvenido a nuestro videoclub");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        arriba.add(titulo);

        panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(3, 2, 0, 0));

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblUsuario);

        usuario = new JTextField();
        panel.add(usuario);
        usuario.setColumns(10);

        JLabel lblContrasea = new JLabel("Contraseña");
        lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblContrasea);

        contraseña = new JTextField();
        panel.add(contraseña);
        contraseña.setColumns(10);

        InicioSesion = new JButton("Inicio de Sesion");
        InicioSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aqui va el codigo que se ejecutara al pulsar el boton de inicio de sesion
            }
        });
        panel.add(InicioSesion);

        Registro = new JButton("Registro");
        Registro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aqui va el codigo que se ejecutara al pulsar el boton de registro
            }
        });
        panel.add(Registro);

        VerCatalogo = new JButton("Ver Catalogo");
        VerCatalogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // aqui va el codigo que se ejecutara al pulsar el boton de ver catalogo
            }
        });
        panel.add(VerCatalogo);
    }
}


