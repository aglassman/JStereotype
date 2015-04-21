package org.gmjm.stereotype.example;

import static org.junit.Assert.*;

import org.gmjm.stereotype.EntityStereotyperFactory;
import org.gmjm.stereotype.IEntityStereotyper;
import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.example.componenets.Address;
import org.gmjm.stereotype.example.componenets.Files;
import org.gmjm.stereotype.example.componenets.PropertyHolder;
import org.junit.Test;

public class Example {
	
	/*
	 * Before looking at this example, take a quick look at the following classes.
	 * org.gmjm.stereotype.example.Stereotype1.java
	 * org.gmjm.stereotype.example.Stereotype2.java
	 * org.gmjm.stereotype.example.components.*
	 * 
	 * First, create a stereotype factory for each class you'd like to stereotype.  The factory
	 * uses reflection to create a collection of IPropertyDescriptors that describe the class
	 * attributes.  All attributes are mapped regardless of access modifiers.  If you'd like to 
	 * make an attribute optional, or ignore it completely, check out the AnnotationExample.
	 * 
	 */
	IEntityStereotyper<Stereotype1> stereotyper1 = EntityStereotyperFactory.create(Stereotype1.class);
	IEntityStereotyper<Stereotype2> stereotyper2 = EntityStereotyperFactory.create(Stereotype2.class);
	
	
	@Test
	public void stereotypeTest()
	{
		//First create a backing entity 
		IDynamicEntity de = new HashMapDynamicEntity();
		
		//Add all the properties that make up the composition
		de.setProperty("address", new Address("123 Fake street", "Dallas", "WI", "54321"));
		de.setProperty("files",new Files());
		de.setProperty("propertyHolder",new PropertyHolder());
		de.setProperty("weight", 3.4f);
		de.setProperty("length", 2.4f);
		de.setProperty("height", 1.4f);
		
		/*
		 * Create a stereotyped object.  
		 */
		Stereotype1 obj1 = stereotyper1.stereotype(de);
		Stereotype2 obj2 = stereotyper2.stereotype(de);
		
		//Check that attribute Objects are the same objects
		assertEquals(obj1.address,obj2.address);
		assertEquals(obj1.files,obj2.files);
		assertEquals(obj1.propertyHolder,obj2.propertyHolder);
		assertEquals(obj1.weight,obj2.weight);
		
		//Check that stereotype 2 has the length and width properties
		assertNotNull(obj2.length);
		assertNotNull(obj2.height);
		assertTrue(obj2.length.getValue() == 2.4f);
		assertTrue(obj2.height.getValue() == 1.4f);
		
		/* stereotyping obj1 -> obj3,   obj1's backing map populates obj3's properties
		 * despite obj1 not having all of Stereotype2 attributes.
		 */
		Stereotype2 obj3 = stereotyper2.stereotype(obj1);
		assertNotNull(obj3.length);
		assertNotNull(obj3.height);
		assertTrue(obj3.length.getValue() == 2.4f);
		assertTrue(obj3.height.getValue() == 1.4f);
		
	}

}
