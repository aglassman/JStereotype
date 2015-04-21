package org.gmjm.stereotype.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

public class JsonFieldSetterUtil {

	public static Number getBoxedNumber(Class<? extends Number> numberClass, JsonNumber jsonNumber) {
		if (numberClass == Long.class) {
			return jsonNumber.longValueExact();
		}

		if (numberClass == Integer.class) {
			return Integer.valueOf(jsonNumber.intValueExact());
		}

		if (numberClass == Double.class) {
			return Double.valueOf(jsonNumber.doubleValue());
		}

		if (numberClass == Float.class) {
			return Float.valueOf(jsonNumber.bigDecimalValue().floatValue());
		}

		if (numberClass == BigInteger.class) {
			return jsonNumber.bigIntegerValueExact();
		}

		if (numberClass == BigDecimal.class) {
			return jsonNumber.bigDecimalValue();
		}

		throw new IllegalArgumentException("Cannot extract type: " + numberClass.getName() + " from JsonNumber.");
	}

	/**
	 * Sets the targetObject's field using the correct getter on the JsonNumber
	 * class. Used for primitive and boxed types.
	 * 
	 * @param targetObject
	 * @param field
	 * @param number
	 * @throws IllegalAccessException
	 */
	public static void setNumberField(Object targetObject, Field field, JsonNumber number) throws IllegalAccessException {

		Class<?> fieldClass = field.getType();

		if (fieldClass.isPrimitive()) {
			if (fieldClass == long.class) {
				field.set(targetObject, number.longValueExact());
			}

			if (fieldClass == int.class) {
				field.set(targetObject, number.intValueExact());
			}

			if (fieldClass == double.class) {
				field.set(targetObject, number.doubleValue());
			}

			if (fieldClass == float.class) {
				field.set(targetObject, number.bigDecimalValue().floatValue());
			}
		} else {
			field.set(targetObject, getBoxedNumber((Class<Number>)fieldClass, number));
		}

	}

}
