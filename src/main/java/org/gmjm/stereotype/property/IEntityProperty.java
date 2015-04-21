package org.gmjm.stereotype.property;

import org.gmjm.stereotype.entity.IDynamicEntity;


public interface IEntityProperty<T> {

	String getPropertyName();
	
	T getValue();
	
	void setValue(T value);
		
	IDynamicEntity getBackingDynamicEntity();
	
	void setBackingDynamicEntity(IDynamicEntity dynamicEntity);
}
