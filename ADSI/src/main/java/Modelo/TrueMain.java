package Modelo;

import Vista.VistaPrincipal;
import Controladores.VideoClub;

public class TrueMain {
    public static void main(String[] args) {
        VideoClub.getGestorGeneral().recuperarBD();
        VideoClub.getGestorGeneral().iniciarScheduler();
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.setVisible(true);
    }
}

