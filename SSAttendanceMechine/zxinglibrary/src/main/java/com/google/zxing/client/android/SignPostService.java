package com.google.zxing.client.android;

import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class SignPostService {
    static int SIGN_FAILED = 4;
    static int SIGN_SUCCEEDED = 5;

    public static int send(List<NameValuePair> params) {
        // 返回值
        int responseInt = SIGN_FAILED;
        // 定位服务器的Servlet
        String servlet = "SignServlet";
        // 通过 POST 方式获取 HTTP 服务器数据
        String responseMsg;
        responseMsg = MyHttpPost.executeHttpPost(servlet, params);
        Log.i("tag", "SignService: responseMsg = " + responseMsg);
        // 解析服务器数据，返回相应 Long 值
        if(responseMsg.equals("SUCCEEDED")) {
            responseInt = SIGN_SUCCEEDED;
        }
        return responseInt;
    }
}
