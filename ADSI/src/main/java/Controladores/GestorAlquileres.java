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

    public boolean alquilarPelicula(Usuario usuario, Pelicula peli) {
        String nombreP = peli.getNombrePelicula();
        String genero = peli.getGenero();
        Integer añoProd = peli.getAñoProd();
        Optional<Pelicula> peliOpt = BD.consultarPelicula(nombreP, genero, añoProd); 
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
        Alquiler nuevoAlquiler = new Alquiler(pelicula);
        this.listaAlquileres.add(nuevoAlquiler);
        usuario.alquilarPelicula(nuevoAlquiler);
        pelicula.setDisponible(false);

        // Registrar en la base de datos
        BD.registrarAlquiler(usuario.getId(), BD.consultarIdPelicula(pelicula.getNombrePelicula(), pelicula.getAñoProd(), pelicula.getGenero()));
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

    public JSONArray  obtenerAlquileresUsuario(int idUsuario) { //llama a gestorUsuario, busca a ese usuario y devuelve el JSON de sus alquileres
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        Usuario usuario = gestorUsuarios.obtenerUsuarioPorId(idUsuario);
        return usuario.mostrarMisAlquileres();
    }
}