package org.fmm.librosparroquiales.javafx.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainLayoutController {

	// 1. Variable estática que guardará la única instancia viva del contenedor principal
    private static MainLayoutController instance;
    
    @FXML
    private StackPane contentArea; // El contenedor central de tu FXML

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnNuevoBautismo;

    @FXML
    private Button btnCertificados;

    // Inyectamos el contexto de Spring para pasárselo al FXMLLoader
//    private final ConfigurableApplicationContext springContext;

//    public MainLayoutController(ConfigurableApplicationContext springContext) {
//        this.springContext = springContext;
//    }

    /**
     * 2. Método estático público para que cualquier controlador de la app 
     * pueda acceder al MainLayoutController sin hacer 'new'.
     */
    public static MainLayoutController getInstance() {
        return instance;
    }    
    
    public MainLayoutController() {
//        this.springContext = springContext;
    }

    @FXML
    public void initialize() {
    	// 3. En cuanto JavaFX crea este controlador, guardamos su referencia
        instance = this;    	
    	
        // Al arrancar, mostramos por defecto el tablero de búsqueda
        cargarPantalla("/fxml/dashboard.fxml");
    }

    /**
     * 4. El método que cambia el contenido central de la pantalla.
     * Recibe cualquier vista (Parent, VBox, AnchorPane...) y la incrusta.
     */
    public void setContenidoCentral(Node view) {
        if (contentArea == null) {
            System.err.println("Error: 'contentArea' no está inicializado en MainLayout.");
            return;
        }

        // Forzamos a que la nueva vista se expanda al máximo
        if (view instanceof javafx.scene.layout.Region) {
            ((javafx.scene.layout.Region) view).setMaxWidth(Double.MAX_VALUE);
            ((javafx.scene.layout.Region) view).setMaxHeight(Double.MAX_VALUE);
        }

        // Reemplazamos el contenido antiguo por el nuevo
        contentArea.getChildren().setAll(view);
    }    
    
    @FXML
    void mostrarDashboard(ActionEvent event) {
        cargarPantalla("/fxml/dashboard.fxml");
    }

    @FXML
    void mostrarNuevoBautismo(ActionEvent event) {
        cargarPantalla("/fxml/baptism-form.fxml");
    }

    @FXML
    void mostrarCertificados(ActionEvent event) {
        cargarPantalla("/fxml/visor-certificado.fxml");
    }

    private void cargarPantalla(String fxmlPath) {
        // 1. Verificamos primero si el recurso existe de verdad
        java.net.URL url = getClass().getResource(fxmlPath);
        
        if (url == null) {
            // Al dejarlo en un mensaje de consola, evitamos que la aplicación aborte
            System.out.println("Navegando hacia: " + fxmlPath + " (El archivo se creará en el siguiente paso)");
            return; 
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();
            setContenidoCentral(view);
        } catch (IOException e) {
            System.err.println("Error al parsear el archivo FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
    
}
