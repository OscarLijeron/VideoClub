package Controladores;

import java.util.ArrayList;

import org.json.JSONArray;

import Modelo.Pelicula;
import Modelo.SQLiteConnection;
import Modelo.Usuario;

public class VideoClub {
	private static VideoClub miGestorG=new VideoClub();
	
	
	GestorAlquileres gestorA=GestorAlquileres.getGestorAlquileres();
	GestorPeliculas gestorP=GestorPeliculas.getGestorPeliculas();
	GestorUsuarios  gestorU=GestorUsuarios.getGestorUsuarios();
	SQLiteConnection BD=SQLiteConnection.getSQLiteConnection();
	private  VideoClub() {}
	public static VideoClub getGestorGeneral() {
		if (miGestorG==null) {
			miGestorG=new VideoClub();
		}
		return miGestorG;
	}
	
	public void añadirPeliAlCatalogo(String pNombre,Integer pAñoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAñoProd,pGenero);
		this.BD.AñadirPeli(pNombre, pGenero, pAñoProd);
		this.BD.EliminarPeli(this.BD.consultarIdPelicula(pNombre, pAñoProd, pGenero)); //elimina la peli del sol
		this.gestorP.añadirPeliAlCatalogo(pPeli);	
	}
	public void eliminarSolicitudPelicula(Integer pIdUsuario,String pNombre,Integer pAñoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAñoProd,pGenero);
		int idPeli=this.BD.consultarIdPelicula(pNombre, pAñoProd, pGenero);
		this.BD.EliminarSolicitudPeli(pIdUsuario,idPeli);
		this.gestorU.eliminarSolicitudPelicula(pIdUsuario, pPeli);	
	}
	public void recuperarBD() {
		this.BD.recuperarUsuarios();
		this.BD.recuperarPelis();
		this.BD.recuperarSolicitudPelis();
	}

    public JSONArray obtenerAlquileresUsuario(int idUsuario) {
		return gestorA.obtenerAlquileresUsuario(idUsuario);
	}

	public JSONArray obtenerPeliculas() {
		return gestorP.mostrarPeliculas();
	}

	//alquilar pelicula
	public void alquilarPelicula(int idUsuario, String nombreP) {
		GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
		Usuario usuario = gestorUsuarios.obtenerUsuarioPorId(idUsuario);
		Pelicula pelicula = gestorP.obtenerPeliculaPorNombre(nombreP);
		if (usuario != null && pelicula != null) {
			gestorA.alquilarPelicula(usuario, pelicula);
		}
	}


}
