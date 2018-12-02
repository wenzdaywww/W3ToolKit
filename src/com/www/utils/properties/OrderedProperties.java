package com.www.utils.properties;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * ˳���ȡProperties�ļ���
 * @author www
 *
 */
@SuppressWarnings("serial")
public class OrderedProperties extends Properties{
	
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	@Override
	public Set<String> stringPropertyNames() {
		Set<String> set = new LinkedHashSet<String>();
		for (Object key : keys) {
			set.add((String) key);
		}
		return set;
	}

	@Override
	public Set<Object> keySet() {
		return keys;
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(keys);
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}
}
