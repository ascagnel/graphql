package com.dibs.graphql.data.request;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;

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
		
		Set<String> keys = keySet();
		Set<String> otherKeys = other.keySet();
		
		if (!keys.equals(otherKeys)) {
			return false;
		}
		
		for (String key : keys) {
			Object value = getTypedValue(key);
			Object otherValue = other.getTypedValue(key);
			
			if (value == null && otherValue != null  || value != null && otherValue == null) {
				return false;
			}
			
			if (value != null) {
				if (value.getClass().isArray()) {
					if (!Arrays.deepEquals((Object[]) value, (Object[]) otherValue)) {
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
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 41)
			.append(entrySet())
			.hashCode();
	}
}
