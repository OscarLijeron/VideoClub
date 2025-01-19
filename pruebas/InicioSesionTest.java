package pruebas;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Controladores.VideoClub;

class InicioSesionTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testInicioSesion() {
        // Configurar la base de datos o entorno para la prueba
        VideoClub.getGestorGeneral().recuperarBD();

        // Caso 1: Inicio de sesión exitoso para usuario admin
        String nombreAdmin = "admin";
        String correoAdmin = "hola";
        String contraseñaAdmin = "hola";
        Integer idAdmin = VideoClub.getGestorGeneral().iniciarSesion(nombreAdmin, contraseñaAdmin, correoAdmin);
        assertNotNull(idAdmin, "El usuario admin debería ser encontrado.");
        boolean esAdmin = VideoClub.getGestorGeneral().comprobarSiEsAdmin(idAdmin);
        assertTrue(esAdmin, "El usuario debería ser identificado como admin.");

        // Caso 2: Inicio de sesión exitoso para usuario no admin
        String nombreUsuario = "raul";
        String correoUsuario = "adios";
        String contraseñaUsuario = "adios";
        Integer idUsuario = VideoClub.getGestorGeneral().iniciarSesion(nombreUsuario, contraseñaUsuario, correoUsuario);
        assertNotNull(idUsuario, "El usuario no admin debería ser encontrado.");
        boolean noEsAdmin = VideoClub.getGestorGeneral().comprobarSiEsAdmin(idUsuario);
        assertFalse(noEsAdmin, "El usuario no debería ser identificado como admin.");

        // Caso 3: Usuario no encontrado
        String nombreInexistente = "inexistente";
        String correoInexistente = "inexistente@videoclub.com";
        String contraseñaInexistente = "wrongPass";
        Integer idInexistente = VideoClub.getGestorGeneral().iniciarSesion(nombreInexistente, contraseñaInexistente, correoInexistente);
        assertNull(idInexistente, "El usuario inexistente no debería ser encontrado.");
    }
}
