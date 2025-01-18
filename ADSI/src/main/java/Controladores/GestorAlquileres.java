package Controladores;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.Timestamp;

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

    public int alquilarPelicula(int idUsuario, String nombrePeli, int añoProd, String genero) {
        Optional<Pelicula> peliOpt = GestorPeliculas.getGestorPeliculas().obtenerPeliculaPorNAG(nombrePeli, añoProd, genero);
        if (peliOpt.isEmpty()) {
            System.out.println("Pelicula no encontrada en el catalogo.");
            return 0;
        }
        Pelicula pelicula = peliOpt.get();
        if (!pelicula.estaDisponible()) {
            System.out.println("La pelicula no esta disponible.");
            return 1;
        }
        // Registrar el alquiler
        pelicula.setDisponible(false);
        Alquiler nuevoAlquiler = new Alquiler(pelicula, idUsuario);
        this.listaAlquileres.add(nuevoAlquiler);
        GestorUsuarios.getGestorUsuarios().alquilarPelicula(idUsuario, nuevoAlquiler);

        // Registrar en la base de datos
        BD.registrarAlquiler(idUsuario, BD.consultarIdPelicula(pelicula.getNombrePelicula(), pelicula.getAñoProd(), pelicula.getGenero()), nuevoAlquiler.getFechaAlquiler());
        System.out.println("Pelicula alquilada exitosamente.");
        return 2;
    }

    public JSONArray  obtenerAlquileresUsuario(int idUsuario) { 
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        Usuario usuario = gestorUsuarios.obtenerUsuarioPorId(idUsuario);
        return usuario.mostrarMisAlquileres();
    }

    public void eliminarAlquilerVencido(int idUsuario, String nombrePeli, int añoProd, String genero) {
        Optional<Pelicula> peliOpt = GestorPeliculas.getGestorPeliculas().obtenerPeliculaPorNAG(nombrePeli, añoProd, genero);
        if (peliOpt.isEmpty()) {
            System.out.println("Pelicula no encontrada en el catalogo.");
            return;
        }
        Pelicula pelicula = peliOpt.get();
        Optional<Alquiler> alquilerOpt = this.listaAlquileres.stream().filter(a -> a.esEstaPeli(pelicula) && a.getIdUsuario() == idUsuario).findFirst();
        if (alquilerOpt.isEmpty()) {
            System.out.println("El usuario no tiene alquilada la pelicula.");
            return;
        }
        Alquiler alquiler = alquilerOpt.get();
        if (alquiler.estaVencido()) {
            this.listaAlquileres.removeIf(a -> a.esEstaPeli(alquiler.getPelicula()));
            GestorUsuarios.getGestorUsuarios().obtenerUsuarioPorId(idUsuario).eliminarAlquiler(alquiler);
            pelicula.setDisponible(true);
            System.out.println("Alquiler eliminado exitosamente.");
        } else {
            System.out.println("El alquiler no esta vencido.");
        }
    }

    public void recuperarAlquiler( int idUsuario, String nombrePeli, int añoProd, String genero, Timestamp fechaAlquiler) {
        Alquiler alquiler = new Alquiler(GestorPeliculas.getGestorPeliculas().obtenerPeliculaPorNAG(nombrePeli, añoProd, genero).get(), idUsuario, fechaAlquiler); 
        this.listaAlquileres.add(alquiler);
        GestorUsuarios.getGestorUsuarios().obtenerUsuarioPorId(idUsuario).alquilarPelicula(alquiler);
    }

}