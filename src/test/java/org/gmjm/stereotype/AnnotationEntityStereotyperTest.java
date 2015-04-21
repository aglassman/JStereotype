package org.gmjm.stereotype;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.gmjm.stereotype.annotation.NotRequired;
import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamic;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.entity.JavaAssistDynamicEntityClassModifier;
import org.gmjm.stereotype.loader.JsonLoader;
import org.gmjm.stereotype.loader.ReflectionBasedJsonToJavaMapper;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.IPropertyExtractor;
import org.gmjm.stereotype.property.Property;
import org.gmjm.stereotype.property.PropertyDescriptor;
import org.gmjm.stereotype.property.ReflectionPropertyExtractor;
import org.junit.Test;

import util.FileLoader;

public class AnnotationEntityStereotyperTest {
	
	//Object to stereotype
	public static class MyObject {
		Property<Float> friction;
		Property<Integer> count;
		Property<Double> result;
		TestValueObject testValueObject;
		
		@NotRequired
		Property<Long> length;
		
		@NotRequired(ignore=true)
		Integer accessCount;
	}
	
	//Meets stereotype
	public static class OtherObject {
		Property<Float> friction;
		
		Property<Integer> count;
		
		Property<Double> result;
		
		TestValueObject testValueObject;
	}
	
	//Does not meet stereotype (required fields missing)
	public static class BadObject {
		TestValueObject testValueObject;
		Integer count;
	}

	IPropertyExtractor extractor = new ReflectionPropertyExtractor();

	Collection<IPropertyDescriptor> buildableProperties = extractor.extract(MyObject.class);	
			
	IEntityStereotyper<MyObject> stereotyper = new AnnotationEntityStereotyper<>(
			MyObject.class,
			buildableProperties,
			extractor,
			new JavaAssistDynamicEntityClassModifier(),
			new JsonLoader(new ReflectionBasedJsonToJavaMapper(),buildableProperties));
	
	@Test
	public void create_validTestObject_notNullAndCorrectType()
	{
		MyObject myObj = stereotyper.create();
		assertNotNull(myObj);
		assertTrue(myObj instanceof MyObject);		
	}
	
	@Test
	public void getStereotypeProperties_validTestObject_expectedPropertiesReturned() throws NoSuchFieldException, SecurityException
	{
		Collection<IPropertyDescriptor> extracted = stereotyper.getStereotypeProperties();
		
		Collection<IPropertyDescriptor> expected = Arrays.asList(
				new PropertyDescriptor("friction",TestObject.class),
				new PropertyDescriptor("count",TestObject.class),
				new PropertyDescriptor("result",TestObject.class),
				new PropertyDescriptor("length",TestObject.class),
				new PropertyDescriptor("testValueObject", TestObject.class));
		
		assertTrue(expected.containsAll(extracted));
	}
	
	@Test
	public void createStereotypedDynamicEntity_validTestObject_expectedPropertiesExistOnDynamicEntity()
	{
		IDynamicEntity de = stereotyper.createStereotypedDynamicEntity();
		
		assertTrue(de.hasProperty("friction"));
		assertTrue(de.hasProperty("count"));
		assertTrue(de.hasProperty("result"));
		assertTrue(de.hasProperty("length"));
		assertTrue(de.hasProperty("testValueObject"));
	}
	
	@Test
	public void meetsStereotype_sameClass_returnsTrue()
	{
		TestObject to = new TestObject();
		assertTrue(stereotyper.meetsStereotype(to));
	}
	
	@Test
	public void meetsStereotype_differentClassWithMatchingAttributes_returnsTrue()
	{
		OtherObject oo = new OtherObject();
		assertTrue(stereotyper.meetsStereotype(oo));
	}
	
