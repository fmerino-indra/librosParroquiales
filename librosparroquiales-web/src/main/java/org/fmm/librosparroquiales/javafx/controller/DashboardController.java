package org.fmm.librosparroquiales.javafx.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.javafx.Launcher;
import org.springframework.data.domain.Page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

// Importa aquí tu entidad real cuando corresponda:
// import org.fmm.communitymgmt.model.BaptismRequest;

public class DashboardController {

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnModificar; // El nuevo botón del FXML
    @FXML
    private Button btnCertificate;
    
    @FXML
    private TableView<BaptismRequest> tablaBautismos;

    @FXML
    private TableColumn<BaptismRequest, String> colFecha;

    @FXML
    private TableColumn<BaptismRequest, String> colBautizado;

    @FXML
    private TableColumn<BaptismRequest, String> colPadres;

    @FXML
    private TableColumn<BaptismRequest, String> colRegistro;

    @FXML
    private TableColumn<BaptismRequest, String> colEstado;

    private final ObservableList<BaptismRequest> listaDatos = FXCollections.observableArrayList();

    private BaptismRequestService baptismRequestService = null;
    @FXML
    public void initialize() {
        // 1. Configurar cómo se mapean las propiedades del objeto a las columnas de la tabla
        colFecha.setCellValueFactory(new PropertyValueFactory<>("appointment"));
        colBautizado.setCellValueFactory(new PropertyValueFactory<>("baptizingFullName"));
        colPadres.setCellValueFactory(new PropertyValueFactory<>("parentNames"));
//        colRegistro.setCellValueFactory(new PropertyValueFactory<>("registro"));
        //colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // 2. Cargar datos simulados (Esto se sustituirá por: baptismService.findLatestRequests())
        baptismRequestService = (BaptismRequestService)Launcher.springContext.getBean("BaptismRequestService");
        System.out.println("Servicio del modelo recuperado con éxito: " + (baptismRequestService != null));

        
        // ESCUCHADOR DE SELECCIÓN: Activa o desactiva el botón Modificar
        tablaBautismos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	selectedState();
            } else {
            	notSelectedState();
            }
        });
        
        //listaDatos.add(new BaptismRequestMock("24/05/2026", "Martín García López", "Carlos y Elena", "Libro 42, Folio 12", "Registrado"));
        //listaDatos.add(new BaptismRequestMock("26/05/2026", "Sofía Rodríguez Gómez", "Luis y Carmen", "---", "Pendiente"));

        tablaBautismos.setItems(listaDatos);
        
        tablaBautismos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tablaBautismos.getSelectionModel().getSelectedItem() != null) {
            	irAModificar(null);
            }
        });
        ejecutarBusqueda(null);
    }

    @FXML
    void ejecutarBusqueda(ActionEvent event) {
        String texto = txtBuscar.getText();
        System.out.println("Buscando en la base de datos: " + texto);
        Page<BaptismRequest> resultados = baptismRequestService.getBaptismRequest(texto, null, null);
        
        listaDatos.setAll(resultados.getContent());
        // Aquí harás: 
        // List<BaptismRequest> resultados = baptismService.searchRequests(texto);
        // listaDatos.setAll(resultados);
    }
    @FXML
    void irAModificar(ActionEvent event) {
        BaptismRequest seleccionando = tablaBautismos.getSelectionModel().getSelectedItem();
        if (seleccionando == null) return;

        try {
            // 1. Cargamos el FXML del formulario de forma manual
            java.net.URL url = getClass().getResource("/fxml/baptism-form.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();

            // 2. Extraemos su controlador para pasarle los datos de la fila seleccionada
            BaptismFormController formularioController = loader.getController();
            formularioController.cargarDatosParaModificar(seleccionando);

            // 3. ¡Invocación mágica! Le pasamos la vista al contenedor principal de la aplicación
            MainLayoutController.getInstance().setContenidoCentral(view);

        } catch (Exception e) {
            System.err.println("Error al cambiar a la pantalla de modificación");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleImprimirCertificado() {
        // 1. Obtener la partida seleccionada de la lista del dashboard

        BaptismRequest partidaSeleccionada = tablaBautismos.getSelectionModel().getSelectedItem();

        if (partidaSeleccionada == null) {
            mostrarAlerta("Atención", "Selección requerida", "Por favor, selecciona una partida de la lista para imprimir.");
            return;
        }

        // Deshabilitar botón para evitar doble clic accidental
        btnCertificate.setDisable(true);

        // 2. Ejecutar la generación en segundo plano (Task) para no congelar el Dashboard
        Task<File> jasperTask = new Task<>() {
            @Override
            protected File call() throws Exception {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("ID_PARTIDA", partidaSeleccionada.getId()); 

                // Generamos los bytes del PDF usando el servicio que ya tenías
                byte[] pdfBytes = baptismRequestService.exportToPdf("/reports/partida_bautismo.jrxml", parametros, partidaSeleccionada);

                // Almacenamiento en archivo temporal
                File tempPdf = File.createTempFile("certificado_bautismo_" + partidaSeleccionada.getId() + "_", ".pdf");
                try (FileOutputStream fos = new FileOutputStream(tempPdf)) {
                    fos.write(pdfBytes);
                }
                return tempPdf;
            }
        };

        // Si la generación termina con éxito
        jasperTask.setOnSucceeded(event -> {
            btnCertificate.setDisable(false);
            File pdfFile = jasperTask.getValue();
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile); // Abre el visor de PDF del sistema
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al abrir", "No se pudo abrir el lector de PDF.");
            }
        });

        // Si falla (error en la query de Jasper, base de datos abajo, etc.)
        jasperTask.setOnFailed(event -> {
            btnCertificate.setDisable(false);
            Throwable e = jasperTask.getException();
            e.printStackTrace();
            mostrarAlerta("Error", "Error en Reporte", "No se pudo generar el certificado: " + e.getMessage());
        });

        // Arrancar el hilo
        Thread thread = new Thread(jasperTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void mostrarAlerta(String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.showAndWait();
    }    
    @FXML
    void baptismCertificate(ActionEvent event) {
    	
    }
    private void selectedState() {
    	setSelectedState(true);
    }
    private void notSelectedState() {
    	setSelectedState(false);
    }
    
    private void setSelectedState(boolean selected) {
        btnModificar.setDisable(!selected);
    	btnCertificate.setDisable(!selected);
    }
}
