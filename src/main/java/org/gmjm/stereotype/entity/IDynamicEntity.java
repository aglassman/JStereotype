package org.gmjm.stereotype.entity;

import java.util.Map;

import org.gmjm.stereotype.property.IEntityProperty;

public interface IDynamicEntity {

	boolean hasProperty(String key);

	<T> T getProperty(String key);

	<T> T getProperty(IEntityProperty entityProperty);

	<T> void setProperty(String key, T value);
	
	Map<String, Object> getPropertyMap();

}