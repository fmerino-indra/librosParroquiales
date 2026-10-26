package org.fmm.librosparroquiales.javafx.controller.address;

import java.util.ArrayList;
import java.util.List;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.javafx.controller.AbstractFxController;
import org.fmm.librosparroquiales.javafx.controller.ModalHelper;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

	public class AddressShortFragmentController extends AbstractFxController implements AddressEventPublisher {

		@FXML private Label lblPanelTitle;
		
	    @FXML private TextField txtAddressId;
	    @FXML private TextField txtShortAddress1;
	    @FXML private TextField txtShortAddress2;
	    
	    private Address selectedAddress = new Address();
	    
	    @FXML private Button btnSearch;
	    @FXML private Button btnClean;
	    @FXML private VBox root;
	    
	    private String title;

	    private List<AddressChangeListener> listeners = new ArrayList<AddressChangeListener>();
	    private boolean processing = false;
	    private String identifier = null;
	    
	    
	    @FXML
	    public void initialize() {
	    	ObservableMap<Object, Object> properties = root.getProperties();
	    	properties.addListener((MapChangeListener.Change<? extends Object, ? extends Object> change) -> {
	    		if ("panelTitle".equals(change.getKey())) {
	    			lblPanelTitle.setText(""+change.getValueAdded());
	    		} else if ("modalTitle".equals(change.getKey())) {
	    			title =""+change.getValueAdded();
	    		}
	    			    		
	    	    System.out.println(change.getKey());
	    	});
	    	root.getParent();
	        // Inicializaciones si fueran necesarias
	    }

	    /**
	     * Extrae los datos de los campos de texto y devuelve un objeto Address.
	     */
	    public Address getSelectedAddress() {
	        return selectedAddress;
	    }

	    /**
	     * Rellena los campos de texto a partir de un objeto Address existente.
	     */
	    public void setSelectedAddress(Address address) {
	    	this.selectedAddress = address;
	    	setSelectedState(address);
	    }
	    
	    private void setSelectedState(Address address) {
	        boolean isNotNull = (address != null);
	        if (isNotNull) {
	        	if (address.getId()!= null)
	        		txtAddressId.setText(address.getId().toString());
	        	txtShortAddress1.setText(address.getFullAddress1());
	        	txtShortAddress2.setText(address.getFullAddress2());
	        } else {
	        	txtAddressId.clear();
		    	txtShortAddress1.clear();
		    	txtShortAddress2.clear();
	        }
	        txtAddressId.setEditable(false);
        	txtShortAddress1.setEditable(!isNotNull);
        	txtShortAddress2.setEditable(!isNotNull);
        	
        	btnSearch.setDisable(isNotNull);
        	btnClean.setDisable(!isNotNull);
	    }
	    
	    @FXML
	    void searchAddressModal(ActionEvent event) {
	    	
	    	if (title == null)
	    		title = "Search Address";
	    	
    		Address selected = openAddressModalSelector(title);
    		if (selected != null) {
    			setSelectedAddress(selected);
    			fireEvent(new AddressSelectedEvent(null, selectedAddress, true, identifier));
    		}

	    }
	    @FXML
	    void cleanAddress(ActionEvent event) {
	    	Address oldAddress = this.selectedAddress;
	    	setSelectedAddress(null);
	        fireEvent(new AddressSelectedEvent(oldAddress, null, true, this.identifier));
	    	
	    }
	    
	    private Address openAddressModalSelector(String title) {
	    	return ModalHelper.<Address, AddressSelectorController>openModal(
				"/fxml/address/address-selector.fxml",
				title,
				this.btnSearch.getScene().getWindow(),
				null,
				controller -> controller.getDireccionSeleccionada()
	    	);
	    }    
	    
		public String getTitle() {
			return title;
		}

		private void setTitle(String title) {
			this.title = title;
		}

		@Override
		public void addEventListener(AddressChangeListener listener) {
			listeners.add(listener);
		}

		@Override
		public void removeEventListener(AddressChangeListener listener) {
			listeners.remove(listener);
		}
/*
		@Override
		public void fireEvent(AddressSelectedEvent event) {
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

		@Override
		public List<AddressChangeListener> getListeners() {
			if (listeners == null)
				listeners = new ArrayList<AddressChangeListener>();
			
			return listeners;
		}

	}
