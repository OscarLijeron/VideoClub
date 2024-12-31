package Vista;

import javax.swing.*;
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
        setTitle("Alquilar Película");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        getContentPane().add(panelPrincipal);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla para mostrar las películas
        String[] columnNames = {"Nombre", "Año", "Género"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablePeliculas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablePeliculas);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        //Boton para volver a la ventana anterior
        JButton btnVolver = new JButton("Volver");
        panelPrincipal.add(btnVolver, BorderLayout.SOUTH);

        // Listeners
        btnBuscar.addActionListener(e -> buscarPeliculas());
        tablePeliculas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePeliculas.getSelectedRow() != -1) {
                int row = tablePeliculas.getSelectedRow();
                String nombre = tableModel.getValueAt(row, 0).toString();
                String anio = tableModel.getValueAt(row, 1).toString();
                String genero = tableModel.getValueAt(row, 2).toString();
                int idPelicula = GestorPeliculas.getGestorPeliculas().obtenerPeliculaPorNAG(nombre, Integer.parseInt(anio), genero);
                VideoClub.getGestorGeneral().alquilarPelicula(this.idUsuario, idPelicula);
            }
        });
        btnVolver.addActionListener(e -> {
        this.setVisible(false); // Ocultar la vista actual
        InicioSesion.getInicioSesion(this.idUsuario).mostrar();
        });
        // Cargar todas las películas al inicio
        cargarPeliculasDesdeJSON(GestorPeliculas.getGestorPeliculas().mostrarPeliculas());
    }

    // Cargar películas en la tabla desde un JSONArray
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

    // Buscar películas por nombre
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
