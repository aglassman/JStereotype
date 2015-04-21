package org.gmjm.stereotype.property;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.junit.Test;

public class DynamicEntityPropertyTest {
	@Test(expected=IllegalArgumentException.class)
	public void constructor_nullPropertyName()
	{
		new DynamicEntityProperty<>(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void  constructor_nullDynamicEntityName()
	{
		new DynamicEntityProperty<>("propName", null);
	}
	
	@Test
	public void setValue_returnsSameObject()
	{
		IDynamicEntity de = new HashMapDynamicEntity();
		IEntityProperty<Object> ep = new DynamicEntityProperty<>("testObj",de);
		
		Object expected = new Object();
		ep.setValue(expected);
		
		assertEquals(expected,ep.getValue());
	}
	
	@Test
	public void getValue_returnsSameObject()
	{
		IDynamicEntity de = new HashMapDynamicEntity();
		IEntityProperty<Object> ep = new DynamicEntityProperty<>("testObj",de);
		
		Object expected = new Object();
		de.setProperty("testObj", expected);
		
		assertEquals(expected,ep.getValue());
	}
	
	@Test
	public void equals_samePropNameAndBackingEntity_expectTrue()
	{
		IDynamicEntity de = new HashMapDynamicEntity();
		IEntityProperty<Object> ep1 = new DynamicEntityProperty<>("testObj",de);
		IEntityProperty<Object> ep2 = new DynamicEntityProperty<>("testObj",de);
		
		assertEquals(ep1,ep2);
	}
	
	@Test
	public void equals_differentPropNameAndBackingEntity_expectFalse()
	{
		IDynamicEntity de = new HashMapDynamicEntity();
		IEntityProperty<Object> ep1 = new DynamicEntityProperty<>("testObj1",de);
		IEntityProperty<Object> ep2 = new DynamicEntityProperty<>("testObj2",de);
		
		assertNotEquals(ep1,ep2);
	}
	
	@Test
	public void equals_samePropNameAndDifferentBackingEntity_expectFalse()
	{
		IDynamicEntity de1 = new HashMapDynamicEntity();
		IDynamicEntity de2 = new HashMapDynamicEntity();
		IEntityProperty<Object> ep1 = new DynamicEntityProperty<>("testObj",de1);
		IEntityProperty<Object> ep2 = new DynamicEntityProperty<>("testObj",de2);
		
		assertNotEquals(ep1,ep2);
	}
	
	@Test
	public void equals_samePropNameAndSameBackingEntity_equals()
	{
		Map<String,Object> backingMap = new HashMap<>();
		IDynamicEntity de1 = new HashMapDynamicEntity(backingMap);
		IDynamicEntity de2 = new HashMapDynamicEntity(backingMap);
		IEntityProperty<Object> ep1 = new DynamicEntityProperty<>("testObj",de1);
		IEntityProperty<Object> ep2 = new DynamicEntityProperty<>("testObj",de2);
		
		assertTrue(ep1.equals(ep2));
		assertTrue(ep2.equals(ep1));
	}
}
