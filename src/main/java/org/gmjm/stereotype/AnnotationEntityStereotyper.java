package org.gmjm.stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamic;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntityClassModifier;
import org.gmjm.stereotype.loader.IDynamicEntityLoader;
import org.gmjm.stereotype.loader.IStereotypeLoader;
import org.gmjm.stereotype.loader.StereotypeLoader;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.IPropertyExtractor;
import org.gmjm.stereotype.property.Property;

public class AnnotationEntityStereotyper<T> implements IEntityStereotyper<T> {

	private final Class<? extends T> modifiedClass;
	private final IDynamicEntityClassModifier classModifier;
	private final Collection<IPropertyDescriptor> stereotypeProperties;
	private final Collection<IPropertyDescriptor> requiredProperties;
	private final IPropertyExtractor propertyExtractor;
	private final IDynamicEntityLoader dynamicEntityLoader;
	
	
	public AnnotationEntityStereotyper(Class<T> stereotypeClass,Collection<IPropertyDescriptor> buildableProperties, IPropertyExtractor propertyExtractor, IDynamicEntityClassModifier classModifier, IDynamicEntityLoader dynamicEntityLoader) {
		this.classModifier = classModifier;
		this.modifiedClass = (Class<? extends T>)classModifier.createModifiedSubclass(stereotypeClass);
		this.propertyExtractor = propertyExtractor;
		this.stereotypeProperties = buildableProperties;
		this.requiredProperties = stereotypeProperties.stream()
				.filter(stereotypeProperty -> !stereotypeProperty.isRequired())
				.collect(Collectors.toList());
		this.dynamicEntityLoader = dynamicEntityLoader;
	}
	
	@Override
	public T create() {
		try {
			return modifiedClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDynamicEntity createStereotypedDynamicEntity() {
		IDynamicEntity de = new HashMapDynamicEntity();
		stereotypeProperties.stream()
			.forEach(propertyDescriptor -> {
				de.setProperty(propertyDescriptor.getPropetyName(),null);
			});
		return de;
	}

	@Override
	public boolean meetsStereotype(Object object) {
		if(modifiedClass.getClass().isAssignableFrom(object.getClass()))
		{
			return true;
		}			
		
		return propertyExtractor.extract(object.getClass()).containsAll(requiredProperties);
		
	}

	@Override
	public T stereotype(Object object) {
		
		if(object instanceof IDynamic)
		{
			return loadFromDynamicEntity(((IDynamic)object).getDynamicEntity());
		}
		
		if(object instanceof IDynamicEntity)
		{
			 return loadFromDynamicEntity((IDynamicEntity)object);
		}
		
		return loadFromNonDynamicEntity(object);
		
	}

	private T loadFromNonDynamicEntity(Object object) {
		final List<IPropertyDescriptor> objectProperties = new ArrayList<>(propertyExtractor.extract(object.getClass()));
		
		T t = getNewInstance();
		
		if(t == null)
			return null;
		
		stereotypeProperties.stream()
			.forEach(propertyDescriptor -> {
				int objectFieldIndex = objectProperties.indexOf(propertyDescriptor);
				if(objectFieldIndex > -1)
				{
					Object value = getPropertyValue(object, objectProperties.get(objectFieldIndex));
					setPropertyValue(t, propertyDescriptor, value);
				}
			});
		
		return t;
	}

	private void setPropertyValue(T target, IPropertyDescriptor propertyDescriptor, Object value) {
		try {
			propertyDescriptor.field().set(target, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Object getPropertyValue(Object target, final IPropertyDescriptor propertyDescriptor) {
		try {
			return propertyDescriptor.field().get(target);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private T loadFromDynamicEntity(IDynamicEntity object)  {
		
		T t = getNewInstance();
		
		if(t == null)
			return null;
		
		stereotypeProperties.stream()
			.forEach(propertyDescriptor -> {
				try {
					if(IPropertyDescriptor.isIEntityProperty(propertyDescriptor))
					{
						propertyDescriptor.field().set(t, new Property(propertyDescriptor.getPropetyName(),object));
					} 
					else 
					{
						propertyDescriptor.field().set(t, object.getProperty(propertyDescriptor.getPropetyName()));						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		
		return t;
	}

	private T getNewInstance() {
		try {
			return modifiedClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Override
	public IStereotypeLoader<T> getLoader() {
		return new StereotypeLoader<T>(this,dynamicEntityLoader);
	}

	@Override
	public Collection<IPropertyDescriptor> getStereotypeProperties() {
		return stereotypeProperties;
	}
	


}
