package org.gmjm.stereotype.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {
	
	public static Class getGenericSuperclass(Class inputClass)
	{
		return (Class)getGenericSuperclassType(inputClass);
	}
	
	public static Type getGenericSuperclassType(Class inputClass)
	{
		return getGenericSuperclassTypes(inputClass)[0];
	}
	
	public static Type[] getGenericSuperclassTypes(Class inputClass)
	{
		return getTypesFromSuperclass(inputClass);
	}
	
	private static Type[] getTypesFromSuperclass(Class inputClass)
	{
		return ((ParameterizedType) inputClass.getGenericSuperclass()).getActualTypeArguments();
	}

	public static Class getGenericType(Field field) {
		Type type = field.getGenericType();
		if(type instanceof ParameterizedType)
		{
			return (Class) ((ParameterizedType)type).getActualTypeArguments()[0];
		}
		
		return null;
	}
}
