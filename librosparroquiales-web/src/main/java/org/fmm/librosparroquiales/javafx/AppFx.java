package org.fmm.librosparroquiales.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFx extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
/*		
		var javaVersion = "21";
		var javafxVersion = "21";
		
		var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		var scene = new Scene(new StackPane(label), 800, 600);
		
		primaryStage.setScene(scene);
		primaryStage.show();
*/		
		
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/formulario-bautismo-v2.fxml"));
        
        primaryStage.setTitle("Mi Aplicación JavaFX");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}
}
