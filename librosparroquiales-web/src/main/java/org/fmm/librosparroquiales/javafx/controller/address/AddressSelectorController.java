package org.fmm.librosparroquiales.javafx.controller.address;

import org.fmm.librosparroquiales.common.model.Address;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddressSelectorController {

    @FXML private TextField txtFiltroDireccion;
    
    @FXML private HBox contendorPaneles;
//    @FXML private VBox panelTabla;
    // Panel lateral de alta rápida
    @FXML private VBox panelAltaLateral;
    
    // Tabla y columnas
    @FXML private TableView<Address> tableDirecciones;
    @FXML private TableColumn<Address, String> colCalle;
    @FXML private TableColumn<Address, String> colNumero;
    @FXML private TableColumn<Address, String> colPiso;
    @FXML private TableColumn<Address, String> colLetra;
    @FXML private TableColumn<Address, String> colCp;
    @FXML private TableColumn<Address, String> colPoblacion;
    @FXML private TableColumn<Address, String> colProvincia;
    
    @FXML private AddressFragmentController addressFragmentController; // El nombre debe coincidir con el fx:id + "Controller"
    /*
    @FXML private TextField txtNuevaCalle;
    @FXML private TextField txtNuevoNumero;
    @FXML private TextField txtNuevoPiso;
    @FXML private TextField txtNuevoCp;
    @FXML private TextField txtNuevaLocalidad;
	*/
    
    private Address direccionSeleccionada;
    private ObservableList<Address> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Vincular las columnas de la tabla con los atributos de la entidad Address
        colCalle.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStreetName()));
        colNumero.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStreetNumber()));
        colPiso.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFloor())); // Asumiendo getFloor() para piso/letra
        colCp.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPostalCode()));
        colPoblacion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCity()));

        // 2. Comportamiento del panel lateral: oculto por defecto al arrancar
        panelAltaLateral.setVisible(false);
        panelAltaLateral.setManaged(false);

        // 3. Listener para el filtrado en tiempo real (puedes añadir aquí tu debouncer/timer para JPA)
        txtFiltroDireccion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDirecciones(newValue);
        });
        
        // Carga inicial simulada (aquí llamarías a tu servicio para traer el histórico de direcciones)
        cargarDireccionesDesdeBaseDatos();
    }

    private void cargarDireccionesDesdeBaseDatos() {
        // Esto se rellenará con las direcciones que devuelva tu repositorio JPA
        // masterData.addAll(addressService.findAll());
        // tableDirecciones.setItems(masterData);
    }

    private void filtrarDirecciones(String textoFiltro) {
        // Lógica de filtrado en la lista o petición paginada a base de datos
    }

    // --- ACCIONES DEL PANEL LATERAL ---

    @FXML
    void mostrarFormularioAlta(ActionEvent event) {
        panelAltaLateral.setVisible(true);
        panelAltaLateral.setManaged(true);
        
        tableDirecciones.prefWidthProperty().bind(contendorPaneles.widthProperty().multiply(0.1));
        panelAltaLateral.prefWidthProperty().bind(contendorPaneles.widthProperty().multiply(0.9));
        
        //addressFragmentController.
        //txtNuevaCalle.requestFocus(); // Ponemos el foco en el primer campo
    }

    @FXML
    void ocultarFormularioAlta(ActionEvent event) {
        panelAltaLateral.setVisible(false);
        panelAltaLateral.setManaged(false);
        limpiarCamposAlta();
        tableDirecciones.prefWidthProperty().bind(contendorPaneles.widthProperty());
    }

    @FXML
    void crearDireccionRapida(ActionEvent event) {
        Address nuevaAddress = null;
        nuevaAddress = addressFragmentController.getOrCreateAddress();
    	/*
        String calle = txtNuevaCalle.getText().trim();
        String numero = txtNuevoNumero.getText().trim();
        String localidad = txtNuevaLocalidad.getText().trim();

        // Validación rápida de campos obligatorios
        if (calle.isEmpty() || numero.isEmpty() || localidad.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obligatorios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellene al menos la Calle, el Número y la Localidad.");
            alert.showAndWait();
            return;
        }

        // Instanciamos el nuevo objeto de tu dominio
        Address nuevaAddress = new Address();
        nuevaAddress.setStreet(calle);
        nuevaAddress.setNumber(numero);
        nuevaAddress.setFloor(txtNuevoPiso.getText().trim());
        nuevaAddress.setZipCode(txtNuevoCp.getText().trim());
        nuevaAddress.setCity(localidad);
*/
        // Asignamos el resultado y cerramos la ventana modal
        this.direccionSeleccionada = nuevaAddress;
        cerrarVentana();
    }

    // --- ACCIONES DE LA BOTONERA INFERIOR ---

    @FXML
    void aceptarSeleccion(ActionEvent event) {
        Address seleccion = tableDirecciones.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            this.direccionSeleccionada = seleccion;
            cerrarVentana();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selección requerida");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una dirección de la lista o cree una nueva en el panel lateral.");
            alert.showAndWait();
        }
    }

    @FXML
    void cerrar(ActionEvent event) {
        this.direccionSeleccionada = null;
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtFiltroDireccion.getScene().getWindow();
        stage.close();
    }

    private void limpiarCamposAlta() {
    	addressFragmentController.limpiarCampos();
    }

    // Método público que usará el formulario principal para recuperar el objeto
    public Address getDireccionSeleccionada() {
        return direccionSeleccionada;
    }
}