package Controladores;

import Modelo.Pelicula;
import Modelo.SQLiteConnection;
import Modelo.Usuario;

public class VideoClub {
	private static VideoClub miGestorG=new VideoClub();
	
	
	
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
	public void aņadirPeliAlCatalogo(String pNombre,Integer pAņoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAņoProd,pGenero);
		this.BD.AņadirPeli(pNombre, pGenero, pAņoProd);
		this.gestorP.aņadirPeliAlCatalogo(pPeli);	
	}
	public void eliminarSolicitudPelicula(Integer pIdUsuario,String pNombre,Integer pAņoProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAņoProd,pGenero);
		int idPeli=this.BD.consultarIdPelicula(pNombre, pAņoProd, pGenero);
		this.BD.EliminarSolicitudPeli(pIdUsuario,idPeli);
		this.gestorU.eliminarSolicitudPelicula(pIdUsuario, pPeli);	
	}

}
