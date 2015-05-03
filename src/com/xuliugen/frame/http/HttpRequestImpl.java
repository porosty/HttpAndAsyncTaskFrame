package com.xuliugen.frame.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;

import com.xuliugen.frame.http.inter.HttpRequestInter;
import com.xuliugen.frame.http.inter.HttpResponseInter;


public class HttpRequestImpl implements HttpRequestInter,ResponseHandler<HttpResponseInter> {

	protected HttpUriRequest httpUriRequest;// 用于获取request的url地址
	private AbstractHttpClient abstractHttpClient; // client对象

	// 构�?�方�?
	public HttpRequestImpl(AbstractHttpClient httpClient) {
		this.abstractHttpClient = httpClient;
	}

	// get方法
	public HttpUriRequest getHttpRequest() {
		return httpUriRequest;
	}

	//获得request的url
	public String getRequestURL() {
		return httpUriRequest.getURI().toString();
	}

	//执行request请求，并返回�?个response对象接口
	public HttpResponseInter request() throws Exception {
		return abstractHttpClient.execute(httpUriRequest, this);//传入的ResponseHandler对象
	}

	/**
	 * 继承ResponseHandler接口要实现的方法
	 * 
	 * 执行完毕之后对response对象的处理操�?
	 */
	public HttpResponseInter handleResponse(HttpResponse response)throws ClientProtocolException, IOException {
		//返回实现HttpResponseInter的类
		HttpResponseInter httpResponseInter = new HttpResponseImpl(response); //返回的时候需要response
		return httpResponseInter;
	}

}
