package com.xuliugen.frame.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.xuliugen.frame.bean.Result;
import com.xuliugen.frame.http.inter.HttpResponseInter;
import com.xuliugen.frame.util.JSONUtil;


/**
 * HttpClient客户端的顶层类
 * 
 * @author xuliugen
 * 
 */
public class BaseHttpClient {

	private AbstractHttpClient httpClient;

	public static final int DEFAULT_RETIES_COUNT = 5;

	protected int retriesCount = DEFAULT_RETIES_COUNT;

	// 设置最大连接数
	public final static int MAX_TOTAL_CONNECTIONS = 100;
	
	// 设置获取连接的最大等待时间
	public final static int WAIT_TIMEOUT = 30000;
	
	// 设置每个路由最大连接数
	public final static int MAX_ROUTE_CONNECTIONS = 100;
	
	// 设置连接超时时间
	public final static int CONNECT_TIMEOUT = 10000;
	
	// 设置读取超时时间
	public final static int READ_TIMEOUT = 10000;

	/**
	 * 构造方法，调用初始化方法
	 */
	public BaseHttpClient() {
		initHttpClient();
	}

	/**
	 * 初始化客户端参数
	 */
	private void initHttpClient() {
		
		//http的参数
		HttpParams httpParams = new BasicHttpParams();

		//设置最大连接数
		ConnManagerParams.setMaxTotalConnections(httpParams,MAX_TOTAL_CONNECTIONS);

		//设置获取连接的最大等待时间
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);

		//设置每个路由最大连接数
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);

		// 设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));//设置端口80
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));//设置端口443

		//就是管理SchemeRegistry的
		ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);

		httpClient = new DefaultHttpClient(clientConnectionManager, httpParams);

		//创建http重新连接的handler
		httpClient.setHttpRequestRetryHandler(new BaseHttpRequestRetryHandler(retriesCount));
	}

	/**
	 * 将参数转化为 List<BasicNameValuePair> 的集合
	 */
	private List<BasicNameValuePair> parseParams(HashMap<String, Object> params) {

		if (params == null || 0 == params.size()){
			return null;
		}

		List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>(params.size());

		for (Entry<String, Object> entry : params.entrySet()) {
			paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
		}
		return paramsList;
	}
	
	/**
	 * 向服务器端请求：当请求只有url 没有参数的时候
	 */
	public String post(String url) throws Exception {
		return post(url, null); //调用有参数的时候执行的post并将参数设置为null
	}

	/**
	 * post请求之后返回T类型的结果
	 */
	public <T> T post(String url, HashMap<String, Object> params, Class<T> clz) throws Exception {
		String json = post(url, params);
		return JSONUtil.fromJson(json, clz); //转化为具体的类型返回
	}
	
	/**
	 * 当请求有参数的时候，其他函数间接调用该方法
	 */
	public String post(String url, HashMap<String, Object> params) throws Exception {
		
		//将传入的参数转化为参数实体：将params转化为enrity的对象：表单entity
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parseParams(params));

		return request(url, entity).getResponseStreamAsString();

	}
	
	/**
	 * 将post执行的结果直接返回
	 */
	public Result postAsResult(String url, HashMap<String, Object> params)throws Exception {
		return post(url, params, Result.class);
	}
	
	/**
	 * 将post执行的结果一Stream的形式返回
	 */
	public InputStream postAsStream(String url, HashMap<String, Object> params) throws Exception {

		//将传入的参数转化为参数实体
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parseParams(params));

		return request(url, entity).getResponseStream();
	}
	
	/**
	 * 
	 */
	public HttpResponseInter request(String url, HttpEntity entity) throws Exception {

		HttpRequestImpl httpRequestImpl = new ExecuteHttpPost(httpClient, url, entity);

		return httpRequestImpl.request();

	}
}
