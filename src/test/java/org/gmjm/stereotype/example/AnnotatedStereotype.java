package org.gmjm.stereotype.example;

import org.gmjm.stereotype.annotation.NotRequired;
import org.gmjm.stereotype.example.componenets.Address;
import org.gmjm.stereotype.example.componenets.Files;
import org.gmjm.stereotype.example.componenets.PropertyHolder;
import org.gmjm.stereotype.property.IEntityProperty;

public class AnnotatedStereotype {
	IEntityProperty<Float> weight;
	Files files;
	PropertyHolder propertyHolder;
	Address address;
	
	/*
	 * Fields annotated with NotRequired will map to the backing entity, but
	 * if the property does not exist in the backing entity, it will still 
	 * meet the stereotype.
	 */
	@NotRequired
	IEntityProperty<Long> trueCount;
	
	/*
	 * If NotRequired(ignore=true) is defined, then this property will not
	 * be included in the stereotype definition at all.  It will not be mapped
	 * even if the property exists in the backing entity.
	 */
	@NotRequired(ignore=true)
	Integer count;
}
