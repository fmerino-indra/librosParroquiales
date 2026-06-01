package org.fmm.librosparroquiales.javafx.controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AbstractFxModalController extends AbstractFxController {

    protected void cerrarModal(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    
    protected void alRedimensionar(Node aNode) {
    	aNode.sceneProperty().addListener((obsScene, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obsWindow, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        Stage stage = (Stage) newWindow;

                        var width= newScene.getWidth();
                        var height = newScene.getHeight();
                        newScene.getRoot().resize(width, height);
//                        stage.setMinWidth(width);
//                        stage.setMinHeight(height);
                        newScene.getRoot().requestLayout();
                        
                        //vBox.setPrefSize(width, height);
                        
                        
                        
                        
                        // Creamos una línea de retraso (Timeline) de 300 milisegundos
                        // Si se vuelve a ejecutar antes de que acabe, se reinicia (efecto Debounce)
                        javafx.animation.Timeline temporizador = new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(
                                javafx.util.Duration.millis(300), 
                                evento -> {
                                    // ESTE CÓDIGO SOLO SE EJECUTA AL DETENER EL MOVIMIENTO
                                    System.out.println("=== VENTANA REDIMENSIONADA ===");
                                    System.out.println("Ancho final: " + stage.getWidth());
                                    System.out.println("Alto final: " + stage.getHeight());
                                    System.out.println("==============================");
                                }
                            )
                        );

                        // Escuchamos el cambio de ancho de la ventana
                        stage.widthProperty().addListener((obsWidth, oldWidth, newWidth) -> {
                            temporizador.stop(); // Cancela el tic-tac anterior si el usuario sigue arrastrando
                            temporizador.play(); // Inicia la cuenta atrás de nuevo
                        });

                        // Escuchamos también el cambio de alto de la ventana
                        stage.heightProperty().addListener((obsHeight, oldHeight, newHeight) -> {
                            temporizador.stop();
                            temporizador.play();
                        });
                    }
                });
            }
        });    	
    }
    protected void chivato(Node aNode) {
    	// EL CHIVATO DENTRO DEL CONTROLLER:
        // Esperamos a que el componente se pegue a la escena y a la ventana
        aNode.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        Stage stage = (Stage) newWindow;
                        
                        // En cuanto la ventana se muestre de verdad, disparamos el diagnóstico
                        stage.setOnShown(event -> {
                            System.out.println("=== DIAGNÓSTICO DESDE EL CONTROLADOR ===");
                            System.out.println("Ancho de la Ventana (Stage): " + stage.getWidth());
                            System.out.println("Alto de la Ventana (Stage): " + stage.getHeight());
                            System.out.println("Ancho de la Escena (Scene): " + stage.getScene().getWidth());
                            System.out.println("Alto de la Escena (Scene): " + stage.getScene().getHeight());
                            System.out.println("========================================");
                        });
                    }
                });
            }
        });    	
    }
    
}
