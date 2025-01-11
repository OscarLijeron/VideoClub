package Modelo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

