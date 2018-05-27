package com.example.SigningSystem.signingsystemclient;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends FragmentActivity {
	
	private Fragment[] fragments;
	private int index;
    // 当前fragment的index
    private int currentTabIndex;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    public FragmentMain contactfragment;
    private FragmentClass classfragment;
    private FragmentBook bookfragment;
    private FragmentAboutme aboutmefragment;
    private TextView titleTextView;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }
    
    private void initView() {
    	//titleTextView = new TextView(null);
    	contactfragment = new FragmentMain();
    	classfragment = new FragmentClass();
    	bookfragment = new FragmentBook();
    	aboutmefragment = new FragmentAboutme();
        fragments = new Fragment[] { contactfragment, classfragment,
                bookfragment, aboutmefragment };
        titleTextView = (TextView) findViewById(R.id.titletext);
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_contact);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_class);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_book);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_aboutme);
        imagebuttons[0].setSelected(true);
        
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_contact);
        textviews[1] = (TextView) findViewById(R.id.tv_class);
        textviews[2] = (TextView) findViewById(R.id.tv_book);
        textviews[3] = (TextView) findViewById(R.id.tv_aboutme);
        textviews[0].setTextColor(0xFF45C01A);
        
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, contactfragment)
                .add(R.id.fragment_container, classfragment)
                .add(R.id.fragment_container, bookfragment)
                .add(R.id.fragment_container, aboutmefragment)
                .hide(classfragment).hide(bookfragment)
                .hide(aboutmefragment).show(contactfragment).commit();
    }
    
    public void onTabClicked(View view) {
        switch (view.getId()) {
        case R.id.re_contact:
        	titleTextView.setText("系统");
            index = 0;
            break;
        case R.id.re_class:
        	titleTextView.setText("课程");
            index = 1;
            break;
        case R.id.re_book:
        	titleTextView.setText("图书");
            index = 2;
            break;
        case R.id.re_aboutme:
        	titleTextView.setText("用户");
            index = 3;
            break;

        }
        
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            trx.replace(R.id.fragment_container, fragments[index]);
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }
	
public void onClicked(View view) {
		
		switch (view.getId()) {
		
		case R.id.rl_book1:
			new AlertDialog.Builder(this).setTitle("高等数学").setMessage(R.string.gdsx).setPositiveButton("确定", null).show();
			break;
		case R.id.rl_book2:
			new AlertDialog.Builder(this).setTitle("概率论").setMessage(R.string.gll).setPositiveButton("确定", null).show();
			break;
		
		case R.id.rl_book3:
			new AlertDialog.Builder(this).setTitle("线性代数").setMessage(R.string.xxds).setPositiveButton("确定", null).show();
			break;
		
		case R.id.rl_book4:
			new AlertDialog.Builder(this).setTitle("操作系统").setMessage(R.string.czxt).setPositiveButton("确定", null).show();
			break;
		
		case R.id.rl_book5:
			new AlertDialog.Builder(this).setTitle("信息安全").setMessage(R.string.xxaq).setPositiveButton("确定", null).show();
			break;
		
		case R.id.rl_book6:
			new AlertDialog.Builder(this).setTitle("数据结构").setMessage(R.string.sjjg).setPositiveButton("确定", null).show();
			break;
			
		case R.id.btn_slt_class:
			new AlertDialog.Builder(this).setTitle("课程选择").setMessage("课程选择成功！").setPositiveButton("确定", null).show();
			break;
		default:
			break;
		}
	}

}
