package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Controladores.GestorPeliculas;
import Controladores.GestorUsuarios;
import Controladores.VideoClub;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PeticionesPelis extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton Aceptar;
	private JButton Denegar;
	private JPanel arriba;
	private JButton Salir;
	private JLabel titulo;
	private JPanel panel_1;
	private JTextArea peticionesInfo;
	private JTextField tituloPeli;
	private JTextField añoPeli;
	private JTextField generoPeli;
	private Controler controler = null;
	private JButton buscarPeticiones;
	private JTextField idUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeticionesPelis frame = new PeticionesPelis();
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
	public PeticionesPelis() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		contentPane.add(getArriba(), BorderLayout.NORTH);
		contentPane.add(getPanel_1(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getAceptar());
			panel.add(getDenegar());
			panel.add(getTextField_3());
			panel.add(getAñoPeli());
			panel.add(getGeneroPeli());
		}
		return panel;
	}
	private JButton getAceptar() {
		if (Aceptar == null) {
			Aceptar = new JButton("Aceptar peticion");
		}
		Aceptar.addActionListener(getControler());
		return Aceptar;
	}
	private JButton getDenegar() {
		if (Denegar == null) {
			Denegar = new JButton("Denegar peticion");
		}
		Denegar.addActionListener(getControler());
		return Denegar;
	}
	private JPanel getArriba() {
		if (arriba == null) {
			arriba = new JPanel();
			arriba.add(getSalir());
			arriba.add(getTitulo());
			arriba.add(getBuscarPeticiones());
			arriba.add(getIdUsuario());
		}
		return arriba;
	}
	private JButton getSalir() {
		if (Salir == null) {
			Salir = new JButton("Salir");
			Salir.setHorizontalAlignment(SwingConstants.LEFT);
		}
		Salir.addActionListener(getControler());
		return Salir;
	}
	private JLabel getTitulo() {
		if (titulo == null) {
			titulo = new JLabel("Peticiones Peliculas");
			titulo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return titulo;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getPeticionesInfo());
		}
		return panel_1;
	}
	private JTextArea getPeticionesInfo() {
		if (peticionesInfo == null) {
			peticionesInfo = new JTextArea();
		}
		return peticionesInfo;
	}
	private JTextField getTextField_3() {
		if (tituloPeli == null) {
			tituloPeli = new JTextField();
			tituloPeli.setText("Poner titulo ");
			tituloPeli.setColumns(15);
		}
		return tituloPeli;
	}
	private JTextField getAñoPeli() {
		if (añoPeli == null) {
			añoPeli = new JTextField();
			añoPeli.setText("Poner año produccion");
			añoPeli.setColumns(15);
		}
		return añoPeli;
	}
	private JTextField getGeneroPeli() {
		if (generoPeli == null) {
			generoPeli = new JTextField();
			generoPeli.setText("Poner genero");
			generoPeli.setColumns(15);
		}
		return generoPeli;
	}
	private Controler getControler() {
		if (controler == null) {
			controler = new Controler();
		}
		return controler;
	}
	
	private class Controler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(buscarPeticiones)){	
				VideoClub.getGestorGeneral().recuperarBD();
				String idUsuarioTexto=idUsuario.getText();
				int idUsuario=Integer.parseInt(idUsuarioTexto);
				JSONArray jsonArray = GestorUsuarios.getGestorUsuarios().mostrarSolicitudesPeli(idUsuario);
				StringBuilder detalles = new StringBuilder();

				if (jsonArray != null && jsonArray.length() > 0) {
				    for (int i = 0; i < jsonArray.length(); i++) {
				        JSONObject pelicula = jsonArray.getJSONObject(i);
				        String titulo = pelicula.getString("titulo");
				        int anioNum = pelicula.getInt("año");
				        String anio = String.valueOf(anioNum);
				        String genero = pelicula.getString("genero");
				        detalles.append((i + 1) + ". Titulo: " + titulo + ", Año: " + anio + ", Genero: " + genero + "\n");
				    }
				    peticionesInfo.setText(detalles.toString());
				} else {
				    peticionesInfo.setText("No se encontraron solicitudes.");
				}
			}
			if (e.getSource().equals(Aceptar)){			
				String idUsuarioTexto=idUsuario.getText();
				int idUsuario=Integer.parseInt(idUsuarioTexto);
				String tituloP=tituloPeli.getText();
				String añoPTexto=añoPeli.getText();
				int añoP=Integer.parseInt(añoPTexto);
				String generoP=generoPeli.getText();
				VideoClub.getGestorGeneral().eliminarSolicitudPelicula(idUsuario, tituloP, añoP, generoP);
				VideoClub.getGestorGeneral().añadirPeliAlCatalogo(tituloP, añoP, generoP);
							
			}
			if (e.getSource().equals(Denegar)){			
				String idUsuarioTexto=idUsuario.getText();
				int idUsuario=Integer.parseInt(idUsuarioTexto);
				String tituloP=tituloPeli.getText();
				String añoPTexto=añoPeli.getText();
				int añoP=Integer.parseInt(añoPTexto);
				String generoP=generoPeli.getText();
				VideoClub.getGestorGeneral().eliminarSolicitudPelicula(idUsuario, tituloP, añoP, generoP);
				
			}
			
			if (e.getSource().equals(Salir)){
			
				System.exit(0);
				
			}
			
		}
	}
	private JButton getBuscarPeticiones() {
		if (buscarPeticiones == null) {
			buscarPeticiones = new JButton("Buscar Peticiones");
			buscarPeticiones.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		buscarPeticiones.addActionListener(getControler());
		return buscarPeticiones;
	}
	private JTextField getIdUsuario() {
		if (idUsuario == null) {
			idUsuario = new JTextField();
			idUsuario.setText("Pon tu Id de usuario");
			idUsuario.setColumns(15);
		}
		return idUsuario;
	}
}
