package org.gmjm.stereotype.entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

import org.gmjm.stereotype.annotation.Stereotype;

public class JavaAssistDynamicEntityClassModifier implements IDynamicEntityClassModifier{

	static private Map<Class,Class> createdClasses = new HashMap<>();
	
	ClassPool cp;
	
	public JavaAssistDynamicEntityClassModifier() {
		cp = new ClassPool(false);
		cp.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
	}
	
	@Override
	public <T> Class<T> createModifiedSubclass(Class<T> clazz) {
		if(createdClasses.containsKey(clazz))
		{
			return createdClasses.get(clazz);
		}
		
		try {
			CtClass cclass = cp.makeClass(clazz.getName()+"_Stereotype");
			cclass.setSuperclass(cp.get(clazz.getName()));
			
			addIDynamicEntityField(cclass);
			
			addConstructors(cclass);
			
			addIDynamicInterfaceCode(cclass);
			
			Class newClass = cclass.toClass();
			createdClasses.put(clazz, newClass);
			
			return newClass;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private void addIDynamicEntityField(CtClass cclass)
			throws CannotCompileException {
		CtField ctField = CtField.make("private org.gmjm.stereotype.entity.IDynamicEntity dynamicEntity;", cclass);
		cclass.addField(ctField);
	}

	private void addConstructors(CtClass cclass) throws NotFoundException,
			CannotCompileException {
		CtClass[] constructorParams = {cp.get(IDynamicEntity.class.getName())};
		CtConstructor cons = new CtConstructor(constructorParams, cclass);
		cons.setBody("dynamicEntity = $1;");
		cclass.addConstructor(cons);
		
		constructorParams = new CtClass[]{};
		cons = new CtConstructor(constructorParams, cclass);
		cons.setBody("dynamicEntity = new org.gmjm.stereotype.entity.HashMapDynamicEntity();");
		cclass.addConstructor(cons);
	}
	
	private void addIDynamicInterfaceCode(CtClass cclass) throws Exception {
		cclass.addInterface(cp.get(IDynamic.class.getName()));
		 
	    CtMethod method = new CtMethod(cp.get(IDynamicEntity.class.getName()), "getDynamicEntity", new CtClass[]{}, cclass);
	    method.setBody("return dynamicEntity;");
	    cclass.addMethod(method);
	}

	private boolean hasAnnotation(CtClass cclass) throws ClassNotFoundException {
		Object obj = cclass.getAnnotations();
		return Arrays.stream(cclass.getAnnotations())
				.anyMatch(object -> ((Annotation)object).annotationType().equals(Stereotype.class));
				
	}
}
