package com.demo.ihealth.drawhookviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawAutoHookView duihao = (DrawAutoHookView) findViewById(R.id.duihao);
        duihao.postDelayed(new Runnable() {
            @Override
            public void run() {
                duihao.setLoading(false);
            }
        }, 5000);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duihao.setLoading(true);
                duihao.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        duihao.setLoading(false);
                    }
                }, 5000);
            }
        });
        tryit();

    }

    public void tryit() {
        Log.e("main", "------------------try");
        SeekBar s = null;
        try {
            s.setMax(100);
        } catch (Exception e) {
            Log.e("main", "------------------catch");
            e.printStackTrace();
            Log.e("main", "------------------printStackTrace");
        }
        Log.e("main", "------------------final");

    }
}
