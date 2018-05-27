package com.example.SigningSystem.signingsystemclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhouyufan on 2018/4/21.
 */

public class CrouseActivity extends AppCompatActivity {

    static int GETCROUSE_FAILED = 4;
    static int GETCROUSE_SUCCEEDED = 5;
    Handler handler;
    Dialog dialog;
    TextView classSchedule;
    String response;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crouse);
        classSchedule = (TextView)findViewById(R.id.classSchedule);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String myclass = sharedPreferences.getString("class", "");
        if (myclass != "") {
            classSchedule.setText(myclass);
        }
        dialog = new Dialog(CrouseActivity.this);
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
                                    TextView t = (TextView)findViewById(R.id.class.getField(s).getInt(null));
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
                        Toast.makeText(CrouseActivity.this, "获取课表失败", Toast.LENGTH_SHORT).show();
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
