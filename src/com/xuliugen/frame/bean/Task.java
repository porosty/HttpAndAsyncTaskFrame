package com.xuliugen.frame.bean;

import java.util.Map;

/**
 * 任务的实体类
 * 
 * @author xuliugen
 * 
 */
public class Task {

	private int taskId;// 任务ID
	private Map<String, Object> taskParams;// 参数

	public static final int USER_LOGIN = 1;

	public Task(int taskId, Map<String, Object> taskParams) {
		this.taskId = taskId;
		this.taskParams = taskParams;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

}
