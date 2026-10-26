package org.fmm.librosparroquiales.javafx.controller.person;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.javafx.controller.AbstractFxController;
import org.fmm.librosparroquiales.javafx.controller.gender.GenderFragmentController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PersonFormFragmentController extends AbstractFxController {

    @FXML private TextField txtName;
    @FXML private TextField txtSurname1;
    @FXML private TextField txtSurname2;
    @FXML private DatePicker dpBirthdate;
    
    private Person selectedPerson = null;
    
    @FXML
    private GenderFragmentController genderFragmentController= null;
    
    @FXML
    public void initialize() {
    	//TODO Convertir a propiedades en FXML
        this.genderFragmentController.setLabelText("Sexo");
        clearForm();
    }
    
    public Person getPersonaSeleccionada() {
    	if (selectedPerson == null) {
    		selectedPerson = new Person();
    		updateBean(selectedPerson);
    	}
		return selectedPerson;
    }

    public void setSelectedPerson(Person person) {
    	this.selectedPerson = person;
    	displayPerson(selectedPerson);
    }
    
    private void displayPerson(Person person) {
    	if (person != null) {
	    	this.txtName.setText(person.getName());
	    	this.txtSurname1.setText(person.getSurname1());
	    	this.txtSurname2.setText(person.getSurname2());
	    	this.dpBirthdate.setValue(person.getBirthday());
	    	this.genderFragmentController.setGender(person.getGender());
    	}
    }
    
    private Person updateBean(Person person) {
    	if (person != null) {
	    	person.setName(txtName.getText());
	    	person.setSurname1(txtSurname1.getText());
	    	person.setSurname2(txtSurname2.getText());
	    	person.setBirthday(dpBirthdate.getValue());
	    	person.setGender(genderFragmentController.getGender());
    	}
    	return person;
    }
    
    @FXML
    void crearPersonaRapida(ActionEvent event) {
        String nombre = txtName.getText().trim();
        String ap1 = txtSurname1.getText().trim();
        String ap2 = txtSurname2.getText().trim();

        if (nombre.isEmpty() || ap1.isEmpty()) {
            // Mostrar una alerta básica si faltan datos obligatorios
            return;
        }

        // Instanciamos el objeto Person básico
        Person nueva = new Person();
        nueva.setName(nombre);
        nueva.setSurname1(ap1);
        nueva.setSurname2(ap2);
        nueva.setBirthday(dpBirthdate.getValue());
        nueva.setGender(this.genderFragmentController.getGender()); // El género se hereda del buscador de origen automáticamente

        this.selectedPerson = nueva;
    }
    
    public void clearForm() {
        txtName.clear();
        txtSurname1.clear();
        txtSurname2.clear();
        dpBirthdate.setValue(null);
        genderFragmentController.clear();
    }
    
    public void clear() {
    	clearForm();
    	this.setSelectedPerson(null);
    }
}