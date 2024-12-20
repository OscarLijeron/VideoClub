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
	public void a�adirPeliAlCatalogo(String pNombre,Integer pA�oProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pA�oProd,pGenero);
		this.BD.A�adirPeli(pNombre, pGenero, pA�oProd);
		this.gestorP.a�adirPeliAlCatalogo(pPeli);	
	}
	public void eliminarSolicitudPelicula(Integer pIdUsuario,String pNombre,Integer pA�oProd,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pA�oProd,pGenero);
		int idPeli=this.BD.consultarIdPelicula(pNombre, pA�oProd, pGenero);
		this.BD.EliminarSolicitudPeli(pIdUsuario,idPeli);
		this.gestorU.eliminarSolicitudPelicula(pIdUsuario, pPeli);	
	}

}
