package com.example.SigningSystem.signingsystemclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class FragmentMain extends Fragment {
	
	private RelativeLayout xiaoaiLayout;
	private RelativeLayout searchClass;
	private TextView welcome;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		welcome = (TextView) getView().findViewById(R.id.welcome);
		xiaoaiLayout = (RelativeLayout) getView().findViewById(R.id.sign);
		xiaoaiLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context indexcontext = getActivity();
				Intent contactxiaoai=new Intent(indexcontext, SignActivity.class);
				startActivity(contactxiaoai);
			}
		});

		searchClass = (RelativeLayout) getView().findViewById(R.id.mycrouse);
		searchClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context indexcontext = getActivity();
				Intent contactxiaoai=new Intent(indexcontext, CrouseActivity.class);
				startActivity(contactxiaoai);
			}
		});
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		String wel = sharedPreferences.getString("username","");
		if(wel != null){
			Calendar calendar = Calendar.getInstance();
			int r = calendar.get(Calendar.AM_PM);
			if (r == Calendar.AM) {
				wel = "上午好！" + wel;
			} else {
				wel = "下午好！" + wel;
			}
			welcome.setText(wel);
		}
	}
}
