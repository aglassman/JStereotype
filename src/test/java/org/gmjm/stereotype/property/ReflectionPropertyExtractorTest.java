package org.gmjm.stereotype.property;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.gmjm.stereotype.TestObject;
import org.gmjm.stereotype.TestValueObject;
import org.gmjm.stereotype.annotation.NotRequired;
import org.junit.Test;

public class ReflectionPropertyExtractorTest {
	
	public static class MyObject {
		Property<Float> friction;
		Property<Integer> count;
		Property<Double> result;
		TestValueObject testValueObject;
		
		@NotRequired
		Property<Long> length;
		
		@NotRequired(ignore=true)
		Integer accesCount;
	}
	
	IPropertyExtractor extractor = new ReflectionPropertyExtractor();

	@Test
	public void test() throws NoSuchFieldException, SecurityException
	{
		Collection<IPropertyDescriptor> extracted = extractor.extract(MyObject.class);
		
		Collection<IPropertyDescriptor> expected = Arrays.asList(
				new PropertyDescriptor("friction",TestObject.class),
				new PropertyDescriptor("count",TestObject.class),
				new PropertyDescriptor("result",TestObject.class),
				new PropertyDescriptor("length",TestObject.class),
				new PropertyDescriptor("testValueObject", TestObject.class));
		
		assertTrue(extracted.containsAll(expected));
		
	}
}
