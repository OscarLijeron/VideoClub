package Vista;

import Controladores.GestorAlquileres;
import javax.swing.*;
import java.awt.*;

public class VistaAlquiler extends JFrame {
    private GestorAlquileres gestorAlquileres;

    public VistaAlquiler(GestorAlquileres gestor) {
        this.gestorAlquileres = gestor;
        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Alquileres");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //configurar los componentes
    }
}


