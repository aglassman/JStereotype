package org.gmjm.stereotype.example;

import static org.junit.Assert.assertEquals;

import org.gmjm.stereotype.EntityStereotyperFactory;
import org.gmjm.stereotype.IEntityStereotyper;
import org.gmjm.stereotype.TestValueObject;
import org.gmjm.stereotype.AnnotationEntityStereotyperTest.BadObject;
import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IEntityProperty;
import org.junit.Test;

public class GotchaExample {
	
	public static class BadObject {
		IEntityProperty<Integer> countProperty;
		Integer count;
	}
	
	@Test
	public void gotchaExample()
	{
		IEntityStereotyper<BadObject> boStereotyper = EntityStereotyperFactory.create(BadObject.class);
		
		IDynamicEntity de = new HashMapDynamicEntity();
		de.setProperty("count", 6);
		
		BadObject bo = boStereotyper.stereotype(de);
		assertEquals(Integer.valueOf(6),bo.count);
		
		de.setProperty("count", 7);
		
		/*
		 * Since Integer is immutable, you have to replace the value
		 * with a new instance of Integer.  The stereotyped entity has
		 * no way of knowing the backing entity was updated, so the 
		 * attribute is still 6.
		 * 
		 * See the correctWay() test for how to overcome this.
		 * 
		 */
		assertEquals(Integer.valueOf(6),bo.count);
	}
	
	@Test
	public void correctWay()
	{
		/*
		 * The IStereotypeFactory instance knows that IEntityProperty fields are just
		 * an interface for interacting with the backing entity.  You set the instance 
		 * in the backing entity as normal, but now the stereotyped class has a way
		 * of getting and setting the value in the backing entity.
		 *  
		 */
		IEntityStereotyper<BadObject> boStereotyper = EntityStereotyperFactory.create(BadObject.class);
		
		IDynamicEntity de = new HashMapDynamicEntity();
		de.setProperty("countProperty", 6);
		
		BadObject bo = boStereotyper.stereotype(de);
		assertEquals(Integer.valueOf(6),bo.countProperty.getValue());
		
		de.setProperty("countProperty", 7);
		
		assertEquals(Integer.valueOf(7),bo.countProperty.getValue());
	}
}
