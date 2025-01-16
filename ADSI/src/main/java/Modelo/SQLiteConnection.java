package Modelo;
import java.sql.Statement;
import Controladores.GestorPeliculas;
import Controladores.GestorUsuarios;
import Controladores.VideoClub;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import java.util.concurrent.ScheduledExecutorService;

// Estos imports me salen que sobran asi que si algun momento llegan a ser necesarios los descomentamos
//import java.time.LocalDate;
//import java.sql.Date;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;

public class SQLiteConnection {
	private static SQLiteConnection miDB=new SQLiteConnection();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
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
	                // Si no se encuentra la pelicula
	                System.out.println("Usuario no encontrado.");
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
	public void EliminarPeliSol(String pNombrePeli,String pGenero, Integer pAño) {
        String url = "jdbc:sqlite:ADSI.db";

        String sql = "DELETE FROM Pelicula WHERE nombre = ? AND genero= ? AND año= ? AND esSolicitada= ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pNombrePeli); 
            pstmt.setString(2, pGenero); 
            pstmt.setInt(3, pAño); 
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
    
        // Consulta SQL para recuperar los datos según las condiciones especificadas
        String sql = "SELECT nombre, correo, contraseña, rol FROM Usuarios " +
                     "WHERE idValidador IS NOT NULL OR (idValidador IS NULL AND rol = 'Admin')";
    
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
        
            // Consulta SQL para recuperar los datos de la tabla Pelicula
            String sql = "SELECT nombre, genero, año, estaDisponible FROM Pelicula WHERE esSolicitada ='False'";
        
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
        
                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String genero = rs.getString("genero");
                    Integer año = rs.getInt("año");
                    String estaDisponibleTexto = rs.getString("estaDisponible");
                    Boolean estaDisponible = "True".equalsIgnoreCase(estaDisponibleTexto);
                    GestorPeliculas.getGestorPeliculas().añadirPeliAlCatalogoParaRecuperar(nombre, año, genero, estaDisponible);
                    System.out.println("Pelicula recuperada: " + nombre + " " + año + " " + genero + " " + estaDisponible);
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
        
            String sql1 = "UPDATE Pelicula SET estaDisponible = 'False' WHERE idPelicula = ?";
            String sql2 = "INSERT INTO Alquiler (fechaAlquiler, idUsuario, idPelicula) VALUES (?, ?, ?)";
        
            // Obtener la fecha y hora actual en el formato deseado
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaAlquilerTexto = ahora.format(formatter); // Genera el texto formateado
        
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                 PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
        
                // Marcar la película como no disponible
                pstmt1.setInt(1, idPeli);
                pstmt1.executeUpdate();
                System.out.println("Película marcada como no disponible.");
        
                // Insertar el alquiler en la base de datos
                pstmt2.setString(1, fechaAlquilerTexto); // Insertar la fecha formateada como texto
                pstmt2.setInt(2, idUsuario);
                pstmt2.setInt(3, idPeli);
                pstmt2.executeUpdate();
        
                System.out.println("Registro Alquiler insertado correctamente.");
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

		        // Establecer los parametros de la consulta
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

		    // Retornar un Optional vacio si no se encuentra la pelicula
		    return Optional.empty();
		}

