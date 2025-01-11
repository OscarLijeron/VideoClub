package Controladores;

import org.json.JSONArray;

import Modelo.Pelicula;
import Modelo.SQLiteConnection;

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
	
	public void a�adirPeliAlCatalogo(String pNombre,Integer pA�oProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pA�oProd,pGenero);
		this.BD.A�adirPeli(pNombre, pGenero, pA�oProd);
		this.gestorP.a�adirPeliAlCatalogo(pPeli);	
	}
	public void eliminarSolicitudPelicula(Integer pIdUsuario,String pNombre,Integer pA�oProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pA�oProd,pGenero);
		int idPeli=this.BD.consultarIdPelicula(pNombre, pA�oProd, pGenero);
		this.BD.EliminarSolicitudPeli(pIdUsuario,idPeli);
		this.BD.EliminarPeliSol(pNombre, pGenero, pA�oProd);
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

	public void alquilarPelicula(int idUsuario, int idPelicula){
		gestorA.alquilarPelicula(idUsuario, idPelicula);
	}

	public void registrarse(String pNombre, String pContrase�a, String pCorreo) {
		this.gestorU.registrarse(pNombre, pContrase�a, pCorreo);
	}
		
	public void aceptarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		this.gestorU.aceptarSolicitudRegistro(pIdAdmin,pIdUsuario);
	}
		
	public void eliminarCuenta(Integer pIdUsuario) {
		this.gestorU.eliminarCuenta(pIdUsuario);
	}

	public Integer iniciarSesion(String pNombre,String pContrase�a, String pCorreo) {
		return this.gestorU.iniciarSesion(pNombre, pContrase�a, pCorreo);
		
	}

	public void eliminarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		this.gestorU.eliminarSolicitudRegistro(pIdAdmin,pIdUsuario);
	}

	public void actualizarDatosPersonales(Integer pIdUsuario, String pNombre, String pContrase�a, String pCorreo) {
		this.gestorU.actualizarDatosPersonales(pIdUsuario,pNombre,pContrase�a,pCorreo);
	}

}
