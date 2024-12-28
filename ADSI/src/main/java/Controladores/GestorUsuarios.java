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
	
	
	private static SQLiteConnection BD=SQLiteConnection.getSQLiteConnection();
	private  GestorUsuarios() {
		catalogoUsuarios=new ArrayList<Usuario>();
	}
	public static GestorUsuarios getGestorUsuarios() {
		if (miGestorU==null) {
			miGestorU=new GestorUsuarios();
		}
		return miGestorU;
	}
	public void a�adirSolicitudPelicula(String pNombre,Integer pA�o, String pGenero) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.esAdmin())
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

			if (usuario!=null) { 
			    Pelicula pPeli= new Pelicula(pNombre,pA�o,pGenero);
			    BD.A�adirPeliSol(pNombre, pGenero, pA�o);
			    int idPeli=BD.consultarIdPelicula(pNombre, pA�o, pGenero);
			    BD.A�adirSolicitudPeli(usuario.getId(),idPeli);
			    usuario.A�adirSolicitudPelicula(pPeli);
			} else {
			    System.out.println("No se encontr� ning�n usuario administrador.");
			}
		
	}
	public void a�adirSolicitudPeliculaParaRecuperar(String pNombre,Integer pA�o, String pGenero) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.esAdmin())
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario!=null) {
		    System.out.println("Si se encontr� usuario administrador.");
		   
		    Pelicula pPeli = new Pelicula(pNombre, pA�o, pGenero);
		    usuario.A�adirSolicitudPelicula(pPeli);  // Accede al usuario y a�ade la solicitud de pel�cula
		} else {
		    System.out.println("No se encontr� ning�n usuario administrador.");
		}

	}
	public JSONArray mostrarSolicitudesPeli(Integer pID) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.tieneEsteId(pID))
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario != null) {
			System.out.println("Usuario encontrado");
		} else {
			System.out.println("Usuario con ID " + pID + " no encontrado.");
		}

		return usuario.mostrarSolicitudesPeli();
		
		
	}
	
	public void eliminarSolicitudPelicula(Integer pIdUsuario,Pelicula pPelicula) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.tieneEsteId(pIdUsuario))
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario
		if (usuario != null) {
			System.out.println("Usuario encontrado");
			usuario.EliminarSolicitudPelicula(pPelicula);
		} else {
			System.out.println("Usuario con ID " + pIdUsuario + " no encontrado.");
		}
		
	}
	public void a�adirUsuarioParaRecuperar(String pNombre, String pContrase�a, String pCorreo, String pRol) {
		Usuario unUsuario=new Usuario(pNombre, pContrase�a, pCorreo, pRol);
		this.catalogoUsuarios.add(unUsuario);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
