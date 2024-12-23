package Modelo;
import java.sql.Statement;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLiteConnection {
	private static SQLiteConnection miDB=new SQLiteConnection();
	private  SQLiteConnection() {}
	public static SQLiteConnection getSQLiteConnection() {
		if (miDB==null) {
			miDB=new SQLiteConnection();
		}
		return miDB;
	}
	public static void main(String[] args) {
        String url = "jdbc:sqlite:ADSI.db"; // Ruta al archivo de base de datos

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexion exitosa a SQLite.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void EliminarSolicitudPeli(Integer pIdUsuario, Integer pIdPeli) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "DELETE FROM SolicitudPelicula WHERE idUsuario = ? AND idPelicula = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pIdUsuario); // id del usuario
            pstmt.setInt(2, pIdPeli); // id de la peli
            pstmt.executeUpdate();

            System.out.println("Registro eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void AñadirSolicitudPeli(Integer pIdUsuario, Integer pIdPeli) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO SolicitudPelicula (idUsuario, idPelicula) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pIdUsuario); // id del usuario
            pstmt.setInt(2, pIdPeli); // id de la peli
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void AñadirPeli(String pNombrePeli,String pGenero, Integer pAño) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Pelicula (nombre, genero, año) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pAño); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Integer consultarIdPelicula(String pNombre, Integer pAño, String pGenero) {
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
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecucion
	    }
	}
	public Integer consultarIdUsuario(String pNombre, String pCorreo, String pContraseña) {
	    // Ruta de conexion a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con parametros
	    String sql = "SELECT idUsuario FROM Pelicula WHERE nombre = ? AND correo = ? AND contraseña = ?";

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
	                return rs.getInt("idPelicula");
	            } else {
	                // Si no se encuentra la pelicula
	                System.out.println("Usuario no encontrada.");
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecucion
	    }
	}
	public void AñadirPeliSol(String pNombrePeli,String pGenero, Integer pAño) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Pelicula (nombre, genero, año, esSolicitada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pAño); 
            pstmt.setString(4, "True"); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Optional<Pelicula> consultarPeliculaPorNombre(String pNombrePeli) {
    // Ruta de conexión a la base de datos SQLite
    String url = "jdbc:sqlite:ADSI.db";

    // Consulta SQL con parámetros
    String sql = "SELECT idPelicula, nombre, genero, año, disponible FROM Pelicula WHERE nombre = ?";

    // Uso de try-with-resources para manejar la conexión y recursos automáticamente
    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Asignar valores a los parámetros de la consulta
        pstmt.setString(1, pNombrePeli);

        // Ejecutar la consulta y procesar el resultado
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("idPelicula");
                String nombre = rs.getString("nombre");
                String genero = rs.getString("genero");
                int año = rs.getInt("año");
                boolean disponible = rs.getBoolean("disponible");

                // Crear una instancia de Pelicula
                Pelicula pelicula = new Pelicula(nombre, año, genero);
                pelicula.setId(id); // Si tienes un método setId en Pelicula
                pelicula.setDisponible(disponible);

                // Retornar la película envuelta en un Optional
                return Optional.of(pelicula);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Retornar un Optional vacío si no se encontró la película
    return Optional.empty();
}

public void registrarAlquiler(int idUsuario, String nombrePelicula) {
	// Ruta de conexión a la base de datos SQLite
	String url = "jdbc:sqlite:ADSI.db";

	// Consulta SQL con parámetros
	String sql = "INSERT INTO Alquiler (idUsuario, nombrePelicula) VALUES (?, ?)";

	// Uso de try-with-resources para manejar la conexión y recursos automáticamente
	try (Connection conn = DriverManager.getConnection(url);
		 PreparedStatement pstmt = conn.prepareStatement(sql)) {

		// Asignar valores a los parámetros de la consulta
		pstmt.setInt(1, idUsuario);
		pstmt.setString(2, nombrePelicula);

		// Ejecutar la consulta
		pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
	
}
