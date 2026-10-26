package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.util.enums.BaptismRelativeEnum;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

public class RelativeFactory {

	public static Relative newRelative (BaptismRelativeEnum role) {
		Relative retorno = null;
		retorno = switch (role) {
			case BaptismRelativeEnum.FATHER -> new Father();
			case BaptismRelativeEnum.MOTHER -> new Mother();
			case BaptismRelativeEnum.GRANDFATHER -> new GrandFather();
			case BaptismRelativeEnum.GRANDMOTHER -> new GrandMother();
			default -> throw new IllegalArgumentException("Unexpected value: " + role);
		};
		return retorno;
	}
	
	public static String getFormTitle(BaptismRelativeEnum role) {
		return switch (role) {
			case BaptismRelativeEnum.FATHER -> "Datos adicionales del Padre";
			case BaptismRelativeEnum.MOTHER -> "Datos adicionales de la Madre";
			case BaptismRelativeEnum.GRANDFATHER -> "Datos adicionales de la Abuela";
			case BaptismRelativeEnum.GRANDMOTHER -> "Datos adicionales del Abuelo";
			default -> throw new IllegalArgumentException("Unexpected value: " + role);
		};		
	}
	
	public static String getFormTextLabel(BaptismRelativeEnum role) {
		return role.getDescription();
	};
	
	public static String getTextLabel(BaptismRelativeEnum role) {
		return String.format("Buscar %S", role.getDescription());
	}
}
