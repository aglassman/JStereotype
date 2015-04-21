package org.gmjm.stereotype.property;

import org.gmjm.stereotype.entity.IDynamicEntity;


public class Property<T> extends DynamicEntityProperty<T> {

	public Property(String propertyName, IDynamicEntity dynamicEntity) {
		super(propertyName,dynamicEntity);
	}
}