        public void RegistrarUsuario(Integer idUsuario, Integer idValidador) {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";
        
            // Consulta SQL para actualizar el idValidador de un usuario basado en su correo
            String sql = "UPDATE Usuarios SET idValidador = ? WHERE id = ?";
        
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
                // Establecer los valores de los parámetros
                pstmt.setInt(1, idValidador); // Nuevo valor para idValidador
                pstmt.setInt(2, idUsuario); // Correo del usuario a actualizar
        
                // Ejecutar la actualización
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

        public void ActualizarDatosUsuario(Integer pId, String nuevoNombre, String nuevaContraseña, String nuevoCorreo) {
            String url = "jdbc:sqlite:ADSI.db";
        
            // Consulta SQL para actualizar los datos del usuario
            String sql = "UPDATE Usuarios SET nombre = ?, contraseña = ?, correo = ? WHERE id = ?";
        
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Establecemos los valores en la consulta
                pstmt.setString(1, nuevoNombre);        // Nuevo nombre
                pstmt.setString(2, nuevaContraseña);    // Nueva contraseña
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
        
        public void limpiarAlquileresVencidos() {  
            String url = "jdbc:sqlite:ADSI.db";
            String sqlSeleccionar = "SELECT idUsuario, idPelicula FROM Alquiler WHERE julianday('now') - julianday(fechaAlquiler) >= 2"; 
            String sqlEliminar = "DELETE FROM Alquiler WHERE idUsuario = ? AND idPelicula = ?";
            String sqlActualizarPelicula = "UPDATE Pelicula SET estaDisponible = 'True' WHERE idPelicula = ?";
            String sqlNombreGnroAño = "SELECT nombre, genero, año FROM Pelicula WHERE idPelicula = ?";
        
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmtSeleccionar = conn.prepareStatement(sqlSeleccionar);
                 PreparedStatement stmtEliminar = conn.prepareStatement(sqlEliminar);
                 PreparedStatement stmtActualizarPelicula = conn.prepareStatement(sqlActualizarPelicula);
                 PreparedStatement stmtNombreGnroAño = conn.prepareStatement(sqlNombreGnroAño);
                 ResultSet rs = stmtSeleccionar.executeQuery()) {
        
                while (rs.next()) {
                    int idUsuario = rs.getInt("idUsuario");
                    int idPelicula = rs.getInt("idPelicula");
        
                    // Obtener el nombre, género y año de la película
                    stmtNombreGnroAño.setInt(1, idPelicula);
                    try (ResultSet rsPelicula = stmtNombreGnroAño.executeQuery()) {
                        if (rsPelicula.next()) {
                            String nombre = rsPelicula.getString("nombre");
                            String genero = rsPelicula.getString("genero");
                            int año = rsPelicula.getInt("año");
        
                            // Sincronizar el modelo pasando nombre, género y año
                            VideoClub.getGestorGeneral().eliminarAlquilerVencido(idUsuario, nombre, año, genero);
                        }
                    }
        
                    // Eliminar el alquiler vencido
                    stmtEliminar.setInt(1, idUsuario);
                    stmtEliminar.setInt(2, idPelicula);
                    stmtEliminar.executeUpdate();
        
                    // Marcar la película como disponible
                    stmtActualizarPelicula.setInt(1, idPelicula);
                    stmtActualizarPelicula.executeUpdate();
                }
        
                System.out.println("Limpieza de alquileres vencidos completada.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void iniciarScheduler() {
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    limpiarAlquileresVencidos();
                } catch (Exception e) {
                    System.err.println("Error al ejecutar la limpieza: " + e.getMessage());
                    e.printStackTrace();
                }
            }, 0, 1, TimeUnit.HOURS); // Ejecutar cada 1 hora
        }

        public void recuperarAlquileres() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";

            // Consulta SQL para recuperar los datos de la tabla Alquiler
            String sql = "SELECT idUsuario, idPelicula, fechaAlquiler FROM Alquiler";

            // Formato del campo fecha almacenado como TEXT en la base de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    int idUsuario = rs.getInt("idUsuario");
                    int idPelicula = rs.getInt("idPelicula");
            
                    // Convertir la fechaAlquiler (TEXT) a Timestamp
                    String fechaAlquilerTexto = rs.getString("fechaAlquiler");
                    LocalDateTime fechaAlquilerLocalDateTime = LocalDateTime.parse(fechaAlquilerTexto, formatter);
                    Timestamp fechaAlquiler = Timestamp.valueOf(fechaAlquilerLocalDateTime);

                    // Obtener el nombre, género y año de la película
                    String sqlNombreGnroAño = "SELECT nombre, genero, año FROM Pelicula WHERE idPelicula = ?";
                    try (PreparedStatement stmtNombreGnroAño = conn.prepareStatement(sqlNombreGnroAño)) {
                        stmtNombreGnroAño.setInt(1, idPelicula);
                        try (ResultSet rsPelicula = stmtNombreGnroAño.executeQuery()) {
                            if (rsPelicula.next()) {
                                String nombre = rsPelicula.getString("nombre");
                                String genero = rsPelicula.getString("genero");
                                int año = rsPelicula.getInt("año");

                                // Sincronizar el modelo pasando nombre, género y año
                                VideoClub.getGestorGeneral().recuperarAlquiler(idUsuario, nombre, año, genero, fechaAlquiler);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void recuperarSolicitudesUsuarios() {
            // Ruta de la base de datos SQLite
            String url = "jdbc:sqlite:ADSI.db";
        
            // Consulta SQL para recuperar los usuarios con idValidador NULL, excluyendo los que tienen rol 'admin'
            String sql = "SELECT nombre, correo, contraseña, rol FROM Usuarios " +
                         "WHERE idValidador IS NULL AND rol != 'Admin'";
        
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
        
                // Recorrer los resultados y mostrar los datos por pantalla
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String contraseña = rs.getString("contraseña");
                    String rol = rs.getString("rol");
                    GestorUsuarios.getGestorUsuarios().añadirSolicitudUsuarioParaRecuperar(nombre, contraseña, correo, rol);
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void AñadirSolUsuario(String pNombre,String pContraseña, String pCorreo) {
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
    
}
