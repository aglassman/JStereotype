package org.gmjm.stereotype;

import org.gmjm.stereotype.annotation.NotRequired;
import org.gmjm.stereotype.annotation.Stereotype;
import org.gmjm.stereotype.property.Property;

@Stereotype
public class TestObject {

	Property<Float> friction;
	Property<Integer> count;
	Property<Double> result;
	TestValueObject testValueObject;
	
	@NotRequired
	Property<Long> length;
	
	public TestObject()
	{
		
	}
	
	public Float getFriction()
	{
		return friction.getValue();
	}
	
	public Integer getCount()
	{
		return count.getValue();
	}
	
	public Double getResult()
	{
		return result.getValue();
	}
	
	public boolean hasLength()
	{
		return length != null && length.getValue() != null;
	}
	
	public Long getLength()
	{
		return length.getValue();
	}
	
	public float getDamage()
	{
		return testValueObject.damage;
	}
	
	public TestValueObject getTestValueObject()
	{
		return testValueObject;
	}
	
}