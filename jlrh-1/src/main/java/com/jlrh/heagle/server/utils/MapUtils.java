package com.jlrh.heagle.server.utils;
import java.beans.BeanInfo ;
import java.beans.Introspector ;
import java.beans.PropertyDescriptor ;
import java.lang.reflect.Field ;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method ;
import java.lang.reflect.Modifier ;
import java.util.HashMap ;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * maptoDao
 * map数据转换成实体类工具
 * @author zzw
 *
 */
public class MapUtils {
	
	/**
	 * map转换成object
	 *
	 * @param <T>
	 * @param map
	 * @param t
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> t)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {

		T instance = t.newInstance();

		BeanUtils.populate(instance, map);

		return instance;

	}
	
	
	/**
	 * object转换成map
	 *
	 * @param obj
	 * @return
	 */
	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null)
			return null ;
		return new org.apache.commons.beanutils.BeanMap(obj) ;
	}
	
	
	/**
	 * 
	 *
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject1(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null ;
		Object obj = beanClass.newInstance() ;
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass()) ;
		PropertyDescriptor [] propertyDescriptors = beanInfo.getPropertyDescriptors() ;
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod() ;
			if (setter != null) {
				setter.invoke(obj, map.get(property.getName())) ;
			}
		}
		return obj ;
	}
	
	
	/**
	 * 
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap1(Object obj) throws Exception {
		if (obj == null)
			return null ;
		Map<String, Object> map = new HashMap<String, Object>() ;
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass()) ;
		PropertyDescriptor [] propertyDescriptors = beanInfo.getPropertyDescriptors() ;
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName() ;
			// 默认PropertyDescriptor会有一个class对象，剔除之

			if (key.compareToIgnoreCase("class") == 0) {
				continue ;
			}
			Method getter = property.getReadMethod() ;
			Object value = getter != null ? getter.invoke(obj) : null ;
			map.put(key, value) ;
		}
		return map ;
	}
	
	
	/**
	 * 
	 *
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject2(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null ;
		Object obj = beanClass.newInstance() ;
		Field [] fields = obj.getClass().getDeclaredFields() ;
		for (Field field : fields) {
			int mod = field.getModifiers() ;
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue ;
			}
			field.setAccessible(true) ;
			field.set(obj, map.get(field.getName())) ;
		}
		return obj ;
	}
	
	
	/**
	 * 
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap2(Object obj) throws Exception {
		if (obj == null) { return null ; }
		Map<String, Object> map = new HashMap<String, Object>() ;
		Field [] declaredFields = obj.getClass().getDeclaredFields() ;
		for (Field field : declaredFields) {
			field.setAccessible(true) ;
			map.put(field.getName(), field.get(obj)) ;
		}
		return map ;
	}
	
	
	/**
	 * 将任意vo转化成map
	 * 
	 * @param t vo对象
	 * @return
	 */
	public static <T> Map<String, Object> convert2Map(T t) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		Method [] methods = t.getClass().getMethods() ;
		try {
			for (Method method : methods) {
				Class<?> [] paramClass = method.getParameterTypes() ;
				if (paramClass.length > 0) { // 如果方法带参数，则跳过
					continue ;
				}
				String methodName = method.getName() ;
				if (methodName.startsWith("get")) {
					Object value = method.invoke(t) ;
					result.put(methodName, value) ;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace() ;
		} catch (IllegalAccessException e) {
			e.printStackTrace() ;
		} catch (InvocationTargetException e) {
			e.printStackTrace() ;
		} catch (SecurityException e) {
			e.printStackTrace() ;
		}
		return result ;
	}
}
