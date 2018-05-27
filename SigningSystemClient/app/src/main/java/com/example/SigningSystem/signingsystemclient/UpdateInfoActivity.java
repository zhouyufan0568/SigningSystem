package com.example.SigningSystem.signingsystemclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyufan on 2018/5/25.
 */

public class UpdateInfoActivity extends AppCompatActivity {

    private RelativeLayout className;
    private TextView tv_class;
    private TextView tv_name;
    private TextView tv_sex;
    String response;
    Handler handler;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        className = (RelativeLayout) findViewById(R.id.re_class);
        tv_class = (TextView) findViewById(R.id.tv_class);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String sex = sharedPreferences.getString("sex", "");
        String myclass = sharedPreferences.getString("class", "");
        if (username != "") {
            tv_name.setText(username);
        }
        if (sex != "") {
            tv_sex.setText(sex);
        }
        if (myclass != "") {
            tv_class.setText(myclass);
        }

        className.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog = new Dialog(UpdateInfoActivity.this);
                dialog.setTitle("正在获取班级...");
                dialog.setCancelable(false);
                dialog.show();
                new GetInfoPostThread("classname").start();
            }
        });

        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 111) {  // 处理发送线程传回的消息
                    if (msg.obj.toString().equals("SUCCEEDED")) {
                        String[] res = response.split(":");
                        if (res[0].equals("classname")) {
                            dialog.dismiss();
                            final String[] classname = res[1].split(",");
                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfoActivity.this);
                            builder.setIcon(R.drawable.icon);
                            builder.setTitle("选择班级");
                            //    设置一个下拉的列表选择项
                            builder.setItems(classname, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tv_class.setText(classname[which]);
                                }
                            });
                            builder.show();
                        }
                    }
                }else if(msg.what == 112){
                    if (msg.obj.toString().equals("SUCCEEDED")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("username", tv_name.getText().toString());
                        editor.putString("sex", tv_sex.getText().toString());
                        editor.putString("class", tv_class.getText().toString());
                        editor.commit();
                        Toast.makeText(UpdateInfoActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateInfoActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    public class SendInfoPostThread extends Thread {
        public String username;
        public String sex;
        public String classname;

        public SendInfoPostThread(String username, String sex, String classname) {
            this.username = username;
            this.sex = sex;
            this.classname = classname;
        }
        @Override
        public void run() {
            // Sevice传回int
            response = null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            params.add(new BasicNameValuePair("id", sharedPreferences.getString("id", "")));
            params.add(new BasicNameValuePair("usertype", "user"));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("sex", sex));
            params.add(new BasicNameValuePair("classname", classname));
            // 发送数据，获取对象
            response = GetCrousePostServer.sendInfo(params);
            Log.i("tag", "UpdateInfoActivity: response = " + response);
            // 准备发送消息
            Message msg = handler.obtainMessage();
            // 设置消息默认值
            msg.what = 112;
            // 服务器返回信息的判断和处理
            msg.obj = response;
            handler.sendMessage(msg);
        }
    }

    public class GetInfoPostThread extends Thread {
        public String type;

        public GetInfoPostThread(String type) {
            this.type = type;
        }
        @Override
        public void run() {
            // Sevice传回int
            response = null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            params.add(new BasicNameValuePair("info", type));
            params.add(new BasicNameValuePair("usertype", "user"));
            params.add(new BasicNameValuePair("id", sharedPreferences.getString("id", "")));
            // 发送数据，获取对象
            response = GetCrousePostServer.getInfo(params);
            Log.i("tag", "UpdateInfoActivity: responseInt = " + response);
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


    public void back(View view) {
        new SendInfoPostThread(tv_name.getText().toString(), tv_sex.getText().toString(), tv_class.getText().toString()).start();
        finish();
    }

}