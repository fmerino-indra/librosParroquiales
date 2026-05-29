package org.fmm.librosparroquiales.common.util.enums;

public enum BaptismRelativeEnum {
	FATHER("Father"),
	MOTHER("Mother"),
	GODFATHER("GodFather"),
	GODMOTHER("GodMother"),
	GRANDFATHER("GrandFather"),
	GRANDMOTHER("GrandMother");
	
	private final String description;
	
	BaptismRelativeEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
