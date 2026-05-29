package org.fmm.librosparroquiales.common.util.enums;

public enum GenderEnum {
	M("Male"),
	F("Female"),
	N("NS/NC");
	
	private final String description;
	
	GenderEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
