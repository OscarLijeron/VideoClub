package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controladores.GestorUsuarios;
import Controladores.VideoClub;

class PeticionesPeliTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		VideoClub.getGestorGeneral().recuperarBD();
		//Sabemos que el id del admin es 1
		//para ver si da el mensaje cuando no hay solicitudes en la lista de solicitudes del admin 
		String rdo=buscarSolicitudes();
		assertEquals("No se encontraron solicitudes.",rdo);
		
		//Para ver si se solicita bien una pelicula
		
		//Nos aseguramos que la peli no existia
		int idpeliNoValida=this.consultarIdPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		assertFalse(ExisteSolicitudPelicula(1,idpeliNoValida));
		//la añadimos
		this.solicitarPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		//Nos aseguramos que la peli existe ahora
		int idpeliValida=this.consultarIdPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		assertTrue(ExisteSolicitudPelicula(1,idpeliValida));
		
		//para ver si busco bien la solicitud
		String rdo1=buscarSolicitudes();
		assertTrue(compararDetalles(rdo1, "PeliculaPrueba", "2004", "PeliculaPrueba"));
		
		//Para ver si se deniega la solicitud bien
		this.denegar(1, "PeliculaPrueba", 2004, "PeliculaPrueba");
		assertFalse(ExisteSolicitudPelicula(1,idpeliValida));
		
		//Para ver si se acepta bien
		//la solicitamos
		this.solicitarPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		int idpeliValida2=this.consultarIdPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		//la aceptamos
		this.aceptar(1, "PeliculaPrueba", 2004, "PeliculaPrueba");
		assertFalse(ExisteSolicitudPelicula(1,idpeliValida2));
		assertTrue(ExistePelicula("PeliculaPrueba", "PeliculaPrueba", 2004));
		
		//limpiamos 
		int idPeli=this.consultarIdPelicula("PeliculaPrueba", 2004, "PeliculaPrueba");
		EliminarPeli(idPeli);
		assertFalse(ExistePelicula("PeliculaPrueba", "PeliculaPrueba", 2004));
		
		
	}
	//metodo que simula el boton denegar una solicitud pelicula de la vista PeticionesPeli,tiene el mismo codigo
	private void denegar(int idAdmin,String pTitulo, int pAnio, String pGenero) {
		VideoClub.getGestorGeneral().eliminarSolicitudPelicula(idAdmin,pTitulo,pAnio,pGenero);
	}
	//metodo que simula el boton aceptar una solicitud pelicula de la vista PeticionesPeli,tiene el mismo codigo
		private void aceptar(int idAdmin,String pTitulo, int pAnio, String pGenero) {
			VideoClub.getGestorGeneral().eliminarSolicitudPelicula(idAdmin,pTitulo,pAnio,pGenero);
			VideoClub.getGestorGeneral().añadirPeliAlCatalogo(pTitulo, pAnio, pGenero);
		}
	//metodo que simula la vista de solicitar una pelicula de la vista PedirSolicitudPeli,tiene el mismo codigo
	private void solicitarPelicula (String tituloP, Integer anioP,String generoP) {
		GestorUsuarios.getGestorUsuarios().añadirSolicitudPelicula(tituloP, anioP, generoP);
	}
	//metodo que comprueba que una solicitud existe
	private boolean ExisteSolicitudPelicula(Integer idAdmin, Integer idPelicula) {
		// Ruta de conexion a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL para verificar la existencia
	    String sql = "SELECT 1 FROM SolicitudPelicula WHERE idPelicula = ? AND idUsuario = ?";

	    // Uso de try-with-resources para cerrar automaticamente los recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los parametros de la consulta
	        pstmt.setInt(1, idPelicula);
	        pstmt.setInt(2, idAdmin);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Retorna true si encuentra resultados
	            return rs.next();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // Indica un error en la ejecucion
	    }		
	}
	//metodo saca elid de una peli
	private Integer consultarIdPelicula(String pNombre, Integer pAño, String pGenero) {
	    // Ruta de conexion a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con parametros
	    String sql = "SELECT idPelicula FROM Pelicula WHERE nombre = ? AND año = ? AND genero = ?";

	    // Uso de try-with-resources para cerrar automaticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los parametros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setInt(2, pAño);
	        pstmt.setString(3, pGenero);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Verificar si hay resultados
	            if (rs.next()) {
	                return rs.getInt("idPelicula");
	            } else {
	                // Si no se encuentra la pelicula
	                System.out.println("Pelicula no encontrada.");
	                return -1;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecucion
	    }
	}
	//metodo que comprueba que una pelicula existe
		private boolean ExistePelicula(String nombrePelicula, String pGenero, Integer pAñoprod) {
			// Ruta de conexion a la base de datos SQLite
		    String url = "jdbc:sqlite:ADSI.db";

		    // Consulta SQL para verificar la existencia
		    String sql = "SELECT 1 FROM Pelicula WHERE esSolicitada = 'False' AND nombre = ? AND año = ? AND genero = ?";

		    // Uso de try-with-resources para cerrar automaticamente los recursos
		    try (Connection conn = DriverManager.getConnection(url);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		    	// Establecer los parametros de la consulta
		        pstmt.setString(1, nombrePelicula);
		        pstmt.setInt(2, pAñoprod);
		        pstmt.setString(3, pGenero);

		        // Ejecutar la consulta
		        try (ResultSet rs = pstmt.executeQuery()) {
		            // Retorna true si encuentra resultados
		            return rs.next();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false; // Indica un error en la ejecucion
		    }		
		}
	//metodo que simula buscar peticiones 
		private String buscarSolicitudes() {
			JSONArray jsonArray = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesPeli(1);
			StringBuilder detalles = new StringBuilder();

			if (jsonArray != null && jsonArray.length() > 0) {
			    for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject pelicula = jsonArray.getJSONObject(i);
			        String titulo = pelicula.getString("titulo");
			        int anioNum = pelicula.getInt("año");
			        String anio = String.valueOf(anioNum);
			        String genero = pelicula.getString("genero");
			        detalles.append((i + 1) + ". Titulo: " + titulo + ", Año: " + anio + ", Genero: " + genero + "\n");
			    }
			    return detalles.toString();
			} else {
			    return "No se encontraron solicitudes.";
			}
		}
		private boolean compararDetalles(String stringCompleto, String titulo, String anio, String genero) {
		    // Verifica si el string contiene los indicadores clave
		    if (!stringCompleto.contains("Titulo:") || !stringCompleto.contains("Año:") || !stringCompleto.contains("Genero:")) {
		        System.out.println("El formato del string no es valido.");
		        return false;
		    }

		    // Extraer los valores de titulo, año y genero usando split
		    try {
		        String tituloExtraido = stringCompleto.split("Titulo: ")[1].split(", Año: ")[0].trim();
		        String anioExtraido = stringCompleto.split("Año: ")[1].split(", Genero: ")[0].trim();
		        String generoExtraido = stringCompleto.split("Genero: ")[1].trim();

		        // Comparar los valores extraidos con los parametros
		        return titulo.equals(tituloExtraido) && anio.equals(anioExtraido) && genero.equals(generoExtraido);
		    } catch (Exception e) {
		        System.out.println("Error al procesar el string: " + e.getMessage());
		        return false;
		    }
		}
        //metodo que elimina pelis
		private void EliminarPeli(Integer pIdPeli) {
	        String url = "jdbc:sqlite:ADSI.db";

	        String sql = "DELETE FROM Pelicula WHERE idPelicula = ?";

	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, pIdPeli); // id de la peli
	            pstmt.executeUpdate();

	            System.out.println("Pelicula eliminado correctamente.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
