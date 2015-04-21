package org.gmjm.stereotype;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.LoaderClassPath;

import org.gmjm.stereotype.annotation.Stereotype;
import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamic;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntityClassModifier;
import org.gmjm.stereotype.entity.JavaAssistDynamicEntityClassModifier;
import org.junit.Test;

public class JavaAssistDynamicEntityClassModifierTest {
	
	@Stereotype
	public static class ModifyMe {
		private Float myFloat = 3f;
	}
	
	
	@Test
	public void createModifiedSubclass_assertCorrectModificationsMade() throws Exception
	{
		IDynamicEntityClassModifier modifier = new JavaAssistDynamicEntityClassModifier();
		
		Class<ModifyMe> a = ModifyMe.class;
		Class<? extends ModifyMe> b = modifier.createModifiedSubclass(a);
		
		assertTrue(Arrays.stream(b.getInterfaces()).anyMatch(i -> i.equals(IDynamic.class)));
		assertTrue(b.getGenericSuperclass().equals(ModifyMe.class));
		
		ModifyMe me = (ModifyMe)b.newInstance();
		IDynamic dynamic = (IDynamic)b.newInstance();
		
		assertTrue(dynamic.getDynamicEntity() instanceof HashMapDynamicEntity);
	}
	
	@Test
	public void testIDynamicEntityConstructor() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		IDynamicEntityClassModifier modifier = new JavaAssistDynamicEntityClassModifier();
		
		Class<ModifyMe> newClass = modifier.createModifiedSubclass(ModifyMe.class);
		
		Constructor<ModifyMe> c = newClass.getConstructor(IDynamicEntity.class);
		
		IDynamicEntity de = new HashMapDynamicEntity();
		
		ModifyMe me = c.newInstance(de);
		
		assertTrue(me instanceof IDynamic);
		assertEquals(de,((IDynamic)me).getDynamicEntity());
		
	}
}
