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
	public void SolicitarPelicula() {
		
	}
    public void SolicitarRegistro(Usuario pUsuario) {
    	this.solicitudesUsuario.add(pUsuario);
		//
	}
	public void AlquilarPelicula() {
		
		
	}
	public void AñadirSolicitudPelicula(Pelicula pPeli) {
		this.solicitudesPelicula.add(pPeli);
		
	}
	public void EliminarSolicitudPelicula(Pelicula pPeli) {
		Pelicula peliC=null;
		boolean enc=false;
		int i=0;
		while(i<this.solicitudesPelicula.size() && enc==false) {
			peliC=this.solicitudesPelicula.get(i);
			if (pPeli.getAñoProd()==peliC.getAñoProd() && pPeli.getGenero().equals(peliC.getGenero()) && pPeli.getNombrePelicula().equals(peliC.getNombrePelicula())) {
				enc=true;
			}
			i++;
		}
		if (enc) {
		this.solicitudesPelicula.remove(peliC);
		}else {
			System.out.println("No esta la peli");
		}
		
	}
	public void EliminarAlquiler(Alquiler pAlquiler) {
		this.misAlquileres.remove(pAlquiler);
		//
	}
	public JSONArray mostrarSolicitudesPeli() {
		JSONArray jsonArray = new JSONArray();

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


}
