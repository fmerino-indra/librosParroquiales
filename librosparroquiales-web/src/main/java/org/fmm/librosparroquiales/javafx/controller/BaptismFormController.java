package org.fmm.librosparroquiales.javafx.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.common.service.PersonService;
import org.fmm.librosparroquiales.common.util.enums.BaptismRelativeEnum;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.fmm.librosparroquiales.javafx.controller.address.AddressChangeListener;
import org.fmm.librosparroquiales.javafx.controller.address.AddressSelectedEvent;
import org.fmm.librosparroquiales.javafx.controller.address.AddressShortFragmentController;
import org.fmm.librosparroquiales.javafx.controller.gender.GenderFragmentController;
import org.fmm.librosparroquiales.javafx.controller.person.PersonChangeListener;
import org.fmm.librosparroquiales.javafx.controller.person.PersonSearcherFragmentController;
import org.fmm.librosparroquiales.javafx.controller.person.PersonSelectedEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BaptismFormController extends AbstractFxController {
    // --- ELEMENTOS DE NAVEGACIÓN ---
    @FXML private Label lblPaso1, lblPaso2, lblPaso3, lblPaso4;
    @FXML private VBox panelPaso1, panelPaso2, panelPaso3, panelPaso4;
    @FXML private Button btnAnterior, btnSiguiente;
    @FXML private Button btnBuscarPadre, btnLimpiarPadre, btnBuscarMadre, btnLimpiarMadre;

    // --- CAMPOS PASO 1 (CANDIDATO) ---
 // Componentes del Candidato
//    @FXML private ToggleButton btnToggleMenor;
//    @FXML private ToggleButton btnToggleMayor;
//    @FXML private ToggleGroup adultGroup;
    
    @FXML private TextField txtCanNombre, txtCanApellido1, txtCanApellido2, txtCanLugarNac;
    @FXML private DatePicker dpCanFechaNac;
    @FXML private GenderFragmentController genderFragmentController;
    //@FXML private ComboBox<String> cbCanSexo;

//    @FXML private HBox panelDireccionCandidatoMayor;
    
    @FXML private VBox panelDireccionCandidatoMayor;
    @FXML private HBox panelDireccionComun;
    @FXML private VBox panelDireccionesSeparadas;    
    
    @FXML private AddressShortFragmentController addressMayorFragmentController; // El nombre debe coincidir con el fx:id + "Controller"
    @FXML private AddressShortFragmentController commonShortAddressFragmentController;
    @FXML private AddressShortFragmentController fatherAddressFragmentController;
    @FXML private AddressShortFragmentController motherAddressFragmentController;
    
    @FXML private PersonSearcherFragmentController fatherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController motherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController godfatherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController godmotherPersonSearcherFragmentController;
    
    @FXML private PersonSearcherFragmentController fatherGrandfatherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController fatherGrandmotherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController motherGrandfatherPersonSearcherFragmentController;
    @FXML private PersonSearcherFragmentController motherGrandmotherPersonSearcherFragmentController;

    @FXML private PersonSearcherFragmentController celebrantPersonSearcherFragmentController;
    
    // --- CAMPOS DE DIRECCIÓN DESGLOSADA ---
    @FXML private TextField txtDirCalle;
    @FXML private TextField txtDirNumero;
    @FXML private TextField txtDirCodigoPostal;
    @FXML private TextField txtDirPoblacion;
    @FXML private TextField txtDirProvincia;
    @FXML private TextField txtDirPiso;
    @FXML private TextField txtDirLetra;
    @FXML private TextField txtDirPais;
    
    // --- CAMPOS PASO 2 (PADRES) ---
    @FXML private TextField txtPadreApellidos, txtMadreApellidos, txtPadresFiliacion;

    // --- CAMPOS PASO 3 (PADRINOS/ABUELOS) ---
    @FXML private TextField txtPadrino, txtMadrina, txtAbueloPat, txtAbuelaPat, txtAbueloMat, txtAbuelaMat;

    // --- CAMPOS PASO 4 (REGISTRO) ---
    @FXML private DatePicker dpFechaCeleb;
    @FXML private TextField txtMinistro, txtRegLibro, txtRegFolio, txtRegNumero;
    @FXML private TextArea txtObservaciones;

    @FXML private Label lblBadgeEdad;
    
    private int pasoActual = 1;
    
    // --- SERVICIOS ---
    private BaptismRequestService baptismRequestService;
    private PersonService personService;
//    private RelativeService relativeService;
    
    // --- REFERENCIAS INTERNAS DEL MODELO ---
    private BaptismRequest baptismRequest = new BaptismRequest();

    private BaptismalCandidate candidate = null;
    private Father father = null;
    private Mother mother = null;
    private Person godfather = null;
    private Person godmother = null;
    
    private GrandFather fatherGrandFather = null;
    private GrandMother fatherGrandMother = null;
    private GrandFather motherGrandFather = null;
    private GrandMother motherGrandMother = null;

    // Instancias de datos retenidas en el formulario principal
    private Address candidateAddress;
    private Address commonAddress;
    private Address fatherAddress;
    private Address motherAddress;
	
    
    private Person celebrant;
    
    @FXML
    public void initialize() {
        // Recuperamos los servicios necesariosl
    	baptismRequestService = this.getBaptismRequestServiceBean();
    	personService = this.getPersonServiceBean();
  //  	relativeService = this.getRelativeServiceBean();
    	
        actualizarVisibilidadPasos();
        //initStepOne();
        this.genderFragmentController.setLabelText("Sexo");
        
        fatherPersonSearcherFragmentController.setIdentifier("FATHER");
        fatherPersonSearcherFragmentController.configureLabels("Buscar Padre", "Datos del Padre");
        fatherPersonSearcherFragmentController.addEventListener(this.personListener);
        fatherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.M);

        motherPersonSearcherFragmentController.setIdentifier("MOTHER");
        motherPersonSearcherFragmentController.configureLabels("Buscar Madre", "Datos de la Madre");
        motherPersonSearcherFragmentController.addEventListener(this.personListener);
        motherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.F);

