package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.common.service.PersonService;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BaptismFormController extends AbstractFxController {

    // --- ELEMENTOS DE NAVEGACIÓN ---
    @FXML private Label lblPaso1, lblPaso2, lblPaso3, lblPaso4;
    @FXML private VBox panelPaso1, panelPaso2, panelPaso3, panelPaso4;
    @FXML private Button btnAnterior, btnSiguiente;
    @FXML private Button btnBuscarPadre, btnLimpiarPadre, btnBuscarMadre, btnLimpiarMadre;

    // --- CAMPOS PASO 1 (CANDIDATO) ---
    @FXML private TextField txtCanNombre, txtCanApellido1, txtCanApellido2, txtCanLugarNac;
    @FXML private DatePicker dpCanFechaNac;
    @FXML private ComboBox<String> cbCanSexo;

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
    @FXML private TextField txtPadreNombre, txtPadreApellidos, txtMadreNombre, txtMadreApellidos, txtPadresFiliacion;

    // --- CAMPOS PASO 3 (PADRINOS/ABUELOS) ---
    @FXML private TextField txtPadrino, txtMadrina, txtAbueloPat, txtAbuelaPat, txtAbueloMat, txtAbuelaMat;

    // --- CAMPOS PASO 4 (REGISTRO) ---
    @FXML private DatePicker dpFechaCeleb;
    @FXML private TextField txtMinistro, txtRegLibro, txtRegFolio, txtRegNumero;
    @FXML private TextArea txtObservaciones;

    private int pasoActual = 1;
    private BaptismRequestService baptismRequestService;
    private PersonService personService;
    
    private BaptismRequest baptismRequest = null;
    
    @FXML
    public void initialize() {
        // Recuperamos el servicio de creación de tu proyecto de manera tradicional
//        this.baptismRequestService = Launcher.springContext.getBean(BaptismRequestService.class);
    	this.baptismRequestService = this.getBaptismRequestServiceBean();
    	personService = this.getPersonServiceBean();
    	
        // Inicializar combos
        cbCanSexo.getItems().addAll("Varón", "Mujer");
        cbCanSexo.setValue("Varón");

        actualizarVisibilidadPasos();
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

    private boolean validarPaso(int paso) {
        // Validaciones básicas para evitar nulos molestos
        if (paso == 1) {
            if (txtCanNombre.getText().trim().isEmpty() 
            		|| txtCanApellido1.getText().trim().isEmpty() 
            		|| dpCanFechaNac.getValue() == null
            		|| txtDirCalle.getText().trim().isEmpty()
            		|| txtDirNumero.getText().trim().isEmpty()
            		|| txtDirPoblacion.getText().trim().isEmpty()
            		) {
                mostrarAlerta("Campos obligatorios", "Por favor, rellene el Nombre, Primer Apellido, Fecha de Nacimiento. Calle, número y población");
                return false;
            }
        }
        if (paso == 4) {
            if (dpFechaCeleb.getValue() == null || txtRegLibro.getText().trim().isEmpty() || txtRegFolio.getText().trim().isEmpty() || txtRegNumero.getText().trim().isEmpty()) {
                mostrarAlerta("Campos obligatorios", "Por favor, rellene la Fecha de Celebración y los datos de Libro, Folio y Número.");
                return false;
            }
        }
        return true;
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

	public void cargarDatosParaModificar(BaptismRequest seleccionado) {
		this.baptismRequest = seleccionado;
		if (seleccionado == null)
			return;
		var can = seleccionado.getCandidate();
		
		// Poblar los controles visuales con el estado actual del objeto
		if (can != null) { 
			if (can.getPersonData() != null) {
				var pd = seleccionado.getCandidate().getPersonData();
				txtCanNombre.setText(pd.getName());
				txtCanApellido1.setText(pd.getSurname1());
				txtCanApellido2.setText(pd.getSurname2());
				dpCanFechaNac.setValue(pd.getBirthday());
				txtCanLugarNac.setText(seleccionado.getCandidate().getBirthLocation());
			}
			var add = can.getAddress(); 
			if (add != null) {
				txtDirCalle.setText(add.getStreetName());
		        txtDirNumero.setText(add.getStreetNumber());
		        txtDirPiso.setText(add.getFloor());     // Tu getter para el piso
		        txtDirLetra.setText(add.getDoor());   // Tu getter para la letra
		        txtDirCodigoPostal.setText(add.getPostalCode());
		        txtDirPoblacion.setText(add.getCity());
		        txtDirProvincia.setText(add.getProvince());
		        txtDirPais.setText(add.getCountry());    // Tu getter para el país			
		    }
		}
	}
	
	private void mapearCamposAlBean() {
		BaptismalCandidate baptismalCandidate = null;
		Address address = null;
		
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

	    var pd = this.baptismRequest.getCandidate().getPersonData();
	    
	    // Sincronizamos el estado de la interfaz hacia el modelo de datos
	    pd.setName(txtCanNombre.getText().trim());
	    pd.setSurname1(txtCanApellido1.getText().trim());
	    pd.setSurname2(txtCanApellido2.getText().trim());
	    pd.setBirthday(dpCanFechaNac.getValue());
	    
	    this.baptismRequest.setAppointment(dpFechaCeleb.getValue());
	    // ... mapear el resto de campos
	    
	    var direccion = baptismalCandidate.getAddress();
	    
	    direccion.setStreetName(txtDirCalle.getText().trim());
	    direccion.setStreetNumber(txtDirNumero.getText().trim());
	    direccion.setFloor(txtDirPiso.getText().trim());
	    direccion.setDoor(txtDirLetra.getText().trim());
	    direccion.setPostalCode(txtDirCodigoPostal.getText().trim());
	    direccion.setCity(txtDirPoblacion.getText().trim());
	    direccion.setProvince(txtDirProvincia.getText().trim());
	    direccion.setCountry(txtDirPais.getText().trim());	    
	}
	/*
	@FXML
	public void buscarPadreModal(ActionEvent event) {
		
	}
	@FXML
	public void desvincularPadre(ActionEvent event) {
		
	}
	@FXML
	public void buscarMadreModal(ActionEvent event) {
		
	}
	@FXML
	public void desvincularMadre(ActionEvent event) {
		
	}
	*/
    // --- REFERENCIAS INTERNAS DEL MODELO ---
    private Father padreAsociado = null;
    private Person madreAsociado = null;

    @FXML
    void buscarPadreModal(ActionEvent event) {
        Person seleccionada = abrirPersonModalSelector(GenderEnum.M);
        if (seleccionada != null) {
        	this.padreAsociado=personService.findFatherByPerson(seleccionada);
        	
        	// Al no tener id no existe en bbdd
        	if (padreAsociado.getId() == null) {
        		padreAsociado = openParentModalSelector(padreAsociado);
        		// Hay que guardar el padre.
        	}
            //this.padreAsociado = seleccionada;
            
            // Inyectamos datos y bloqueamos campos para que no se altere el registro existente
            txtPadreNombre.setText(seleccionada.getName());
            txtPadreApellidos.setText(seleccionada.getSurname1() + " " + seleccionada.getSurname2());
            
            txtPadreNombre.setEditable(false);
            txtPadreApellidos.setEditable(false);
            
            // Ajustamos botonera
            btnBuscarPadre.setDisable(true);
            btnLimpiarPadre.setDisable(false);
        }
    }

    @FXML
    void desvincularPadre(ActionEvent event) {
        this.padreAsociado = null;
        
        txtPadreNombre.clear();
        txtPadreApellidos.clear();
        
        txtPadreNombre.setEditable(true);
        txtPadreApellidos.setEditable(true);
        
        btnBuscarPadre.setDisable(false);
        btnLimpiarPadre.setDisable(true);
    }

    @FXML
    void buscarMadreModal(ActionEvent event) {
        Person seleccionada = abrirPersonModalSelector(GenderEnum.F);
        if (seleccionada != null) {
            this.madreAsociado = seleccionada;
            
            txtMadreNombre.setText(seleccionada.getName());
            txtMadreApellidos.setText(seleccionada.getSurname1() + " " + seleccionada.getSurname2());
            
            txtMadreNombre.setEditable(false);
            txtMadreApellidos.setEditable(false);
            
            btnBuscarMadre.setDisable(true);
            btnLimpiarMadre.setDisable(false);
        }
    }

    @FXML
    void desvincularMadre(ActionEvent event) {
        this.madreAsociado = null;
        
        txtMadreNombre.clear();
        txtMadreApellidos.clear();
        
        txtMadreNombre.setEditable(true);
        txtMadreApellidos.setEditable(true);
        
        btnBuscarMadre.setDisable(false);
        btnLimpiarMadre.setDisable(true);
    }

    /**
     * Método genérico y reutilizable para levantar la ventana modal de selección.
     */
    private Person abrirPersonModalSelector(GenderEnum gender) {
        try {
            java.net.URL url = getClass().getResource("/fxml/person-selector.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            javafx.scene.Parent root = loader.load();
            final VBox vBox = (VBox)root;
            
            // Configurar el escenario del diálogo (Modal Stage)
            Stage modalStage = new Stage();
            modalStage.setTitle("Seleccionar Persona");
            modalStage.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
            modalStage.initOwner(btnBuscarPadre.getScene().getWindow());       // Asocia la ventana padre
            
            double width = vBox.getPrefWidth();
            double height = vBox.getPrefHeight();
            
            modalStage.setScene(new javafx.scene.Scene(root, width, height));
            modalStage.setWidth(width);
            modalStage.setHeight(height);
            modalStage.setMinWidth(width);
            modalStage.setMinHeight(height);
            
            // Recuperamos el controlador del modal para extraer el resultado obtenido
            PersonSelectorController modalController = loader.getController();
            modalController.setGender(gender);
            
         // EL CHIVATO: Imprime las medidas reales en la consola de Eclipse en cuanto se abre
            modalStage.setOnShown(event -> {
                System.out.println("=== DIAGNÓSTICO DE DIMENSIONES MODAL ===");
                System.out.println("Ancho del Stage (Ventana): " + modalStage.getWidth());
                System.out.println("Alto del Stage (Ventana): " + modalStage.getHeight());
                System.out.println("Ancho de la Scene (Contenido): " + modalStage.getScene().getWidth());
                System.out.println("Alto de la Scene (Contenido): " + modalStage.getScene().getHeight());
                System.out.println("Ancho pref del VBox raíz: " + vBox.getPrefWidth());
                System.out.println("========================================");
                
                modalStage.setWidth(width);
                modalStage.setHeight(height);
                vBox.setPrefSize(width, height);
                root.requestLayout();
                
            });            
            
            // Mostramos y esperamos a que el usuario cierre la ventana
            modalStage.showAndWait();

            return modalController.getPersonaSeleccionada();

        } catch (Exception e) {
            System.err.println("Error al abrir el modal selector de personas.");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Método genérico y reutilizable para levantar la ventana modal de formulario de Father.
     */
    private Father openParentModalSelector(Father father) {
        try {
            java.net.URL url = getClass().getResource("/fxml/parent-form.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            javafx.scene.Parent root = loader.load();
            final VBox vBox = (VBox)root;
            
            // Configurar el escenario del diálogo (Modal Stage)
            Stage modalStage = new Stage();
            modalStage.setTitle("Datos adicionales Padre");
            modalStage.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
            modalStage.initOwner(btnBuscarPadre.getScene().getWindow());       // Asocia la ventana padre
            
            double width = vBox.getPrefWidth();
            double height = vBox.getPrefHeight();
            
            modalStage.setScene(new javafx.scene.Scene(root, width, height));
            modalStage.setWidth(width);
            modalStage.setHeight(height);
            modalStage.setMinWidth(width);
            modalStage.setMinHeight(height);
            
            // Recuperamos el controlador del modal para extraer el resultado obtenido
            // [FMMP] Hay que ver si ya está creado
            ParentFormController parentModalController = loader.getController();
            parentModalController.setFather(father);
            
            
         // EL CHIVATO: Imprime las medidas reales en la consola de Eclipse en cuanto se abre
            modalStage.setOnShown(event -> {
                System.out.println("=== DIAGNÓSTICO DE DIMENSIONES MODAL ===");
                System.out.println("Ancho del Stage (Ventana): " + modalStage.getWidth());
                System.out.println("Alto del Stage (Ventana): " + modalStage.getHeight());
                System.out.println("Ancho de la Scene (Contenido): " + modalStage.getScene().getWidth());
                System.out.println("Alto de la Scene (Contenido): " + modalStage.getScene().getHeight());
                System.out.println("Ancho pref del VBox raíz: " + vBox.getPrefWidth());
                System.out.println("========================================");
                
                modalStage.setWidth(width);
                modalStage.setHeight(height);
                vBox.setPrefSize(width, height);
                root.requestLayout();
                
            });            
            
            // Mostramos y esperamos a que el usuario cierre la ventana
            modalStage.showAndWait();

            return parentModalController.getFather();

        } catch (Exception e) {
            System.err.println("Error al abrir el modal formulario de Parents.");
            e.printStackTrace();
            return null;
        }
    }	
    
}
