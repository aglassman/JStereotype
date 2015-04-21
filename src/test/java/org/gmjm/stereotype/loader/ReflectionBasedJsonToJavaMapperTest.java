package org.gmjm.stereotype.loader;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IEntityProperty;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.Property;
import org.gmjm.stereotype.property.PropertyDescriptor;
import org.junit.Test;

public class ReflectionBasedJsonToJavaMapperTest {
	
	JsonObjectBuilder jObjBuilder = Json.createObjectBuilder();
	
	IJsonToJavaMapper mapper = new ReflectionBasedJsonToJavaMapper();
	
	public static class PropHolder {
		int ival;
		float fval;
		double dval;
	}
	
	public static class MyObj {
		IEntityProperty<Float> friction;
		IEntityProperty<Integer> count;
		BigDecimal bigDecimal;
		PropHolder propHolder;
	}
	
	@Test
	public void createAndMap_jsonObjectHasValue_valueMappedToIEntityProperty() throws NoSuchFieldException, SecurityException
	{
		JsonObject jObj = jObjBuilder
				.add("friction", BigDecimal.valueOf(1.35d))
				.build();
		
		IDynamicEntity de = new HashMapDynamicEntity();
		IPropertyDescriptor pd = new PropertyDescriptor("friction",MyObj.class);
		
		mapper.createAndMap(jObj, de, pd);
		
		Object obj = de.getProperty("friction");
		
		assertEquals(Float.class,obj.getClass());
		assertEquals(Float.valueOf("1.35"),obj);
		
		MyObj myObj = new MyObj();
		myObj.friction = new Property<>("friction", de);
		
		assertEquals(Float.valueOf("1.35"),myObj.friction.getValue());
		
	}
	
	@Test
	public void createAndMap_unmappableField_nullObjectReturned() throws NoSuchFieldException, SecurityException
	{
		JsonObject jObj = jObjBuilder
				.add("bigDecimal", BigDecimal.valueOf(1.35d))
				.build();
		
		IDynamicEntity de = new HashMapDynamicEntity();
		IPropertyDescriptor pd = new PropertyDescriptor("bigDecimal",MyObj.class);
		
		mapper.createAndMap(jObj, de, pd);
		
		assertNull(de.getProperty("bigDecimal"));		
	}
	
	@Test
	public void createAndMap_unmappablpropHolderField_objectReturned() throws NoSuchFieldException, SecurityException
	{
		JsonObject propHolderJson = jObjBuilder
				.add("ival", 1)
				.add("fval", 1.2f)
				.add("dval", 1.3d)
				.build();
		
		JsonObject jObj = jObjBuilder
				.add("propHolder", propHolderJson)
				.build();
		
		IDynamicEntity de = new HashMapDynamicEntity();
		IPropertyDescriptor pd = new PropertyDescriptor("propHolder",MyObj.class);
		
		mapper.createAndMap(jObj, de, pd);
		
		Object obj = de.getProperty("propHolder");
		
		assertTrue(obj instanceof PropHolder);
		PropHolder ph = (PropHolder)obj;

		assertTrue(ph.dval == 1.3d);
		assertTrue(ph.ival == 1);
		assertEquals(Float.valueOf(1.2f),Float.valueOf(ph.fval));
		
	}
}
