package org.fmm.librosparroquiales.javafx.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.net.URL;
import java.util.function.Consumer;

public final class ModalHelper {

    // Constructor privado para evitar instanciación de clase de utilidad
    private ModalHelper() {}

    /**
     * Abre una ventana modal de forma genérica.
     *
     * @param <T>           Tipo de dato devuelto por el modal.
     * @param <C>           Tipo del controlador asociado al FXML.
     * @param fxmlPath      Ruta del recurso FXML (ej. "/fxml/address/address-selector.fxml").
     * @param title         Título de la ventana modal.
     * @param ownerWindow   Ventana padre sobre la que se bloquea el modal.
     * @param initializer   Acción para configurar el controlador de forma personalizada.
     * @param resultProvider Función para extraer el resultado del controlador tras el cierre.
     * @return El resultado obtenido del controlador, o null si ocurre un error.
     */
    public static <T, C> T openModal(
            String fxmlPath,
            String title,
            Window ownerWindow,
            Consumer<C> initializer,
            java.util.function.Function<C, T> resultProvider) {

        try {
            URL url = ModalHelper.class.getResource(fxmlPath);
            if (url == null) {
                throw new IllegalArgumentException("No se encontró el archivo FXML en la ruta: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Configuramos el Stage
            Stage modalStage = new Stage();
            modalStage.setTitle(title);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(ownerWindow);

            // Manejo dinámico de dimensiones basado en el nodo raíz (Region)
            if (root instanceof Region region) {
                double width = region.getPrefWidth();
                double height = region.getPrefHeight();

                modalStage.setScene(new Scene(root, width, height));
                modalStage.setWidth(width);
                modalStage.setHeight(height);
                modalStage.setMinWidth(width);
                modalStage.setMinHeight(height);

                // Conservamos tu lógica de diagnóstico y reajuste en el OnShown
                modalStage.setOnShown(event -> {
                    /* System.out.println("=== DIAGNÓSTICO " + title + " ===");
                    System.out.println("Ancho Stage: " + modalStage.getWidth());
                    System.out.println("Alto Stage: " + modalStage.getHeight());
                    */
                    modalStage.setWidth(width);
                    modalStage.setHeight(height);
                    region.setPrefSize(width, height);
                    root.requestLayout();
                });
            } else {
                modalStage.setScene(new Scene(root));
            }

            // Recuperamos el controlador y ejecutamos la inicialización personalizada
            C controller = loader.getController();
            if (initializer != null && controller != null) {
                initializer.accept(controller);
            }

            // Mostramos y esperamos a que el usuario interactúe
            modalStage.showAndWait();

            // Retornamos el resultado usando el proveedor funcional
            return (controller != null) ? resultProvider.apply(controller) : null;

        } catch (Exception e) {
            System.err.println("Error al abrir la ventana modal: " + title);
            e.printStackTrace();
            return null;
        }
    }
}