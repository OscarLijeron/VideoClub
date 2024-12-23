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
	
	private  GestorPeliculas() {}
	public static GestorPeliculas getGestorPeliculas() {
		if (miGestorP==null) {
			miGestorP=new GestorPeliculas();
		}
		return miGestorP;
	}
	public void añadirPeliAlCatalogo(Pelicula pPeli) {
		this.catalogoPelis.add(pPeli);
	}
	public JSONArray mostrarCatalogoAmpliado(String pTitulo) {
	    String resultados = buscarPeliculas(pTitulo); // Busca pel�culas por t�tulo
	    JSONArray peliculas = new JSONArray(); // Inicializa un JSONArray vac�o

	    if (resultados != null) { // Verifica si hay resultados
	        JSONObject json = new JSONObject(resultados); // Convierte el String en un JSONObject
	        if (json.has("Search")) { // Verifica si el objeto contiene la clave "Search"
	            peliculas = json.getJSONArray("Search"); // Extrae el array de resultados
	        } else {
	            System.out.println("No se encontraron pel�culas."); // Mensaje si no hay pel�culas
	        }
	    } else {
	        System.out.println("No se pudo obtener resultados de la API."); // Mensaje si la API falla
	    }

	    return peliculas; // Devuelve el cat�logo de pel�culas (vac�o si no hay resultados)
	}

	public static String buscarPeliculas(String titulo) {
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
	
}
