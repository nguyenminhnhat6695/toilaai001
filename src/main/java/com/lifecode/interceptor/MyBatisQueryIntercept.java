package com.lifecode.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * MyBatis Select Query Debug
 */
@Service
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MyBatisQueryIntercept implements Interceptor, HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(getClass());

	final private static String LOGGING_FORMAT = "\n==================== mapperId ====================\n" + "{}\n" + "==================== query ====================\n" + "\t\t{}";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("----------------MyBatisQueryIntercept--------------");
		try {
			if (logger.isDebugEnabled()) {
				Object[] args = invocation.getArgs();
				MappedStatement mappedStatement = (MappedStatement) args[0];
				Object parameterObject = args[1];
				String query = makeQuery(mappedStatement, parameterObject);
				logger.debug(LOGGING_FORMAT, mappedStatement.getId(), query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

	public static String makeQuery(MappedStatement mappedStatement, Object parameterObject) throws Exception {
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();

		StringBuffer stringBuffer = new StringBuffer();
		Matcher matcher = Pattern.compile("\\?").matcher(boundSql.getSql());
		int i = 0;

		if (parameterObject != null) {
			if (parameterObject instanceof String || parameterObject instanceof Integer || parameterObject instanceof Long) {
				while (matcher.find()) {
					matcher.appendReplacement(stringBuffer, formatParameter(parameterObject));
				}
			} else if (parameterObject instanceof Map) {
				Map<?, ?> parameterMap = (Map<?, ?>) parameterObject;
				String property = "";
				String[] array = null;
				Class<?> parameterClass = null;
				Object obj = null;
				while (matcher.find()) {
					property = parameterMappingList.get(i).getProperty();
					if (property.contains(".")) {
						array = property.split("\\.");
						parameterClass = Class.forName(parameterMap.get(array[0]).getClass().getName().toString());
						obj = parameterClass.getMethod("get" + array[1].substring(0, 1).toUpperCase() + array[1].substring(1)).invoke(parameterMap.get(array[0]), new Object[] {});
						matcher.appendReplacement(stringBuffer, formatParameter(obj));
					} else {
						Object value = null;
						try {
							if (parameterMap.containsKey(property)) {
								value = parameterMap.get(property);
							} else if (boundSql.hasAdditionalParameter(property)) {
								value = boundSql.getAdditionalParameter(property);
							} else {
								MetaObject metaObject = mappedStatement.getConfiguration().newMetaObject(parameterObject);
								if (metaObject != null) value = metaObject.getValue(property);
							}
						} catch (Exception e) {
							value = property;
						}
						matcher.appendReplacement(stringBuffer, formatParameter(value));
					}
					i++;
				}
			} else {
				Class<?> parameterClass = Class.forName(parameterObject.getClass().getName().toString());
				Object obj = null;
				while (matcher.find()) {
					obj = parameterClass.getMethod("get" + parameterMappingList.get(i).getProperty().substring(0, 1).toUpperCase() + parameterMappingList.get(i).getProperty().substring(1)).invoke(parameterObject, new Object[] {});
					matcher.appendReplacement(stringBuffer, formatParameter(obj));
					i++;
				}
			}
		}

		matcher.appendTail(stringBuffer);

		StringTokenizer lineStripper = new StringTokenizer(stringBuffer.toString(), "\n");
		StringBuilder builder = new StringBuilder();
		String line = null;

		while (lineStripper.hasMoreTokens()) {
			line = lineStripper.nextToken();
			if (!line.trim().equals("")) builder.append(line).append("\n");
		}
		return builder.toString();
	}

	private static String formatParameter(final Object value) {
		if (value == null) return "NULL";
		if (value instanceof Date) return new SimpleDateFormat("'TO_DATE('''yyyyMMddHHmmss'','''YYYYMMDDHH24MISS''')").format((Date) value);
		if (value instanceof String) return "'" + Matcher.quoteReplacement((String) value) + "'";
		return Matcher.quoteReplacement(value.toString());
	}
}