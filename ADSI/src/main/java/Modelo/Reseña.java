package Modelo;

public class Reseña {
	private Float puntuacionP;
	private String comentario;
	private Usuario usuarioAutor;
	
	public Reseña(Float pPuntuacion, String pComentario, Usuario pUsuario) {
		this.comentario=pComentario;
		this.puntuacionP=pPuntuacion;
		this.usuarioAutor=pUsuario;
	}
	public Float getPuntuacionP() {
		return puntuacionP;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
		//
	}
	public Usuario getUsuarioAutor() {
		return usuarioAutor;
	}
	

}
