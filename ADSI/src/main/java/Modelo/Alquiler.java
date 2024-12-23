package Modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Alquiler {
	private Pelicula peliculaAlquilada;
	private Date fechaAlquiler;

	public Alquiler(Pelicula pPelicula) {
		this.peliculaAlquilada=pPelicula;
		// Obtiene la fecha actual como LocalDate
        LocalDate fechaActual = LocalDate.now();
		this.fechaAlquiler=Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	public Boolean esEstaPeli(Pelicula pPelicula) {
		return this.peliculaAlquilada.equals(pPelicula);
	}

	public Date getFechaAlquiler() {
		return fechaAlquiler;
	}

	public Pelicula getPelicula() {
		return peliculaAlquilada;
	}

}
