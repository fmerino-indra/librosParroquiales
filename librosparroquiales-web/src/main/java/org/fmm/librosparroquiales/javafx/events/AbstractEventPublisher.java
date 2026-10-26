package org.fmm.librosparroquiales.javafx.events;

import java.util.List;

public interface AbstractEventPublisher<T extends AbstractEvent<?>, S extends AbstractEventListener<T>> {
	public abstract void addEventListener(S listener);
	public abstract void removeEventListener(S listener);
	public abstract List<S> getListeners();
	public boolean isProcessing();
	public void setProcessing(boolean processing);
	
	public default void fireEvent(T event) {
		if (isProcessing())
			return;
		else
			setProcessing(true);

		getListeners().forEach(listener ->{
			try {
				listener.handleEvent(event);
			} catch (Exception e) {
				setProcessing(false);
				e.printStackTrace();
			}
		});
		setProcessing(false);
		
	}
	
/*
		public void fireEvent(AddressSelectedEvent event) {
			if (isProcessing())
				return;
			else
				processing = true;
			listeners.forEach(listener ->{
				try {
					listener.handleEvent(event);
				} catch (Exception e) {
					processing = false;
					e.printStackTrace();
				}
			});
			processing = false;
		}
		public boolean isProcessing() {
			return processing;
		}
	
 */
}
