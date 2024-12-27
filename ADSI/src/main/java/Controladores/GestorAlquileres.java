package Controladores;

import java.util.ArrayList;
import java.util.Optional;
import Modelo.Alquiler;
import Modelo.Pelicula;
import Modelo.Usuario;
import Modelo.SQLiteConnection;

public class GestorAlquileres {

    private static GestorAlquileres miGestorA = new GestorAlquileres();
    private ArrayList<Alquiler> listaAlquileres;
    SQLiteConnection BD = SQLiteConnection.getSQLiteConnection();

    private GestorAlquileres() {
        this.listaAlquileres = new ArrayList<>();
    }

    public static GestorAlquileres getGestorAlquileres() {
        if (miGestorA == null) {
            miGestorA = new GestorAlquileres();
        }
        return miGestorA;
    }

    public boolean alquilarPelicula(Usuario usuario, String nombrePelicula, String pGenero, Integer pA�oprod) {
        Optional<Pelicula> peliOpt = BD.consultarPelicula(nombrePelicula, pGenero, pA�oprod); 
        if (peliOpt.isEmpty()) {
            System.out.println("Pelicula no encontrada en el cat�logo.");
            return false;
        }

        Pelicula pelicula = peliOpt.get();
        if (!pelicula.estaDisponible()) {
            System.out.println("La pel�cula no est� disponible.");
            return false;
        }

        // Registrar el alquiler
        Alquiler nuevoAlquiler = new Alquiler(pelicula);
        this.listaAlquileres.add(nuevoAlquiler);
        usuario.alquilarPelicula(nuevoAlquiler);
        pelicula.setDisponible(false);

        // Registrar en la base de datos
        BD.registrarAlquiler(usuario.getId(), BD.consultarIdPelicula(pelicula.getNombrePelicula(), pelicula.getA�oProd(), pelicula.getGenero()));
        System.out.println("Pelicula alquilada exitosamente.");
        return true;
    }

    public ArrayList<Pelicula> obtenerPeliculasAlquiladas(Usuario usuario) {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        for (Alquiler alquiler : usuario.getMisAlquileres()) {
            peliculas.add(alquiler.getPelicula());
        }
        return peliculas;
    }
}