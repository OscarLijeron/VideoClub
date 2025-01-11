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
	
	public void añadirPeliAlCatalogo(String pNombre,Integer pAñoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAñoProd,pGenero);
		this.BD.AñadirPeli(pNombre, pGenero, pAñoProd);
		this.gestorP.añadirPeliAlCatalogo(pPeli);	
	}
	public void eliminarSolicitudPelicula(Integer pIdUsuario,String pNombre,Integer pAñoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAñoProd,pGenero);
		int idPeli=this.BD.consultarIdPelicula(pNombre, pAñoProd, pGenero);
		this.BD.EliminarSolicitudPeli(pIdUsuario,idPeli);
		this.BD.EliminarPeliSol(pNombre, pGenero, pAñoProd);
		this.gestorU.eliminarSolicitudPelicula(pIdUsuario, pPeli);	
	}
	public void iniciarScheduler() {
		this.BD.iniciarScheduler();
	}
	public void recuperarBD() {
		this.BD.recuperarPelis();
		this.BD.recuperarSolicitudPelis();
		this.BD.iniciarScheduler();
	}

    public JSONArray obtenerAlquileresUsuario(int idUsuario) {
		return gestorA.obtenerAlquileresUsuario(idUsuario);
	}

	public JSONArray obtenerPeliculas() {
		return gestorP.mostrarPeliculas();
	}

	public void alquilarPelicula(int idUsuario, String nombrePeli, int añoProd, String genero) {
		gestorA.alquilarPelicula(idUsuario,  nombrePeli,  añoProd,  genero);
	}

	public void registrarse(String pNombre, String pContraseña, String pCorreo) {
		this.gestorU.registrarse(pNombre, pContraseña, pCorreo);
	}
		
	public void aceptarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		this.gestorU.aceptarSolicitudRegistro(pIdAdmin,pIdUsuario);
	}
		
	public void eliminarCuenta(Integer pIdUsuario) {
		this.gestorU.eliminarCuenta(pIdUsuario);
	}

	public Integer iniciarSesion(String pNombre,String pContraseña, String pCorreo) {
		return this.gestorU.iniciarSesion(pNombre, pContraseña, pCorreo);
		
	}

	public void eliminarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		this.gestorU.eliminarSolicitudRegistro(pIdAdmin,pIdUsuario);
	}

	public void actualizarDatosPersonales(Integer pIdUsuario, String pNombre, String pContraseña, String pCorreo) {
		this.gestorU.actualizarDatosPersonales(pIdUsuario,pNombre,pContraseña,pCorreo);
	}

	public void eliminarAlquilerVencido(int idUsuario, String nombrePeli, int añoProd, String genero) {
		this.gestorA.eliminarAlquilerVencido( idUsuario,  nombrePeli,  añoProd,  genero);
	}

}
