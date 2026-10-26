package org.fmm.librosparroquiales.javafx.controller.person;

import org.fmm.librosparroquiales.javafx.events.AbstractEventListener;

public interface PersonChangeListener extends AbstractEventListener<PersonSelectedEvent>{
	@Override
	void handleEvent(PersonSelectedEvent event);
}
