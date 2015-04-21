package org.gmjm.stereotype;

import java.util.Collection;

import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.loader.IStereotypeLoader;
import org.gmjm.stereotype.property.IPropertyDescriptor;

public interface IEntityStereotyper<T> {

	T create();

	IDynamicEntity createStereotypedDynamicEntity();

	boolean meetsStereotype(Object dynamicEntity);
	
	T stereotype(Object dynamicEntity);
	
	IStereotypeLoader<T> getLoader();
	
	Collection<IPropertyDescriptor> getStereotypeProperties();
	
}