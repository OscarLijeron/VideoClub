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

}
