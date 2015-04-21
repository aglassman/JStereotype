package org.gmjm.stereotype.property;

import java.lang.reflect.Field;

public interface IPropertyDescriptor {

	String getPropetyName();

	Class getPropertyClass();
	
	boolean isRequired();
	
	Field field();

	public static boolean isIEntityProperty(IPropertyDescriptor propertyDescriptor) {
		return IEntityProperty.class.isAssignableFrom(propertyDescriptor.getPropertyClass());
	}

}