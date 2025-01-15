package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controladores.VideoClub;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlquileresUsuario extends JFrame {
    private static AlquileresUsuario instance = null;
    private int idUsuario;
    private JPanel contentPane;
    private JList<String> listAlquileres;

    // Constructor privado que recibe el nombre del usuario
    private AlquileresUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        initialize();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent e) {
                recargarAlquileres();  // Recargamos la lista al mostrar la ventana
            }
        });
    }

    // Metodo para obtener la instancia de la clase Singleton
    public static AlquileresUsuario getAlquileresUsuario(int idUsuario) {
        if (instance == null) {
            instance = new AlquileresUsuario(idUsuario);
        }
        return instance;
    }

    // Metodo para inicializar los componentes graficos
    private void initialize() {
        // Configuracion de la ventana
        setTitle("Mis Alquileres");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Titulo
        JLabel lblTitle = new JLabel("Peliculas Alquiladas", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Lista de alquileres
        JSONArray alquileres = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario);
        DefaultListModel<String> alquileresModel = new DefaultListModel<>();

        for (int i = 0; i < alquileres.length(); i++) {
            JSONObject alquiler = alquileres.getJSONObject(i);
            String pelicula = alquiler.getString("titulo") + " (" + alquiler.getInt("año") + ")";
            alquileresModel.addElement(pelicula);
        }

        listAlquileres = new JList<>(alquileresModel);
        listAlquileres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAlquileres.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(listAlquileres);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        // Boton Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Accion al hacer clic en Volver
                volverAInicioSesion();
            }
        });
        panelBotones.add(btnVolver);
    }

    // Metodo para regresar a la vista de inicio de sesion
    private void volverAInicioSesion() {
        JOptionPane.showMessageDialog(this, "Volviendo a inicio de sesion...");
        this.setVisible(false); // Ocultar la vista actual
        InicioSesion.getInicioSesion(idUsuario).mostrar(); // Mostrar la vista de inicio de sesion
    }

    // Metodo para mostrar la ventana
    public void mostrar() {
        setVisible(true);
    }

    private void recargarAlquileres() {
        // Obtenemos los alquileres actualizados
        JSONArray alquileres = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario);
        DefaultListModel<String> alquileresModel = new DefaultListModel<>();
        
        for (int i = 0; i < alquileres.length(); i++) {
            JSONObject alquiler = alquileres.getJSONObject(i);
            String pelicula = alquiler.getString("titulo") + " (" + alquiler.getInt("año") + ")";
            alquileresModel.addElement(pelicula);
        }
    
        // Actualizamos el modelo de la lista con los nuevos datos
        listAlquileres.setModel(alquileresModel);
    }
}
