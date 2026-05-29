package org.fmm.librosparroquiales.javafx.controller;

import java.util.ArrayList;
import java.util.List;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

@Deprecated
public class BaptismRequestController {

    // Controles FXML - Bautizando
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido1;
    @FXML private TextField txtApellido2;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtLugarNacimiento;

    // Controles FXML - Padre
    @FXML private TextField txtPadreNombre;
    @FXML private TextField txtPadreDni;
    @FXML private TextField txtPadreNatural;

    // Controles FXML - Madre
    @FXML private TextField txtMadreNombre;
    @FXML private TextField txtMadreDni;
    @FXML private TextField txtMadreNatural;

    // Controles FXML - Abuelos
    @FXML private TextField txtAbueloPaterno;
    @FXML private TextField txtAbuelaPaterna;
    @FXML private TextField txtAbueloMaterno;
    @FXML private TextField txtAbuelaMaterna;

    // Objeto principal que se enviará a persistir
    private BaptismRequest solicitudActual = new BaptismRequest();

    @FXML
    public void initialize() {
        // Configurar la búsqueda inteligente en los campos clave
        configurarBuscadorPredictivoBautizando(txtApellido1);
        configurarBuscadorPredictivoPadre(txtPadreNombre);
        configurarBuscadorPredictivoMadre(txtMadreNombre);
    }

