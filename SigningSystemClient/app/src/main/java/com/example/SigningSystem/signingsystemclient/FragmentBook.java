package com.example.SigningSystem.signingsystemclient;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentBook extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_book, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	/*
	public void onClicked(View view) {
		
		switch (view.getId()) {
		
		case R.id.rl_book1:
			new AlertDialog.Builder(getActivity()).setTitle("高等数学").setMessage(R.string.gdsx).setPositiveButton("确定", null).show();
			break;
		case R.id.rl_book2:
			new AlertDialog.Builder(getActivity()).setTitle("线性代数").setMessage(R.string.xxds).setPositiveButton("确定", null).show();
			break;
		
		case R.id.rl_book3:
			
			break;
		
		case R.id.rl_book4:
			
			break;
		
		case R.id.rl_book5:
			
			break;
		
		case R.id.rl_book6:
			
			break;
		default:
			break;
		}
	}*/
}
