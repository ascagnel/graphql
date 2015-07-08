package com.dibs.graphql.prune;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FieldTrimmer<I> {
	private static final Log LOG = LogFactory.getLog(FieldTrimmer.class);
	private static PropertyUtilsBean PROPERTY_UTILS_BEAN = new PropertyUtilsBean();

	private String name;
	private Set<String> fields;
	private boolean exploded = false;

	public FieldTrimmer()
	{
	}

	public FieldTrimmer(Set<String> fields)
	{
		this.fields = fields;
	}

	public static <O> void trim(List<O> objects, Set<String> fields) {
		try
		{
			FieldTrimmer<O> fieldTrimmer = new FieldTrimmer<>(fields);
			
			for (O o : objects) {
				fieldTrimmer.transform(o);
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	public static <O> void trim(O o, Set<String> fields)
	{
		try
		{
			FieldTrimmer<O> fieldTrimmer = new FieldTrimmer<>(fields);
			fieldTrimmer.transform(o);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}

	private void explodeFields()
	{
		LOG.trace("explodeFields(): pre-explode " + this.fields);
		Set<String> explodedFields = new HashSet<>();

		for (String field : this.fields)
		{
			String[] parts = field.split("\\.");
			String value = null;
			for (String part : parts)
			{
				if (value == null)
				{
					value = part;
				}
				else
				{
					value = value + "." + part;
				}

				explodedFields.add(value);

				if (value.equals(field))
				{
					explodedFields.add(value + ".*");
				}

			}
		}

		this.fields = Collections.unmodifiableSet(explodedFields);
		this.exploded = true;
		LOG.trace("explodeFields(): post explode " + this.fields);

	}

	private void trim(String prefix, Object bean)
	{
		try
		{
			if (LOG.isTraceEnabled())
			{
				LOG.trace("convert(): processing " + prefix + " class " + bean.getClass().getName());
			}

			PropertyDescriptor[] propertyDescriptors = PROPERTY_UTILS_BEAN.getPropertyDescriptors(bean);

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
			{
				String name = propertyDescriptor.getName();

				String field = prefix == null ? name : prefix + "." + name;

				if (fields.contains(field))
				{
					if (fields.contains(field + ".*"))
					{
						// this is a leaf
						// leave all the nested properties on the bean
						continue;
					}

					MethodUtil methodUtil = MethodUtil.getInstance(bean, field);
					if (methodUtil.isReadLegit())
					{
						Object fieldValue = methodUtil.invokeRead(bean);
						trim(field, fieldValue);
					}
				}
				else
				{
					// null out the value
					MethodUtil methodUtil = MethodUtil.getInstance(bean, name);
					if (methodUtil.isWriteLegit())
					{
						methodUtil.invokeWrite(bean, null);
						LOG.trace("convert(): set " + field + " = null");
					}
				}
			}

		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Error processing prefix=[" + prefix + "] bean=[" + bean + "]", ex);
		}
	}

	public Set<String> getFields()
	{
		return fields;
	}

	public void setFields(Set<String> fields)
	{
		this.fields = fields;
	}

	public void addAllFields(Collection<String> fields)
	{
		if (this.fields == null)
		{
			this.fields = new HashSet<>();
		}

		this.fields.addAll(fields);
	}
	public void addAllFields(String... fields)
	{
		if (fields != null)
		{
			addAllFields(Arrays.asList(fields));
		}
	}

	
	public I transform(I input) throws Exception
	{
		try
		{
			if (!exploded)
			{
				explodeFields();
			}

			if (!fields.contains("*"))
			{
				trim(null, input);
			}

			return (I) input;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
