package org.fmm.librosparroquiales.javafx.controller.address;

import org.fmm.librosparroquiales.javafx.events.AbstractEventListener;

public interface AddressChangeListener extends AbstractEventListener<AddressSelectedEvent>{
	@Override
	void handleEvent(AddressSelectedEvent event);
}
