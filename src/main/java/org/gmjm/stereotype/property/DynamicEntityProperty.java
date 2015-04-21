package org.gmjm.stereotype.property;

import org.gmjm.stereotype.entity.IDynamicEntity;

public class DynamicEntityProperty<T> implements IEntityProperty<T> {

	public final String propertyName;
	public IDynamicEntity dynamicEntity;
	
	public DynamicEntityProperty(String propertyName, IDynamicEntity dynamicEntity) {
		if (propertyName == null) {
			throw new IllegalArgumentException("propertyName cannot be null.");
		}
		if (dynamicEntity == null) {
			throw new IllegalArgumentException("dynamicEntity cannot be null.");
		}
		this.propertyName = propertyName;
		this.dynamicEntity = dynamicEntity;
	}

	@Override
	public T getValue() {
		return dynamicEntity.getProperty(propertyName);
	}

	@Override
	public void setValue(T value) {
		dynamicEntity.setProperty(propertyName, value);
	}
	
	@Override
	public String getPropertyName() {
		return propertyName;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof IEntityProperty)
		{
			IEntityProperty<?> entityProperty = (IEntityProperty<?>)o;
			return propertyName.equals(entityProperty.getPropertyName())
					&& dynamicEntity.equals(entityProperty.getBackingDynamicEntity());
		}
		
		return false;
	}

	@Override
	public IDynamicEntity getBackingDynamicEntity() {
		return dynamicEntity;
	}

	@Override
	public void setBackingDynamicEntity(IDynamicEntity dynamicEntity) {
		this.dynamicEntity = dynamicEntity;
	}

}
