import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controladores.GestorUsuarios;
import Controladores.VideoClub;

class VerCuentasModificarTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    void test(){
        //para el caso en el que no hay ninguna cuenta
        String rdo=verCuentasParaModificar();
        assertEquals("No se encontraron cuentas.",rdo);

        //para el caso en el que hay cuentas
        VideoClub.getGestorGeneral().recuperarBD();
        String rdo1=verCuentasParaModificar();
        assertNotEquals("No se encontraron cuentas.",rdo1);
        
    }

    private String verCuentasParaModificar() {
        JSONArray jsonArray = GestorUsuarios.getGestorUsuarios().mostrarUsuariosParaModificar(1);
        StringBuilder detalles = new StringBuilder();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject usuario = jsonArray.getJSONObject(i);
                String nombre = usuario.getString("nombre");
                String contraseña = usuario.getString("contraseña");
                String correo = usuario.getString("correo");
                int id = usuario.getInt("id");
                String idTexto = String.valueOf(id);
                detalles.append((i + 1) + ". Nombre: " + nombre + ", Contraseña: " + contraseña + ", Correo: " + correo + ", Id: " + idTexto + "/n");
            }
            return detalles.toString();
        } else {
            return "No se encontraron cuentas.";
        }
    }

}