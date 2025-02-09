package Modelo;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class api {
    private static final String API_KEY = "9ef47ffb"; // Sustituye con tu clave de API
    private static final String BASE_URL = "http://www.omdbapi.com/";

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

    public static String obtenerDetallePelicula(String imdbId) {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al buscador de peliculas de OMDb");
        
        while (true) {
            System.out.println("\n1. Buscar peliculas");
            System.out.println("2. Obtener detalles de una pelicula");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Introduce el titulo de la pelicula: ");
                    String titulo = scanner.nextLine();
                    String resultados = buscarPeliculas(titulo);
                    if (resultados != null) {
                        JSONObject json = new JSONObject(resultados);
                        if (json.has("Search")) {
                            JSONArray peliculas = json.getJSONArray("Search");
                            for (int i = 0; i < peliculas.length(); i++) {
                                JSONObject pelicula = peliculas.getJSONObject(i);
                                System.out.println((i + 1) + ". " + pelicula.getString("Title") + 
                                                   " (" + pelicula.getString("Year") + ") - ID: " + pelicula.getString("imdbID"));
                            }
                        } else {
                            System.out.println("No se encontraron peliculas.");
                        }
                    }
                    break;
                case "2":
                    System.out.print("Introduce el IMDb ID de la pelicula: ");
                    String imdbId = scanner.nextLine();
                    String detalles = obtenerDetallePelicula(imdbId);
                    if (detalles != null) {
                        JSONObject pelicula = new JSONObject(detalles);
                        System.out.println("Detalles de la pelicula:");
                        System.out.println("Titulo: " + pelicula.optString("Title"));
                        System.out.println("Año: " + pelicula.optString("Year"));
                        System.out.println("Genero: " + pelicula.optString("Genre"));
                        System.out.println("Director: " + pelicula.optString("Director"));
                        System.out.println("Argumento: " + pelicula.optString("Plot"));
                    } else {
                        System.out.println("No se encontro informacion para esa ID.");
                    }
                    break;
                case "3":
                    System.out.println("!Adios!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}

