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
			    System.out.println("No se encontro ningun usuario administrador.");
			}
		
	}
	public void a�adirSolicitudPeliculaParaRecuperar(String pNombre,Integer pA�o, String pGenero) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.esAdmin())
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario!=null) {
		    System.out.println("Si se encontro usuario administrador.");
		   
		    Pelicula pPeli = new Pelicula(pNombre, pA�o, pGenero);
		    usuario.A�adirSolicitudPelicula(pPeli);  // Accede al usuario y a�ade la solicitud de pel�cula
		} else {
		    System.out.println("No se encontro ningun usuario administrador.");
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

	//-------------------------------------------------------------------
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		for (Usuario usuario : catalogoUsuarios) {
			if (usuario.getId() == idUsuario) {
				return usuario;
			}
		}
		return null;
	}
	//-------------------------------------------------------------------
	public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
		for (Usuario usuario : catalogoUsuarios) {
			if (usuario.getNombre().equals(nombreUsuario)) {
				return usuario;
			}
		}
		return null;
	}
	//-------------------------------------------------------------------

	public void registrarse(String pNombre, String pContrase�a, String pCorreo) {
		Usuario admin = this.catalogoUsuarios.stream()
			.filter(p -> p.esAdmin())
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario
		
			if (admin!=null) {
				Usuario usuario = new Usuario(pNombre, pContrase�a, pCorreo, "Usuario");
				admin.SolicitarRegistro(usuario);
			}
			else {
				System.out.println("No se encontro ningun usuario administrador.");
			}
	}
		
	public void aceptarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		Usuario admin = this.catalogoUsuarios.stream()
			.filter(p -> p.tieneEsteId(pIdAdmin))
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario
		
		if (admin!=null) {
			Optional<Usuario> solUsuario = admin.getSolicitudesUsuario().stream()
				.filter(p-> p.getId() == pIdUsuario).findFirst();
		
			if (solUsuario.isEmpty()) {
				System.out.println("No se encontro la solicitud");
			}
			else {
				Usuario usuario = solUsuario.get();
				this.catalogoUsuarios.add(usuario);
				admin.ValidarUsuario(usuario);	
				admin.eliminarSolicitudRegistro(usuario);			
				BD.RegistrarUsuario(usuario.getNombre(),usuario.getContrase�a(), usuario.getCorreo(),pIdAdmin);
			}
		}
		else{
			System.out.println("No se encontro ningun usuario administrador");
		}
	}
		
	public void eliminarCuenta(Integer pIdUsuario) {
		//Creo que me falta algo en esta
		Usuario usuario = this.obtenerUsuarioPorId(pIdUsuario);
		if (usuario !=null) {
			this.catalogoUsuarios.remove(usuario);
			BD.EliminarUsuario(pIdUsuario);
		}
	}

	public Integer iniciarSesion(String pNombre, String pContrase�a, String pCorreo) {
		Integer idUsuario = BD.consultarIdUsuario(pNombre,pCorreo,pContrase�a);
		if(idUsuario!= null) {
			return idUsuario;
		}
		else {
			return null;
		}

	}

	public boolean comprobarQueEsAdmin(Integer idUsuario){
		boolean esAdmin = false;
		Usuario usuario = this.obtenerUsuarioPorId(idUsuario);
		if (usuario.esAdmin()) {
			esAdmin=true;
			return esAdmin;
		}
		return esAdmin;
	}

	public void eliminarSolicitudRegistro(Integer pIdAdmin, Integer pIdUsuario) {
		Usuario admin = this.catalogoUsuarios.stream()
			.filter(p -> p.tieneEsteId(pIdAdmin))
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario
		
		if (admin!=null) {
			Optional<Usuario> solUsuario = admin.getSolicitudesUsuario().stream()
				.filter(p-> p.getId() == pIdUsuario).findFirst();
		
			if (solUsuario.isEmpty()) {
				System.out.println("No se encontro la solicitud");
			}
			else {
				Usuario usuario = solUsuario.get();	
				admin.eliminarSolicitudRegistro(usuario);		
				
			}
		}
		else{
			System.out.println("No se encontro ningun usuario administrador");
		}
	}

	public void actualizarDatosPersonales(Integer pIdUsuario, String pNombre, String pContrase�a, String pCorreo) {
		Usuario usuario = this.catalogoUsuarios.stream()
			.filter(p -> p.tieneEsteId(pIdUsuario))
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario!=null) {
			BD.ActualizarDatosUsuario(pIdUsuario, pNombre, pContrase�a, pCorreo);
			usuario.actualizarDatos(pNombre,pContrase�a,pCorreo);
		}
		else{
			System.out.println("No se encontro ningun usuario");
		}
	}
}
