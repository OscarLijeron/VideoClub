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

    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalItems;
    private List<JSONObject> peliculasList;

    private JButton btnAnterior;
    private JButton btnSiguiente;

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
        setTitle("Alquilar Película");
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

        // Botón para alquilar
        JButton btnAlquilar = new JButton("Alquilar");
        btnAlquilar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAlquilar.setBackground(new Color(35, 41, 122));
        btnAlquilar.setForeground(Color.WHITE);
        panelInferior.add(btnAlquilar);

        // Botón para volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(35, 41, 122));
        btnVolver.setForeground(Color.WHITE);
        panelInferior.add(btnVolver);

        // Listeners
        btnBuscar.addActionListener(e -> buscarPeliculas());
        btnVolver.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Volviendo al menú principal...");
            this.setVisible(false); 
            InicioSesion.getInicioSesion(this.idUsuario).mostrar();
        });
        btnAlquilar.addActionListener(e -> { 
            int row = tablePeliculas.getSelectedRow();
            if (row != -1) {
                String nombre = tableModel.getValueAt(row, 0).toString();
                String anio = tableModel.getValueAt(row, 1).toString();
                String genero = tableModel.getValueAt(row, 2).toString();
                int rdo = VideoClub.getGestorGeneral().alquilarPelicula(this.idUsuario, nombre, Integer.parseInt(anio), genero);
                
                if (rdo == 0) {
                    JOptionPane.showMessageDialog(this, "Película no encontrada en el catálogo.");
                } else if (rdo == 1) {
                    JOptionPane.showMessageDialog(this, "La película no está disponible.");
                } else {
                    JOptionPane.showMessageDialog(this, "Película alquilada con éxito.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una película para alquilar.");
            }
        });

        btnAnterior.addActionListener(e -> cambiarPagina(-1));
        btnSiguiente.addActionListener(e -> cambiarPagina(1));

        // Cargar todas las peliculas al inicio
        cargarPeliculasDesdeJSON(GestorPeliculas.getGestorPeliculas().mostrarPeliculas());
    }

    // Cargar peliculas en la tabla desde un JSONArray
    private void cargarPeliculasDesdeJSON(JSONArray peliculas) {
        peliculasList = new ArrayList<>();
        for (int i = 0; i < peliculas.length(); i++) {
            peliculasList.add(peliculas.getJSONObject(i));
        }
        totalItems = peliculasList.size();
        currentPage = 1; // Reiniciar a la primera página al cargar nuevas películas
        mostrarPagina(currentPage);
    }

    // Mostrar una página de películas
    private void mostrarPagina(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems);

        tableModel.setRowCount(0); // Limpiar la tabla

        for (int i = start; i < end; i++) {
            JSONObject pelicula = peliculasList.get(i);
            tableModel.addRow(new Object[]{
                    pelicula.getString("Nombre"),
                    pelicula.getInt("Año"),
                    pelicula.getString("Genero")
            });
        }

        btnAnterior.setEnabled(page > 1);
        btnSiguiente.setEnabled(page * itemsPerPage < totalItems);
    }

    // Cambiar de página
    private void cambiarPagina(int direction) {
        currentPage += direction;
        mostrarPagina(currentPage);
    }

    // Buscar peliculas por nombre
    private void buscarPeliculas() {
        String query = txtBuscar.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            // Si no hay búsqueda, carga todas las películas
            cargarPeliculasDesdeJSON(GestorPeliculas.getGestorPeliculas().mostrarPeliculas());
            return;
        }

        JSONArray peliculas = GestorPeliculas.getGestorPeliculas().mostrarPeliculas();
        List<JSONObject> peliculasFiltradas = new ArrayList<>();

        for (int i = 0; i < peliculas.length(); i++) {
            JSONObject pelicula = peliculas.getJSONObject(i);
            if (pelicula.getString("Nombre").toLowerCase().contains(query)) {
                peliculasFiltradas.add(pelicula);
            }
        }

        if (peliculasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron películas que coincidan con el criterio de búsqueda.");
        }

        // Reiniciar la página y cargar las películas filtradas
        currentPage = 1;
        cargarPeliculasDesdeJSON(new JSONArray(peliculasFiltradas));
    }

    public void mostrar() {
        setVisible(true);
    }
}
