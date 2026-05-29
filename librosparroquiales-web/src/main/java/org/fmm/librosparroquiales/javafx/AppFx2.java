package org.fmm.librosparroquiales.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFx2 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-layout.fxml"));

//		loader.setControllerFactory(Launcher.springContext::getBean);
//	    loader.setClassLoader(getClass().getClassLoader());		
		
        Parent root = loader.load();
        
        primaryStage.setTitle("Libros Parroquiales");
        primaryStage.setScene(new Scene(root));//, 800, 600));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}
}
