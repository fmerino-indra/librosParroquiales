package org.fmm.librosparroquiales.javafx.events;

public interface AbstractEventListener<E extends AbstractEvent<?>> {
	void handleEvent(E event);
}
