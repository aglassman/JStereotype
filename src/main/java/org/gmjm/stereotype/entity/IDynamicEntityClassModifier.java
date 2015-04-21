package org.gmjm.stereotype.entity;


public interface IDynamicEntityClassModifier {
	/**
	 *  Creates a subclass of the clazz at runtime.
	 * 
	 *  private IDynamicEntity dynamicEntity;
	 *    
	 *  returned class implements the {@link IDynamic} interface
	 *  
	 */
	<T> Class<T> createModifiedSubclass(Class<T> clazz);
}
