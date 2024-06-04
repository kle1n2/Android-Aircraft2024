package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static boolean musicSwitch;
    public static void setMusicSwitch(String s){
        if(Objects.equals(s, "开启音效")) musicSwitch = true;
        else musicSwitch = false;
    }
    public static boolean getMusicSwitch(){
        return musicSwitch;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getActivityManager().addActivity(this);
        String musicSwitch;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=(Button) findViewById(R.id.btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OfflineActivity.class);
                intent.putExtra("musicSwitch",MainActivity.getMusicSwitch());
                startActivity(intent);
            }
        });
        RadioGroup radioGroup = findViewById(R.id.music);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                MainActivity.setMusicSwitch(radioButton.getText().toString());
            }
        });

    }


}