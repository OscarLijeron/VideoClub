package Modelo;

import Vista.VistaPrincipal;
import Controladores.VideoClub;

public class Main {
    public static void main(String[] args) {
        VideoClub.getGestorGeneral().recuperarBD();
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.setVisible(true);
    }
}
