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

class EliminarCuentasTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void test(){
        //para el caso en el que no hay ninguna cuenta
        String rdo=verCuentasParaEliminar();
        assertEquals("No se encontraron cuentas.",rdo);

        //para el caso en el que hay cuentas
        //añadimos una cuenta para hacer las pruebas
        this.AñadirUsuarioParaProbar("usuarioPrueba", "usuarioPrueba", "usuarioPrueba");
        //comprobamos si se ha añadido
        assertTrue(ExisteUsuario("usuarioPrueba", "usuarioPrueba", "usuarioPrueba"));
        VideoClub.getGestorGeneral().recuperarBD();
        String rdo1=verCuentasParaEliminar();
        assertNotEquals("No se encontraron cuentas.",rdo1);

        //lo eliminamos
        int idUsuario = this.consultarIdUsuario("usuarioPrueba", "usuarioPrueba", "usuarioPrueba");
        VideoClub.getGestorGeneral().eliminarCuenta(idUsuario);
        //comprobamos si existe (para ver si se ha borrado bien)
        assertFalse(ExisteUsuario("usuarioPrueba", "usuarioPrueba", "usuarioPrueba"));

        
    }

    private String verCuentasParaEliminar() {
        JSONArray jsonArray = GestorUsuarios.getGestorUsuarios().mostrarUsuariosParaBorrar();
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
            return "No se encontraron cuentas.";
        }
    }

    private void AñadirUsuarioParaProbar(String pNombre,String pContraseña, String pCorreo) {
            String url = "jdbc:sqlite:ADSI.db";

            String sql = "INSERT INTO Usuarios (nombre, contraseña, correo , rol , idValidador) VALUES (?, ?, ?, ?,?)";
            try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, pNombre); 
                pstmt.setString(2, pContraseña); 
                pstmt.setString(3, pCorreo); 
                pstmt.setString(4, "Usuario");    
                pstmt.setInt(5, 1);
                pstmt.executeUpdate();
            
                
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private boolean ExisteUsuario(String nombreUsuario, String contraseña, String correo) {
			// Ruta de conexion a la base de datos SQLite
		    String url = "jdbc:sqlite:ADSI.db";

		    // Consulta SQL para verificar la existencia
		    String sql = "SELECT 1 FROM Usuarios WHERE nombre = ? AND contraseña = ? AND correo = ?";

		    // Uso de try-with-resources para cerrar automaticamente los recursos
		    try (Connection conn = DriverManager.getConnection(url);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		    	// Establecer los parametros de la consulta
		        pstmt.setString(1, nombreUsuario);
		        pstmt.setString(2, contraseña);
		        pstmt.setString(3, correo);

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

    
}