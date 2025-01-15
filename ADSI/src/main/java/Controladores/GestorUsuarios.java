package Controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

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
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.esAdmin())
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

			if (usuario!=null) { 
			    Pelicula pPeli= new Pelicula(pNombre,pAño,pGenero);
			    BD.AñadirPeliSol(pNombre, pGenero, pAño);
			    int idPeli=BD.consultarIdPelicula(pNombre, pAño, pGenero);
			    BD.AñadirSolicitudPeli(usuario.getId(),idPeli);
			    usuario.AñadirSolicitudPelicula(pPeli);
			} else {
			    System.out.println("No se encontro ningun usuario administrador.");
			}
		
	}
	public void añadirSolicitudPeliculaParaRecuperar(String pNombre,Integer pAño, String pGenero) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.esAdmin())
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario!=null) {
		    System.out.println("Si se encontro usuario administrador.");
		   
		    Pelicula pPeli = new Pelicula(pNombre, pAño, pGenero);
		    usuario.AñadirSolicitudPelicula(pPeli);  // Accede al usuario y a�ade la solicitud de pel�cula
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
	public void añadirUsuarioParaRecuperar(String pNombre, String pContraseña, String pCorreo, String pRol) {
		Usuario unUsuario=new Usuario(pNombre, pContraseña, pCorreo, pRol);
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

	public void registrarse(String pNombre, String pContraseña, String pCorreo) {
		Usuario admin = this.catalogoUsuarios.stream()
			.filter(p -> p.esAdmin())
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario
		
			if (admin!=null) {
				BD.AñadirSolUsuario(pNombre, pContraseña, pCorreo);
				Usuario usuario = new Usuario(pNombre, pContraseña, pCorreo, "Usuario");
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
				admin.eliminarSolicitudRegistro(usuario);			
				BD.RegistrarUsuario(pIdUsuario,pIdAdmin);
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

	public Integer iniciarSesion(String pNombre, String pContraseña, String pCorreo) {
		for (Usuario usuario : catalogoUsuarios) {
			if (usuario.getNombre().equals(pNombre) && usuario.getContraseña().equals(pContraseña) && usuario.getCorreo().equals(pCorreo)) {
				return usuario.getId();
			}
		}
		return null;

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
				BD.EliminarUsuario(pIdUsuario);		
				
			}
		}
		else{
			System.out.println("No se encontro ningun usuario administrador");
		}
	}

	public void actualizarDatosPersonales(Integer pIdUsuario, String pNombre, String pContraseña, String pCorreo) {
		Usuario usuario = this.catalogoUsuarios.stream()
			.filter(p -> p.tieneEsteId(pIdUsuario))
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario!=null) {
			BD.ActualizarDatosUsuario(pIdUsuario, pNombre, pContraseña, pCorreo);
			usuario.actualizarDatos(pNombre,pContraseña,pCorreo);
		}
		else{
			System.out.println("No se encontro ningun usuario");
		}
	}

	public void añadirSolicitudUsuarioParaRecuperar(String pNombre, String pContraseña, String pCorreo, String pRol) {
		Usuario unUsuario=new Usuario(pNombre, pContraseña, pCorreo, pRol);
		Usuario admin = this.catalogoUsuarios.stream()
			.filter(p -> p.esAdmin())
			.findFirst()
			.orElse(null); // Devuelve null si no encuentra un usuario

		if (admin!=null){
			admin.SolicitarRegistro(unUsuario);
		}
		else{
			System.out.println("No se encontró ningún usuario");
		}
	}

	public JSONArray mostrarSolicitudesUsuario(Integer pID) {
		Usuario usuario = this.catalogoUsuarios.stream()
                .filter(p -> p.tieneEsteId(pID))
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra un usuario

		if (usuario != null) {
			System.out.println("Usuario encontrado");
		} else {
			System.out.println("Usuario con ID " + pID + " no encontrado.");
		}

		return usuario.mostrarSolicitudesUsuario();
		
		
	}

	public JSONArray mostrarUsuarios() {
		JSONArray jsonArray = new JSONArray();
		for (Usuario usuario : this.catalogoUsuarios) {
			JSONObject jsonUsuario = new JSONObject();
			jsonUsuario.put("nombre", usuario.getNombre());
			jsonUsuario.put("contraseña", usuario.getContraseña());
			jsonUsuario.put("correo", usuario.getCorreo());
			jsonUsuario.put("id",usuario.getId());
			jsonArray.put(jsonUsuario);
		}
		return jsonArray; 
	}

	public boolean comprobarExistenciaUsuario(Integer pIdUsuario){
		for (Usuario usuario : catalogoUsuarios) {
			if (usuario.getId()==pIdUsuario) {
				return true;
			}
		}
		return false;
	}

}
