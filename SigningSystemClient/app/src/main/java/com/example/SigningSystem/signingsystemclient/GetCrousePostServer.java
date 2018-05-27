package com.example.SigningSystem.signingsystemclient;

import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by zhouyufan on 2018/4/21.
 */

public class GetCrousePostServer {
    static int GETCROUSE_FAILED = 4;
    static int GETCROUSE_SUCCEEDED = 5;

    public static String send(List<NameValuePair> params) {
        // 定位服务器的Servlet
        String servlet = "GetCrouseServlet";
        // 通过 POST 方式获取 HTTP 服务器数据
        String responseMsg = null;
        responseMsg = MyHttpPost.executeHttpPost(servlet, params);
        Log.i("tag", "GetCrouseService: responseMsg = " + responseMsg);
        // 解析服务器数据，返回相应 Long 值
        if(responseMsg != null) {
            Log.i("tag", "GetCrouseService: responseInt = " + responseMsg);
    }
        return responseMsg;
    }

    public static String getInfo(List<NameValuePair> params) {
        // 定位服务器的Servlet
        String servlet = "SearchInfoServlet";
        // 通过 POST 方式获取 HTTP 服务器数据
        String responseMsg = null;
        responseMsg = MyHttpPost.executeHttpPost(servlet, params);
        Log.i("tag", "SearchInfoService: responseMsg = " + responseMsg);

        return responseMsg;
    }

    static String sendInfo(List<NameValuePair> params) {
        // 定位服务器的Servlet
        String servlet = "SendInfoServlet";
        // 通过 POST 方式获取 HTTP 服务器数据
        String responseMsg = null;
        responseMsg = MyHttpPost.executeHttpPost(servlet, params);
        Log.i("tag", "SendInfoService: responseMsg = " + responseMsg);

        return responseMsg;
    }
}
