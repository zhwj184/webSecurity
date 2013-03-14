package org.websecurity.util;

import java.lang.reflect.Method;

public class ClassUtil {

	public static boolean checkIfExsit(Class<?> cls, String methodName){
		Method[] methods = cls.getMethods();
		for(Method method: methods){
			if(method.getName().equals(methodName)){
				return true;
			}
		}
		return false;
	}
}
