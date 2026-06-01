package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

public class RelativeFactory {

	public static Relative newRelative (GenderEnum gender) {
		if (GenderEnum.M == gender) {
			return new Father();
		} else {
			return new Mother();
		}
	}
	
	public static String getTextTitle(GenderEnum gender) {
		if (GenderEnum.M == gender) 
			return "Datos del Padre";
		else 
			return "Datos de la Madre";
	}
}
