package org.fmm.librosparroquiales.javafx.events;

public class AbstractEvent<T> {
	private T current;
	private T old;
	protected boolean success;
	
	public AbstractEvent(T old, T current, boolean success) {
		super();
		this.current = current;
		this.old = old;
		this.success = success;
	}

	public T getCurrent() {
		return current;
	}

	public T getOld() {
		return old;
	}

	public boolean isSuccess() {
		return success;
	}
}