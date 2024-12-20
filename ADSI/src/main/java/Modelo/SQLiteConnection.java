package Modelo;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
                System.out.println("Conexi�n exitosa a SQLite.");
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
	public void A�adirSolicitudPeli(Integer pIdUsuario, Integer pIdPeli) {
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
	public void A�adirPeli(String pNombrePeli,String pGenero, Integer pA�o) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Pelicula (nombre, genero, a�o) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pA�o); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Integer consultarIdPelicula(String pNombre, Integer pA�o, String pGenero) {
	    // Ruta de conexi�n a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con par�metros
	    String sql = "SELECT idPelicula FROM Pelicula WHERE nombre = ? AND a�o = ? AND genero = ?";

	    // Uso de try-with-resources para cerrar autom�ticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los par�metros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setInt(2, pA�o);
	        pstmt.setString(3, pGenero);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Verificar si hay resultados
	            if (rs.next()) {
	                return rs.getInt("idPelicula");
	            } else {
	                // Si no se encuentra la pel�cula
	                System.out.println("Pel�cula no encontrada.");
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecuci�n
	    }
	}
	public Integer consultarIdUsuario(String pNombre, String pCorreo, String pContrase�a) {
	    // Ruta de conexi�n a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con par�metros
	    String sql = "SELECT idUsuario FROM Pelicula WHERE nombre = ? AND correo = ? AND contrase�a = ?";

	    // Uso de try-with-resources para cerrar autom�ticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los par�metros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setString(2, pCorreo);
	        pstmt.setString(3, pContrase�a);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Verificar si hay resultados
	            if (rs.next()) {
	                return rs.getInt("idPelicula");
	            } else {
	                // Si no se encuentra la pel�cula
	                System.out.println("Usuario no encontrada.");
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecuci�n
	    }
	}
	public void A�adirPeliSol(String pNombrePeli,String pGenero, Integer pA�o) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Pelicula (nombre, genero, a�o, esSolicitada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pA�o); 
            pstmt.setString(3, "True"); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
