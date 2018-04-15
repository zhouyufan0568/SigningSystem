package com.example.SigningSystem.signingsystemclient;

import android.app.Dialog;
import android.content.Context;
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

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class RegisterActivity extends AppCompatActivity {
    EditText id, password;
    Button loginBtn;
    //Button returnBtn;
    EditText password2, username;
    Dialog dialog;
    Handler handler;
    static int LOGIN_FAILED = 0;
    static int LOGIN_SUCCEEDED = 1;
    static int REGISTER_FAILED = 2;
    static int REGISTER_SUCCEEDED = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        username = (EditText)findViewById(R.id.username);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        //returnBtn = (Button)findViewById(R.id.returnBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEdit()){//检查注册信息
                    if(isConnectingToInternet()){ //检查网络
                        if (id.getText().toString().equals(""))
                            Toast.makeText(RegisterActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                        else {
                            //启动登录Thread
                            dialog = new Dialog(RegisterActivity.this);
                            dialog.setTitle("正在注册，请稍后...");
                            dialog.setCancelable(false);
                            dialog.show();
                            new RegisterPostThread(id.getText().toString(),
                                    username.getText().toString(),
                                    password.getText().toString()).start();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "网络未连接", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Handle,Msg返回成功信息，跳转到其他Activity
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dialog.dismiss();
                if (msg.what == 222) {  // 处理发送线程传回的消息
                    if(msg.obj.toString().equals("SUCCEEDED")){
                        Log.i("tag", "注册模拟跳转");
                        //跳转
                        Toast.makeText(RegisterActivity.this, "模拟跳转", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "网络貌似不通", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        /*
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到登录Activity
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });*/
    }

    //注册Thread调用RegisterPostService，返回Msg
    public class RegisterPostThread extends Thread {
        public String id, username, password;

        public RegisterPostThread(String id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        @Override
        public void run() {
            // Sevice传回int
            int responseInt = 0;
            if(!id.equals("")) {
                // 要发送的数据
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id", id));
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                // 发送数据，获取对象
                responseInt = RegisterPostService.send(params);
                Log.i("tag", "RegisterActivity: responseInt = " + responseInt);
                // 准备发送消息
                Message msg = handler.obtainMessage();
                // 设置消息默认值
                msg.what = 222;
                // 服务器返回信息的判断和处理
                if(responseInt == REGISTER_FAILED) {
                    msg.obj = "FAILED";
                }else if(responseInt == REGISTER_SUCCEEDED) {
                    msg.obj = "SUCCEEDED";
                }
                handler.sendMessage(msg);
            }
        }
    }

    //检查注册信息
    public boolean checkEdit(){
        if(id.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password2.getText().toString().equals(password.getText().toString())){
            Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
