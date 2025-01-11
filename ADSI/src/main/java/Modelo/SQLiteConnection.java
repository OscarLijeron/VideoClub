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
	public void EliminarPeli(Integer pIdPeli) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "DELETE FROM Pelicula WHERE idPelicula = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pIdPeli); // id de la peli
            pstmt.executeUpdate();

            System.out.println("Pelicula eliminado correctamente.");
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
	                System.out.println("Pelicula no encontrada.");
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
	    String sql = "SELECT id FROM Usuarios WHERE nombre = ? AND correo = ? AND contrase�a = ?";

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
	                return rs.getInt("id");
	            } else {
	                // Si no se encuentra la pel�cula
	                System.out.println("Usuario no encontrado.");
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
            pstmt.setString(4, "True"); 
            pstmt.executeUpdate();

            System.out.println("Registro insertado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void EliminarPeliSol(String pNombrePeli,String pGenero, Integer pA�o) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "DELETE FROM Pelicula WHERE nombre = ? AND genero= ? AND a�o= ? AND esSolicitada= ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pA�o); 
            pstmt.setString(4, "True"); 
            pstmt.executeUpdate();

            System.out.println("Registro eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
        
        public void recuperarUsuarios() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, correo, contrase�a, rol FROM Usuarios";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String contrase�a = rs.getString("contrase�a");
                    String rol = rs.getString("rol");
                    GestorUsuarios.getGestorUsuarios().a�adirUsuarioParaRecuperar(nombre, contrase�a, correo, rol);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void recuperarPelis() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, genero, a�o FROM Pelicula WHERE esSolicitada ='False'";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String genero = rs.getString("genero");
                    Integer a�o = rs.getInt("a�o");
                    GestorPeliculas.getGestorPeliculas().a�adirPeliAlCatalogoParaRecuperar(nombre, a�o, genero);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void recuperarSolicitudPelis() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Usuarios
            String sql = "SELECT nombre, genero, a�o FROM Pelicula WHERE esSolicitada ='True'";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String genero = rs.getString("genero");
                    Integer a�o = rs.getInt("a�o");
                    GestorUsuarios.getGestorUsuarios().a�adirSolicitudPeliculaParaRecuperar(nombre, a�o, genero);
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
		public Optional<Pelicula> consultarPelicula(String nombrePelicula, String pGenero, Integer pA�oprod) {
		    // Ruta de la base de datos SQLite
		    String url = "jdbc:sqlite:ADSI.db";

		    // Consulta SQL
		    String sql = "SELECT nombre, genero, a�o FROM Pelicula WHERE esSolicitada = 'False' AND nombre = ? AND a�o = ? AND genero = ?";

		    try (Connection conn = DriverManager.getConnection(url);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        // Establecer los par�metros de la consulta
		        pstmt.setString(1, nombrePelicula);
		        pstmt.setInt(2, pA�oprod);
		        pstmt.setString(3, pGenero);

		        // Ejecutar la consulta y procesar los resultados
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                // Crear una instancia de Pelicula con los datos obtenidos
		                String nombre = rs.getString("nombre");
		                String genero = rs.getString("genero");
		                int a�o = rs.getInt("a�o");

		                Pelicula pelicula = new Pelicula(nombre, a�o, genero);
	                return Optional.of(pelicula);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    // Retornar un Optional vac�o si no se encuentra la pel�cula
		    return Optional.empty();
		}

        public void RegistrarUsuario(String pNombre,String pContrase�a, String pCorreo, Integer idValidador) {
            String url = "jdbc:sqlite:ADSI.db";

            String sql = "INSERT INTO Usuarios (nombre, contrase�a, correo , rol, idValidador) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, pNombre); 
                pstmt.setString(2, pContrase�a); 
                pstmt.setString(3, pCorreo); 
                pstmt.setString(4, "Usuario");
                pstmt.setInt(5, idValidador);
                pstmt.executeUpdate();
            
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            
        public void EliminarUsuario(Integer pId) {
            String url = "jdbc:sqlite:ADSI.db";
            
            //Primero borramos sus relaciones
            String sql1 = "DELETE FROM SolicitudPelicula WHERE idUsuario = ?";
            String sql2 = "DELETE FROM Alquiler WHERE idUsuario = ?";
            
            //Ahora borramos el usuario
            String sql3 = "DELETE FROM Usuarios WHERE id = ?";
            
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                 PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                 PreparedStatement pstmt3 = conn.prepareStatement(sql3);) {
            
                pstmt1.setInt(1, pId);
                pstmt1.executeUpdate();
            
                pstmt2.setInt(1, pId);
                pstmt2.executeUpdate();
            
                pstmt3.setInt(1, pId);
                pstmt3.executeUpdate();
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void ActualizarDatosUsuario(Integer pId, String nuevoNombre, String nuevaContrase�a, String nuevoCorreo) {
            String url = "jdbc:sqlite:ADSI.db";
        
            // Consulta SQL para actualizar los datos del usuario
            String sql = "UPDATE Usuarios SET nombre = ?, contrase�a = ?, correo = ? WHERE id = ?";
        
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Establecemos los valores en la consulta
                pstmt.setString(1, nuevoNombre);        // Nuevo nombre
                pstmt.setString(2, nuevaContrase�a);    // Nueva contrase�a
                pstmt.setString(3, nuevoCorreo);        // Nuevo correo
                pstmt.setInt(4, pId);                   // ID del usuario a actualizar
        
                // Ejecutamos la actualización
                int filasActualizadas = pstmt.executeUpdate();
        
                // Informamos si se realizó la actualización correctamente
                if (filasActualizadas > 0) {
                    System.out.println("Datos del usuario actualizados correctamente.");
                } else {
                    System.out.println("No se encontró un usuario con el ID especificado.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
            

    


}
