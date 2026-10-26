package org.fmm.librosparroquiales.javafx.controller.person;

import java.time.LocalDate;
import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.service.PersonService;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.fmm.librosparroquiales.javafx.controller.AbstractFxModalController;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PersonSelectorController extends AbstractFxModalController {

	@FXML private Label lblTituloBuscador;
    @FXML private TextField txtFiltro;
    
    @FXML private HBox contendorPaneles;
    
    @FXML private VBox panelTabla;
    @FXML private TableView<Person> tablaPersonas;
    @FXML private TableColumn<Person, String> colNombre;
    @FXML private TableColumn<Person, String> colApellido1;
    @FXML private TableColumn<Person, String> colApellido2;
    @FXML private TableColumn<Person, LocalDate> colFecNac;
    
 // Panel lateral de alta
    @FXML private VBox panelAltaLateral;
/*
    @FXML private TextField txtNuevoNombre;
    @FXML private TextField txtNuevoApellido1;
    @FXML private TextField txtNuevoApellido2;
    @FXML private DatePicker dpFechaNacimiento;
    */
    @FXML private TextField txtBirthLocation;
    
    @FXML private Button btnSeleccionar;

    private Person personaSeleccionada = null;
    private PersonService personService = null;
    private GenderEnum genderCriteria = null;
    
    @FXML private PersonFormFragmentController personFormFragmentController;
    
    @FXML
    public void initialize() {
        // Mapeos de columnas estándar del modelo Person
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("surname1")); // Adapta a tus getters ficticios o reales
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("surname2")); // Adapta a tus getters ficticios o reales
        colFecNac.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        ocultarFormularioAlta(null);
        //panelAltaLateral.setVisible(false);
        //panelAltaLateral.setManaged(false); // Evita que reserve espacio visual si está oculto
        
        // Habilitar botón de selección al clicar una fila
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldStr, newStr) -> {
            btnSeleccionar.setDisable(newStr == null);
        });
        personService = getPersonServiceBean();

        // SplitPane
        SplitPane.setResizableWithParent(panelAltaLateral, Boolean.FALSE);
        
        // Doble clic en la tabla actúa como selección directa
        tablaPersonas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tablaPersonas.getSelectionModel().getSelectedItem() != null) {
                confirmarSeleccion(null);
            }
        });
        //chivato();
        alRedimensionar(txtFiltro);
        
    }
    
    /**
     * Rellena la tabla desde el exterior (la llamará el controlador principal
     * 
     */
    private void cargarDatosEnTabla(List<Person> personList) {
    	tablaPersonas.setItems(FXCollections.observableArrayList(personList));
    	if (personList.isEmpty()) {
    		tablaPersonas.setPlaceholder(new Label("No se encontraron registros."));
    	}
    }
    
    private void busqueda(String criteria, GenderEnum gender) {
        List<Person> personList = null;
//      personList = personService.findPerson(filtro, filtro, filtro);
        personList = personService.findPersonByGender(criteria, criteria, criteria, gender);
        //return personList;
        cargarDatosEnTabla(personList);

    }
    
    @FXML
    void filtrarPersona(ActionEvent event) {
        String filtro = txtFiltro.getText().trim();
        System.out.println("Buscando en PersonRepository donde nombre/apellidos contengan: " + filtro);
        busqueda(filtro, genderCriteria);
    }

    @FXML
    void confirmarSeleccion(ActionEvent event) {
        Person seleccion = tablaPersonas.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
        	this.personaSeleccionada = seleccion;
            cerrarModal();
        }
    }
    @FXML
    void cancelar(ActionEvent event) {
        this.personaSeleccionada = null;
        cerrarModal();
    }

    void cerrarModal() {
        // Cerramos la ventana actual (modal)
        Stage stage = (Stage) txtFiltro.getScene().getWindow();
        stage.close();
    }

    public Person getPersonaSeleccionada() {
        return personaSeleccionada;
    }

//	public GenderEnum getGender() {
//		return gender;
//	}

//	public void setGender(GenderEnum gender) {
//		this.gender = gender;
//	}
	
    public void configurarSelector(String titulo, String criteria, GenderEnum gender) {
        this.lblTituloBuscador.setText(titulo);
        this.genderCriteria = gender;
        this.txtFiltro.setText(criteria);
        // Opcional: lanzar aquí una búsqueda inicial en blanco filtrada por este género
        busqueda(criteria, gender);
    }
    
    @FXML
    void mostrarFormularioAlta(ActionEvent event) {
        panelAltaLateral.setVisible(true);
        panelAltaLateral.setManaged(true);
        //txtNuevoNombre.requestFocus();
        panelTabla.prefWidthProperty().bind(contendorPaneles.widthProperty().multiply(0.1));
        panelAltaLateral.prefWidthProperty().bind(contendorPaneles.widthProperty().multiply(0.9));
        
    }

    @FXML
    void ocultarFormularioAlta(ActionEvent event) {
        panelAltaLateral.setVisible(false);
        panelAltaLateral.setManaged(false);
        limpiarCamposAlta();
        
        panelTabla.prefWidthProperty().bind(contendorPaneles.widthProperty());
    }

    @FXML
    void crearPersonaRapida(ActionEvent event) {
        // Instanciamos el objeto Person básico
        Person nueva = null;
        nueva = this.personFormFragmentController.getPersonaSeleccionada();

        this.personaSeleccionada = nueva;
        cerrarModal();
    	/*
        String nombre = txtNuevoNombre.getText().trim();
        String ap1 = txtNuevoApellido1.getText().trim();
        String ap2 = txtNuevoApellido2.getText().trim();

        if (nombre.isEmpty() || ap1.isEmpty()) {
            // Mostrar una alerta básica si faltan datos obligatorios
            return;
        }

        // Instanciamos el objeto Person básico
        Person nueva = new Person();
        nueva.setName(nombre);
        nueva.setSurname1(ap1);
        nueva.setSurname2(ap2);
        nueva.setGender(this.genderCriteria); // El género se hereda del buscador de origen automáticamente

        this.personaSeleccionada = nueva;
        cerrarModal();
        */
    }
    
    private void limpiarCamposAlta() {
    	personFormFragmentController.clear();
    	txtBirthLocation.clear();
/*    	
        txtNuevoNombre.clear();
        txtNuevoApellido1.clear();
        txtNuevoApellido2.clear();
        */
    }
}