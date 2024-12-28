package Modelo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usuario {
	private String nombre;
	private String contraseña;
	private String correo;
	private String rol;
	private Integer id;
	private  ArrayList<Usuario> solicitudesUsuario=new ArrayList<Usuario>();
	private  ArrayList<Alquiler> misAlquileres=new ArrayList<Alquiler>();
	private  ArrayList<Pelicula> solicitudesPelicula=new ArrayList<Pelicula>();
	
	
	SQLiteConnection BD=SQLiteConnection.getSQLiteConnection();
	public Usuario(String pNombre, String pContraseña,String pCorreo,String pRol) {
		this.nombre=pNombre;
		this.contraseña=pContraseña;
		this.correo=pCorreo;
		this.rol=pRol;
		this.id=this.BD.consultarIdUsuario(pNombre, pCorreo, pContraseña);
		
	}
	public void IniciarSesion() {
		
	}
	public void ValidarUsuario(Usuario pUsuario) {
		this.solicitudesUsuario.remove(pUsuario);
		//
	}
	public void EliminarCuenta() {
		
	}
    public void SolicitarRegistro(Usuario pUsuario) {
    	this.solicitudesUsuario.add(pUsuario);
		//
	}
	public void alquilarPelicula(Alquiler pAlquiler) {
		this.misAlquileres.add(pAlquiler);
		
		
	}
	public void AñadirSolicitudPelicula(Pelicula pPeli) {
		this.solicitudesPelicula.add(pPeli);
		
	}
	public void EliminarSolicitudPelicula(Pelicula pPeli) {
	    // Recorrer la lista de solicitudes de película
	    for (int i = 0; i < this.solicitudesPelicula.size(); i++) {
	        Pelicula peliC = this.solicitudesPelicula.get(i);
	        System.out.print(pPeli.getAñoProd().equals(peliC.getAñoProd()));
	        // Comprobar si la película coincide con la que queremos eliminar
	        if (pPeli.getAñoProd().equals(peliC.getAñoProd()) &&
	            pPeli.getGenero().equals(peliC.getGenero()) &&
	            pPeli.getNombrePelicula().equals(peliC.getNombrePelicula())) {
	            
	            // Eliminar la película de la lista
	            this.solicitudesPelicula.remove(i);
	            System.out.println("Película eliminada: " + pPeli.getNombrePelicula());
	            return; // Salir del método después de eliminar la película
	        }
	    }

	    // Si llegamos aquí, significa que no hemos encontrado la película
	    System.out.println("No está la película en la lista.");
	}

	public void EliminarAlquiler(Alquiler pAlquiler) {
		this.misAlquileres.remove(pAlquiler);
		//
	}
	public JSONArray mostrarSolicitudesPeli() {
		JSONArray jsonArray = new JSONArray();
        System.out.print(this.solicitudesPelicula.size());
        for (Pelicula pelicula : this.solicitudesPelicula) {
            JSONObject jsonPelicula = new JSONObject();
            jsonPelicula.put("titulo", pelicula.getNombrePelicula());
            jsonPelicula.put("año", pelicula.getAñoProd());
            jsonPelicula.put("genero", pelicula.getGenero());
            jsonArray.put(jsonPelicula);
        }
        return jsonArray;		
	}
	public void mostrarSolicitudesUsuario() {
		
	}
	public Boolean esAdmin() {
		String str="Admin";
		return this.rol.equals(str);
	}
	public Boolean tieneEsteId(Integer pId) {
		return this.id==pId;
	}
	public int getId( ) {
		return this.id;
	}
	public ArrayList<Alquiler> getMisAlquileres() {
		return this.misAlquileres;
	}


}
