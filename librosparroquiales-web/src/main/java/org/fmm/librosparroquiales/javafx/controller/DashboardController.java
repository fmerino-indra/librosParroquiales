package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.javafx.Launcher;
import org.springframework.data.domain.Page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    // Aquí irá tu servicio inyectado por Spring más adelante:
    // private final BaptismService baptismService;
    // public DashboardController(BaptismService baptismService) { ... }

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
                btnModificar.setDisable(false);
            } else {
                btnModificar.setDisable(true);
            }
        });        
        //listaDatos.add(new BaptismRequestMock("24/05/2026", "Martín García López", "Carlos y Elena", "Libro 42, Folio 12", "Registrado"));
        //listaDatos.add(new BaptismRequestMock("26/05/2026", "Sofía Rodríguez Gómez", "Luis y Carmen", "---", "Pendiente"));

        tablaBautismos.setItems(listaDatos);
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
    // =========================================================================
    // CLASE POJO TEMPORAL (MOCK)
    // Borra esta clase interna en cuanto uses tu entidad real BaptismRequest
    // =========================================================================
    /*
    public static class BaptismRequestMock {
        private final String fechaSolicitud;
        private final String bautizado;
        private final String padres;
        private final String registro;
        private final String estado;

        public BaptismRequestMock(String fecha, String bautizado, String padres, String registro, String estado) {
            this.fechaSolicitud = fecha;
            this.bautizado = bautizado;
            this.padres = padres;
            this.registro = registro;
            this.estado = estado;
        }

        public String getFechaSolicitud() { return fechaSolicitud; }
        public String getBautizado() { return bautizado; }
        public String getPadres() { return padres; }
        public String getRegistro() { return registro; }
        public String getEstado() { return estado; }
    }
    */
}
