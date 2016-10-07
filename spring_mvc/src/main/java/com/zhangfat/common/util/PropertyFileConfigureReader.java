package com.zhangfat.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyFileConfigureReader extends PropertyPlaceholderConfigurer {

	private static Map<String,String> ctxPropertiesMap;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props){
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String,String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}
	
	public static Object getContextProperty(String name){
		return ctxPropertiesMap.get(name);
	}
}