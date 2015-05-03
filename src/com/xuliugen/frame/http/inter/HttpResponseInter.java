package com.xuliugen.frame.http.inter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.ParseException;

/**
 * 响应的接�?
 * @author xuliugen
 */
public interface HttpResponseInter {

	//返回状�?�码
	public int statusCode();

	// 向客户端返回流数�?
	public InputStream getResponseStream() throws IllegalStateException,IOException;

	//向客户端返回字节数组
	public byte[] getResponseStreamAsByte() throws IOException;

	//向客户端返回JSON数据
	public String getResponseStreamAsString() throws ParseException, IOException;

}
