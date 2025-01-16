package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;

import Controladores.GestorUsuarios;
import Controladores.VideoClub;

public class SolicitudesRegistro extends JFrame {

    private JPanel contentPane;
    private JTable tableSolicitudes;
    private DefaultTableModel tableModel;
    private JButton btnCargarSolicitudes;
    private JButton btnAceptarSolicitud;
    private JButton btnDenegarSolicitud;
    private JButton btnVolver;

    // Constructor que recibe el ID del administrador
    public SolicitudesRegistro(Integer pIdAdmin) {
        setTitle("Gestion de Solicitudes de Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400); // Ajustar tamaño para nueva columna

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior para el titulo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(35, 41, 122));
        JLabel lblTitulo = new JLabel("Gestion de Solicitudes de Registro");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // Tabla para mostrar las solicitudes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Correo", "Contraseña"}, 0);
        tableSolicitudes = new JTable(tableModel);
        tableSolicitudes.setFont(new Font("Arial", Font.PLAIN, 14));
        tableSolicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleccion unica
        JScrollPane scrollPane = new JScrollPane(tableSolicitudes);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Boton para cargar las solicitudes
        btnCargarSolicitudes = new JButton("Cargar Solicitudes");
        btnCargarSolicitudes.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarSolicitudes.setBackground(new Color(35, 41, 122));
        btnCargarSolicitudes.setForeground(Color.WHITE);
        btnCargarSolicitudes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarSolicitudes(pIdAdmin);
            }
        });
        panelBotones.add(btnCargarSolicitudes);

        // Boton para aceptar solicitud
        btnAceptarSolicitud = new JButton("Aceptar Solicitud");
        btnAceptarSolicitud.setFont(new Font("Arial", Font.BOLD, 14));
        btnAceptarSolicitud.setBackground(new Color(35, 41, 122));
        btnAceptarSolicitud.setForeground(Color.WHITE);
        btnAceptarSolicitud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableSolicitudes.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una solicitud.");
                    return;
                }
                int idUsuario = (int) tableModel.getValueAt(selectedRow, 0);
                VideoClub.getGestorGeneral().aceptarSolicitudRegistro(pIdAdmin, idUsuario);
                JOptionPane.showMessageDialog(null, "Solicitud aceptada.");
                cargarSolicitudes(pIdAdmin);
            }
        });
        panelBotones.add(btnAceptarSolicitud);

        // Boton para denegar solicitud
        btnDenegarSolicitud = new JButton("Denegar Solicitud");
        btnDenegarSolicitud.setFont(new Font("Arial", Font.BOLD, 14));
        btnDenegarSolicitud.setBackground(new Color(255, 69, 0));
        btnDenegarSolicitud.setForeground(Color.WHITE);
        btnDenegarSolicitud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableSolicitudes.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una solicitud.");
                    return;
                }
                int idUsuario = (int) tableModel.getValueAt(selectedRow, 0);
                VideoClub.getGestorGeneral().eliminarSolicitudRegistro(pIdAdmin, idUsuario);
                JOptionPane.showMessageDialog(null, "Solicitud denegada.");
                cargarSolicitudes(pIdAdmin);
            }
        });
        panelBotones.add(btnDenegarSolicitud);

        // Boton para volver
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Volviendo al menu principal...");
                setVisible(false);
                InicioSesionAdmin.getInicioSesionAdmin(pIdAdmin).mostrar();
            }
        });
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    // Metodo para cargar las solicitudes en la tabla
    private void cargarSolicitudes(Integer pIdAdmin) {
        JSONArray solicitudes = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesUsuario(pIdAdmin);
        tableModel.setRowCount(0); // Limpiar la tabla
        if (solicitudes != null && solicitudes.length() > 0) {
            for (int i = 0; i < solicitudes.length(); i++) {
                JSONObject solicitud = solicitudes.getJSONObject(i);
                int id = solicitud.getInt("id");
                String nombre = solicitud.getString("nombre");
                String correo = solicitud.getString("correo");
                String contraseña = solicitud.getString("contraseña"); 
                tableModel.addRow(new Object[]{id, nombre, correo, contraseña});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron solicitudes.");
        }
    }
}

