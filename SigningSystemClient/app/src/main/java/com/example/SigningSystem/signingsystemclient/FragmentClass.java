package com.example.SigningSystem.signingsystemclient;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentClass extends Fragment {
	static int GETCROUSE_FAILED = 4;
	static int GETCROUSE_SUCCEEDED = 5;
	Handler handler;
	Dialog dialog;
	TextView classSchedule;
	String response;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_crouse, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		classSchedule = (TextView) getActivity().findViewById(R.id.classSchedule);
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		String myclass = sharedPreferences.getString("class", "");
		if (myclass != "") {
			classSchedule.setText(myclass);
		}
		dialog = new Dialog(getActivity());
		dialog.setTitle("正在获取课表...");
		dialog.setCancelable(false);
		dialog.show();
		new GetCrousePostThread(classSchedule.getText().toString()).start();

		//Handle,Msg返回成功信息，跳转到其他Activity
		handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				dialog.dismiss();
				if (msg.what == 111) {  // 处理发送线程传回的消息
					if(msg.obj.toString().equals("SUCCEEDED")){
						try {
							JSONObject jsontemp = new JSONObject(response);
							String jsonStr = jsontemp.getString("scheduleInfo");
							JSONArray scheduleInfo = new JSONArray(jsonStr);
							for(int i = 0; i<scheduleInfo.length(); i++){
								JSONObject crouseInfo = scheduleInfo.getJSONObject(i);
								int day = crouseInfo.getInt("day");
								int cindex = crouseInfo.getInt("cindex");
								String cname = crouseInfo.getString("cname");
								String s = "crouse" + day + cindex;
								try {
									TextView t = (TextView) getActivity().findViewById(R.id.class.getField(s).getInt(null));
									t.setText(cname);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (NoSuchFieldException e) {
									e.printStackTrace();
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else{
						Toast.makeText(getActivity(), "获取课表失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
	}

	//登录Thread调用GetCrousePostServer，返回Msg
	public class GetCrousePostThread extends Thread {
		public String classname;

		public GetCrousePostThread(String classname) {
			this.classname = classname;
		}

		@Override
		public void run() {
			// Sevice传回int
			response = null;
			if(!classname.equals("")) {
				// 要发送的数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
				params.add(new BasicNameValuePair("id", sharedPreferences.getString("id", "")));
				params.add(new BasicNameValuePair("classname", classname));
				// 发送数据，获取对象
				response = GetCrousePostServer.send(params);
				Log.i("tag", "CrouseActivity: responseInt = " + response);
				// 准备发送消息
				Message msg = handler.obtainMessage();
				// 设置消息默认值
				msg.what = 111;
				// 服务器返回信息的判断和处理
				if(response == null) {
					msg.obj = "FAILED";
				}else{
					msg.obj = "SUCCEEDED";
				}
				handler.sendMessage(msg);
			}
		}
	}
}
