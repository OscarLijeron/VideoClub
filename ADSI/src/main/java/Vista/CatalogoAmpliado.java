package Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;
import org.json.JSONObject;
import Controladores.GestorPeliculas;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

// Estos imports me salen que sobran en el codigo, los comento para que no molesten
//import java.awt.EventQueue;
//import javax.swing.SwingConstants;
//import java.awt.Color;

public class CatalogoAmpliado extends JFrame {

	private JPanel contentPane;
	private JLabel titulo;
	private JPanel panel;
	private JTextField textField;
	private JButton botonBuscar;
	private JLabel lblNewLabel_2;
	private JButton Salir;
	private Controler controler = null;
	private JTextArea infoPelis;
	private int idUsuario;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatalogoAmpliado frame = new CatalogoAmpliado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CatalogoAmpliado(Integer pIdUsuario) {
		idUsuario=pIdUsuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getTitulo(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		contentPane.add(getInfoPelis(), BorderLayout.CENTER);
	}

	private JLabel getTitulo() {
		if (titulo == null) {
			titulo = new JLabel("                                                        Catalogo Ampliado");
		}
		return titulo;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblNewLabel_2());
			panel.add(getTextField());
			panel.add(getBotonBuscar());
			panel.add(getSalir());
		}
		return panel;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBotonBuscar() {
		if (botonBuscar == null) {
			botonBuscar = new JButton("Buscar Pelicula");
		}
		botonBuscar.addActionListener(getControler());
		return botonBuscar;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Titulo:");
		}
		return lblNewLabel_2;
	}
	private Controler getControler() {
		if (controler == null) {
			controler = new Controler();
		}
		return controler;
	}
	
	private class Controler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(botonBuscar)){	
				String tituloPeli = textField.getText(); // Obtener el texto del JTextField
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
				infoPelis.setText(info);			
			}
			
			if (e.getSource().equals(Salir)){
			
				volverAInicioSesion();
				
			}
			
		}
	}
	private JButton getSalir() {
		if (Salir == null) {
			Salir = new JButton("Salir");
		}
		Salir.addActionListener(getControler());
		return Salir;
	}
	private JTextArea getInfoPelis() {
		if (infoPelis == null) {
			infoPelis = new JTextArea();
		}
		return infoPelis;
	}
	 // Metodo para regresar a la vista de inicio de sesion
    private void volverAInicioSesion() {
        JOptionPane.showMessageDialog(this, "Volviendo a inicio de sesion...");
        this.setVisible(false); // Ocultar la vista actual
        InicioSesion.getInicioSesion(idUsuario).mostrar(); // Mostrar la vista de inicio de sesion
    }
}
