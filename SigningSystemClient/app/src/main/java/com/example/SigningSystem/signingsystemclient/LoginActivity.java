package com.example.SigningSystem.signingsystemclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText id, password;
    Button loginBtn, registerBtn;
    String response;
    Handler handler;
    static int LOGIN_FAILED = 0;
    static int LOGIN_SUCCEEDED = 1;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        id = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button)findViewById(R.id.login);
        registerBtn = (Button)findViewById(R.id.register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(isConnectingToInternet()){ //检查网络
                    if (id.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    else {
                        //启动登录Thread
                        dialog = new Dialog(LoginActivity.this);
                        dialog.setTitle("正在登录，请稍后...");
                        dialog.setCancelable(false);
                        dialog.show();
                        new LoginPostThread(id.getText().toString(),
                                password.getText().toString()).start();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册Activity
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Handle,Msg返回成功信息，跳转到其他Activity
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dialog.dismiss();
                if (msg.what == 111) {  // 处理发送线程传回的消息
                    if(msg.obj.toString().equals("SUCCEEDED")){
                        String info = response.split(":")[1];
                        String[] infos = info.split(",");
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("id", infos[0]);
                        editor.putString("username", infos[1]);
                        editor.putString("sex", infos[2]);
                        editor.putString("class", infos[3]);
                        editor.commit();//提交修改

                        /// /跳转
                        Intent intent = new Intent(LoginActivity.this,
                                IndexActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "账号和密码不匹配", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

    }

    //登录Thread调用LoginPostService，返回Msg
    public class LoginPostThread extends Thread {
        public String id, password;

        public LoginPostThread(String id, String password) {
            this.id = id;
            this.password = password;
        }

        @Override
        public void run() {
            response= null;
            if(!id.equals("")) {
                // 要发送的数据
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id", id));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("usertype", "user"));
                // 发送数据，获取对象
                response = LoginPostService.send(params);
                Log.i("tag", "LoginActivity: response = " + response);
                // 准备发送消息
                Message msg = handler.obtainMessage();
                // 设置消息默认值
                msg.what = 111;
                // 服务器返回信息的判断和处理
                if(response.split(":")[1].equals("null")) {
                    msg.obj = "FAILED";
                }else {
                    msg.obj = "SUCCEEDED";
                }
                handler.sendMessage(msg);
            }
        }
    }

    // 检测网络状态
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}
