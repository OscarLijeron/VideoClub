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

class SolicitudesRegistroTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testSolicitudesRegistro() {
        VideoClub.getGestorGeneral().recuperarBD();
        //Sabemos que el id del admin es 1
        //Para saber si se da el mensaje de cuando no hay solicitudes
        String rdoBuscar1 = buscarSolicitudesUsuario();
        assertEquals("No se encontraron solicitudes.",rdoBuscar1);

        //Para saber si se encontro solicitud
        //Hacemos una peticion de registro
        int rdo1 = VideoClub.getGestorGeneral().registrarse("usuarioPruebaR", "usuarioPruebaR", "usuarioPruebaR");
        assertEquals(0, rdo1);
        String rdoBuscar2 = buscarSolicitudesUsuario();
        assertNotEquals("No se encontraron solicitudes.", rdoBuscar2);

        //Para probar que funciona el aceptar la solicitud
        int idSolUsuario = consultarIdUsuario("usuarioPruebaR", "usuarioPruebaR", "usuarioPruebaR");
        VideoClub.getGestorGeneral().aceptarSolicitudRegistro(1,idSolUsuario);
        //Vemos si se da ahora el mensaje de cuando no hay solicitudes(ya que se borra la solicitud)
        rdoBuscar1 = buscarSolicitudesUsuario();
        assertEquals("No se encontraron solicitudes.",rdoBuscar1);
        //Vemos si el usuario ahora tiene idValidador, para ello hacemos una consulta
        //    a la BD buscando al usuario por su id e idValidador
        assertTrue(ExisteUsuarioValidado(idSolUsuario,1));
        //Limpiamos
        EliminarUsuarioParaPrueba(idSolUsuario);
        assertFalse(ExisteUsuario(idSolUsuario));

        //Para probar que funciona el denegar la solicitud
        //Añadimos una solicitud
        rdo1 = VideoClub.getGestorGeneral().registrarse("usuarioPruebaR1", "usuarioPruebaR1", "usuarioPruebaR1");
        assertEquals(0, rdo1);
        int idSolUsuario1 = consultarIdUsuario("usuarioPruebaR1", "usuarioPruebaR1", "usuarioPruebaR1");
        VideoClub.getGestorGeneral().eliminarSolicitudRegistro(1,idSolUsuario1);
        //Vemos si se da ahora el mensaje de cuando no hay solicitudes(ya que se borra la solicitud)
        rdoBuscar1 = buscarSolicitudesUsuario();
        assertEquals("No se encontraron solicitudes.",rdoBuscar1);
        //Vemos ahora si no existe el usuario
        assertFalse(ExisteUsuario(idSolUsuario1));

    }

    public Integer consultarIdUsuario(String pNombre, String pCorreo, String pContraseña) {
	    // Ruta de conexion a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con parametros
	    String sql = "SELECT id FROM Usuarios WHERE nombre = ? AND correo = ? AND contraseña = ?";

	    // Uso de try-with-resources para cerrar automaticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los parametros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setString(2, pCorreo);
	        pstmt.setString(3, pContraseña);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Verificar si hay resultados
	            if (rs.next()) {
	                return rs.getInt("id");
	            } else {
	                // Si no se encuentra el usuario
	                System.out.println("Usuario no encontrado.");
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecucion
	    }
	}

    private String buscarSolicitudesUsuario() {
			JSONArray jsonArray = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesUsuario(1);
			StringBuilder detalles = new StringBuilder();

			if (jsonArray != null && jsonArray.length() > 0) {
			    for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject usuario = jsonArray.getJSONObject(i);
			        String nombre = usuario.getString("nombre");
			        String contraseña = usuario.getString("contraseña");
			        String correo = usuario.getString("correo");
			        int id = usuario.getInt("id");
                    String idTexto = String.valueOf(id);
			        detalles.append((i + 1) + ". Nombre: " + nombre + ", Contraseña: " + contraseña + ", Correo: " + correo + ", Id: " + idTexto + "/n");
			    }
			    return detalles.toString();
			} else {
			    return "No se encontraron solicitudes.";
			}
	}

    private boolean ExisteUsuarioValidado(int idUsuario, int idAdmin) {
        // Ruta de conexion a la base de datos SQLite
        String url = "jdbc:sqlite:ADSI.db";

        // Consulta SQL para verificar la existencia
        String sql = "SELECT 1 FROM Usuarios WHERE id=? AND idValidador=?";

        // Uso de try-with-resources para cerrar automaticamente los recursos
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Establecer los parametros de la consulta
            pstmt.setInt(1, idUsuario);
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

    public void EliminarUsuarioParaPrueba(int idUsuario) {
        String url = "jdbc:sqlite:ADSI.db";
    
        // SQL para borrar el usuario específico
        String sql = "DELETE FROM Usuarios WHERE id=?";
    
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Asignar los valores a los parámetros
            pstmt.setInt(1, idUsuario);
    
            // Ejecutar el borrado
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("No se encontró un usuario con los datos proporcionados.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean ExisteUsuario(int idUsuario) {
        // Ruta de conexion a la base de datos SQLite
        String url = "jdbc:sqlite:ADSI.db";

        // Consulta SQL para verificar la existencia
        String sql = "SELECT 1 FROM Usuarios WHERE id=? ";

        // Uso de try-with-resources para cerrar automaticamente los recursos
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Establecer los parametros de la consulta
            pstmt.setInt(1, idUsuario);
            
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




    
    
}
