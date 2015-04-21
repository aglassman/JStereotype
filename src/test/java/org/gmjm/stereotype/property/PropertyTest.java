package org.gmjm.stereotype.property;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyTest {
	
	public static class MyObj {
		Property<Float> friction;
		Object obj;
	}
	
	@Test()
	public void testPropertyConstructorProperty_valid() throws NoSuchFieldException, SecurityException
	{
		PropertyDescriptor pd = new PropertyDescriptor("friction", MyObj.class);
		
		assertEquals(Property.class,pd.getPropertyClass());
	}
	
	@Test()
	public void testPropertyConstructor_valid() throws NoSuchFieldException, SecurityException
	{
		PropertyDescriptor pd = new PropertyDescriptor("obj", MyObj.class);
		
		assertEquals(Object.class,pd.getPropertyClass());
	}
}
