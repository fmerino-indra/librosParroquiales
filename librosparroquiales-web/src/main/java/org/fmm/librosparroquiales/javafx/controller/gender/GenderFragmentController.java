package org.fmm.librosparroquiales.javafx.controller.gender;

import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class GenderFragmentController {

    @FXML private ToggleGroup sexoGroup;
    @FXML private ToggleButton btnVaron;
    @FXML private ToggleButton btnMujer;
    @FXML private Label labelSexo;
    
    @FXML
    public void initialize() {
        // Detectar cuándo cambia la selección
        sexoGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ToggleButton botonSeleccionado = (ToggleButton) newValue;
                String genero = botonSeleccionado.getText();
                System.out.println("Opción seleccionada: " + genero);
            }
        });
    }
    public void setLabelText(String text) {
    	labelSexo.setText(text);
    }
    public boolean isMale() {
		return btnVaron.isSelected();
    }
    
    public boolean isFemale() {
    	return btnMujer.isSelected();
    }
    
    public boolean hasGender() {
    	return sexoGroup.getSelectedToggle() != null;
    }
    
    public GenderEnum getGender() {
    	if (isMale())
    		return GenderEnum.M;
    	if (isFemale())
    		return GenderEnum.F;
    	
    	return GenderEnum.N;
    }
    
    public void setGender(GenderEnum gender) {
    	if (gender == GenderEnum.M)
    		sexoGroup.selectToggle(btnVaron);
    	else if (gender == GenderEnum.F)
    		sexoGroup.selectToggle(btnMujer);
    	else
    		sexoGroup.selectToggle(null);
    }
    
    public void clear() {
    	setGender(null);
    }
}