	@Test
	public void stereotype_object_validDynamicEntity()
	{	
		IDynamicEntity de = new HashMapDynamicEntity();
		
		Object p1 = new Property<Integer>("count", de);
		Object p2 = new Property<Float>("friction", de);
		Object p3 = new Property<Double>("result", de);
		Object p4 = new TestValueObject();
		
		de.setProperty("testValueObject", p4);
				
		MyObject myObj = stereotyper.stereotype(de);
		
		assertEquals(p1, myObj.count);
		assertEquals(p2, myObj.friction);
		assertEquals(p3, myObj.result);
		assertEquals(p4, myObj.testValueObject);
		
	}
	
	@Test
	public void stereotype_testObject2_validDynamicEntity()
	{	
		IDynamicEntity de = new HashMapDynamicEntity();
				
		OtherObject oo = new OtherObject();
		oo.count = new Property<Integer>("count", de);
		oo.friction = new Property<Float>("friction", de);
		oo.result = new Property<Double>("result", de);
		oo.testValueObject = new TestValueObject();
				
		MyObject myObj = stereotyper.stereotype(oo);
		
		assertEquals(oo.count, myObj.count);
		assertEquals(oo.friction, myObj.friction);
		assertEquals(oo.result, myObj.result);
		assertEquals(oo.testValueObject, myObj.testValueObject);
		
		oo.count.setValue(1);
		oo.friction.setValue(22.3f);
		oo.result.setValue(33.993d);
		oo.testValueObject.attackCount = 9;
		
		assertEquals(oo.count.getValue(), myObj.count.getValue());
		assertEquals(oo.friction.getValue(), myObj.friction.getValue());
		assertEquals(oo.result.getValue(), myObj.result.getValue());
		assertEquals(oo.testValueObject.attackCount, myObj.testValueObject.attackCount);
		
	}

	@Test
	public void stereotype_badObject_doesNotMeetStereotype()
	{	
		BadObject bo = new BadObject();
		bo.testValueObject = new TestValueObject();
				
		assertFalse(stereotyper.meetsStereotype(bo));
		
	}
	
	@Test
	/**
	 * This still passes.  The BadObject will be stereotyped as
	 * much as possible, but wont have all the stereotype fields.
	 */
	public void stereotype_badObject_validDynamicEntity()
	{	
		BadObject bo = new BadObject();
		bo.testValueObject = new TestValueObject();
				
		MyObject myObj = stereotyper.stereotype(bo);
		
		assertEquals(bo.testValueObject, myObj.testValueObject);
		
	}
	
	@Test
	public void stereotype_testIDynamicAdded()
	{
		BadObject bo = new BadObject();
		bo.testValueObject = new TestValueObject();
		
		MyObject mo = stereotyper.stereotype(bo);
		
		assertTrue(mo instanceof IDynamic);
	}
	
	@Test
	public void getLoader_testLoader_successfulLoad() throws FileNotFoundException, IOException
	{
		File file = FileLoader.load("custom_game_object.json");
		MyObject myObject = stereotyper.getLoader().loadFromFile(file);
		
		assertEquals(Integer.valueOf(10), myObject.count.getValue());
		assertEquals(Float.valueOf(10.002f), myObject.friction.getValue());
		assertEquals(Double.valueOf(10.92378d), myObject.result.getValue());
		assertEquals(Integer.valueOf(100), Integer.valueOf(myObject.testValueObject.attackCount));
		assertEquals(Float.valueOf(10.42f), Float.valueOf(myObject.testValueObject.damage));
		assertEquals(Boolean.valueOf(true), myObject.testValueObject.needsRepair);
		
	}
	
	@Test
	public void testInteger()
	{
		IEntityStereotyper<BadObject> boStereotyper = EntityStereotyperFactory.create(BadObject.class);
		
		IDynamicEntity de = new HashMapDynamicEntity();
		de.setProperty("count", 6);
		de.setProperty("testValueObject", new TestValueObject());
		
		BadObject bo = boStereotyper.stereotype(de);
		assertEquals(Integer.valueOf(6),bo.count);
		
		de.setProperty("count", 7);
		
		assertEquals(Integer.valueOf(6),bo.count);
		
	}
	
}
