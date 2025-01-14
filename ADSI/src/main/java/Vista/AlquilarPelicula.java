package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Controladores.GestorPeliculas;
import Controladores.VideoClub;

public class AlquilarPelicula extends JFrame {
    private static AlquilarPelicula instance = null;
    private int idUsuario;
    private JTable tablePeliculas;
    private JTextField txtBuscar;
    private DefaultTableModel tableModel;

    // Constructor privado
    private AlquilarPelicula(int idUsu) {
        this.idUsuario = idUsu;
        initialize();
    }

    // Singleton
    public static AlquilarPelicula getAlquilarPelicula(int idUsuario) {
        if (instance == null) {
            instance = new AlquilarPelicula(idUsuario);
        }
        return instance;
    }

    // Inicializar componentes gráficos
    private void initialize() {
        setTitle("Alquilar Pelicula");
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

        JLabel lblTitulo = new JLabel("Alquilar Película");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo);

        // Panel de búsqueda y tabla
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(10, 10));
        contentPane.add(panelCentral, BorderLayout.CENTER);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(35, 41, 122));
        btnBuscar.setForeground(Color.WHITE);
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelCentral.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla para mostrar las peliculas
        String[] columnNames = {"Nombre", "Año", "Género"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablePeliculas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablePeliculas);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con los botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        JButton btnAlquilar = new JButton("Alquilar");
        btnAlquilar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAlquilar.setBackground(new Color(35, 41, 122));
        btnAlquilar.setForeground(Color.WHITE);
        panelInferior.add(btnAlquilar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        panelInferior.add(btnVolver);

        // Listeners
        btnBuscar.addActionListener(e -> buscarPeliculas());
        btnVolver.addActionListener(e -> {
            this.setVisible(false); // Ocultar la vista actual
            InicioSesion.getInicioSesion(this.idUsuario).mostrar();
        });
        btnAlquilar.addActionListener(e -> {
            int row = tablePeliculas.getSelectedRow();
            if (row != -1) {
                String nombre = tableModel.getValueAt(row, 0).toString();
                String anio = tableModel.getValueAt(row, 1).toString();
                String genero = tableModel.getValueAt(row, 2).toString();
                VideoClub.getGestorGeneral().alquilarPelicula(this.idUsuario, nombre, Integer.parseInt(anio), genero);
            }
        });

        // Cargar todas las peliculas al inicio
        cargarPeliculasDesdeJSON(GestorPeliculas.getGestorPeliculas().mostrarPeliculas());
    }

    // Cargar peliculas en la tabla desde un JSONArray
    private void cargarPeliculasDesdeJSON(JSONArray peliculas) {
        tableModel.setRowCount(0);
        for (int i = 0; i < peliculas.length(); i++) {
            JSONObject pelicula = peliculas.getJSONObject(i);
            tableModel.addRow(new Object[]{
                    pelicula.getString("Nombre"),
                    pelicula.getInt("Año"),
                    pelicula.getString("Genero")
            });
        }
    }

    // Buscar peliculas por nombre
    private void buscarPeliculas() {
        String query = txtBuscar.getText().toLowerCase();
        JSONArray peliculas = GestorPeliculas.getGestorPeliculas().mostrarPeliculas();
        List<JSONObject> peliculasFiltradas = new ArrayList<>();

        for (int i = 0; i < peliculas.length(); i++) {
            JSONObject pelicula = peliculas.getJSONObject(i);
            if (pelicula.getString("Nombre").toLowerCase().contains(query)) {
                peliculasFiltradas.add(pelicula);
            }
        }
        // Convertir la lista filtrada a JSONArray
        cargarPeliculasDesdeJSON(new JSONArray(peliculasFiltradas));
    }

    public void mostrar() {
        setVisible(true);
    }
}
