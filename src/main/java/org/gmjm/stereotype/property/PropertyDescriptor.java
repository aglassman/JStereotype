package org.gmjm.stereotype.property;

import java.lang.reflect.Field;

import org.gmjm.stereotype.annotation.NotRequired;

public class PropertyDescriptor implements IPropertyDescriptor {
	private final String propetyName;
	private final Class<?> propertyClass;
	private final boolean isRequired;
	private final Field field;

	public PropertyDescriptor(Field field) {
		this.propetyName = field.getName();
		this.propertyClass = field.getType();
		this.isRequired = field.isAnnotationPresent(NotRequired.class);;
		this.field = field;
		this.field.setAccessible(true);
	}

	public PropertyDescriptor(String propertyName, Class clazz) throws NoSuchFieldException, SecurityException {
		this.field = clazz.getDeclaredField(propertyName);
		this.field.setAccessible(true);
		this.propetyName = propertyName;
		this.propertyClass = field.getType();
		this.isRequired = field.isAnnotationPresent(NotRequired.class);
		
	}

	@Override
	public String getPropetyName() {
		return propetyName;
	}

	@Override
	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof IPropertyDescriptor) {
			IPropertyDescriptor pd = (IPropertyDescriptor) o;
			return propetyName.equals(pd.getPropetyName())
					&& propertyClass.equals(pd.getPropertyClass())
					&& isRequired == pd.isRequired();
		}
		return false;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}

	@Override
	public Field field() {
		return field;
	}

}
