package Controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;

import Modelo.Pelicula;
import Modelo.SQLiteConnection;
import Modelo.Usuario;

public class GestorUsuarios {
	private static GestorUsuarios miGestorU=new GestorUsuarios();
	private ArrayList<Usuario> catalogoUsuarios;
	
	
	SQLiteConnection BD=SQLiteConnection.getSQLiteConnection();
	private  GestorUsuarios() {}
	public static GestorUsuarios getGestorUsuarios() {
		if (miGestorU==null) {
			miGestorU=new GestorUsuarios();
		}
		return miGestorU;
	}
	public void a�adirSolicitudPelicula(String pNombre,Integer pA�o, String pGenero) {
		Optional<Usuario> unUsuario = this.catalogoUsuarios.stream()
			    .filter(p -> p.esAdmin())
			    .findFirst();

			if (unUsuario.isPresent()) {
			    Usuario admin = unUsuario.get();
			    Pelicula pPeli= new Pelicula(pNombre,pA�o,pGenero);
			    this.BD.A�adirPeliSol(pNombre, pGenero, pA�o);
			    int idPeli=this.BD.consultarIdPelicula(pNombre, pA�o, pGenero);
			    this.BD.A�adirSolicitudPeli(admin.getId(),idPeli);
			    admin.A�adirSolicitudPelicula(pPeli);
			} else {
			    System.out.println("No se encontr� ning�n usuario administrador.");
			}
		
	}
	public JSONArray mostrarSolicitudesPeli(Integer pID) {
		Usuario usuario=this.catalogoUsuarios.stream().filter(p->p.tieneEsteId(pID)).collect(null);
		return usuario.mostrarSolicitudesPeli();
		
		
	}
	
	public void eliminarSolicitudPelicula(Integer pIdUsuario,Pelicula pPelicula) {
		Usuario unUsuario=this.catalogoUsuarios.stream().filter(p->p.tieneEsteId(pIdUsuario)).collect(null);
		unUsuario.EliminarSolicitudPelicula(pPelicula);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
