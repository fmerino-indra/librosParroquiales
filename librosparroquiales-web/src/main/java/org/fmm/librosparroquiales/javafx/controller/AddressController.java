package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.Address;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

	public class AddressController extends AbstractFxController {

	    @FXML private TextField txtAddCalle;
	    @FXML private TextField txtAddNumero;
	    @FXML private TextField txtAddPiso;
	    @FXML private TextField txtAddLetra;

	    @FXML private TextField txtCodigoPostal;
	    @FXML private TextField txtAddPoblacion;
	    @FXML private TextField txtAddProvincia;
	    @FXML private TextField txtAddPais;			
	    
	    @FXML
	    public void initialize() {
	        // Inicializaciones si fueran necesarias
	    }

	    /**
	     * Extrae los datos de los campos de texto y devuelve un objeto Address.
	     */
	    public Address getAddress() {
	        Address address = new Address();
	        address = getAddress(address);
	        return address;
	    }

	    /**
	     * Extrae los datos de los campos de texto y devuelve el objeto Address actualizado.
	     */
	    public Address getAddress(Address address) {
	        address.setStreetName(txtAddCalle.getText().trim());
	        address.setStreetNumber(txtAddNumero.getText().trim());
	        address.setFloor(txtAddPiso.getText().trim());
	        address.setDoor(txtAddLetra.getText().trim());
	        
	        address.setPostalCode(txtCodigoPostal.getText().trim());
	        address.setCity(txtAddPoblacion.getText().trim());
	        address.setProvince(txtAddProvincia.getText().trim());
	        address.setCountry(txtAddPais.getText().trim());
	        return address;
	    }

	    /**
	     * Rellena los campos de texto a partir de un objeto Address existente.
	     */
	    public void setAddress(Address address) {
	        if (address != null) {
	            txtAddCalle.setText(address.getStreetName());
	            txtAddNumero.setText(address.getStreetNumber());
				txtAddPiso.setText(address.getFloor());
				txtAddLetra.setText(address.getDoor());

	            txtAddPoblacion.setText(address.getCity());
	            txtCodigoPostal.setText(address.getPostalCode());
				txtAddProvincia.setText(address.getProvince());
				txtAddPais.setText(address.getCountry());			
	        }
	    }
	    
	    public void limpiarCampos() {
	        txtAddCalle.clear();
	        txtAddNumero.clear();
			txtAddPiso.clear();
			txtAddLetra.clear();

	        txtAddPoblacion.clear();
	        txtCodigoPostal.clear();
			txtAddProvincia.clear();
			txtAddPais.clear();			
	    }
	}
