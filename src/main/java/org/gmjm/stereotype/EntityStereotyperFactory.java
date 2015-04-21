package org.gmjm.stereotype;

import java.util.Collection;

import org.gmjm.stereotype.annotation.Stereotype;
import org.gmjm.stereotype.entity.IDynamicEntityClassModifier;
import org.gmjm.stereotype.entity.JavaAssistDynamicEntityClassModifier;
import org.gmjm.stereotype.loader.IDynamicEntityLoader;
import org.gmjm.stereotype.loader.IJsonToJavaMapper;
import org.gmjm.stereotype.loader.JsonLoader;
import org.gmjm.stereotype.loader.ReflectionBasedJsonToJavaMapper;
import org.gmjm.stereotype.property.IPropertyDescriptor;
import org.gmjm.stereotype.property.IPropertyExtractor;
import org.gmjm.stereotype.property.ReflectionPropertyExtractor;

public class EntityStereotyperFactory {

	public static <T> IEntityStereotyper<T> create(Class<T> stereotypeClass) {

		if (stereotypeClass.getAnnotation(Stereotype.class) == null) {
			new IllegalArgumentException(String.format(
					"Class: %s must have the @Stereotype annotation.",
					stereotypeClass));
		}

		IPropertyExtractor propertyExtractor = new ReflectionPropertyExtractor();
		Collection<IPropertyDescriptor> buildableProperties = propertyExtractor.extract(stereotypeClass);
		IDynamicEntityClassModifier classModifier = new JavaAssistDynamicEntityClassModifier();
		IJsonToJavaMapper jsonToJavaMapper = new ReflectionBasedJsonToJavaMapper();
		IDynamicEntityLoader dynamicEntityLoader = new JsonLoader(jsonToJavaMapper, buildableProperties);
		
		return new AnnotationEntityStereotyper<>(stereotypeClass, buildableProperties, propertyExtractor, classModifier, dynamicEntityLoader);
	}
}
