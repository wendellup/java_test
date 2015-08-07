package cn.egame.common.xml.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dom4j.Element;

import cn.egame.common.util.Utils;

public class AnnotationProcessor {

	/**
	 * 
	 * @param <T>
	 * @param bean
	 * @param element
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */

	public <T> Object doProcess(Class<T> c, Element element)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		// 获取JavaBean类类型
		Class<T> beanClass = c;
		T t = c.newInstance();
		// 根据注解获取属性对象
		PropertyForAnnotated myProperty = beanClass
				.getAnnotation(PropertyForAnnotated.class);
		// 判断JavaBean是否被标注为@PropertyForAnnotated
		if (myProperty != null) {
			// 获取JavaBean的全部方法
			Method[] methods = beanClass.getMethods();
			for (Method method : methods) {
				// 根据注解获取属性对象
				myProperty = method.getAnnotation(PropertyForAnnotated.class);
				// 判断方法是否被标注为@PropertyForAnnotated
				if (myProperty != null) {
					// 根据属性名称获取属性值
					String myPropertyName = element.elementTextTrim(myProperty
							.tagName());
					if (!Utils.stringIsNullOrEmpty(myPropertyName)) {
						
						method.invoke(t, new Object[] { myPropertyName });
					}
				}
			}
		}
		return t;
	}

	

}