//Nombre y Apellidos del Padrino
        godfatherPersonSearcherFragmentController.setIdentifier("GODFATHER");
        godfatherPersonSearcherFragmentController.configureLabels("Buscar Padrino", "Nombre y apellidos del Padrino");
        godfatherPersonSearcherFragmentController.addEventListener(this.personListener);
        godfatherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.M);

//Nombre y Apellidos de la Madrina
        godmotherPersonSearcherFragmentController.setIdentifier("GODMOTHER");
        godmotherPersonSearcherFragmentController.configureLabels("Buscar Madrina", "Nombre y apellidos de la Madrina");
        godmotherPersonSearcherFragmentController.addEventListener(this.personListener);
        godmotherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.F);

        
        fatherGrandfatherPersonSearcherFragmentController.setIdentifier("FATHERGRANDFATHER");
        fatherGrandfatherPersonSearcherFragmentController.configureLabels("Buscar Abuelo", "Abuelo paterno");
        fatherGrandfatherPersonSearcherFragmentController.addEventListener(this.personListener);
        fatherGrandfatherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.M);

        fatherGrandmotherPersonSearcherFragmentController.setIdentifier("FATHERGRANDMOTHER");
        fatherGrandmotherPersonSearcherFragmentController.configureLabels("Buscar Abuela", "Abuela paterna");
        fatherGrandmotherPersonSearcherFragmentController.addEventListener(this.personListener);
        fatherGrandmotherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.F);

        motherGrandfatherPersonSearcherFragmentController.setIdentifier("MOTHERGRANDFATHER");
        motherGrandfatherPersonSearcherFragmentController.configureLabels("Buscar Abuelo", "Abuelo materno");
        motherGrandfatherPersonSearcherFragmentController.addEventListener(this.personListener);
        motherGrandfatherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.M);

        motherGrandmotherPersonSearcherFragmentController.setIdentifier("MOTHERGRANDMOTHER");
        motherGrandmotherPersonSearcherFragmentController.configureLabels("Buscar Abuela", "Abuela materna");
        motherGrandmotherPersonSearcherFragmentController.addEventListener(this.personListener);
        motherGrandmotherPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.F);

        celebrantPersonSearcherFragmentController.setIdentifier("CELEBRANT");
        celebrantPersonSearcherFragmentController.configureLabels("Search Celebrant Minister", "Celebrant");
        celebrantPersonSearcherFragmentController.addEventListener(this.personListener);
        celebrantPersonSearcherFragmentController.setSearchGenderCriteria(GenderEnum.M);

        addressMayorFragmentController.getSelectedAddress();
        commonShortAddressFragmentController.getSelectedAddress();
        fatherAddressFragmentController.getSelectedAddress();
        motherAddressFragmentController.getSelectedAddress();

        addressMayorFragmentController.addEventListener(addressListener);
        commonShortAddressFragmentController.addEventListener(addressListener);
        fatherAddressFragmentController.addEventListener(addressListener);
        motherAddressFragmentController.addEventListener(addressListener);
        
        //if (baptismRequest )
        
        commonShortAddressFragmentController.setIdentifier("COMMONADDRESS");
        
        
        
        //btnToggleMayor.setDisable(true);
        //btnToggleMenor.setDisable(true);
        dpCanFechaNac.valueProperty().addListener((observable, oldValue, newValue) -> {
        	actualizarBadgeEdad();
            /*
        	if (isAdult(newValue))
        		selectMayor();
        	else
        		selectMenor();
        		*/
        });
        
    }
