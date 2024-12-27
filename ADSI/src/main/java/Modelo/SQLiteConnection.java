package Modelo;
import java.sql.Statement;

import Controladores.GestorPeliculas;
import Controladores.GestorUsuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.sql.Date;


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
	    // Ruta de conexi�n a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con par�metros
	    String sql = "SELECT idPelicula FROM Pelicula WHERE nombre = ? AND año = ? AND genero = ?";

	    // Uso de try-with-resources para cerrar autom�ticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los par�metros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setInt(2, pAño);
	        pstmt.setString(3, pGenero);

	        // Ejecutar la consulta
	        try (ResultSet rs = pstmt.executeQuery()) {
	            // Verificar si hay resultados
	            if (rs.next()) {
	                return rs.getInt("idPelicula");
	            } else {
	                // Si no se encuentra la pel�cula
	                System.out.println("Pelicula no encontrada.");
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1; // Indica un error en la ejecuci�n
	    }
	}
	public Integer consultarIdUsuario(String pNombre, String pCorreo, String pContraseña) {
	    // Ruta de conexi�n a la base de datos SQLite
	    String url = "jdbc:sqlite:ADSI.db";

	    // Consulta SQL con par�metros
	    String sql = "SELECT idUsuario FROM Pelicula WHERE nombre = ? AND correo = ? AND contraseña = ?";

	    // Uso de try-with-resources para cerrar autom�ticamente recursos
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Asignar valores a los par�metros de la consulta
	        pstmt.setString(1, pNombre);
	        pstmt.setString(2, pCorreo);
	        pstmt.setString(3, pContraseña);

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
	public void AñadirPeliSol(String pNombrePeli,String pGenero, Integer pAño) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "INSERT INTO Pelicula (nombre, genero, año, esSolicitada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pAño); 
            pstmt.setString(3, "True"); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
        
        public void recuperarUsuarios() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, correo, contraseña, rol FROM Usuarios";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String contraseña = rs.getString("contraseña");
                    String rol = rs.getString("rol");
                    GestorUsuarios.getGestorUsuarios().añadirUsuarioParaRecuperar(nombre, contraseña, correo, rol);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void recuperarPelis() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, genero, año FROM Pelicula WHERE esSolicitada ='False'";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String genero = rs.getString("genero");
                    Integer año = rs.getInt("año");
                    GestorPeliculas.getGestorPeliculas().añadirPeliAlCatalogoParaRecuperar(nombre, año, genero);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void recuperarSolicitudPelis() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, genero, año FROM Pelicula WHERE esSolicitada ='True'";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String genero = rs.getString("genero");
                    Integer año = rs.getInt("año");
                    GestorUsuarios.getGestorUsuarios().añadirSolicitudPeliculaParaRecuperar(nombre, año, genero);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		public void registrarAlquiler(int idUsuario, int idPeli) {
			String url = "jdbc:sqlite:ADSI.db";

	        String sql = "INSERT INTO Alquiler (fechaAlquiler, idUsuario, idPelicula) VALUES (?, ?, ?)";
	        LocalDate localDate = LocalDate.now(); // Fecha actual
	        Date sqlDate = Date.valueOf(localDate); // Convertir a java.sql.Date
	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        	pstmt.setDate(1, sqlDate); 
	            pstmt.setInt(2, idUsuario); 
	            pstmt.setInt(3, idPeli); 
	            pstmt.executeUpdate();

	            System.out.println("Registro insertado correctamente.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
		}
		public Optional<Pelicula> consultarPelicula(String nombrePelicula, String pGenero, Integer pAñoprod) {
		    // Ruta de la base de datos SQLite
		    String url = "jdbc:sqlite:ADSI.db";

		    // Consulta SQL
		    String sql = "SELECT nombre, genero, año FROM Pelicula WHERE esSolicitada = 'False' AND nombre = ? AND año = ? AND genero = ?";

		    try (Connection conn = DriverManager.getConnection(url);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        // Establecer los par�metros de la consulta
		        pstmt.setString(1, nombrePelicula);
		        pstmt.setInt(2, pAñoprod);
		        pstmt.setString(3, pGenero);

		        // Ejecutar la consulta y procesar los resultados
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                // Crear una instancia de Pelicula con los datos obtenidos
		                String nombre = rs.getString("nombre");
		                String genero = rs.getString("genero");
		                int año = rs.getInt("año");

		                Pelicula pelicula = new Pelicula(nombre, año, genero);
	                return Optional.of(pelicula);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    // Retornar un Optional vac�o si no se encuentra la pel�cula
		    return Optional.empty();
		}

        public Usuario obtenerUsuario(Integer idUsuario) {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL
            String sql = "SELECT nombre, correo, contraseña, rol FROM Usuarios WHERE idUsuario = ?";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Establecer los par�metros de la consulta
                pstmt.setInt(1, idUsuario);

                // Ejecutar la consulta y procesar los resultados
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Crear una instancia de Usuario con los datos obtenidos
                        String nombre = rs.getString("nombre");
                        String correo = rs.getString("correo");
                        String contraseña = rs.getString("contraseña");
                        String rol = rs.getString("rol");

                        Usuario usuario = new Usuario(nombre, contraseña, correo, rol);
                        return usuario;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Retornar null si no se encuentra el usuario
            return null;
        } 

    


}
