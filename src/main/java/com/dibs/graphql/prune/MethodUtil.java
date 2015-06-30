package com.dibs.graphql.prune;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MethodUtil
{

	private static final Log LOG = LogFactory.getLog(MethodUtil.class);
	private static PropertyUtilsBean PROPERTY_UTILS_BEAN = new PropertyUtilsBean();
	private static Map<String, MethodUtil> INSTANCE_MAP = new ConcurrentHashMap<String, MethodUtil>();

	private Method writeMethod;
	private Method readMethod;
	private Class<?> propertyType;

	public static MethodUtil getInstance(Object bean, String field)
	{
		// BaseAssert.isNotNull(bean, "bean is null");
		// BaseAssert.isNotNull(field, "field is not");

		String key = buildKey(bean, field);

		MethodUtil methodUtil = INSTANCE_MAP.get(key);
		if (methodUtil == null)
		{
			INSTANCE_MAP.put(key, methodUtil = new MethodUtil(bean, field));
		}
		return methodUtil;
	}

	private static String buildKey(Object bean, String field)
	{
		return bean.getClass().getName() + "-" + field;
	}

	private MethodUtil(Object bean, String field)
	{
		try
		{
			PropertyDescriptor propertyDescriptor = PROPERTY_UTILS_BEAN.getPropertyDescriptor(bean, field);

			if (propertyDescriptor != null)
			{
				this.writeMethod = propertyDescriptor.getWriteMethod();
				this.readMethod = propertyDescriptor.getReadMethod();
				this.propertyType = propertyDescriptor.getPropertyType();
			}
			else
			{
				LOG.warn("MethodUtil(): invalid property name " + field + " on " + bean.getClass().getName());
			}
		}
		catch (Exception ex)
		{
			LOG.error("MethodUtil(): Error loading method util bean [" + bean + "] field[" + field + "]", ex);
		}

	}

	public Method getWriteMethod()
	{
		return writeMethod;
	}

	public Method getReadMethod()
	{
		return readMethod;
	}

	public Class<?> getPropertyType()
	{
		return propertyType;
	}

	public Object invokeRead(Object bean)
	{
		try
		{
			return readMethod == null ? null : readMethod.invoke(bean, (Object[]) null);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void invokeWrite(Object bean, Object value)
	{
		try
		{
			if (writeMethod != null)
			{
				writeMethod.invoke(bean, value);
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public boolean isReadLegit()
	{
		return readMethod != null && propertyType != null;
	}

	public boolean isWriteLegit()
	{
		return writeMethod != null && propertyType != null;
	}

}
