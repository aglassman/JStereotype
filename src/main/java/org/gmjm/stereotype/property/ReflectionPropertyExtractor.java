package org.gmjm.stereotype.property;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.gmjm.stereotype.annotation.NotRequired;

public class ReflectionPropertyExtractor implements IPropertyExtractor {
	
	@Override
	public Collection<IPropertyDescriptor> extract(Class<?> clazz) {

		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> !field.getType().isPrimitive())
				.filter(field -> {
					NotRequired nr = field.getAnnotation(NotRequired.class);
					if(nr == null) return true;
					return !nr.ignore();
				})
				.map(field -> {
					try {
						return (IPropertyDescriptor) new PropertyDescriptor(field);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());

	}
}
