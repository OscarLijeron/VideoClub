package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;

import Controladores.GestorUsuarios;
import Controladores.VideoClub;

public class EliminarCuentas extends JFrame {

    private JPanel contentPane;
    private JTextField txtIdUsuario;
    private JTextArea solicitudesArea;
    private JButton btnCargarSolicitudes;
    private JButton btnEliminarCuenta;
    private JButton btnVolver;

    // Constructor que recibe el ID del administrador
    public EliminarCuentas(Integer pIdAdmin) {
        setTitle("Eliminación de Cuentas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior para el título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(35, 41, 122));
        JLabel lblTitulo = new JLabel("Eliminación de Cuentas");
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

        // Área de texto para mostrar las cuentas
        solicitudesArea = new JTextArea();
        solicitudesArea.setEditable(false);
        solicitudesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(solicitudesArea);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Botón para cargar las cuentas
        btnCargarSolicitudes = new JButton("Cargar Cuentas");
        btnCargarSolicitudes.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarSolicitudes.setBackground(new Color(35, 41, 122));
        btnCargarSolicitudes.setForeground(Color.WHITE);
        btnCargarSolicitudes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSONArray cuentas = GestorUsuarios.getGestorUsuarios().mostrarUsuarios();
                StringBuilder cuentasText = new StringBuilder();

                if (cuentas != null && cuentas.length() > 0) {
                    for (int i = 0; i < cuentas.length(); i++) {
                        JSONObject cuenta = cuentas.getJSONObject(i);
                        String nombre = cuenta.getString("nombre");
                        String correo = cuenta.getString("correo");
                        String contraseña = cuenta.getString("contraseña");
                        int id = cuenta.getInt("id");
                        cuentasText.append("Nombre: ").append(nombre)
                                .append(", Correo: ").append(correo)
                                .append(", Contraseña: ").append(contraseña)
                                .append(", ID: ").append(id).append("\n");
                    }
                    solicitudesArea.setText(cuentasText.toString());
                } else {
                    solicitudesArea.setText("No se encontraron cuentas.");
                }
            }
        });
        panelBotones.add(btnCargarSolicitudes);

        // Botón para eliminar cuenta
        btnEliminarCuenta = new JButton("Eliminar Cuenta");
        btnEliminarCuenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminarCuenta.setBackground(new Color(255, 69, 0));
        btnEliminarCuenta.setForeground(Color.WHITE);
        btnEliminarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuarioTexto = txtIdUsuario.getText();
                if (idUsuarioTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID de usuario.");
                    return;
                }
                int idUsuario = Integer.parseInt(idUsuarioTexto);

                VideoClub.getGestorGeneral().eliminarCuenta(idUsuario);
                JOptionPane.showMessageDialog(null, "Cuenta eliminada.");

                // Recargar las cuentas
                JSONArray cuentas = GestorUsuarios.getGestorUsuarios().mostrarUsuarios();
                StringBuilder cuentasText = new StringBuilder();
                if (cuentas != null && cuentas.length() > 0) {
                    for (int i = 0; i < cuentas.length(); i++) {
                        JSONObject cuenta = cuentas.getJSONObject(i);
                        String nombre = cuenta.getString("nombre");
                        String correo = cuenta.getString("correo");
                        String contraseña = cuenta.getString("contraseña");
                        int id = cuenta.getInt("id");
                        cuentasText.append("Nombre: ").append(nombre)
                                .append(", Correo: ").append(correo)
                                .append(", Contraseña: ").append(contraseña)
                                .append(", ID: ").append(id).append("\n");
                    }
                    solicitudesArea.setText(cuentasText.toString());
                } else {
                    solicitudesArea.setText("No se encontraron cuentas.");
                }
            }
        });
        panelBotones.add(btnEliminarCuenta);

        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(EliminarCuentas.this, "Volviendo al inicio de sesión...");
                EliminarCuentas.this.setVisible(false);
                // Aquí llamarías a la ventana de inicio de sesión
            }
        });
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }
}
