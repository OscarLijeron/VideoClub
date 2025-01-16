package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controladores.GestorPeliculas;

class CataloAmpliadoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		//Las peliculas del catalogo se buscan por titulo 
		String pTitulo1="Harry";
		String pTitulo2="Terminator";
		String pTitulo3="Bruno";
		//Resultados
		String rdo1 =this.botonBuscar(pTitulo1);
		String rdo2 =this.botonBuscar(pTitulo2);
		String rdo3 =this.botonBuscar(pTitulo3);
		//no son null
		assertNotNull(rdo1);
		assertNotNull(rdo2);
		assertNotNull(rdo3);
		//esta en el titulo
		assertTrue(estaEnTitulo(rdo1,pTitulo1));
		assertTrue(estaEnTitulo(rdo2,pTitulo2));
		assertTrue(estaEnTitulo(rdo2,pTitulo2));
		
		
	}
	//metodo que simula el boton buscar de la vista CatalogoAmpliado,tiene el mismo codigo
	private String botonBuscar(String tituloPeli) {
		
		JSONArray jsonArray=GestorPeliculas.getGestorPeliculas().mostrarCatalogoAmpliado(tituloPeli);
		StringBuilder detalles = new StringBuilder();
		for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject pelicula = jsonArray.getJSONObject(i);
	        String titulo = pelicula.getString("Title");
	        String anio = pelicula.getString("Year");
	        String genero=null;
	        if (pelicula.has("Genre")) {
	             genero = pelicula.getString("Genre");
	            
	        } else {
	        	 genero= "No esta disponible";
	        }
	        
	        detalles.append((i + 1) + ". Titulo: " + titulo + ", Año: " + anio + ", Genero: " + genero + "\n");
	    }
		String info=detalles.toString();
		return info;
	}
	private boolean estaEnTitulo(String stringCompleto, String buscar) {
	    try {
	        // Encuentra las posiciones de "Titulo:" y "año:"
	        int inicioTitulo = stringCompleto.indexOf("Titulo: ") + 7; // Longitud de "Titulo:"
	        int inicioAño = stringCompleto.indexOf("Año: ");

	        // Verifica si ambas etiquetas existen en el string
	        if (inicioTitulo == -1 || inicioAño == -1 || inicioTitulo >= inicioAño) {
	            throw new IllegalArgumentException("Formato del string completo no valido.");
	        }

	        // Extrae el contenido entre "Titulo:" y "año:"
	        String contenidoTitulo = stringCompleto.substring(inicioTitulo, inicioAño).trim();

	        // Verifica si el buscar esta en el contenido del titulo
	        return contenidoTitulo.contains(buscar);
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }
	}

}
