package Modelo;

import java.util.ArrayList;

public class Pelicula {
	private String nombre;
	private Integer a�oProd;
	private String genero;
	private Boolean disponible=true;
	private ArrayList<Rese�a> listaRese�as=new ArrayList<Rese�a>();
	
	public Pelicula (String pNombre,Integer pA�oProd,String pGenero) {
		this.nombre=pNombre;
		this.a�oProd=pA�oProd;
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
    	Double rdo=this.listaRese�as.stream()
    		    .mapToDouble(o -> o.getPuntuacionP()) // Mapea cada rese�a a su puntuaci�n
    		    .average()                           // Calcula la media
    		    .orElse(0.0); 
    	return rdo;
    	//
    	
    }
    public void AgregarRese�a (Rese�a pRese�a) {
    	this.listaRese�as.add(pRese�a);
    	//
    }
    public String getNombrePelicula() {
    	return this.nombre;
    }
    public Integer getA�oProd() {
    	return this.a�oProd;
    }
    public String getGenero() {
    	return this.genero;
    }
}