    /**
     * Lógica para el Bautizando (Busca por surname y autorrellena al candidato)
     */
    private void configurarBuscadorPredictivoBautizando(TextField campoTexto) {
        ContextMenu popupSugerencias = new ContextMenu();

        campoTexto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.trim().length() >= 3) {
                // Llamada al método de simulación de BBDD
                List<BaptismRequest> coinciden = buscarSolicitudesAnterioresEnBBDD(newValue.trim());
                
                popupSugerencias.getItems().clear();
                for (BaptismRequest historico : coinciden) {
                    BaptismalCandidate c = historico.getCandidate();
                    String lineaTexto = c.getPersonData().getSurname1() + " " + c.getPersonData().getSurname2() + ", " + c.getPersonData().getName();
                    MenuItem item = new MenuItem(lineaTexto);
                    
                    // Al seleccionar, autorrellena todo el bloque del niño
                    item.setOnAction(e -> {
                        txtNombre.setText(c.getPersonData().getName());
                        txtApellido1.setText(c.getPersonData().getSurname1());
                        txtApellido2.setText(c.getPersonData().getSurname2());
                        dpFechaNacimiento.setValue(c.getPersonData().getBirthday());
                        txtLugarNacimiento.setText(c.getBirthLocation());
                    });
                    popupSugerencias.getItems().add(item);
                }

                if (!popupSugerencias.getItems().isEmpty()) {
                    popupSugerencias.show(campoTexto, javafx.geometry.Side.BOTTOM, 0, 0);
                } else {
                    popupSugerencias.hide();
                }
            } else {
                popupSugerencias.hide();
            }
        });
    }

    /**
     * Lógica para el Padre (Autorrellena sus datos + los de los abuelos paternos)
     */
    private void configurarBuscadorPredictivoPadre(TextField campoTexto) {
        ContextMenu popupSugerencias = new ContextMenu();

        campoTexto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.trim().length() >= 3) {
                List<BaptismRequest> coinciden = buscarSolicitudesAnterioresEnBBDD(newValue.trim());
                
                popupSugerencias.getItems().clear();
                for (BaptismRequest historico : coinciden) {
                    Father p = historico.getFather();
                    MenuItem item = new MenuItem(p.getPersonData().getName() + " (DNI: " + p.getNif() + ")");
                    
                    item.setOnAction(e -> {
                        // 1. Autorrellena los datos del propio Padre
                        txtPadreNombre.setText(p.getFullName());
                        txtPadreDni.setText(p.getNif());
                        txtPadreNatural.setText(p.getBirthLocation());
                        
                        // 2. Cascada: Autorrellena también los abuelos paternos asociados
                        GrandFather abueloP = historico.getFatherGrandFather();
                        GrandMother abuelaP = historico.getFatherGrandMother();
                        txtAbueloPaterno.setText(abueloP.getFullName());
                        txtAbuelaPaterna.setText(abuelaP.getFullName());
                    });
                    popupSugerencias.getItems().add(item);
                }

                if (!popupSugerencias.getItems().isEmpty()) {
                    popupSugerencias.show(campoTexto, javafx.geometry.Side.BOTTOM, 0, 0);
                } else {
                    popupSugerencias.hide();
                }
            } else {
                popupSugerencias.hide();
            }
        });
    }

    /**
     * Lógica para la Madre (Autorrellena sus datos + los de los abuelos maternales)
     */
    private void configurarBuscadorPredictivoMadre(TextField campoTexto) {
        ContextMenu popupSugerencias = new ContextMenu();

        campoTexto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.trim().length() >= 3) {
                List<BaptismRequest> coinciden = buscarSolicitudesAnterioresEnBBDD(newValue.trim());
                
                popupSugerencias.getItems().clear();
                for (BaptismRequest historico : coinciden) {
                    Mother m = historico.getMother();
                    MenuItem item = new MenuItem(m.getPersonData().getName() + " (DNI: " + m.getNif() + ")");
                    
                    item.setOnAction(e -> {
                        // 1. Autorrellena los datos de la Madre
                        txtMadreNombre.setText(m.getFullName());
                        txtMadreDni.setText(m.getNif());
                        txtMadreNatural.setText(m.getBirthLocation());
                        
                        // 2. Cascada: Autorrellena también los abuelos maternales asociados
                        GrandFather abueloM = historico.getFatherGrandFather();
                        GrandMother abuelaM = historico.getFatherGrandMother();
                        txtAbueloMaterno.setText(abueloM.getFullName());
                        txtAbuelaMaterna.setText(abuelaM.getFullName());
                    });
                    popupSugerencias.getItems().add(item);
                }

                if (!popupSugerencias.getItems().isEmpty()) {
                    popupSugerencias.show(campoTexto, javafx.geometry.Side.BOTTOM, 0, 0);
                } else {
                    popupSugerencias.hide();
                }
            } else {
                popupSugerencias.hide();
            }
        });
    }

    /**
     * MÉTODOS DE ACCIÓN DEL FORMULARIO REAL
     */
    @FXML
    private void accionGuardar() {
        // Al guardar, volcamos lo que hay en la pantalla a un objeto nuevo e independiente
/*
        solicitudActual.getCandidate().name = txtNombre.getText();
        solicitudActual.getCandidate().surname1 = txtApellido1.getText();
        solicitudActual.getCandidate().surname2 = txtApellido2.getText();
        solicitudActual.getCandidate().fechaNacimiento = dpFechaNacimiento.getValue();
        solicitudActual.getCandidate().lugarNacimiento = txtLugarNacimiento.getText();

        solicitudActual.getFather().name = txtPadreNombre.getText();
        solicitudActual.getFather().dni = txtPadreDni.getText();
        solicitudActual.getFather().naturalDe = txtPadreNatural.getText();

        solicitudActual.getMother().name = txtMadreNombre.getText();
        solicitudActual.getMother().dni = txtMadreDni.getText();
        solicitudActual.getMother().naturalDe = txtMadreNatural.getText();

        solicitudActual.getPaternalGrandparents().abuelo = txtAbueloPaterno.getText();
        solicitudActual.getPaternalGrandparents().abuela = txtAbuelaPaterna.getText();
        
        solicitudActual.getMaternalGrandparents().abuelo = txtAbueloMaterno.getText();
        solicitudActual.getMaternalGrandparents().abuela = txtAbuelaMaterna.getText();

        // Aquí llamarías a tu EntityManager o DAO para persistir la solicitudActual
        System.out.println("Guardando nueva solicitud independiente para: " + solicitudActual.getCandidate().name);
*/        
    }

    @FXML
    private void accionLimpiar() {
        txtNombre.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        dpFechaNacimiento.setValue(null);
        txtLugarNacimiento.clear();
        txtPadreNombre.clear();
        txtPadreDni.clear();
        txtPadreNatural.clear();
        txtMadreNombre.clear();
        txtMadreDni.clear();
        txtMadreNatural.clear();
        txtAbueloPaterno.clear();
        txtAbuelaPaterna.clear();
        txtAbueloMaterno.clear();
        txtAbuelaMaterna.clear();
        solicitudActual = new BaptismRequest(); // Se reinicia el objeto
    }

    /**
     * MODO SIMULACIÓN: Este es el método al que hay que llamar para interrogar a la BBDD.
     * En producción aquí harías la query JPA: "SELECT b FROM BaptismRequest b WHERE b..."
     */
    private List<BaptismRequest> buscarSolicitudesAnterioresEnBBDD(String textoBusqueda) {
        List<BaptismRequest> resultadosSimulados = new ArrayList<>();
        // Lógica de consulta real con filtrado SQL / JPQL irá aquí
        return resultadosSimulados;
    }
}