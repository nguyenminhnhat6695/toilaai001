package com.lifecode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
	 */
	public static Map<String, Object> toMap(Object obj) {

		if (obj == null)
			return null;

		ObjectMapper oMapper = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(obj, Map.class);

		return map;
	}
	
	/**
	 * convert object list to map list
	 * @param objList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toMapList(Object objList) {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (Object o : (List<Object>) objList) {
			mapList.add(toMap(o));
		}
		return mapList;
	}

	public static Map<String,Object> response(Object data,String jwt, String message,HttpStatus status) {
		mapResult = new HashMap<String,Object>();
		mapResult.put("status", status==null&&data!=null?HttpStatus.OK.value():status==null?"":status.value());
		mapResult.put("data", data==null?"":data);
		mapResult.put("message", StringUtils.isEmpty(message)&&data!=null?"Sucessfully":StringUtils.isEmpty(message)?"":message);
		mapResult.put("accessToken", jwt==null?"":"Bearer "+jwt);
		return mapResult;
	}

	public static Map<String,Object> responseOK(Object data, String jwt, String message) {
		return response(data,jwt, message,HttpStatus.OK);
	}
	
	public static Map<String,Object> responseOK(Object data) {
		return response(data,null,"SUCESSFULLY",HttpStatus.OK);
	}
	
	public static Map<String,Object> responseERROR() {
		return response(null,null,"AN ERROR OCCURRED !",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static Map<String,Object> responseERROR(HttpStatus status,String message,Object data) {
		return response(null,null,"AN ERROR OCCURRED !",status);
	}
}
