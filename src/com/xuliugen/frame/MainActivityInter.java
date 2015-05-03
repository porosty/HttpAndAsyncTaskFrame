package com.xuliugen.frame;

/**
 * Activity的接口
 * 
 * @author xuliugen
 * 
 */
public interface MainActivityInter {

	/**
	 * 初始化操作
	 */
	public void init();

	/**
	 * 刷新UI
	 */
	public void refresh(Object... params);
}
