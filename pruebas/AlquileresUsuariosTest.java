import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import Controladores.VideoClub;
import java.sql.SQLException;

import Modelo.SQLiteConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


class AlquileresUsuariosTest {

    @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void testAlquileresUsuarios() {
        // Para usuario que tiene 1 solo alquiler
        int idUsuario = 9;
        int idPelicula = 24;
        String fechaAlquiler = "2025-01-01 12:00:00";
        //Para usuario que tiene 3 alquileres
        int idUsuario2 = 8;
        int idPelicula2 = 23;
        String fechaAlquiler2 = "2025-01-16 15:30:00";  // The Pursuit of Happyness,2006,Drama,2025-01-16 15:30:00

        int idPelicula3 = 22;
        String fechaAlquiler3 = "2025-01-16 15:30:00"; //La La Land,2014,Romance,2025-01-16 15:30:00

        int idPelicula4 = 21;
        String fechaAlquiler4 = "2025-01-16 15:30:00"; // Niños Grandes, 2008, Comedia, 2025-01-16 15:30:00
        //--------------------------------------------------------------------------------

        insertarAlquilerUsuario(idUsuario, idPelicula, fechaAlquiler);
        insertarAlquilerUsuario(idUsuario2, idPelicula2, fechaAlquiler2);
        insertarAlquilerUsuario(idUsuario2, idPelicula3, fechaAlquiler3);
        insertarAlquilerUsuario(idUsuario2, idPelicula4, fechaAlquiler4);
        
        VideoClub.getGestorGeneral().recuperarBD();

         // caso 1: Usuario tiene 1 alquiler
         
         JSONArray alquileresUsuario = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario);
         // Verificar que el JSONArray tenga 1 alquiler
         assertEquals(1, alquileresUsuario.length(), "El usuario debería tener 1 alquiler.");
         // Obtener el primer alquiler (debería ser el único en este caso)
         JSONObject alquiler = alquileresUsuario.getJSONObject(0);
 
         // Verificar los detalles del alquiler
         assertEquals("El señor de los anillos", alquiler.getString("titulo"));
         assertEquals(2001, alquiler.getInt("año"));
         assertEquals("Fantasia", alquiler.getString("genero"));
         assertEquals("2025-01-01 12:00:00.0", alquiler.getString("fecha"));
 
         // caso 2: Usuario2 tiene 3 alquileres
         JSONArray alquileresUsuario3 = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario2);
        // Verificar que el JSONArray tenga 3 alquileres
        assertEquals(3, alquileresUsuario3.length(), "El usuario debería tener 3 alquileres.");

        // Verificar los detalles del primer alquiler
        JSONObject alquiler1 = alquileresUsuario3.getJSONObject(0);
        assertEquals("The Pursuit of Happyness", alquiler1.getString("titulo"));
        assertEquals(2006, alquiler1.getInt("año"));
        assertEquals("Drama", alquiler1.getString("genero"));
        assertEquals("2025-01-16 15:30:00.0", alquiler1.getString("fecha"));

        // Verificar los detalles del segundo alquiler
        JSONObject alquiler2 = alquileresUsuario3.getJSONObject(1);
        assertEquals("La La Land", alquiler2.getString("titulo"));
        assertEquals(2014, alquiler2.getInt("año"));
        assertEquals("Romance", alquiler2.getString("genero"));
        assertEquals("2025-01-16 15:30:00.0", alquiler2.getString("fecha"));

        // Verificar los detalles del tercer alquiler
        JSONObject alquiler3 = alquileresUsuario3.getJSONObject(2);
        assertEquals("Niños grandes", alquiler3.getString("titulo"));
        assertEquals(2008, alquiler3.getInt("año"));
        assertEquals("Comedia", alquiler3.getString("genero"));
        assertEquals("2025-01-16 15:30:00.0", alquiler3.getString("fecha"));

        // Limpiar la base de datos y mostrar que usuario y usuario2 no tienen alquileres
        // Limpiar la base de datos con el metodo eliminarAlquilerVencido de VideoClub
        SQLiteConnection.getSQLiteConnection().limpiarAlquileresVencidos();
        JSONArray alqUsuario = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario);
        JSONArray alqUsuario2 = VideoClub.getGestorGeneral().obtenerAlquileresUsuario(idUsuario2);
        assertEquals(0, alqUsuario.length(), "El usuario no debería tener alquileres.");
        assertEquals(0, alqUsuario2.length(), "El usuario no debería tener alquileres.");
        }

    private void insertarAlquilerUsuario(int idUsuario, int idPelicula, String fechaAlquiler) {

            // Ruta de conexion a la base de datos SQLite
        String url = "jdbc:sqlite:ADSI.db";
        // Consulta SQL con parámetros
        String sql = "INSERT INTO Alquiler (fechaAlquiler,idUsuario,idPelicula) VALUES (?, ?, ?)";
        String sql2 = "UPDATE Pelicula SET estaDisponible = 'False' WHERE idPelicula = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            
            pstmt.setString(1, fechaAlquiler);
            pstmt.setInt(2, idUsuario);
            pstmt.setInt(3, idPelicula);
            pstmt.executeUpdate();
            pstmt2.setInt(1, idPelicula);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}