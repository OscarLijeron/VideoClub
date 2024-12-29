package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Usuario;
import Controladores.GestorUsuarios;

public class AlquilarPelicula extends JFrame {
    private static AlquilarPelicula instance = null;
    private Usuario usuario;
    // Constructor privado que recibe el nombre del usuario
    private AlquilarPelicula(Usuario usu) {
        this.usuario = usu;
        initialize();
    }

    // Método para obtener la instancia de la clase Singleton
    public static AlquilarPelicula getAlquilarPelicula(Usuario usu) {
        if (instance == null) {
            instance = new AlquilarPelicula(usu);
        }
        return instance;
    }

    // Método para inicializar los componentes gráficos
    private void initialize() {
        // Configuración de la ventana
    }
    public void mostrar() {
        setVisible(true);
    }
}