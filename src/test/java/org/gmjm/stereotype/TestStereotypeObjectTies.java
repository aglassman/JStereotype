package org.gmjm.stereotype;

import static org.junit.Assert.assertTrue;

import org.gmjm.stereotype.property.ReflectionPropertyExtractor;
import org.junit.Test;

public class TestStereotypeObjectTies {
	
	public static class MyObject {
		TestValueObject testValueObject;
	}
	
	public static class OtherObject {
		TestValueObject testValueObject;
	}

	IEntityStereotyper<MyObject> stereotyper = EntityStereotyperFactory.create(MyObject.class);
	
	@Test
	public void testObjectTies()
	{
		TestValueObject tvo = new TestValueObject();
		tvo.damage = 3.5f;
		tvo.needsRepair = false;
		
		OtherObject oo = new OtherObject();
		oo.testValueObject = tvo;
		
		MyObject mo = stereotyper.stereotype(oo);
		
		mo.testValueObject.damage = 5.6f;
		
		assertTrue(tvo == oo.testValueObject);
		assertTrue(tvo == mo.testValueObject);
		assertTrue(mo.testValueObject == oo.testValueObject);
		
		float expected =  5.6f;
		assertTrue(expected == tvo.damage);
		assertTrue(expected == mo.testValueObject.damage);
		assertTrue(expected == oo.testValueObject.damage);
	}
}
