package org.gmjm.stereotype.entity;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.junit.Test;

public class HashMapDynamicEntityTest {
	@Test
	public void equals_differentBackingMaps_notEqual()
	{
		IDynamicEntity de1 = new HashMapDynamicEntity();
		IDynamicEntity de2 = new HashMapDynamicEntity();
		
		assertFalse(de1.equals(de2));
		assertFalse(de2.equals(de1));
	}
	
	@Test
	public void equals_sameBackingMaps_equal()
	{
		Map<String,Object> backingMap = new HashMap<>();
		IDynamicEntity de1 = new HashMapDynamicEntity(backingMap);
		IDynamicEntity de2 = new HashMapDynamicEntity(backingMap);
		
		assertTrue(de1.equals(de2));
		assertTrue(de2.equals(de1));
	}
}
