package Vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Controladores.GestorAlquileres; // Adjust the package name as necessary

public class VistaAlquiler extends Application {
    private GestorAlquileres gestorAlquileres;

    public VistaAlquiler(GestorAlquileres gestor) {
        this.gestorAlquileres = gestor;
    }

    @Override
    public void start(Stage primaryStage) {
        // Crear los componentes de la interfaz
        Label label = new Label("Gestión de Alquileres de Películas");

        Button btnAlquilar = new Button("Alquilar Película");
        Button btnVerAlquiladas = new Button("Ver Películas Alquiladas");
        Button btnSalir = new Button("Salir");

        TextField txtNombrePelicula = new TextField();
        txtNombrePelicula.setPromptText("Nombre de la película");

        // Layout de la vista
        VBox vbox = new VBox(10, label, txtNombrePelicula, btnAlquilar, btnVerAlquiladas, btnSalir);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
        // Acción para alquilar una película
        //btnAlquilar.setOnAction(e -> alquilarPelicula(txtNombrePelicula.getText()));

        // Acción para ver las películas alquiladas
        //btnVerAlquiladas.setOnAction(e -> verPeliculasAlquiladas());

        // Acción para salir
        btnSalir.setOnAction(e -> primaryStage.close());

        // Crear la escena
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setTitle("Videoclub - Alquiler de Películas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

