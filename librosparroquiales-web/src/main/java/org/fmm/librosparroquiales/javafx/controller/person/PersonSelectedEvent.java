package org.fmm.librosparroquiales.javafx.controller.person;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.javafx.events.AbstractEvent;

public class PersonSelectedEvent extends AbstractEvent<Person> {

	private String source;
	
	public PersonSelectedEvent(Person old, Person current, boolean success, String source) {
		super(old,current, success);
		this.setSource(source);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