/*    
    @FXML
    void cambiarModoEdad(ActionEvent event) {
        if (btnToggleMenor.isSelected()) {
        } else {
        }
    }    
    
    private void selectMenor() {
        btnToggleMenor.setSelected(true);    	
        cambiarModoEdad(null);
    }
    private void selectMayor() {
        btnToggleMayor.setSelected(true);    	
        cambiarModoEdad(null);
    }
*/    
    
 // Método a llamar cuando el cálculo automático de edad se dispare
    private void actualizarBadgeEdad() {
    	
        if (isAdult(dpCanFechaNac.getValue())) {
            lblBadgeEdad.setText("Mayor de edad");
            // Verde suave indicando mayoría de edad
            lblBadgeEdad.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 4 12 4 12; -fx-font-size: 12px; -fx-font-weight: bold;");
            
        	System.out.println("[FMMP] Mayor selected");
            panelDireccionCandidatoMayor.setVisible(true);
            panelDireccionCandidatoMayor.setManaged(true);
            //addressMayorFragmentController.displayAddress(this.baptismRequest.getCandidate().getAddress());
            addressMayorFragmentController.setSelectedAddress(this.baptismRequest.getCandidate().getAddress());
            
        } else {
            lblBadgeEdad.setText("Menor de edad");
            // Azul clásico indicando minoría de edad
            lblBadgeEdad.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 4 12 4 12; -fx-font-size: 12px; -fx-font-weight: bold;");
            
        	System.out.println("[FMMP] Menor selected");
            panelDireccionCandidatoMayor.setVisible(false);
            panelDireccionCandidatoMayor.setManaged(false);
            
        }
    }
    private boolean isAdult(LocalDate birthDate) {
    	return birthDate != null
    			&& !birthDate.plusYears(18).isAfter(LocalDate.now());
    }
    @FXML
    void irAlPasoAnterior(ActionEvent event) {
        if (pasoActual > 1) {
            pasoActual--;
            actualizarVisibilidadPasos();
        }
    }

    @FXML
    void irAlPasoSiguiente(ActionEvent event) {
        if (pasoActual < 4) {
            // Validar el paso actual antes de permitir avanzar
            if (validarPaso(pasoActual)) {
                pasoActual++;
                actualizarVisibilidadPasos();
            }
        } else {
            // Si estamos en el paso 4 y pulsamos Siguiente, el botón actúa como "Guardar"
            if (validarPaso(4)) {
                ejecutarGuardado();
            }
        }
    }

	public void cargarDatosParaModificar(BaptismRequest seleccionado) {
		if (seleccionado == null)
			return;
		this.baptismRequest = seleccionado;
		
		candidate = seleccionado.getCandidate();
		father = seleccionado.getFather();
		mother = seleccionado.getMother();
		fatherGrandFather = seleccionado.getFatherGrandFather();
		fatherGrandMother = seleccionado.getFatherGrandMother();
		motherGrandFather = seleccionado.getMotherGrandFather();
		motherGrandMother = seleccionado.getMotherGrandMother();
		godfather = seleccionado.getGodFather();
		godmother = seleccionado.getGodMother();
		celebrant = seleccionado.getMinister();
		
//		Address commonAddress = null;
//		Address fatherAddress = null;
//		Address motherAddress = null;
				
		// Poblar los controles visuales con el estado actual del objeto
		if (candidate != null) { 
			if (candidate.getPersonData() != null) {
				var pd = seleccionado.getCandidate().getPersonData();
				txtCanNombre.setText(pd.getName());
				txtCanApellido1.setText(pd.getSurname1());
				txtCanApellido2.setText(pd.getSurname2());
				dpCanFechaNac.setValue(pd.getBirthday());
				txtCanLugarNac.setText(seleccionado.getCandidate().getBirthLocation());
				genderFragmentController.setGender(pd.getGender());
			}
			if (father != null) {
				fatherPersonSearcherFragmentController.setPersonState(father.getPersonData());
				if (!baptismRequest.isCommonAddress()) {
					candidateAddress = baptismRequest.getCandidate().getAddress();
					fatherAddress = father.getAddress();
				} else
					fatherAddress = null;
					candidateAddress = null;
					commonAddress = father.getAddress();
			}
			if (mother != null) {
				motherPersonSearcherFragmentController.setPersonState(mother.getPersonData());
				if (!baptismRequest.isCommonAddress()) {
					candidateAddress = baptismRequest.getCandidate().getAddress();
					motherAddress = mother.getAddress();
				} else {
					motherAddress = null;
					candidateAddress = null;
					commonAddress = mother.getAddress();
				}
			}
			
			
			if (baptismRequest.isCommonAddress()) {
				unificarAddresses(candidate.getAddress());
				commonShortAddressFragmentController.setSelectedAddress(getCommonAddress());
				
			} else {
				modelSeparatedAddressess();
		    	actionSeparateSelectedController();
				conmutarAVisibilidadSeparada();
				
				//fatherAddressFragmentController.setSelectedAddress(fatherAddress);
				//motherAddressFragmentController.setSelectedAddress(motherAddress);
				
			}
			// Step 02
/*
			if (baptismRequest.isCommonAddress()) {
				setCommonAddress(candidate.getAddress()); 
				if (getCommonAddress() != null) {
					addressMayorFragmentController.setSelectedAddress(getCommonAddress());
			    }
				commonShortAddressFragmentController.setSelectedAddress(getCommonAddress());
			} else {
				conmutarAVisibilidadSeparada();
				if (fatherAddress != null) {
					fatherAddressFragmentController.setSelectedAddress(fatherAddress);
				}
			
				if (motherAddress != null) {
					motherAddressFragmentController.setSelectedAddress(motherAddress);
				}
			}
*/			
			// Step 03
			if (fatherGrandFather != null) {
				fatherGrandfatherPersonSearcherFragmentController.setPersonState(fatherGrandFather.getPersonData());
			}
			if (fatherGrandMother != null) {
				fatherGrandmotherPersonSearcherFragmentController.setPersonState(fatherGrandMother.getPersonData());
			}
			if (motherGrandFather != null) {
				motherGrandfatherPersonSearcherFragmentController.setPersonState(motherGrandFather.getPersonData());
			}
			if (motherGrandMother != null) {
				motherGrandmotherPersonSearcherFragmentController.setPersonState(motherGrandMother.getPersonData());
			}
			if (godfather != null)
				godfatherPersonSearcherFragmentController.setPersonState(godfather);
			if (godmother != null)
				godmotherPersonSearcherFragmentController.setPersonState(godmother);
			
			// Step 04
			dpFechaCeleb.setValue(seleccionado.getAppointment());
			if (seleccionado.getMinister() !=null)
				//txtMinistro.setText(seleccionado.getMinister().getFullName());
				this.celebrantPersonSearcherFragmentController.setPersonState(celebrant);
			else
				this.celebrantPersonSearcherFragmentController.setNoPersonState();
		} // can != null
		
	}
	
    /**
     * Método privados
     */
    
	private void mapearCamposAlBean() {
		BaptismalCandidate baptismalCandidate = null;
		Address address = null;
		Father father = null;
		Mother mother = null;
		
	    if (this.baptismRequest == null) {
	        // Si no veníamos de modificar, es un alta nueva: instanciamos el árbol de objetos
	        this.baptismRequest = new BaptismRequest();
	        baptismalCandidate = new BaptismalCandidate();
	        this.baptismRequest.setCandidate(baptismalCandidate);
	        address = new Address();
	        baptismalCandidate.setAddress(address);
	        
	        // ... instanciar candidate, personData, etc.
	    } else {
	    	baptismalCandidate = baptismRequest.getCandidate();
	    	address = baptismalCandidate.getAddress();
	    	
	    }
	    // Step 1
	    var person = this.baptismRequest.getCandidate().getPersonData();
	    
	    // Sincronizamos el estado de la interfaz hacia el modelo de datos
	    person.setName(txtCanNombre.getText().trim());
	    person.setSurname1(txtCanApellido1.getText().trim());
	    person.setSurname2(txtCanApellido2.getText().trim());
	    person.setBirthday(dpCanFechaNac.getValue());
	    person.setGender(genderFragmentController.getGender());
	    person.setMarried(false);
	    
	    baptismalCandidate.setActive(true);
	    baptismalCandidate.setBirthLocation(txtCanLugarNac.getText().trim());

	    if (dpFechaCeleb.getValue() != null)
	    	this.baptismRequest.setAppointment(dpFechaCeleb.getValue());
	    baptismRequest.setCelebrated(false);
	    
	    // ... mapear el resto de campos
	    
	    // Addresses
	    father = baptismRequest.getFather();
	    mother = baptismRequest.getMother();
	    
	    father.setAddress(fatherAddress);
	    mother.setAddress(motherAddress);
	    
	}

    
    private boolean validarPaso(int paso) {
        // Validaciones básicas para evitar nulos molestos
        if (paso == 1) {
        	if (!validateStepOne()) {
                mostrarAlerta("Campos obligatorios", "Por favor, rellene el Nombre, Primer Apellido, Fecha de Nacimiento.");
                return false;
            }        	// Validar paso 1
        }
        if (paso == 4) {
//            if (dpFechaCeleb.getValue() == null || txtRegLibro.getText().trim().isEmpty() || txtRegFolio.getText().trim().isEmpty() || txtRegNumero.getText().trim().isEmpty()) {
//                mostrarAlerta("Campos obligatorios", "Por favor, rellene la Fecha de Celebración y los datos de Libro, Folio y Número.");
//                return false;
//            }
        }
        return true;
    }
    private boolean validateStepOne() {
    	return !txtCanNombre.getText().trim().isEmpty() 
        		&& !txtCanApellido1.getText().trim().isEmpty() 
        		&& (dpCanFechaNac.getValue() != null);
    }
    private void actualizarVisibilidadPasos() {
        // Control de los paneles centrales (Se activan/desactivan tanto la visibilidad como el cálculo de espacio en el layout)
        panelPaso1.setVisible(pasoActual == 1); panelPaso1.setManaged(pasoActual == 1);
        panelPaso2.setVisible(pasoActual == 2); panelPaso2.setManaged(pasoActual == 2);
        panelPaso3.setVisible(pasoActual == 3); panelPaso3.setManaged(pasoActual == 3);
        panelPaso4.setVisible(pasoActual == 4); panelPaso4.setManaged(pasoActual == 4);

        // Control estético de la barra superior de progreso
        lblPaso1.setStyle(pasoActual == 1 ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;" : "-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;");
        lblPaso2.setStyle(pasoActual == 2 ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;" : "-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;");
        lblPaso3.setStyle(pasoActual == 3 ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;" : "-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;");
        lblPaso4.setStyle(pasoActual == 4 ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;" : "-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 15;");

        // Control de los botones inferiores
        btnAnterior.setDisable(pasoActual == 1);
        if (pasoActual == 4) {
            btnSiguiente.setText("Guardar Alta");
            btnSiguiente.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-cursor: hand;");
        } else {
            btnSiguiente.setText("Siguiente");
            btnSiguiente.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        }
    }

    private void ejecutarGuardado() {
        System.out.println("Extrayendo datos de la interfaz para componer el objeto BaptismRequest...");
        // Habría que poner validaciones, no vaya a ser que...
        mapearCamposAlBean();
        // Invocación del servicio conectado a base de datos
        baptismRequestService.baptismRequestCreate(baptismRequest);
        
        mostrarInformativa("Operación Completada", "El registro del bautismo se ha guardado con éxito en la base de datos.");
        
        // Aquí podrías resetear el formulario o mandar al usuario de vuelta al buscador general
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformativa(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método genérico y reutilizable para levantar la ventana modal de formulario de Father.
     */
    private Relative openRelativeModalSelector(Person parentPerson, String title, BaptismRelativeEnum role) {
    	return ModalHelper.<Relative, ParentFormController>openModal(
			"/fxml/parent-form.fxml",
			title,
			this.txtCanNombre.getScene().getWindow(),
			controller -> controller.loadPersonAndRole(parentPerson, role),
			controller -> controller.getRelative()
    	);
    }
    
    private Father searchFatherFromPerson(Person p) {
        Optional<Father> optFather = null;
        Father father = null;

        if (p != null) {
        	optFather=personService.findFatherByPerson(p);
        	if (optFather.isEmpty()) {
        		father = (Father)openRelativeModalSelector(p, "Datos Padre", BaptismRelativeEnum.FATHER);
        		if (father == null) {
        			this.fatherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		father = optFather.get();
        	}
        }
        return father;
    }
    
    private Mother searchMotherFromPerson(Person p) {
        Optional<Mother> optMother = null;
        Mother mother = null;

        if (p != null) {
        	optMother=personService.findMotherByPerson(p);
        	if (optMother.isEmpty()) {
        		mother = (Mother)openRelativeModalSelector(p, "Datos Madre", BaptismRelativeEnum.MOTHER);
        		if (mother == null) {
        			this.motherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		mother = optMother.get();
        	}
        }
        return mother;
    }
    
    private GrandFather searchFatherGrandFatherFromPerson(Person p) {
        Optional<GrandFather> optGrandFather = null;
        GrandFather grandFather = null;

        if (p != null) {
        	optGrandFather=personService.findGrandFatherByPerson(p);
        	if (optGrandFather.isEmpty()) {
        		grandFather = (GrandFather)openRelativeModalSelector(p, "Datos Abuelo", BaptismRelativeEnum.GRANDFATHER);
        		if (grandFather == null) {
        			this.fatherGrandfatherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		grandFather = optGrandFather.get();
        	}
        }
        return grandFather;
    }
    
    private GrandMother searchFatherGrandMotherFromPerson(Person p) {
        Optional<GrandMother> optGrandMother = null;
        GrandMother grandMother = null;

        if (p != null) {
        	optGrandMother=personService.findGrandMotherByPerson(p);
        	if (optGrandMother.isEmpty()) {
        		grandMother = (GrandMother)openRelativeModalSelector(p, "Datos Abuela", BaptismRelativeEnum.GRANDMOTHER);
        		if (grandMother == null) {
        			this.fatherGrandmotherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		grandMother = optGrandMother.get();
        	}
        }
        return grandMother;
    }
    
    private GrandFather searchMotherGrandFatherFromPerson(Person p) {
        Optional<GrandFather> optGrandFather = null;
        GrandFather grandFather = null;

        if (p != null) {
        	optGrandFather=personService.findGrandFatherByPerson(p);
        	if (optGrandFather.isEmpty()) {
        		grandFather = (GrandFather)openRelativeModalSelector(p, "Datos Abuelo", BaptismRelativeEnum.GRANDFATHER);
        		if (grandFather == null) {
        			this.motherGrandfatherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		grandFather = optGrandFather.get();
        	}
        }
        return grandFather;
    }
    
    private GrandMother searchMotherGrandMotherFromPerson(Person p) {
        Optional<GrandMother> optGrandMother = null;
        GrandMother grandMother = null;

        if (p != null) {
        	optGrandMother=personService.findGrandMotherByPerson(p);
        	if (optGrandMother.isEmpty()) {
        		grandMother = (GrandMother)openRelativeModalSelector(p, "Datos Abuela", BaptismRelativeEnum.GRANDMOTHER);
        		if (grandMother == null) {
        			this.motherGrandmotherPersonSearcherFragmentController.setNoPersonState();
        			return null;
        		}
        	} else {
        		grandMother = optGrandMother.get();
        	}
        }
        return grandMother;
    }
    
	
	
	/**
	 * DIRECCIONES
	 */
	
    @FXML
    void dividirDireccionPadre(ActionEvent event) {
    	conmutarAVisibilidadSeparada();
    	actionSeparateModelAddresses(this.commonAddress);
    	actionSeparateSelectedController();
//    	this.fatherAddress = null;
//    	modeloAlDivisionSeparada();
    }
    @FXML
    void dividirDireccionMadre(ActionEvent event) {
    	conmutarAVisibilidadSeparada();
    	actionSeparateModelAddresses(this.commonAddress);
    	actionSeparateSelectedController();
//    	this.motherAddress = null;
//    	modeloAlDivisionSeparada();
    }
    
    private void modeloAlDivisionSeparada() {
        // Copiamos lo que hubiera en la común al progenitor que NO ha provocado la limpieza
        if (this.getCommonAddress() != null) {
            if (this.fatherAddress == null) this.fatherAddress = this.getCommonAddress();
            if (this.motherAddress == null) this.motherAddress = this.getCommonAddress();
            this.setCommonAddress(null);
        	fatherAddressFragmentController.setSelectedAddress(fatherAddress);
        	motherAddressFragmentController.setSelectedAddress(motherAddress);
        }
    	
    }
    void actionSeparateModelAddresses(Address address) {
        this.fatherAddress = address;
        this.motherAddress = address;
        this.candidateAddress = address;
        this.setCommonAddress(null);
//    	this.commonAddress = null;
    }
    
    void actionSeparateSelectedController() {
        this.fatherAddressFragmentController.setSelectedAddress(fatherAddress);
        this.motherAddressFragmentController.setSelectedAddress(motherAddress);
    }
    void modelSeparatedAddressess() {
    	if (baptismRequest != null) {
    		this.commonAddress = null;
    		if (baptismRequest.getCandidate() != null)
    			this.candidateAddress= baptismRequest.getCandidate().getAddress();
    		if (baptismRequest.getFather() != null)
    			this.fatherAddress = baptismRequest.getFather().getAddress();
    		if (baptismRequest.getMother() != null)
    			this.motherAddress = baptismRequest.getMother().getAddress();
    	}
    }
    
    private void conmutarAVisibilidadSeparada() {
        panelDireccionComun.setVisible(false);
        panelDireccionComun.setManaged(false);
        panelDireccionesSeparadas.setVisible(true);
        panelDireccionesSeparadas.setManaged(true);
        
    }
    
    // --- COMPORTAMIENTO UNIFICAR DIRECCIONES ---
    
    @FXML
    void unificarDesdePadre(ActionEvent event) {
        if (confirmarUnificacion("del Padre")) {
        	unificarAddresses(this.fatherAddress);
            restaurarVistaUnificada();
        }
    }

    @FXML
    void unificarDesdeMadre(ActionEvent event) {
        if (confirmarUnificacion("de la Madre")) {
        	unificarAddresses(this.motherAddress);
            restaurarVistaUnificada();
        }
    }
    void unificarAddresses(Address unificada) {
        this.setCommonAddress(unificada);
        this.fatherAddress = null;
        this.motherAddress = null;
        this.candidateAddress = null;
    	
    }
    
    
    private boolean confirmarUnificacion(String progenitor) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Unificación de Domicilio");
        alert.setHeaderText("¿Desea usar la dirección " + progenitor + " para ambos progenitores?");
        alert.setContentText("Esto desvinculará la otra dirección y fijará este domicilio común también para el niño.");
        
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void restaurarVistaUnificada() {
        panelDireccionesSeparadas.setVisible(false);
        panelDireccionesSeparadas.setManaged(false);
        panelDireccionComun.setVisible(true);
        panelDireccionComun.setManaged(true);
    }    
    
	/**
	 * @return the commonAddress
	 */
	public Address getCommonAddress() {
		return commonAddress;
	}

	/**
	 * @param commonAddress the commonAddress to set
	 */
	public void setCommonAddress(Address commonAddress) {
		this.commonAddress = commonAddress;
		this.commonShortAddressFragmentController.setSelectedAddress(commonAddress);
		if (this.baptismRequest != null)
			baptismRequest.setCommonAddress(commonAddress != null);
		/*
		if (this.candidate != null)
			this.candidate.setAddress(commonAddress);
		if (this.father != null) {
			this.fatherAddress = commonAddress;
			this.father.setAddress(commonAddress);
		}
		if (this.mother != null) {
			this.motherAddress = commonAddress;
			this.mother.setAddress(commonAddress);
		}
		*/
	}    
    

    
    
 // --- MÉTODOS AUXILIARES DE LISTENERS ---
    private Address lanzarBuscadorDireccion() {
        // Lógica análoga a PersonSelector para cargar address-selector.fxml de forma modal
        // Devuelve la Address seleccionada o el objeto nuevo del panel lateral
        return null; // Implementación del cargador de FXMLLoader modal habitual
    }
 // 1. Delegado para Personas
    private final PersonChangeListener personListener = new PersonChangeListener() {
        @Override
        public void handleEvent(PersonSelectedEvent event) {
            handlePersonSelected(event);
        }
    };

    // 2. Delegado para Direcciones
    private final AddressChangeListener addressListener = new AddressChangeListener() {
        @Override
        public void handleEvent(AddressSelectedEvent event) {
            handleAddressSelected(event);
        }
    };


    // 3. Métodos finales donde resides tu lógica
    private void handleAddressSelected(AddressSelectedEvent event) {
        // Tu lógica aquí
    }

	private void handlePersonSelected(PersonSelectedEvent event) {
		if (event.getSource().equals("FATHER"))
			handleFatherEvent(event);
		else if (event.getSource().equals("MOTHER"))
			handleMotherEvent(event);
		else if (event.getSource().equals("GODFATHER"))
			handleGodfatherEvent(event);
		else if (event.getSource().equals("GODMOTHER"))
			handleGodmotherEvent(event);
		else if (event.getSource().equals("FATHERGRANDFATHER"))
			handleFatherGrandFatherEvent(event);
		else if (event.getSource().equals("FATHERGRANDMOTHER"))
			handleFatherGrandMotherEvent(event);
		else if (event.getSource().equals("MOTHERGRANDFATHER"))
			handleMotherGrandFatherEvent(event);
		else if (event.getSource().equals("MOTHERGRANDMOTHER"))
			handleMotherGrandMotherEvent(event);
		else if (event.getSource().equals("CELEBRANT"))
			handleCelebrantEvent(event);
	}
	
	private void handleCelebrantEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		this.celebrant=newValue;
		this.baptismRequest.setMinister(this.celebrant);
	}
	private void handleFatherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		Father father = null;
		if (newValue != null) {
			father = searchFatherFromPerson(newValue);
			this.father=father;
			
			if (this.father.getAddress() != null && this.baptismRequest.isCommonAddress()) {
				setCommonAddress(father.getAddress());
			}
		} else {
			this.father = null;
		}
		this.baptismRequest.setFather(father);
	}
    
	private void handleMotherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		Mother mother = null;
		if (newValue != null) {
			mother = searchMotherFromPerson(newValue);
			this.mother=mother;
		} else {
			this.mother = null;
		}
		this.baptismRequest.setMother(mother);
	}
	
	private void handleFatherGrandFatherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		GrandFather grandFather = null;
		if (newValue != null) {
			grandFather = searchFatherGrandFatherFromPerson(newValue);
			this.fatherGrandFather=grandFather;
		} else {
			this.fatherGrandFather = null;
		}
		this.baptismRequest.setFatherGrandFather(grandFather);
	}

	private void handleFatherGrandMotherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		GrandMother grandMother = null;
		if (newValue != null) {
			grandMother = searchFatherGrandMotherFromPerson(newValue);
			this.fatherGrandMother=grandMother;
		} else {
			this.fatherGrandMother = null;
		}
		this.baptismRequest.setFatherGrandMother(grandMother);
	}

	private void handleMotherGrandFatherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		GrandFather grandFather = null;
		if (newValue != null) {
			grandFather = searchMotherGrandFatherFromPerson(newValue);
			this.motherGrandFather=grandFather;
		} else {
			this.motherGrandFather = null;
		}
		this.baptismRequest.setMotherGrandFather(grandFather);
	}

	private void handleMotherGrandMotherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		GrandMother grandMother = null;
		if (newValue != null) {
			grandMother = searchMotherGrandMotherFromPerson(newValue);
			this.motherGrandMother=grandMother;
		} else {
			this.motherGrandMother = null;
		}
		this.baptismRequest.setMotherGrandMother(grandMother);
	}

	private void handleGodfatherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		this.godfather=newValue;
		this.baptismRequest.setGodFather(godfather);
		
	}

	private void handleGodmotherEvent(PersonSelectedEvent event) {
		Person newValue = null;
		newValue=event.getCurrent();
		this.godmother=newValue;
		this.baptismRequest.setGodMother(godmother);
	}


	// Estados
}
