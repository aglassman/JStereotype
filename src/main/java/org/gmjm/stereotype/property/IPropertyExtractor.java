package org.gmjm.stereotype.property;

import java.util.Collection;

public interface IPropertyExtractor {
	Collection<IPropertyDescriptor> extract(Class<?> clazz);
}
