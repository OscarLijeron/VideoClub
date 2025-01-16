package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controladores.GestorUsuarios;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Estos imports me salen que sobran en el codigo, los comento para que no molesten
/*import java.awt.EventQueue;
import org.json.JSONArray;
import org.json.JSONObject;
import Controladores.VideoClub;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;*/


public class PedirSolicitudPeli extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField tituloPeli;
	private JTextField anioPeli;
	private JTextField generoPeli;
	private JButton Solicitar;
	private JPanel Arriba;
	private JLabel Titulo;
	private JButton Salir;
	private Controler controler = null;
	private int idUsuario;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PedirSolicitudPeli frame = new PedirSolicitudPeli();
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
	public PedirSolicitudPeli(Integer pIdUsuario) {
		idUsuario=pIdUsuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(getArriba());
		contentPane.add(getPanel());
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getTituloPeli());
			panel.add(getAnioPeli());
			panel.add(getGeneroPeli());
			panel.add(getSolicitar());
		}
		return panel;
	}
	private JTextField getTituloPeli() {
		if (tituloPeli == null) {
			tituloPeli = new JTextField();
			tituloPeli.setText("Poner titulo aqui");
			tituloPeli.setColumns(10);
		}
		return tituloPeli;
	}
	private JTextField getAnioPeli() {
		if (anioPeli == null) {
			anioPeli = new JTextField();
			anioPeli.setText("Poner año produccion aqui");
			anioPeli.setColumns(10);
		}
		return anioPeli;
	}
	private JTextField getGeneroPeli() {
		if (generoPeli == null) {
			generoPeli = new JTextField();
			generoPeli.setText("Poner el genero aqui");
			generoPeli.setColumns(10);
		}
		return generoPeli;
	}
	private JButton getSolicitar() {
		if (Solicitar == null) {
			Solicitar = new JButton("Solicitar");
		}
		Solicitar.addActionListener(getControler());
		return Solicitar;
	}
	private JPanel getArriba() {
		if (Arriba == null) {
			Arriba = new JPanel();
			Arriba.add(getTitulo());
			Arriba.add(getSalir());
		}
		return Arriba;
	}
	private JLabel getTitulo() {
		if (Titulo == null) {
			Titulo = new JLabel("Mandar Solicitud Pelicula");
		}
		return Titulo;
	}
	private JButton getSalir() {
		if (Salir == null) {
			Salir = new JButton("Salir");
		}
		Salir.addActionListener(getControler());
		return Salir;
	}
	private Controler getControler() {
		if (controler == null) {
			controler = new Controler();
		}
		return controler;
	}
	
	private class Controler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(Solicitar)){	
				String tituloP=tituloPeli.getText();
				String anioPTexto=anioPeli.getText();
				int anioP=Integer.parseInt(anioPTexto);
				String generoP=generoPeli.getText();
				GestorUsuarios.getGestorUsuarios().añadirSolicitudPelicula(tituloP, anioP, generoP);
			}
			
			if (e.getSource().equals(Salir)){
			
				volverAInicioSesion();
				
			}
			
		}
	}
	 // Metodo para regresar a la vista de inicio de sesion
    private void volverAInicioSesion() {
        JOptionPane.showMessageDialog(this, "Volviendo al menu principal...");
        this.setVisible(false); // Ocultar la vista actual
        InicioSesion.getInicioSesion(idUsuario).mostrar(); // Mostrar la vista de inicio de sesion
    }
}