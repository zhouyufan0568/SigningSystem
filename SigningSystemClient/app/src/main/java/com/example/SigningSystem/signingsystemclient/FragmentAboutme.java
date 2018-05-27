package com.example.SigningSystem.signingsystemclient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SdCardPath")
public class FragmentAboutme extends Fragment {

    private TextView tv_name;
    private RelativeLayout re_myinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aboutme, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_name = (TextView) getView().findViewById(R.id.tv_name);
        re_myinfo = (RelativeLayout) getView().findViewById(R.id.re_myinfo);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if(username != "") {
            tv_name.setText(username);
        }else{
            Toast.makeText(getActivity(), "获取用户名失败", Toast.LENGTH_SHORT).show();
        }

        re_myinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Context indexcontext = getActivity();
                Intent intent = new Intent(indexcontext, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
