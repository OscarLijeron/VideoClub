package Controladores;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Modelo.Pelicula;
public class GestorPeliculas {

	private static GestorPeliculas miGestorP=new GestorPeliculas();
	private ArrayList<Pelicula> catalogoPelis;
	private static final String API_KEY = "9ef47ffb"; // Sustituye con tu clave de API
	private static final String BASE_URL = "http://www.omdbapi.com/";
	
	private  GestorPeliculas() {
		catalogoPelis=new ArrayList<Pelicula>();
	}
	public static GestorPeliculas getGestorPeliculas() {
		if (miGestorP==null) {
			miGestorP=new GestorPeliculas();
		}
		return miGestorP;
	}

    public Pelicula obtenerPeliculaPorNombre(String pNombre) {
        for (Pelicula p : this.catalogoPelis) {
            if (p.getNombrePelicula().equals(pNombre)) {
                return p;
            }
        }
        return null;
    }

    public JSONArray mostrarPeliculas() {
        JSONArray peliculas = new JSONArray(); // Inicializa un JSONArray vac�o

        if (this.catalogoPelis.size() > 0) { // Verifica si hay pel�culas en el cat�logo
            for (Pelicula p : this.catalogoPelis) { // Recorre todas las pel�culas del cat�logo
                JSONObject pelicula = new JSONObject(); // Inicializa un JSONObject vac�o
                pelicula.put("Nombre", p.getNombrePelicula()); // A�ade el nombre de la pel�cula
                pelicula.put("Año", p.getAñoProd()); // A�ade el a�o de producci�n
                pelicula.put("Genero", p.getGenero()); // A�ade el g�nero de la pel�cula
                peliculas.put(pelicula); // A�ade el JSONObject al JSONArray
            }
        } else {
            System.out.println("No hay peliculas en el catalogo."); // Mensaje si no hay pel�culas
        }

        return peliculas; // Devuelve el cat�logo de pel�culas (vac�o si no hay pel�culas)
    }


	public void añadirPeliAlCatalogo(Pelicula pPeli) {
		this.catalogoPelis.add(pPeli);
	}
	public void añadirPeliAlCatalogoParaRecuperar(String pNombre,Integer pAño,String pGenero) {
		Pelicula pPeli=new Pelicula(pNombre,pAño,pGenero);
		this.catalogoPelis.add(pPeli);
	}
	public JSONArray mostrarCatalogoAmpliado(String pTitulo) {
		String resultados = buscarPeliculas(pTitulo);
	    JSONArray jsonArray = new JSONArray();

	    if (resultados != null) {
	        JSONObject json = new JSONObject(resultados);
	        if (json.has("Search")) {
	            JSONArray peliculas = json.getJSONArray("Search");
	            for (int i = 0; i < peliculas.length(); i++) {
	                JSONObject pelicula = peliculas.getJSONObject(i);
	                String imdbId = pelicula.getString("imdbID");
	                String detallesPelicula = obtenerDetallePelicula(imdbId);
	                JSONObject peliculaJson = new JSONObject();
	                peliculaJson.put("Title", pelicula.getString("Title"));
	                peliculaJson.put("Year", pelicula.getString("Year"));
	                peliculaJson.put("imdbID", imdbId);

	                if (detallesPelicula != null) {
	                    JSONObject detallesJson = new JSONObject(detallesPelicula);
	                    peliculaJson.put("Genre", detallesJson.optString("Genre"));
	                } else {
	                    peliculaJson.put("Genre", "Desconocido");
	                }
	                jsonArray.put(peliculaJson);
	            }
	        } else {
	            System.out.println("No se encontraron películas.");
	        }
	    }
	    return jsonArray;
	}

	public String buscarPeliculas(String titulo) {
        try {
            String url = BASE_URL + "?apikey=" + API_KEY + "&s=" + titulo + "&type=movie";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            System.out.println("Error en la solicitud: " + e.getMessage());
        }
        return null;
    }
	public int longitudcatalogo() {
		return this.catalogoPelis.size();
	}
	public static void main(String[] args) {
        try {
            // Obtener instancia del gestor de pel�culas
            GestorPeliculas gestorPeliculas = GestorPeliculas.getGestorPeliculas();

            // T�tulo de pel�cula a buscar (puedes cambiarlo para pruebas)
            String tituloABuscar = "Batman";

            // Llamar al m�todo mostrarCatalogoAmpliado
            JSONArray catalogoAmpliado = gestorPeliculas.mostrarCatalogoAmpliado(tituloABuscar);

            // Mostrar resultados
            if (catalogoAmpliado != null && catalogoAmpliado.length() > 0) {
                System.out.println("Peliculas encontradas:");
                for (int i = 0; i < catalogoAmpliado.length(); i++) {
                    JSONObject pelicula = catalogoAmpliado.getJSONObject(i);
                    System.out.println("Titulo: " + pelicula.getString("Title"));
                    System.out.println("Año: " + pelicula.getString("Year"));
                    System.out.println("Tipo: " + pelicula.getString("Type"));
                    System.out.println("Poster: " + pelicula.getString("Poster"));
                    System.out.println("-------------------------");
                }
            } else {
                System.out.println("No se encontraron peliculas para el titulo proporcionado.");
            }
        } catch (Exception e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
            e.printStackTrace();
        }


        private  String obtenerDetallePelicula(String imdbId) {
        try {
            String url = BASE_URL + "?apikey=" + API_KEY + "&i=" + imdbId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            System.out.println("Error en la solicitud: " + e.getMessage());
        }
        return null;
    }
        
    }
	
}
