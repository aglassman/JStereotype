package org.gmjm.stereotype.loader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.gmjm.stereotype.TestValueObject;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IEntityProperty;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.PropertyDescriptor;
import org.junit.Test;

import util.FileLoader;

public class JsonLoaderTest {
	
	public static class TestObject {
		IEntityProperty<Float> friction;
		IEntityProperty<Integer> count;
		IEntityProperty<Double> result;
		TestValueObject testValueObject;
		IEntityProperty<Long> length;
	}
		
	IJsonToJavaMapper jsonToJavaMapper = new ReflectionBasedJsonToJavaMapper();
	IDynamicEntityLoader loader = new JsonLoader(jsonToJavaMapper,getBuildableProperties());
	
	public static Collection<IPropertyDescriptor> getBuildableProperties() {
		try {
			return Arrays.asList(
					new PropertyDescriptor("friction", TestObject.class),
					new PropertyDescriptor("count", TestObject.class),
					new PropertyDescriptor("result", TestObject.class),
					new PropertyDescriptor("testValueObject", TestObject.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void test() throws FileNotFoundException, IOException
	{
		File customObjectFile = FileLoader.load("custom_game_object.json");
		
		IDynamicEntity de = loader.loadFromFile(customObjectFile);
		
		assertEquals(Float.valueOf("10.002"),de.getProperty("friction"));
		assertEquals(Integer.valueOf("10"),de.getProperty("count"));
		assertEquals(Double.valueOf("10.92378"),de.getProperty("result"));
		
		TestValueObject tvo = de.getProperty("testValueObject");
		
		assertEquals(100,tvo.attackCount);
		assertEquals(Float.valueOf(10.42f),Float.valueOf(tvo.damage));
		assertEquals(true,tvo.needsRepair);
	}
}
