package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ParentFormController extends AbstractFxModalController {

    @FXML private TextField txtNif;
    @FXML private TextField txtBirthLocation;
    
    // El motor de JavaFX inyecta el controlador del fragmento de forma automática
    @FXML private AddressController addressFragmentController;

    private Relative relative;
    private GenderEnum gender;
    private boolean guardado = false;

    @FXML
    public void initialize() {
    	alRedimensionar(txtNif);
    	
        // El addressFragmentController ya está disponible aquí si se necesita operar con él
    }

    /**
     * Método para pasarle el objeto Father inicializado por tu servicio antes de mostrar la pantalla
     */
    public void setRelative(Relative relative, GenderEnum gender) {
        this.relative = relative;
        this.gender = gender;
        // Si por algún caso ya tuviera datos previos, los cargaríamos en los campos
        txtNif.setText(relative.getNif());
        txtBirthLocation.setText(relative.getBirthLocation());
        addressFragmentController.setAddress(relative.getAddress());
    }

    @FXML
    void guardar(ActionEvent event) {
        if (relative != null) {
            // Recogemos los datos de la pantalla y mapeamos a la entidad
            relative.setNif(txtNif.getText().trim());
            relative.setBirthLocation(txtBirthLocation.getText().trim());
            
            // Le pedimos la dirección directamente al controlador del fragmento
            relative.setAddress(addressFragmentController.getAddress());
            
            this.guardado = true;
            cerrarModal(txtBirthLocation.getScene());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.guardado = false;
        cerrarModal(txtBirthLocation.getScene());
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Relative getRelative() {
        return relative;
    }
}