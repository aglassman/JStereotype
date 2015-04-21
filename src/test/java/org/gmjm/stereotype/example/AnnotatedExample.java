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

public class AnnotatedExample {
	/*
	 * Before looking at this example, take a quick look at the following
	 * classes. org.gmjm.stereotype.example.AnnotatedStereotype.java
	 * 
	 * Just like in the main example, the EntityStereotyperFactory inspects the 
	 * class attributes to define the stereotype.  The difference this time is
	 * that annotations have been used to set the trueCount field to not be required
	 * and the count field to be completely ignored.
	 * 
	 */
	IEntityStereotyper<AnnotatedStereotype> annotatedStereotyper = EntityStereotyperFactory.create(AnnotatedStereotype.class);

	@Test
	public void annotatedExample() {
		// First create a backing entity
		IDynamicEntity de = new HashMapDynamicEntity();

		// Add all the properties that make up the composition
		de.setProperty("address", new Address("123 Fake street", "Dallas", "WI", "54321"));
		de.setProperty("files", new Files());
		de.setProperty("propertyHolder", new PropertyHolder());
		de.setProperty("weight", 3.4f);
		de.setProperty("length", 2.4f);
		de.setProperty("height", 1.4f);
		de.setProperty("trueCount", 22l);
		de.setProperty("count", 5); //Ignored by this stereotyper
				
		AnnotatedStereotype as = annotatedStereotyper.stereotype(de);
		
		assertEquals("Dallas",as.address.getCity());
		assertEquals(Long.valueOf(22l),as.trueCount.getValue());
		assertNull(as.count);
		
	}
	
	@Test
	public void failsStereotypeIfRequiredIsMissing() {
		// First create a backing entity
		IDynamicEntity de = new HashMapDynamicEntity();

		// Add all the properties that make up the composition
		de.setProperty("files", new Files());
		de.setProperty("propertyHolder", new PropertyHolder());
		de.setProperty("weight", 3.4f);
		de.setProperty("length", 2.4f);
		de.setProperty("height", 1.4f);
		de.setProperty("count", 5);
		
		//should return false because address is missing
		assertFalse(annotatedStereotyper.meetsStereotype(de));

		de.setProperty("address", new Address("123 Fake street", "Dallas", "WI", "54321"));
		
		//should now return true because address was set on backing entity.
		assertTrue(annotatedStereotyper.meetsStereotype(de));
	}
}
