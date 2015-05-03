package com.xuliugen.frame;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xuliugen.frame.bean.Task;
import com.xuliugen.frame.task.MainService;
import com.xuliugen.httpandasynctaskframe.R;

public class MainActivity extends Activity implements MainActivityInter {

	private Button btn_login;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_login = (Button) this.findViewById(R.id.btn_login);
		textView=  (TextView) this.findViewById(R.id.textView1);

		// 启动服务
		Intent serviceIntent = new Intent(this, MainService.class);
		startService(serviceIntent);

		btn_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				//构造参数传给Task进行处理
				Map<String, Object> paramsHashMap = new HashMap<String, Object>(2);
				paramsHashMap.put("userName", "xuliugen");
				paramsHashMap.put("password", "123456");

				Task task = new Task(Task.USER_LOGIN, paramsHashMap);
				MainService.newTask(task);
			}
		});

		// 将activity放入到activity队列集合中
		MainService.addActivity(this);
	}

	/******************** 以下两个方法是MainActivityInter接口中的 ********************/
	public void init() {

	}

	public void refresh(Object... params) {
		//根据返回的参数进行更新UI 
		textView.setText(params[0].toString());
	}

}
