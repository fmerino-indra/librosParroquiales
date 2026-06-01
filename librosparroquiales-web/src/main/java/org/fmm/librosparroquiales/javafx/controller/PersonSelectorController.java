package org.fmm.librosparroquiales.javafx.controller;

import java.time.LocalDate;
import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.service.PersonService;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PersonSelectorController extends AbstractFxModalController {

    @FXML private TextField txtFiltro;
    @FXML private TableView<Person> tablaPersonas;
    @FXML private TableColumn<Person, String> colNombre;
    @FXML private TableColumn<Person, String> colApellido1;
    @FXML private TableColumn<Person, String> colApellido2;
    @FXML private TableColumn<Person, LocalDate> colFecNac;
    @FXML private Button btnSeleccionar;

    private Person personaSeleccionada = null;
    private PersonService personService = null;
    private GenderEnum gender = null;
    
    @FXML
    public void initialize() {
        // Mapeos de columnas estándar del modelo Person
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("surname1")); // Adapta a tus getters ficticios o reales
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("surname2")); // Adapta a tus getters ficticios o reales
        colFecNac.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        // Habilitar botón de selección al clicar una fila
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldStr, newStr) -> {
            btnSeleccionar.setDisable(newStr == null);
        });
        personService = getPersonServiceBean();
        
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
    
    @FXML
    void filtrarPersona(ActionEvent event) {
        String filtro = txtFiltro.getText().trim();
        System.out.println("Buscando en PersonRepository donde nombre/apellidos contengan: " + filtro);

        List<Person> personList = null;
//        personList = personService.findPerson(filtro, filtro, filtro);
        personList = personService.findPersonByGender(filtro, filtro, filtro, gender);
        
        cargarDatosEnTabla(personList);
        
        
        
        // Aquí llamarías a tu servicio conectado al Repositorio de Spring:
        // List<Person> resultados = personService.buscarPorFiltro(filtro);
        // tablaPersonas.setItems(FXCollections.observableArrayList(resultados));
    }

    @FXML
    void confirmarSeleccion(ActionEvent event) {
        this.personaSeleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
        
        
        cerrarModal(event);
    }

    @FXML
    void cerrarModal(ActionEvent event) {
        // Cerramos la ventana actual (modal)
        Stage stage = (Stage) txtFiltro.getScene().getWindow();
        stage.close();
    }

    public Person getPersonaSeleccionada() {
        return personaSeleccionada;
    }

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
}