import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controladores.GestorPeliculas;
import Controladores.VideoClub;
import java.sql.SQLException;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class AlquilarPeliculaTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testAlquilarPelicula() {
        // Configurar la base de datos o entorno para la prueba
        VideoClub.getGestorGeneral().recuperarBD();

        // Verificar que se cargaron las películas
        JSONArray peliculas = GestorPeliculas.getGestorPeliculas().mostrarPeliculas();
        assertEquals(15, peliculas.length(), "Deberían haber 15 películas en la base de datos.");

        // Caso 1: Alquiler exitoso
        int idUsuario =9;
        String nombrePelicula ="El señor de los anillos";
        int añoPelicula =2001;
        String generoPelicula = "Fantasia";
        //eliminarAlquileresUsuario(idUsuario);
        //ponerDisponiblePelicula(nombrePelicula, añoPelicula, generoPelicula);
       
        int alquilerExitoso = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario, nombrePelicula, añoPelicula, generoPelicula);
        assertEquals(2, alquilerExitoso, "El alquiler debería ser exitoso.");
        assertEquals(1, consultarNumeroAlquileresUsuario(idUsuario), "El usuario debería tener 1 alquiler.");

         
        // Caso 2: Alquiler fallido por película ya alquilada
        int alquilerFall = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario, nombrePelicula, añoPelicula, generoPelicula);
        assertEquals(1, alquilerFall, "El alquiler debería fallar.");
        assertEquals(1, consultarNumeroAlquileresUsuario(idUsuario), "El usuario debería tener 1 alquiler.");

        // Caso 3: Alquiler fallido por usuario no encontrado
        Integer idUsuarioInexistente = 100;
        int alquilerFall2 = VideoClub.getGestorGeneral().alquilarPelicula(idUsuarioInexistente, nombrePelicula, añoPelicula, generoPelicula);
        assertEquals(1, alquilerFall2, "El alquiler debería fallar.");

        // caso 4: Alquiler fallido por película no encontrada
        String nombrePeliculaInexistente = "Pelicula inexistente";
        int alquilerFall3 = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario, nombrePeliculaInexistente, añoPelicula, generoPelicula);
        assertEquals(0, alquilerFall3, "El alquiler debería fallar.");
        assertEquals(1, consultarNumeroAlquileresUsuario(idUsuario), "El usuario debería tener 1 alquiler.");

        // caso 5: Alquilar muchas peliculas y verificar con el tamaño de la lista de alquileres del usuario
        int idUsuario2 = 8;
        String nombrePelicula2 = "Star wars";
        int añoPelicula2 = 2000;
        String generoPelicula2 = "Ciencia ficcion";
        int alquilerExitoso2 = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario2, nombrePelicula2, añoPelicula2, generoPelicula2);
        assertEquals(2, alquilerExitoso2, "El alquiler debería ser exitoso.");

        String nombrePelicula3 = "Star wars 2";
        int añoPelicula3 = 2004;
        String generoPelicula3 = "Ciencia ficcion";
        int alquilerExitoso3 = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario2, nombrePelicula3, añoPelicula3, generoPelicula3);
        assertEquals(2, alquilerExitoso3, "El alquiler debería ser exitoso.");
        
        String nombrePelicula4 = "Tarzan";
        int añoPelicula4 = 2006;
        String generoPelicula4 = "Cartoon";
        int alquilerExitoso4 = VideoClub.getGestorGeneral().alquilarPelicula(idUsuario2, nombrePelicula4, añoPelicula4, generoPelicula4);
        assertEquals(2, alquilerExitoso4, "El alquiler debería ser exitoso.");
        assertEquals(3, consultarNumeroAlquileresUsuario(idUsuario2), "El usuario debería tener 3 alquileres.");

        // Limpiar la base de datos
        eliminarAlquileresUsuario(idUsuario);
        eliminarAlquileresUsuario(idUsuario2);
        ponerDisponiblePelicula(nombrePelicula, añoPelicula, generoPelicula);
        ponerDisponiblePelicula(nombrePelicula2, añoPelicula2, generoPelicula2);
        ponerDisponiblePelicula(nombrePelicula3, añoPelicula3, generoPelicula3);
        ponerDisponiblePelicula(nombrePelicula4, añoPelicula4, generoPelicula4); 
    
    }

    private int consultarNumeroAlquileresUsuario(int idUsuario){
        // Ruta de conexion a la base de datos SQLite
    String url = "jdbc:sqlite:ADSI.db";
    // Consulta SQL con parametros
    String sql = "SELECT COUNT(*) FROM Alquiler WHERE idUsuario = ?";
    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, idUsuario);
        ResultSet rs = pstmt.executeQuery();
        return rs.getInt(1);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return -1;
    }

    private void eliminarAlquileresUsuario(int idUsuario){
        // Ruta de conexion a la base de datos SQLite
        String url = "jdbc:sqlite:ADSI.db";
        // Consulta SQL con parametros
        String sql = "DELETE FROM Alquiler WHERE idUsuario = ?";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void ponerDisponiblePelicula(String nombrePelicula, int añoPelicula, String generoPelicula){
        // Ruta de conexion a la base de datos SQLite
        String url = "jdbc:sqlite:ADSI.db";
        // Consulta SQL con parametros
        String sql = "UPDATE Pelicula SET estaDisponible = 'True' WHERE nombre = ? AND año = ? AND genero = ?";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombrePelicula);
            pstmt.setInt(2, añoPelicula);
            pstmt.setString(3, generoPelicula);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}