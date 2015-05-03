package com.xuliugen.frame.http.inter;

import org.apache.http.client.methods.HttpUriRequest;
/**
 * 请求的接�?
 * @author xuliugen
 */
public interface HttpRequestInter {

	//获得httpRequest
	public HttpUriRequest getHttpRequest();

	//获得http请求的url地址
	public String getRequestURL();

	//请求服务端：要返回一个response对象
	public HttpResponseInter request() throws Exception;

}
