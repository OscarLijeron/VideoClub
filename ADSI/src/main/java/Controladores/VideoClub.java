package Controladores;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		this.gestorU.eliminarSolicitudPelicula(pIdUsuario, pPeli);	
	}

	// Hazme la funcion de alquilar pelicula
	public void alquilarPelicula(Integer idusuario, String nombrePelicula, String pGenero, Integer pAñoprod) {
		Usuario usuario = this.BD.obtenerUsuario(idusuario);
		gestorA.alquilarPelicula(usuario, nombrePelicula, pGenero, pAñoprod);
	}

	public void registrarse(String pNombre,String pContraseña, String pCorreo) {
		this.gestorU.registrarUsuario(pNombre,pContraseña,pCorreo);
	}
		
	public void procesarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		this.gestorU.procesarSolicitudRegistro(pIdAdmin,pIdUsuario);
	}

}
