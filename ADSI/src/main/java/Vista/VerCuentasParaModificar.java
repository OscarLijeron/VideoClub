package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import Controladores.GestorUsuarios;

//Este import me sale que sobra en el codigo, lo comento para que no moleste
//import Controladores.VideoClub;

public class VerCuentasParaModificar extends JFrame {

    private JPanel contentPane;
    private JTable tableCuentas;
    private DefaultTableModel tableModel;
    private JButton btnCargarCuentas;
    private JButton btnModificarDatos;
    private JButton btnVolver;

    // Constructor que recibe el ID del administrador
    public VerCuentasParaModificar(Integer pIdAdmin) {
        setTitle("Usuarios registrados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior para el título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(35, 41, 122));
        JLabel lblTitulo = new JLabel("Usuarios registrados");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // Tabla para mostrar las cuentas
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Correo", "Contraseña"}, 0);
        tableCuentas = new JTable(tableModel);
        tableCuentas.setFont(new Font("Arial", Font.PLAIN, 14));
        tableCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección única
        JScrollPane scrollPane = new JScrollPane(tableCuentas);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Botón para cargar las cuentas
        btnCargarCuentas = new JButton("Cargar Cuentas");
        btnCargarCuentas.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarCuentas.setBackground(new Color(35, 41, 122));
        btnCargarCuentas.setForeground(Color.WHITE);
        btnCargarCuentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarCuentas(pIdAdmin);
            }
        });
        panelBotones.add(btnCargarCuentas);

        // Botón para modificar cuenta
        btnModificarDatos = new JButton("Modificar datos");
        btnModificarDatos.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificarDatos.setBackground(new Color(255, 69, 0));
        btnModificarDatos.setForeground(Color.WHITE);
        btnModificarDatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCuentas.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario para modificar.");
                    return;
                }
                int idUsuario = (int) tableModel.getValueAt(selectedRow, 0);
                ModificarCuenta frame = new ModificarCuenta(pIdAdmin, idUsuario);
                frame.setVisible(true);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Abriendo ventana de modificación.");
            }
        });
        panelBotones.add(btnModificarDatos);

        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Volviendo al menu principal...");
                setVisible(false); // Ocultar la vista actual
                InicioSesionAdmin.getInicioSesionAdmin(pIdAdmin).mostrar(); // Mostrar la vista de inicio de sesión
            }
        });
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para cargar las cuentas en la tabla
    private void cargarCuentas(Integer pIdAdmin) {
        JSONArray cuentas = GestorUsuarios.getGestorUsuarios().mostrarUsuariosParaModificar(pIdAdmin);
        tableModel.setRowCount(0); // Limpiar la tabla
        if (cuentas != null && cuentas.length() > 0) {
            for (int i = 0; i < cuentas.length(); i++) {
                JSONObject cuenta = cuentas.getJSONObject(i);
                int id = cuenta.getInt("id");
                String nombre = cuenta.getString("nombre");
                String correo = cuenta.getString("correo");
                String contraseña = cuenta.getString("contraseña");
                tableModel.addRow(new Object[]{id, nombre, correo, contraseña});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron cuentas.");
        }
    }
}
