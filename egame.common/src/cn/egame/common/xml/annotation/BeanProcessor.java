package cn.egame.common.xml.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.egame.common.util.Utils;

public class BeanProcessor {
	protected static Logger logger = Logger.getLogger(BeanProcessor.class
			.getSimpleName());
	
	
	
	public  <T> T parseXmlToObject(Class<T> c, String config) {
		String file = Utils.getConfigFile(config);
		System.out.println(file);
		File f = new File(file);

		InputStream is = null;
		T t = null;
		try {
			t = c.newInstance();
			is = new FileInputStream(f);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			org.dom4j.Element root = doc.getRootElement();
			System.out.println(root.getName());
			List<Element> subNodes = root.elements();
			for (Element ele : subNodes) {
				t = doProcessor(t, config, ele);
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (DocumentException e) {
			logger.error(e);
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	private static <T> T doProcessor(T t, String config, Element element) {
		Class<?> beanClass = t.getClass();
		Method[] methods = beanClass.getMethods();
		for (Method method : methods) {
			PropertyForAnnotated propertyAnnotated = method
					.getAnnotation(PropertyForAnnotated.class);

			BeanForAnnotated beanAnnotated = method
					.getAnnotation(BeanForAnnotated.class);

			ListForAnnotated listAnnotated = method
					.getAnnotation(ListForAnnotated.class);
			propertyAnnotated = method
					.getAnnotation(PropertyForAnnotated.class);
			// 判断方法是否被标注为@PropertyForAnnotated
			try {
				if (propertyAnnotated != null
						&& propertyAnnotated.tagName().equalsIgnoreCase(
								element.getName())) {
					method.invoke(t, new Object[]{element.getTextTrim()});

				} else if (beanAnnotated != null
						&& beanAnnotated.name().equalsIgnoreCase(
								element.getName())) {

					AnnotationProcessor processor = new AnnotationProcessor();
					Object obj = processor.doProcess(beanAnnotated.className(),
							element);
					method.invoke(t, new Object[]{obj});
				} else if (listAnnotated != null
						&& listAnnotated.name().equalsIgnoreCase(
								element.getName())) {
					AnnotationProcessor processor = new AnnotationProcessor();
					Object obj = processor.doProcess(listAnnotated.className(),
							element);
					String setMethodName = method.getName();
					String getMethodName = "get"
							+ setMethodName.substring(setMethodName
									.indexOf("set") + 3);
					Method setMethod = beanClass.getMethod(getMethodName,
							new Class[0]);
					List l = (List) setMethod.invoke(t, new Object[]{});
					l.add(obj);
					method.invoke(t, new Object[]{l});

				}
			} catch (IllegalArgumentException e) {
				logger.error(e);
			} catch (IllegalAccessException e) {
				logger.error(e);
			} catch (InvocationTargetException e) {
				logger.error(e);
			} catch (InstantiationException e) {
				logger.error(e);
			} catch (SecurityException e) {
				logger.error(e);
			} catch (NoSuchMethodException e) {
				logger.error(e);
			}

		}

		return t;

	}

}
