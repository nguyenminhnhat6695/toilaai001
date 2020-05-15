package com.lifecode.common;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

public class FieldMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -5018625254427195587L;

	@Override
	public Object put(String key, Object value) {
 		return super.put(StringUtils.lowerCase(key), value);
	}
}
