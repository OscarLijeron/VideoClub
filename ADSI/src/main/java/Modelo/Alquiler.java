package Modelo;

import java.sql.Timestamp;

public class Alquiler {
	private Pelicula peliculaAlquilada;
	private int idUsuario;
	private Timestamp fechaAlquiler;

	public Alquiler(Pelicula pPelicula, int idUsuario) {
		this.peliculaAlquilada=pPelicula;
		this.idUsuario=idUsuario;
		this.fechaAlquiler=new Timestamp(System.currentTimeMillis());
	}

	public Alquiler(Pelicula pPelicula, int idUsuario, Timestamp fechaAlquiler) {
		this.peliculaAlquilada=pPelicula;
		this.idUsuario=idUsuario;
		this.fechaAlquiler=fechaAlquiler;
	}

	public Boolean esEstaPeli(Pelicula pPelicula) {
		if (pPelicula.getNombrePelicula().equals(this.peliculaAlquilada.getNombrePelicula()) && pPelicula.getAñoProd()==this.peliculaAlquilada.getAñoProd() && pPelicula.getGenero().equals(this.peliculaAlquilada.getGenero())) {
			return true;
		} else {
			return false;
		}
	}
	public Pelicula getPelicula() {
		return this.peliculaAlquilada;
	}
	public Timestamp getFechaAlquiler() {
		return this.fechaAlquiler;
	}
	public int getIdUsuario() {
		return this.idUsuario;
	}	
	
	public Boolean estaVencido() {
		// si la fecha actual es mayor a la fecha de alquiler + 48 horas
		return (System.currentTimeMillis() >= this.fechaAlquiler.getTime() + 172800000);
	}
}