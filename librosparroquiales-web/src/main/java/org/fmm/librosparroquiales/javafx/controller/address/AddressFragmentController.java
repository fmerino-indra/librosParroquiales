package org.fmm.librosparroquiales.javafx.controller.address;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.javafx.controller.AbstractFxController;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

	public class AddressFragmentController extends AbstractFxController {

		@FXML private Label lblPanelTitle;
	    @FXML private VBox root;

		@FXML private TextField txtAddCalle;
	    @FXML private TextField txtAddNumero;
	    @FXML private TextField txtAddPiso;
	    @FXML private TextField txtAddLetra;

	    @FXML private TextField txtAddCodigoPostal;
	    @FXML private TextField txtAddPoblacion;
	    @FXML private TextField txtAddProvincia;
	    @FXML private TextField txtAddPais;			
	    
	    @FXML
	    public void initialize() {
	    	ObservableMap<Object, Object> properties = root.getProperties();
	    	properties.addListener((MapChangeListener.Change<? extends Object, ? extends Object> change) -> {
	    		if ("panelTitle".equals(change.getKey())) {
	    			lblPanelTitle.setText(""+change.getValueAdded());
	    		}
	    			    		
	    	    System.out.println(change.getKey());
	    	});
	    	root.getParent();
	    }

	    /**
	     * Extrae los datos de los campos de texto y devuelve un objeto Address.
	     */
	    public Address getOrCreateAddress() {
	        Address address = new Address();
	        address = updateBeanAddress(address);
	        return address;
	    }

	    /**
	     * Extrae los datos de los campos de texto y devuelve el objeto Address actualizado.
	     */
	    public Address updateBeanAddress(Address address) {
	        address.setStreetName(txtAddCalle.getText().trim());
	        address.setStreetNumber(txtAddNumero.getText().trim());
	        address.setFloor(txtAddPiso.getText().trim());
	        address.setDoor(txtAddLetra.getText().trim());
	        
	        address.setPostalCode(txtAddCodigoPostal.getText().trim());
	        address.setCity(txtAddPoblacion.getText().trim());
	        address.setProvince(txtAddProvincia.getText().trim());
	        address.setCountry(txtAddPais.getText().trim());
	        return address;
	    }

	    /**
	     * Rellena los campos de texto a partir de un objeto Address existente.
	     */
	    public void displayAddress(Address address) {
	        if (address != null) {
	        	setSelectedState(address);
	        } else
	        	setSelectedState(null);
	    }
	    
	    public void limpiarCampos() {
			setSelectedState(null);
			
	    }
	    
	    private void setSelectedState(Address address) {
	        boolean isNotNull = (address != null);
	        if (isNotNull) {
	        	if (txtAddCalle !=null)
	        		txtAddCalle.setText(address.getStreetName());
	            txtAddNumero.setText(address.getStreetNumber());
				txtAddPiso.setText(address.getFloor());
				txtAddLetra.setText(address.getDoor());

	            txtAddPoblacion.setText(address.getCity());
	            txtAddCodigoPostal.setText(address.getPostalCode());
				txtAddProvincia.setText(address.getProvince());
				txtAddPais.setText(address.getCountry());			
	        } else {
		        txtAddCalle.clear();
		        txtAddNumero.clear();
				txtAddPiso.clear();
				txtAddLetra.clear();

		        txtAddPoblacion.clear();
		        txtAddCodigoPostal.clear();
				txtAddProvincia.clear();
				txtAddPais.clear();
	        }
	    }
	}
