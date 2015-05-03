package com.xuliugen.frame.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import com.xuliugen.frame.http.inter.HttpResponseInter;


/**
 * 接口的实现类
 * 
 * @author xuliugen
 * 
 */
public class HttpResponseImpl implements HttpResponseInter {

	private HttpResponse response; // HttpResponse对象
	private HttpEntity entity; // HttpEntity试题对象

	public HttpResponseImpl(HttpResponse response) throws IOException {

		this.response = response;
		HttpEntity tempEntity = response.getEntity();//获得服务器端返回的entity
		if (null != tempEntity) {
			entity = new BufferedHttpEntity(tempEntity); 
		}
	}

	// 返回response对象的状态码
	public int statusCode() {
		return response.getStatusLine().getStatusCode();
	}

	// 获得结果的stream
	public InputStream getResponseStream() throws IllegalStateException,IOException {
		
		InputStream inputStream = entity.getContent();
		return inputStream;
	}

	// 获得的结果转化为字符数组
	public byte[] getResponseStreamAsByte() throws IOException {
		return EntityUtils.toByteArray(entity);
	}

	// 获得的结果转化为string
	public String getResponseStreamAsString() throws ParseException, IOException {
		return EntityUtils.toString(entity);
	}

}
