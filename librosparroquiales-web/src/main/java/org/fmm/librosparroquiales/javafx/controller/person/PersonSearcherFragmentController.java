package org.fmm.librosparroquiales.javafx.controller.person;

import java.util.ArrayList;
import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.fmm.librosparroquiales.javafx.controller.AbstractFxController;
import org.fmm.librosparroquiales.javafx.controller.ModalHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PersonSearcherFragmentController extends AbstractFxController implements PersonEventPublisher {
	// --- MODELS --- 
	private Person selectedPerson;
    // --- SERVICIOS ---
//    private PersonService personService;
    
    // --- COMPONENTS
    @FXML private Button btnSearchPerson, btnCleanPerson;
    
    @FXML private Label lblDescription;
    @FXML private TextField txtPersonFullName;
    
    private String title="Search Persons";
    
    private List<PersonChangeListener> listeners = new ArrayList<PersonChangeListener>();
    private boolean processing = false;
    
    private String identifier = null;
    private GenderEnum genderCriteria = null;
    
    @FXML
    void searchPersonModal(ActionEvent event) {
        Person seleccionada = openPersonModalSelector(txtPersonFullName.getText().trim(), this.genderCriteria);

        if (seleccionada != null) {
        	setPersonState(seleccionada);
            fireEvent(new PersonSelectedEvent(null, seleccionada, true, this.identifier));
        }
    }
    @FXML
    void cleanPerson(ActionEvent event) {
    	Person personOld = this.selectedPerson;
    	setNoPersonState();
        fireEvent(new PersonSelectedEvent(personOld, null, true, this.identifier));
    	
    }

    private Person openPersonModalSelector(String criteria, GenderEnum gender) {
    	return ModalHelper.<Person, PersonSelectorController>openModal(
			"/fxml/person/person-selector.fxml",
			this.title,
			this.btnSearchPerson.getScene().getWindow(),
			controller -> controller.configurarSelector("Criterio de búsqueda (nombre y apellidos)",criteria, gender),
			controller -> controller.getPersonaSeleccionada()
    	);
    }    
    public void setPersonState(Person person) {
        this.selectedPerson = person;
    	
        // Inyectamos datos y bloqueamos campos para que no se altere el registro existente
        txtPersonFullName.setText(selectedPerson.getFullName());
        txtPersonFullName.setEditable(false);
        
        // Ajustamos botonera
        btnSearchPerson.setDisable(true);
        btnCleanPerson.setDisable(false);
//        fireEvent(new PersonSelectedEvent(null, person, true));
    }
    
    public void setNoPersonState() {
        this.selectedPerson = null;
    	
        // Inyectamos datos y bloqueamos campos para que no se altere el registro existente
        txtPersonFullName.clear();;
        txtPersonFullName.setEditable(true);
        
        // Ajustamos botonera
        btnSearchPerson.setDisable(false);
        btnCleanPerson.setDisable(true);
        txtPersonFullName.requestFocus();
    }

	public Person getSelectedPerson() {
		return selectedPerson;
	}
	
	public void configureLabels(String title, String label) {
		lblDescription.setText(label);
		this.title = title;
	}

	@Override
	public void addEventListener(PersonChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEventListener(PersonChangeListener listener) {
		listeners.remove(listener);
	}
/*
	@Override
	public void fireEvent(PersonSelectedEvent event) {
		if (isProcessing())
			return;
		else
			processing = true;
		listeners.forEach(listener ->{
			try {
				listener.handleEvent(event);
			} catch (Exception e) {
				processing = false;
				e.printStackTrace();
			}
		});
		processing = false;
	}
*/	
	@Override
	public boolean isProcessing() {
		return processing;
	}
	@Override
	public void setProcessing(boolean processing) {
		this.processing = processing;
	}    
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void setSearchGenderCriteria(GenderEnum genderCriteria) {
		this.genderCriteria = genderCriteria;
	}
	@Override
	public List<PersonChangeListener> getListeners() {
		if (listeners == null)
			listeners = new ArrayList<PersonChangeListener>();
		
		return listeners;
	}
}
