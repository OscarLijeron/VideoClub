package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import Controladores.VideoClub;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AlquileresUsuario extends JFrame {
    private static AlquileresUsuario instance = null;
    private int idUsuario;
    private JTable tableAlquileres;
    private DefaultTableModel tableModel;
    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalItems;

    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JButton btnVolver;

    private List<JSONObject> alquileresList;

    // Constructor privado
    private AlquileresUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        initialize();
        // Listener para recargar los datos al mostrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent e) {
                recargarAlquileres(); 
            }
        });
    }

    // Singleton
    public static AlquileresUsuario getAlquileresUsuario(int idUsuario) {
        if (instance == null) {
            instance = new AlquileresUsuario(idUsuario);
        }
        return instance;
    }

    // Inicializar componentes gráficos
    private void initialize() {
        setTitle("Mis Alquileres");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Panel superior con el título
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(35, 41, 122));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("Mis Alquileres");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo);

        // Tabla para mostrar los alquileres
        String[] columnNames = {"Título", "Año", "Género"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableAlquileres = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAlquileres);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con los botones de navegación y el botón "Volver"
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        btnAnterior = new JButton("Anterior");
        btnAnterior.setFont(new Font("Arial", Font.BOLD, 14));
        btnAnterior.setBackground(new Color(35, 41, 122));
        btnAnterior.setForeground(Color.WHITE);
        panelInferior.add(btnAnterior);

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 14));
        btnSiguiente.setBackground(new Color(35, 41, 122));
        btnSiguiente.setForeground(Color.WHITE);
        panelInferior.add(btnSiguiente);

        // Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> volverAInicioSesion());
        panelInferior.add(btnVolver);

        // Listeners de los botones
        btnAnterior.addActionListener(e -> cambiarPagina(-1));
        btnSiguiente.addActionListener(e -> cambiarPagina(1));

        // Cargar alquileres y mostrar la primera página
        cargarAlquileres();
        mostrarPagina(currentPage);
    }

    // Cargar alquileres desde VideoClub
    private void cargarAlquileres() {
        JSONArray alquileres = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario);
        alquileresList = new ArrayList<>();
        for (int i = 0; i < alquileres.length(); i++) {
            alquileresList.add(alquileres.getJSONObject(i));
        }
        totalItems = alquileresList.size();
    }

    // Recargar alquileres y actualizar la tabla
    private void recargarAlquileres() {
        cargarAlquileres(); 
        mostrarPagina(currentPage); 
    }

    // Mostrar una página específica de alquileres
    private void mostrarPagina(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems);

        tableModel.setRowCount(0); // Limpiar la tabla

        for (int i = start; i < end; i++) {
            JSONObject alquiler = alquileresList.get(i);
            String titulo = alquiler.getString("titulo");
            int año = alquiler.getInt("año");
            String genero = alquiler.getString("genero");

            tableModel.addRow(new Object[]{titulo, año, genero});
        }

        btnAnterior.setEnabled(page > 1);
        btnSiguiente.setEnabled(page * itemsPerPage < totalItems);
    }

    // Cambiar de página
    private void cambiarPagina(int direction) {
        currentPage += direction;
        mostrarPagina(currentPage);
    }

    // Método para regresar a la vista de inicio de sesión
    private void volverAInicioSesion() {
        JOptionPane.showMessageDialog(this, "Volviendo al menú principal...");
        this.setVisible(false); 
        InicioSesion.getInicioSesion(idUsuario).mostrar(); 
    }

    public void mostrar() {
        setVisible(true);
    }
}
