package com.xuliugen.frame.bean;

import java.io.Serializable;

import com.google.gson.reflect.TypeToken;
import com.xuliugen.frame.util.JSONUtil;

import android.text.TextUtils;

public class Result implements Serializable {

	private static final long serialVersionUID = -4471961997025265465L;

	private int code;

	private String msg;

	public Result() {
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public <T> T getResult(Class<T> clz) {

		if (TextUtils.isEmpty(msg)) {
			return null;
		}

		return JSONUtil.fromJson(msg, clz);
	}

	public <T> T getResult(TypeToken<T> token) {
		if (TextUtils.isEmpty(msg)) {
			return null;
		}
		return JSONUtil.fromJson(msg, token);
	}

}
