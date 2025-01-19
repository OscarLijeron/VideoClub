package pruebas;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Controladores.VideoClub;

class ActualizarDatosPersonalesTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testActualizarDatosPersonales() {
        //añadimos un usuario para hacer las pruebas
        String nombreUsuario = "usuarioPruebaActualizacion";
        String correoUsuario = "usuarioPruebaActualizacion";
        String contraseñaUsuario = "usuarioPruebaActualizacion";
        AñadirUsuarioValidado(nombreUsuario, contraseñaUsuario, correoUsuario);
        //tambien añadimos una solicitud para hacer la prueba(podemos usar el metodo registrarse de VideoClub)
        AñadirUsuarioNoValidado("solicitudPrueba", "solicitudPrueba", "solicitudPrueba");
        VideoClub.getGestorGeneral().recuperarBD();
        int idUsuario = consultarIdUsuario(nombreUsuario, correoUsuario, contraseñaUsuario);

        //Caso 1: Valores validos (el nombre y correo no pertenecen a otro usuario o solicitud)
        String nombreNuevo = "usuarioNuevaPruebaActualizacion";
        String correoNuevo = "usuarioPruebaActualizacion";
        String contraseñaNueva = "usuarioPruebaActualizacion";
        int rdo = VideoClub.getGestorGeneral().actualizarDatosPersonales(idUsuario, nombreNuevo, contraseñaNueva, correoNuevo);
        assertEquals(0, rdo);
        //Para comprobar que se ha cambiado los datos podemos hacer una consulta para
        //   ver si existe usando los nuevos datos
        assertTrue(ExisteUsuario(nombreNuevo, contraseñaNueva, correoNuevo));

        //Caso 2: Ya existe una solicitud pendiente de aprobar con ese nombre o correo
        // Para la prueba cambiaré el correo
        nombreNuevo = "usuarioNuevaPruebaActualizacion";
        correoNuevo = "solicitudPrueba";
        contraseñaNueva = "usuarioPruebaActualizacion";
        rdo = VideoClub.getGestorGeneral().actualizarDatosPersonales(idUsuario, nombreNuevo, contraseñaNueva, correoNuevo);
        assertEquals(2, rdo);
        //Comprobamos que no se han modificado
        assertFalse(ExisteUsuario(nombreNuevo, contraseñaNueva, correoNuevo));
        //Limpiamos
        EliminarUsuarioParaPrueba("solicitudPrueba", "solicitudPrueba", "solicitudPrueba");

        //Caso 3: Ya existe un usuario con ese nombre o correo
        //Utilizaremos uno de los usuarios que ya estaban en la BD(por ejemplo,admin)
        //Para la prueba cambiaré el nombre
        nombreNuevo = "admin";
        correoNuevo = "usuarioPruebaActualizacion";
        contraseñaNueva = "usuarioPruebaActualizacion";
        rdo = VideoClub.getGestorGeneral().actualizarDatosPersonales(idUsuario, nombreNuevo, contraseñaNueva, correoNuevo);
        assertEquals(3, rdo);
        //Comprobamos que no se han modificado
        assertFalse(ExisteUsuario(nombreNuevo, contraseñaNueva, correoNuevo));

        //Caso 4: Los datos nuevos son iguales a los iniciales
        nombreNuevo = "usuarioNuevaPruebaActualizacion";
        correoNuevo = "usuarioPruebaActualizacion";
        contraseñaNueva = "usuarioPruebaActualizacion";
        rdo = VideoClub.getGestorGeneral().actualizarDatosPersonales(idUsuario, nombreNuevo, contraseñaNueva, correoNuevo);
        assertEquals(4, rdo);
        //Como no se han modificado pero son iguales, si buscamos con los nuevos datos veremos que si existe
        assertTrue(ExisteUsuario(nombreNuevo, contraseñaNueva, correoNuevo));

        //Limpiamos
        EliminarUsuarioParaPrueba(nombreNuevo, contraseñaNueva, correoNuevo);
            
    }

    private void AñadirUsuarioValidado(String pNombre,String pContraseña, String pCorreo) {
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

    private void AñadirUsuarioNoValidado(String pNombre,String pContraseña, String pCorreo) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Usuarios (nombre, contraseña, correo , rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pNombre); 
            pstmt.setString(2, pContraseña); 
            pstmt.setString(3, pCorreo); 
            pstmt.setString(4, "Usuario");    
            pstmt.executeUpdate();
        
            
        } catch (Exception e) {
            e.printStackTrace();
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

    
}