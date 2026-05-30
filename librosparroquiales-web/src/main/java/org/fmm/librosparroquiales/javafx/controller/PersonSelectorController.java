package org.fmm.librosparroquiales.javafx.controller;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PersonSelectorController {

    @FXML private TextField txtFiltro;
    @FXML private TableView<Person> tablaPersonas;
    @FXML private TableColumn<Person, String> colNombre;
    @FXML private TableColumn<Person, String> colApellidos;
    @FXML private TableColumn<Person, LocalDate> colFecNac;
    @FXML private Button btnSeleccionar;

    private Person personaSeleccionada = null;

    @FXML
    public void initialize() {
        // Mapeos de columnas estándar del modelo Person
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("surnames")); // Adapta a tus getters ficticios o reales
        colFecNac.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        // Habilitar botón de selección al clicar una fila
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldStr, newStr) -> {
            btnSeleccionar.setDisable(newStr == null);
        });

        // Doble clic en la tabla actúa como selección directa
        tablaPersonas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tablaPersonas.getSelectionModel().getSelectedItem() != null) {
                confirmarSeleccion(null);
            }
        });
    }

    @FXML
    void filtrarPersona(ActionEvent event) {
        String filtro = txtFiltro.getText().trim();
        System.out.println("Buscando en PersonRepository donde nombre/apellidos contengan: " + filtro);
        
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
}