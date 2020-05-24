package com.lifecode.utils;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EntityManagerFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lifecode.jpa.entity.Tag;
import com.lifecode.mybatis.model.PostVO;

public class Utils {

	static Map<String, Object> mapResult;

	static JSONObject jsonObject;

	static JSONArray jsonArray;
	
	/**
	 * check is Integer
	 * @param object
	 * @return
	 */
	public static boolean isInteger(Object object) {
		if(object instanceof Integer) {
			return true;
		} else {
			String string = object==null?"":object.toString();
			try {
				Integer.parseInt(string);
			} catch(Exception e) {
				return false;
			}	
		}
	  
	    return true;
	}
	
	/**
	 * check empty
	 * @param s
	 * @return
	 */
	public static boolean isEmpty( final String s ) {
		 return s == null || s.trim().isEmpty();
	}
	
	public static boolean isNotEmpty( final String s ) {
		 return !isEmpty(s);
	}

	/**
	 * convert json object to map
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, Object> jsonObjectToMap(JSONObject jsonObject) throws JSONException {

		mapResult = new HashMap<String, Object>();

		Iterator<String> keysItr = jsonObject.keys();

		while (keysItr.hasNext()) {

			String key = keysItr.next();

			Object value = jsonObject.get(key);

			if (value instanceof JSONArray) {
				value = jsonArrayToList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = jsonObjectToMap((JSONObject) value);
			}

			mapResult.put(key, value);
		}

		return mapResult;
	}

	/**
	 * convert json array to list
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> jsonArrayToList(JSONArray array) throws JSONException {

		List<Object> list = new ArrayList<Object>();

		for (int i = 0; i < array.length(); i++) {

			Object value = array.get(i);

			if (value instanceof JSONArray) {

				value = jsonArrayToList((JSONArray) value);
			} else if (value instanceof JSONObject) {

				value = jsonObjectToMap((JSONObject) value);
			}

			list.add(value);
		}

		return list;
	}

	/**
	 * Convert object to json string
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(Object obj) {

		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);

		return jsonStr;
	}

	/**
	 * check json object
	 * @param string
	 * @return
	 */
	public static boolean isJSONObject(String string) {

		try {
			new JSONObject(string);
		} catch (JSONException ex) {
			return false;
		}
		return true;
	}

	/**
	 * check json array
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isJSONArray(String string) {

		try {
			new JSONArray(string);
		} catch (JSONException ex1) {
			// ex1.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * get object from json
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static Object fromJson(Object json) throws JSONException {

		if (json == JSONObject.NULL) {

			return null;
		} else if (json instanceof JSONObject) {
			return jsonObjectToMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return jsonArrayToList((JSONArray) json);
		} else {
			return json;
		}
	}
	
	/**
	 * convert object to map
	 * @param obj
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Map<String, Object> toMap(Object obj) throws InstantiationException, IllegalAccessException {

		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<>();    
		Field[] allFields = obj.getClass().getDeclaredFields();
	    for (Field field : allFields) {
	        field.setAccessible(true);
	        Object objectValue = field.get(obj);
	        map.put(field.getName(), objectValue);
	    }

		return map;
	}

	/**
	 * convert object list to map list
	 * @param objList
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toMapList(Object objList) throws InstantiationException, IllegalAccessException {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (Object o : (List<Object>) objList) {
			mapList.add(toMap(o));
		}
		return mapList;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static <V> V mapToEntity(Map<String,Object> map, Class<?> entity) throws Exception {
		V v = (V) ((Class<? extends Object>) entity).newInstance();

		if(isJpaEntity(v)) {
			for (Field field : v.getClass().getDeclaredFields()) {
				
		    	Annotation annotation = field.getAnnotation(javax.persistence.Column.class);
		    	String columnName = "";
		        if(annotation instanceof javax.persistence.Column){
		           Column column = (Column) annotation;
		           columnName = column.name();
		        } else {
		           columnName = field.getName();
		        }

		        Object mapKeyObj = map.get(columnName).getClass();
		        Object entityFieldObj = field.getType();
		        
		        if (mapKeyObj.equals(entityFieldObj)) {
		        	boolean accessible = field.isAccessible();
			        field.setAccessible(true);
			        field.set(v, map.get(columnName));
			        field.setAccessible(accessible);
		        } else {
		        	throw new Exception("Column name ["+columnName+"] of "+entityFieldObj+" cannot set data from "+mapKeyObj);
		        }
		    } 
			return v;
		}
		return null;
	}
 	
	private static <V> boolean isJpaEntity(final V v) {
	    return v.getClass().isAnnotationPresent(javax.persistence.Entity.class);
	}

//	public static void main(String[] args) throws Exception {
//		Tag a = new Tag();
//		PostVO p = new PostVO();
//		System.out.println(isJpaEntity(p));
//		Map<String,Object> map = new HashMap<String,Object>();
//		Long x = (long) 2;
//		map.put("tag_id",x);
//		map.put("tag","test");
//		String a1 = null;
//		//System.out.println(map.get(a1));
//		mapToEntity(map,Tag.class);
//	}

	public static List<Long> toListLong(List<String> strList) {
		strList.removeAll(Collections.singleton(null));
		List<Integer> intList = strList.stream().map(Integer::parseInt).collect(Collectors.toList());
		List<Long> longList = intList.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());
		return longList;
	}

	public static String urlEncode(final String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
