package org.gmjm.stereotype.example;

import org.gmjm.stereotype.example.componenets.Address;
import org.gmjm.stereotype.example.componenets.Files;
import org.gmjm.stereotype.example.componenets.PropertyHolder;
import org.gmjm.stereotype.property.IEntityProperty;

public class Stereotype1 {
	
	/*
	 * All properties are mapped as required unless noted otherwise by
	 * annotations. See AnnotatedStereotype for an example.
	 * 
	 * Note that object references will not be updated if the instance of
	 * the backing object is replaced.  If you want to be able to get an
	 * instance of an object that may be changing, use IEntityProperty.  This is useful
	 * for accessing immutable objects like Float, Integer, Double, and String that cannot
	 * be modified.
	 */
	
	IEntityProperty<Float> weight;
	Files files;
	PropertyHolder propertyHolder;
	Address address;	
}
