import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Controladores.VideoClub;

class RegistroUsuarioTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testRegistroUsuario() {
        
        VideoClub.getGestorGeneral().recuperarBD();

        //Caso 1: Valores validos (el nombre y correo no pertenecen a otro usuario o solicitud)
        String nombreUsuario = "usuarioPruebaRegistro";
        String correoUsuario = "usuarioPruebaRegistro";
        String contraseñaUsuario = "usuarioPruebaRegistro";
        int rdo = VideoClub.getGestorGeneral().registrarse(nombreUsuario, contraseñaUsuario, correoUsuario);
        assertEquals(0, rdo);
        assertTrue(ExisteUsuario(nombreUsuario, contraseñaUsuario, correoUsuario));

        //Caso 2: Si ya existe una solicitud con ese nombre o correo
        //Aprovechamos la solicitud creada anteriormente para comparar
        String nombreUsuario1 = "usuarioPruebaRegistro";
        String correoUsuario1 = "sjadbjbajd";
        String contraseñaUsuario1 = "usuarioPruebaRegistro";
        int rdo1 = VideoClub.getGestorGeneral().registrarse(nombreUsuario1, contraseñaUsuario1, correoUsuario1);
        assertEquals(1, rdo1);
        assertFalse(ExisteUsuario(nombreUsuario1, contraseñaUsuario1, correoUsuario1));
        //Limpiamos 
        this.EliminarUsuarioParaPrueba(nombreUsuario, contraseñaUsuario, correoUsuario);

        //Caso 3: Si ya existe un usuario con ese nombre o correo
        String nombreUsuario2 = "admin";
        String correoUsuario2 = "sjadbjbajd";
        String contraseñaUsuario2 = "usuarioPruebaRegistro";
        int rdo2 = VideoClub.getGestorGeneral().registrarse(nombreUsuario2, contraseñaUsuario2, correoUsuario2);
        assertEquals(3, rdo2);
        assertFalse(ExisteUsuario(nombreUsuario2, contraseñaUsuario2, correoUsuario2));

        
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
    public void EliminarUsuarioParaPrueba(String pNom, String pCont, String pCorreo) {
        String url = "jdbc:sqlite:ADSI.db";
    
        // SQL para borrar el usuario específico
        String sql = "DELETE FROM Usuarios WHERE nombre = ? AND contraseña = ? AND correo = ?";
    
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Asignar los valores a los parámetros
            pstmt.setString(1, pNom);
            pstmt.setString(2, pCont);
            pstmt.setString(3, pCorreo);
    
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
    
}


