package org.gmjm.stereotype.entity;

import java.util.HashMap;
import java.util.Map;

import org.gmjm.stereotype.property.IEntityProperty;

public class HashMapDynamicEntity implements IDynamicEntity {
	
	private Map<String,Object> propertyMap;
	
	public HashMapDynamicEntity() {
		this.propertyMap = new HashMap<>();
	}
	
	public HashMapDynamicEntity (Map<String,Object> propertyMap)
	{
		this.propertyMap = propertyMap;
	}

	@Override
	public boolean hasProperty(String key)
	{
		return propertyMap.containsKey(key);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key)
	{
		return (T)propertyMap.get(key);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getProperty(IEntityProperty entityProperty)
	{
		return (T)propertyMap.get(entityProperty.getPropertyName());
	}
	
	@Override
	public <T> void setProperty(String key, T value) {
		propertyMap.put(key, value);
	}

	@Override
	public Map<String,Object> getPropertyMap()
	{
		return propertyMap;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof IDynamicEntity)
		{
			return propertyMap == (((IDynamicEntity)o).getPropertyMap());
		}
		
		return false;
	}
	
}
