package com.xuliugen.frame.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.impl.client.AbstractHttpClient;

/**
 * 这里才是真正执行post请求的地�?
 * 
 * 继承HttpRequestImpl 实现客户端向服务器端的请�?
 * 
 * @author xuliugen
 * 
 */
public class ExecuteHttpPost extends HttpRequestImpl {

	public ExecuteHttpPost(AbstractHttpClient httpClient, String url) {
		this(httpClient, url, null);
	}

	public ExecuteHttpPost(AbstractHttpClient httpClient, String url,HttpEntity entity) {
		super(httpClient);//父类中的httpClient

		this.httpUriRequest = new org.apache.http.client.methods.HttpPost(url);// 初始化httpUriRequest

		if (null != entity) {// 设置参数
			((HttpEntityEnclosingRequestBase) httpUriRequest).setEntity(entity);
		}
	}
}
