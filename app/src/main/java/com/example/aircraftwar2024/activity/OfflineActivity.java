package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        Button easyButton=(Button) findViewById(R.id.easy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OfflineActivity.this,GameActivity.class);
                intent.putExtra("gameType",1);
                startActivity(intent);
            }
        });
        Button mediumButton=(Button) findViewById(R.id.medium);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OfflineActivity.this,GameActivity.class);
                intent.putExtra("gameType",2);
                startActivity(intent);
            }
        });
        Button hardButton=(Button) findViewById(R.id.hard);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OfflineActivity.this,GameActivity.class);
                intent.putExtra("gameType",3);
                startActivity(intent);
            }
        });
    }
}