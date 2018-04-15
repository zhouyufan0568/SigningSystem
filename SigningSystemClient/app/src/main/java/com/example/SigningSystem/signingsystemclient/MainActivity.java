package com.example.SigningSystem.signingsystemclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhouyufan on 2018/4/14.
 */

public class MainActivity extends AppCompatActivity {

    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign = (Button)findViewById(R.id.sign);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,
                        SignActivity.class);
                startActivity(intent);
            }
        });
    }

}