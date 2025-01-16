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
		return this.peliculaAlquilada.equals(pPelicula);
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