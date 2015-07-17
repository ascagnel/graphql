package com.dibs.graphql.util;

import java.util.HashSet;
import java.util.Set;

public class Util {

	@SuppressWarnings("unchecked")
	public static <T> Set<T> newHashSet(T... objs) {
	    Set<T> set = new HashSet<>();
	    
	    for (T obj : objs) {
	        set.add(obj);
	    }
	    
	    return set;
	}
	
	public static <D> void assertContains(Set<D> expected, D actual) {
		if (!expected.contains(actual)) {
			throw new RuntimeException("Invalid value [" + actual +"]. Expected one of: " + expected);
		}
	}
}
