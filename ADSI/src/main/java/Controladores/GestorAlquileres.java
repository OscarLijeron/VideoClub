package Controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;

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

    public boolean alquilarPelicula(int idUsuario, int idPelicula) {
        Optional<Pelicula> peliOpt = GestorPeliculas.getGestorPeliculas().obtenerPeliculaPorId(idPelicula);
        if (peliOpt.isEmpty()) {
            System.out.println("Pelicula no encontrada en el catalogo.");
            return false;
        }
        Pelicula pelicula = peliOpt.get();
        if (!pelicula.estaDisponible()) {
            System.out.println("La pelicula no esta disponible.");
            return false;
        }
        // Registrar el alquiler
        Alquiler nuevoAlquiler = new Alquiler(pelicula, idUsuario);
        this.listaAlquileres.add(nuevoAlquiler);
        GestorUsuarios.getGestorUsuarios().obtenerUsuarioPorId(idUsuario).alquilarPelicula(nuevoAlquiler);
        pelicula.setDisponible(false);

        // Registrar en la base de datos
        BD.registrarAlquiler(idUsuario, BD.consultarIdPelicula(pelicula.getNombrePelicula(), pelicula.getAñoProd(), pelicula.getGenero()));
        System.out.println("Pelicula alquilada exitosamente.");
        return true;
    }

    public JSONArray  obtenerAlquileresUsuario(int idUsuario) { 
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        Usuario usuario = gestorUsuarios.obtenerUsuarioPorId(idUsuario);
        return usuario.mostrarMisAlquileres();
    }
}