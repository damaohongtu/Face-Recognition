package com.mao.guli.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mao.guli.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Timer timer=new Timer();
        timer.schedule(new Task(),1500);
    }
    class Task extends TimerTask {
        @Override
        public void run(){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }
    }
}
