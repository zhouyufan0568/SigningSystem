package com.example.SigningSystem.signingsystemclient;

import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class LoginPostService {
    static int LOGIN_FAILED = 0;
    static int LOGIN_SUCCEEDED = 1;

    public static String send(List<NameValuePair> params) {
        // 定位服务器的Servlet
        String servlet = "LoginServlet";
        // 通过 POST 方式获取 HTTP 服务器数据
        String responseMsg = null;
        responseMsg = MyHttpPost.executeHttpPost(servlet, params);
        Log.i("tag", "LoginService: responseMsg = " + responseMsg);
        // 解析服务器数据，返回相应 Long 值
        return responseMsg;
    }
}
