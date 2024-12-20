package Modelo;

import java.util.ArrayList;

public class Pelicula {
	private String nombre;
	private Integer añoProd;
	private String genero;
	private Boolean disponible=true;
	private ArrayList<Reseña> listaReseñas=new ArrayList<Reseña>();
	
	private Pelicula (String pNombre,Integer pAñoProd,String pGenero) {
		this.nombre=pNombre;
		this.añoProd=pAñoProd;
		this.genero=pGenero;
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
}
