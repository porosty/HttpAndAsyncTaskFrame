package com.xuliugen.frame.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtil {
	// private static final Log log = LogFactory.getLog(JSONUtil.class);

	private static final String TAG = "JSONUtil";

	public static final String EMPTY = "";
	public static final String EMPTY_JSON = "{}";
	public static final String EMPTY_JSON_ARRAY = "[]";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	public static final Double SINCE_VERSION_10 = 1.0d;
	public static final Double SINCE_VERSION_11 = 1.1d;
	public static final Double SINCE_VERSION_12 = 1.2d;

	public static String toJson(Object target, Type targetType,
			boolean isSerializeNulls, Double version, String datePattern,
			boolean excludesFieldsWithoutExpose) {
		if (target == null)
			return EMPTY_JSON;
		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls)
			builder.serializeNulls();
		if (version != null)
			builder.setVersion(version.doubleValue());
		if (TextUtils.isEmpty(datePattern))
			datePattern = DEFAULT_DATE_PATTERN;
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();
		String result = EMPTY;
		Gson gson = builder.create();
		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
			} else {
				result = gson.toJson(target);
			}
		} catch (Exception ex) {
			if (target instanceof Collection || target instanceof Iterator
					|| target instanceof Enumeration
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			} else
				result = EMPTY_JSON;
		}
		return result;
	}

	public static String toJson(Object target) {
		return toJson(target, null, true, null, null, false);
	}

	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, true);
	}

	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, true);
	}

	public static String toJson(Object target,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null,
				excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Double version,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null,
				excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, true);
	}

	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, true);
	}

	public static String toJson(Object target, Type targetType,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null,
				excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Type targetType, Double version,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null,
				excludesFieldsWithoutExpose);
	}

	public static <T> T fromJson(String json, TypeToken<T> token,
			String datePattern) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (TextUtils.isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		/* builder.registerTypeAdapter(Map.class, new HashMapDeserializer()); */
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			return null;
		}
	}

	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (TextUtils.isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}
}