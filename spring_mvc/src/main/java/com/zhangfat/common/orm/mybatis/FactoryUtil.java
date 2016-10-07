package com.zhangfat.common.orm.mybatis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FactoryUtil {

	public static Map<String,Object> Bean2Map(Object javaBean) {
		Map<String,Object> ret = new HashMap<String,Object>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put(field,value);
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}
	
}