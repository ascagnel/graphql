package com.dibs.graphql.deserialize;

import java.util.regex.Pattern;

import com.dibs.graphql.deserialize.data.TokenData;
import com.dibs.graphql.deserialize.parser.StreamReader;

public class TypeResolver {
	
	private static final Pattern BOOLEAN = Pattern.compile("true|false");
	private static final Pattern FLOAT = Pattern.compile("[0-9]+\\.?[0-9]+[e]?[0-9]*");
	private static final Pattern INTEGER = Pattern.compile("[0]|[1-9][0-9]*");
	
	private static Object resolveArgumentToType(String argumentString) {
		if (argumentString == null) {
			return null;
		}
		
		boolean isBoolean = BOOLEAN.matcher(argumentString).matches();
		if (isBoolean) {
			return Boolean.valueOf(argumentString);
		}
		
		boolean isInteger = INTEGER.matcher(argumentString).matches();
		if (isInteger) {
			return Integer.valueOf(argumentString);
		}
		
		boolean isFloat = FLOAT.matcher(argumentString).matches();
		if (isFloat) {
			return Float.valueOf(argumentString);
		}
		
		return argumentString;
	}
		
	public static Object rawDataToTypedValue(TokenData tokenData) {
		String value = StreamReader.nullIfEmpty(new String(tokenData.getValue()));
		return resolveArgumentToType(value);
	}
	
	public static Object arrayRawDataToTypedValue(TokenData[] tokenData) {
		String[] values = new String[tokenData.length];
		
		for (int i = 0; i < tokenData.length; i++) {
			values[i] = StreamReader.nullIfEmpty(new String(tokenData[i].getValue()));
		}
		
		Object[] unmatchedTypes = new Object[values.length];
		for (int i = 0; i < values.length; i++) {
			unmatchedTypes[i] = resolveArgumentToType(values[i]);
		}
		
		if (isUniformType(unmatchedTypes)) {
			return unmatchedTypes;
		}
		
		if (isNumeric(unmatchedTypes)) {
			return convertToType(unmatchedTypes, Float.class);
		}
		
		return convertToType(unmatchedTypes, String.class);
	}
	
	private static boolean isUniformType(Object[] objects) {
		Class<?> clazz = null;
		
		for (Object object : objects) {
			if (clazz == null) {
				clazz = object.getClass();
			} else {
				if (clazz != object.getClass()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static boolean isNumeric(Object[] objects) {
		for (Object object : objects) {
			if (!object.getClass().isAssignableFrom(Integer.class) || !object.getClass().isAssignableFrom(Float.class)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static Object[] convertToType(Object[] toConvert, Class<?> targetType) {
		Object[] commonType = new Object[toConvert.length];
		
		for (int i = 0; i < toConvert.length; i++) {
			Object object = toConvert[i];
			
			if (object.getClass() == targetType) {
				commonType[i] = toConvert[i];
			} else {
				if (targetType == Float.class) {
					commonType[i] = Float.valueOf(object.toString());
				} else  if (targetType == String.class) {
					commonType[i] = object.toString();
				}
			}
		}
		
		return commonType;
	}
	
}
