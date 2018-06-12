package com.acme.service.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Address {
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		String oldAmount = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldAmount, name);

	}

	private String name;
	
}
