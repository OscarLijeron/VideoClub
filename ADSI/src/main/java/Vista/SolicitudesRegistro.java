package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;

import Controladores.GestorUsuarios;
import Controladores.VideoClub;

public class SolicitudesRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField txtIdUsuario;
    private JTextArea solicitudesArea;
    private JButton btnCargarSolicitudes;
    private JButton btnAceptarSolicitud;
    private JButton btnDenegarSolicitud;
    private JButton btnVolver;

    // Constructor que recibe el ID del administrador
    public SolicitudesRegistro(Integer pIdAdmin) {
        setTitle("Gestión de Solicitudes de Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior para el título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(35, 41, 122));
        JLabel lblTitulo = new JLabel("Gestión de Solicitudes de Registro");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // Panel central para los campos de texto y mostrar solicitudes
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(10, 10));

        // Panel para el campo de ID Usuario
        JPanel panelIdUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel lblIdUsuario = new JLabel("ID Usuario:");
        lblIdUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panelIdUsuario.add(lblIdUsuario);

        txtIdUsuario = new JTextField(10); // Ajustamos el tamaño de la caja de texto
        txtIdUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panelIdUsuario.add(txtIdUsuario);

        panelCentral.add(panelIdUsuario, BorderLayout.NORTH);

        // Área de texto para mostrar las solicitudes
        solicitudesArea = new JTextArea();
        solicitudesArea.setEditable(false);
        solicitudesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(solicitudesArea);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Botón para cargar las solicitudes
        btnCargarSolicitudes = new JButton("Cargar Solicitudes");
        btnCargarSolicitudes.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarSolicitudes.setBackground(new Color(35, 41, 122));
        btnCargarSolicitudes.setForeground(Color.WHITE);
        btnCargarSolicitudes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSONArray solicitudes = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesUsuario(pIdAdmin);
                StringBuilder solicitudesText = new StringBuilder();

                if (solicitudes != null && solicitudes.length() > 0) {
                    for (int i = 0; i < solicitudes.length(); i++) {
                        JSONObject solicitud = solicitudes.getJSONObject(i);
                        String nombre = solicitud.getString("nombre");
                        String correo = solicitud.getString("correo");
                        int id = solicitud.getInt("id");
                        solicitudesText.append("Nombre: ").append(nombre)
                                .append(", Correo: ").append(correo)
                                .append(", ID: ").append(id).append("\n");
                    }
                    solicitudesArea.setText(solicitudesText.toString());
                } else {
                    solicitudesArea.setText("No se encontraron solicitudes.");
                }
            }
        });
        panelBotones.add(btnCargarSolicitudes);

        // Botón para aceptar solicitud
        btnAceptarSolicitud = new JButton("Aceptar Solicitud");
        btnAceptarSolicitud.setFont(new Font("Arial", Font.BOLD, 14));
        btnAceptarSolicitud.setBackground(new Color(35, 41, 122));
        btnAceptarSolicitud.setForeground(Color.WHITE);
        btnAceptarSolicitud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuarioTexto = txtIdUsuario.getText();
                if (idUsuarioTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID de usuario.");
                    return;
                }
                int idUsuario = Integer.parseInt(idUsuarioTexto);

                VideoClub.getGestorGeneral().aceptarSolicitudRegistro(pIdAdmin, idUsuario);
                JOptionPane.showMessageDialog(null, "Solicitud aceptada.");

                JSONArray solicitudes = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesUsuario(pIdAdmin);
                StringBuilder solicitudesText = new StringBuilder();
                if (solicitudes != null && solicitudes.length() > 0) {
                    for (int i = 0; i < solicitudes.length(); i++) {
                        JSONObject solicitud = solicitudes.getJSONObject(i);
                        String nombre = solicitud.getString("nombre");
                        String correo = solicitud.getString("correo");
                        int id = solicitud.getInt("id");
                        solicitudesText.append("Nombre: ").append(nombre)
                                .append(", Correo: ").append(correo)
                                .append(", ID: ").append(id).append("\n");
                    }
                    solicitudesArea.setText(solicitudesText.toString());
                } else {
                    solicitudesArea.setText("No se encontraron solicitudes.");
                }
            }
        });
        panelBotones.add(btnAceptarSolicitud);

        // Botón para denegar solicitud
        btnDenegarSolicitud = new JButton("Denegar Solicitud");
        btnDenegarSolicitud.setFont(new Font("Arial", Font.BOLD, 14));
        btnDenegarSolicitud.setBackground(new Color(255, 69, 0));
        btnDenegarSolicitud.setForeground(Color.WHITE);
        btnDenegarSolicitud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuarioTexto = txtIdUsuario.getText();
                if (idUsuarioTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID de usuario.");
                    return;
                }
                int idUsuario = Integer.parseInt(idUsuarioTexto);

                VideoClub.getGestorGeneral().eliminarSolicitudRegistro(pIdAdmin, idUsuario);
                JOptionPane.showMessageDialog(null, "Solicitud denegada.");

                JSONArray solicitudes = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesUsuario(pIdAdmin);
                StringBuilder solicitudesText = new StringBuilder();
                if (solicitudes != null && solicitudes.length() > 0) {
                    for (int i = 0; i < solicitudes.length(); i++) {
                        JSONObject solicitud = solicitudes.getJSONObject(i);
                        String nombre = solicitud.getString("nombre");
                        String correo = solicitud.getString("correo");
                        int id = solicitud.getInt("id");
                        solicitudesText.append("Nombre: ").append(nombre)
                                .append(", Correo: ").append(correo)
                                .append(", ID: ").append(id).append("\n");
                    }
                    solicitudesArea.setText(solicitudesText.toString());
                } else {
                    solicitudesArea.setText("No se encontraron solicitudes.");
                }
            }
        });
        panelBotones.add(btnDenegarSolicitud);

        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Volviendo a inicio de sesion...");
                setVisible(false); // Ocultar la vista actual
                InicioSesionAdmin.getInicioSesionAdmin(pIdAdmin).mostrar(); // Mostrar la vista de inicio de sesion
            }
        });
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }
}
