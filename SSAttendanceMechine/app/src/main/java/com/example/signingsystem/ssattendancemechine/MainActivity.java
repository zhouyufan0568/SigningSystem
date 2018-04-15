package com.example.signingsystem.ssattendancemechine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.client.android.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    Button qrcodescan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrcodescan = (Button)findViewById(R.id.qrcodescan);
        qrcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });
    }
}
