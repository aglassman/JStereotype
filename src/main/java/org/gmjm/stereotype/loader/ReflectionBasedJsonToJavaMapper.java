package org.gmjm.stereotype.loader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IEntityProperty;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.Property;
import org.gmjm.stereotype.util.JsonFieldSetterUtil;
import org.gmjm.stereotype.util.ReflectionUtils;

public class ReflectionBasedJsonToJavaMapper implements IJsonToJavaMapper{
	
	@Override
	public void createAndMap(JsonObject jsonObject, IDynamicEntity dynamicEntity, IPropertyDescriptor propertyDescriptor) {
		String propName = propertyDescriptor.getPropetyName();
		
		if(!jsonObject.containsKey(propName))
				return;
		
		JsonValue jsonValue = jsonObject.get(propName);
			
		Object mappedObj = null;
		
		if(IPropertyDescriptor.isIEntityProperty(propertyDescriptor)) {
			mappedObj = getProperty(jsonObject, propName, propertyDescriptor);
		}
		else if (jsonValue.getValueType().equals(ValueType.OBJECT)) {
			mappedObj = createAndSetAllFields(jsonObject.getJsonObject(propName),propertyDescriptor);
		} 
		dynamicEntity.setProperty(propName, mappedObj);
		
	}
	

	private Object createAndSetAllFields(JsonObject propertyJsonObject, IPropertyDescriptor propertyDescriptor) {
		
		try {
			Object targetObject = propertyDescriptor.getPropertyClass().newInstance();
			for(String attributeKey : propertyJsonObject.keySet()) {
				setField(propertyJsonObject, targetObject, attributeKey, attributeKey);
			}
			return targetObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
		
	}

	private void setField(JsonObject propertyJsonObject,
			Object targetObject, String attributeKey, String fieldName) {
		try {
			Field field = targetObject.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
	
			JsonValue jsonValue = propertyJsonObject.get(attributeKey);
	
			switch (jsonValue.getValueType()) {
			case FALSE:
			case TRUE:
				field.set(targetObject, propertyJsonObject.getBoolean(attributeKey));
				break;
			case NUMBER:
				JsonNumber number = propertyJsonObject.getJsonNumber(attributeKey);
				JsonFieldSetterUtil.setNumberField(targetObject, field, number);
				break;
			case STRING:
				field.set(targetObject, propertyJsonObject.getString(attributeKey));
				break;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Object getProperty(JsonObject propertyJsonObject, String attributeKey,IPropertyDescriptor propertyDescriptor) {
		try {
			JsonValue jsonValue = propertyJsonObject.get(attributeKey);
	
			Object result = null;
			
			switch (jsonValue.getValueType()) {
			case FALSE:
			case TRUE:
				result = propertyJsonObject.getBoolean(attributeKey);
				break;
			case NUMBER:
				JsonNumber number = propertyJsonObject.getJsonNumber(attributeKey);
				Class numberClass = ReflectionUtils.getGenericType(propertyDescriptor.field());
				result = JsonFieldSetterUtil.getBoxedNumber(numberClass, number);
				break;
			case STRING:
				result = propertyJsonObject.getString(attributeKey);
				break;
			}
			
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	

}
