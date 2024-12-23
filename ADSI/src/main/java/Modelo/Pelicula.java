package Modelo;

import java.util.ArrayList;

public class Pelicula {
	private Integer id; // Nuevo atributo para almacenar el ID de la película
	private String nombre;
	private Integer añoProd;
	private String genero;
	private Boolean disponible=true;
	private ArrayList<Reseña> listaReseñas=new ArrayList<Reseña>();
	
	// Constructor sin ID, por si aún no tiene al momento de crear la instancia
    public Pelicula(String nombre, Integer añoProd, String genero) {
        this.nombre = nombre;
        this.añoProd = añoProd;
        this.genero = genero;
    }

    // Constructor con ID
    public Pelicula(Integer id, String nombre, Integer añoProd, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.añoProd = añoProd;
        this.genero = genero;
    }

    public Boolean estaDisponible() {
    	return this.disponible==true;
    }
    public void setDisponible(Boolean pDisponible) {
    	this.disponible=pDisponible;
    	//
    }
    public Double calcularPuntuacionPromedio() {
    	Double rdo=this.listaReseñas.stream()
    		    .mapToDouble(o -> o.getPuntuacionP()) // Mapea cada rese�a a su puntuaci�n
    		    .average()                           // Calcula la media
    		    .orElse(0.0); 
    	return rdo;
    	//
    	
    }
    public void AgregarReseña (Reseña pReseña) {
    	this.listaReseñas.add(pReseña);
    	//
    }
    public String getNombrePelicula() {
    	return this.nombre;
    }
    public Integer getAñoProd() {
    	return this.añoProd;
    }
    public String getGenero() {
    	return this.genero;
    }
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
