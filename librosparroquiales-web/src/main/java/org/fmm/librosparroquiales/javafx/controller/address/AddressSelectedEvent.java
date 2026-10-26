package org.fmm.librosparroquiales.javafx.controller.address;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.javafx.events.AbstractEvent;

public class AddressSelectedEvent extends AbstractEvent<Address> {

	private String source;
	
	public AddressSelectedEvent(Address old, Address current, boolean success, String source) {
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
