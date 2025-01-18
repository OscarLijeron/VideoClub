package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

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
    public void SolicitarRegistro(Usuario pUsuario) {
    	this.solicitudesUsuario.add(pUsuario);
	}
	public void alquilarPelicula(Alquiler pAlquiler) {
		this.misAlquileres.add(pAlquiler);
		
		
	}
	public void AñadirSolicitudPelicula(Pelicula pPeli) {
		this.solicitudesPelicula.add(pPeli);
		
	}
	public void EliminarSolicitudPelicula(Pelicula pPeli) {
	    // Recorrer la lista de solicitudes de pelicula
	    for (int i = 0; i < this.solicitudesPelicula.size(); i++) {
	        Pelicula peliC = this.solicitudesPelicula.get(i);
	        System.out.print(pPeli.getAñoProd().equals(peliC.getAñoProd()));
	        // Comprobar si la pelicula coincide con la que queremos eliminar
	        if (pPeli.getAñoProd().equals(peliC.getAñoProd()) &&
	            pPeli.getGenero().equals(peliC.getGenero()) &&
	            pPeli.getNombrePelicula().equals(peliC.getNombrePelicula())) {
	            
	            // Eliminar la pelicula de la lista
	            this.solicitudesPelicula.remove(i);
	            System.out.println("Pelicula eliminada: " + pPeli.getNombrePelicula());
	            return; // Salir del metodo despues de eliminar la pelicula
	        }
	    }

	    // Si llegamos aqui, significa que no hemos encontrado la pelicula
	    System.out.println("No esta la pelicula en la lista.");
	}

	public void eliminarAlquiler(Alquiler pAlquiler) {
		Iterator<Alquiler> iterator = this.misAlquileres.iterator();
		while (iterator.hasNext()) {
			Alquiler alquiler = iterator.next();
			if (alquiler.esEstaPeli(pAlquiler.getPelicula())) {
				iterator.remove(); 
				return; 
			}
		}
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
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public JSONArray mostrarMisAlquileres() {
		JSONArray jsonArray = new JSONArray();
		for (Alquiler alquiler : this.misAlquileres) {
			JSONObject jsonAlquiler = new JSONObject();
			jsonAlquiler.put("titulo", alquiler.getPelicula().getNombrePelicula());
			jsonAlquiler.put("año", alquiler.getPelicula().getAñoProd());
			jsonAlquiler.put("genero", alquiler.getPelicula().getGenero());
			jsonArray.put(jsonAlquiler);
			System.out.println("Pelicula alquilada: "+ alquiler.getPelicula().getNombrePelicula());
		}
		return jsonArray;
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public String getNombre() {
		return this.nombre;
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public String getContraseña() {
	return this.contraseña;
	}
	
	public String getCorreo() {
	return this.correo;
	}
	
	public ArrayList<Usuario> getSolicitudesUsuario() {
	return this.solicitudesUsuario;
	}
	
	public JSONArray mostrarSolicitudesUsuario() {
		JSONArray jsonArray = new JSONArray();
		for (Usuario usuario : this.solicitudesUsuario) {
			JSONObject jsonUsuario = new JSONObject();
			jsonUsuario.put("nombre", usuario.getNombre());
			jsonUsuario.put("contraseña", usuario.getContraseña());
			jsonUsuario.put("correo", usuario.getCorreo());
			jsonUsuario.put("id",usuario.getId());
			jsonArray.put(jsonUsuario);
		}
		return jsonArray; 
	}

	public void eliminarSolicitudRegistro(Usuario pUsuario) {
		this.solicitudesUsuario.remove(pUsuario);
	}

	public void actualizarDatos(String pNombre, String pContraseña, String pCorreo) {
		this.nombre = pNombre;
		this.contraseña = pContraseña;
		this.correo = pCorreo;
	}
	
}