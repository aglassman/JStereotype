package org.gmjm.stereotype.loader;

import javax.json.JsonObject;

import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IPropertyDescriptor;

public interface IJsonToJavaMapper {
	/**
	 * This class will check the jsonObject for a key matching the
	 * propertyDescriptor.getPropertyName() result.  If the propertyDescriptor.getPropertyClass()
	 * result is an instance of 
	 * 
	 * @param jsonObject
	 * @param propertyDescriptor
	 * @return
	 */
	void createAndMap(JsonObject jsonObject, IDynamicEntity dynamicEntity, IPropertyDescriptor propertyDescriptor);
}
