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
	public void añadirSolicitudPelicula(String pNombre,Integer pAño, String pGenero) {
		Optional<Usuario> unUsuario = this.catalogoUsuarios.stream()
			    .filter(p -> p.esAdmin())
			    .findFirst();

			if (unUsuario.isPresent()) {
			    Usuario admin = unUsuario.get();
			    Pelicula pPeli= new Pelicula(pNombre,pAño,pGenero);
			    this.BD.AñadirPeliSol(pNombre, pGenero, pAño);
			    int idPeli=this.BD.consultarIdPelicula(pNombre, pAño, pGenero);
			    this.BD.AñadirSolicitudPeli(admin.getId(),idPeli);
			    admin.AñadirSolicitudPelicula(pPeli);
			} else {
			    System.out.println("No se encontro ningun usuario administrador.");
			}
		
	}
	public void añadirSolicitudPeliculaParaRecuperar(String pNombre,Integer pAño, String pGenero) {
		Optional<Usuario> unUsuario = this.catalogoUsuarios.stream()
			    .filter(p -> p.esAdmin())
			    .findFirst();

			if (unUsuario.isPresent()) {
			    Usuario admin = unUsuario.get();
			    Pelicula pPeli= new Pelicula(pNombre,pAño,pGenero);
			    admin.AñadirSolicitudPelicula(pPeli);
			} else {
			    System.out.println("No se encontro ningun usuario administrador.");
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
	public void añadirUsuarioParaRecuperar(String pNombre, String pContraseña, String pCorreo, String pRol) {
		Usuario unUsuario=new Usuario(pNombre, pContraseña, pCorreo, pRol);
		this.catalogoUsuarios.add(unUsuario);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
