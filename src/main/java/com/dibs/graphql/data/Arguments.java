package com.dibs.graphql.data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

public class Arguments extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 3575680735821573580L;
	

	public Arguments() {
		super();
	}
	

	@SuppressWarnings("unchecked")
	public <T> T getTypedValue(String key) {
		return (T) get(key);
	}
	
	public Class<?> getValueType(String key) {
		Object value = get(key).getClass();
		
		return value == null? null : value.getClass();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (o.getClass() != getClass()) {
			return false;
		}
		
		Arguments other = (Arguments) o;
		
		Set<String> allArguments = keySet();
		Set<String> allOtherArguments = other.keySet();
		
		if (!allArguments.equals(allOtherArguments)) {
			return false;
		}
		
		for (String argument : allArguments) {
			Object value = getTypedValue(argument);
			Object otherValue = other.getTypedValue(argument);
			
			if (value == null && otherValue != null  || value != null && otherValue == null) {
				return false;
			}
			
			if (value != null) {
				if (value.getClass().isArray()) {
					if (!Arrays.deepEquals((Object[]) value, (Object[])otherValue)) {
						return false;
					}
				} else {
					if (!value.equals(otherValue)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
}
