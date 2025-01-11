package Modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Alquiler {
	private Pelicula peliculaAlquilada;
	private int idUsuario;
	private Date fechaAlquiler;

	public Alquiler(Pelicula pPelicula, int idUsuario) {
		this.peliculaAlquilada=pPelicula;
		this.idUsuario=idUsuario;
		// Obtiene la fecha actual como LocalDate
        LocalDate fechaActual = LocalDate.now();
		this.fechaAlquiler=Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public Alquiler(Pelicula pPelicula, int idUsuario, Date fechaAlquiler) {
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
	public Date getFechaAlquiler() {
		return this.fechaAlquiler;
	}
	public int getIdUsuario() {
		return this.idUsuario;
	}	
	
	public Boolean estaVencido() {
		// Obtiene la fecha actual como LocalDate
		LocalDate fechaActual = LocalDate.now();
		Date fechaActualDate=Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long diferencia=fechaActualDate.getTime()-this.fechaAlquiler.getTime();
		long dias=diferencia/(1000*60*60*24);
		return dias>2;
	}
}
