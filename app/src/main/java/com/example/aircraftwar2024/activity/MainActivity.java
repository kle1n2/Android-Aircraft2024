package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.database.Score;
import com.example.aircraftwar2024.game.BaseGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static boolean musicSwitch=false;
    private Handler handler;
    private static Socket socket;
    private static  final String TAG = "MainActivity";
    public static Socket getSocket(){
        return socket;
    }
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

        handler = new Handler(getMainLooper()){
            //当数据处理子线程更新数据后发送消息给UI线程，UI线程更新UI
            @Override
            public void handleMessage(Message msg){
                if(msg.obj=="success"){
                    Intent intent=new Intent(MainActivity.this,GameActivity.class);
                    intent.putExtra("gameType",2);
                    startActivity(intent);
                }
            }
        };

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

        Button mulbtn=(Button) findViewById(R.id.mulbtn);
        mulbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseGame.setMulFlag(true);
                new Thread(new NetConn(handler)).start();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("匹配中，请等待....");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    protected class NetConn extends Thread{
        private BufferedReader in;
        private Handler toClientHandler;

        public NetConn(Handler myHandler){
            this.toClientHandler = myHandler;
        }
        @Override
        public void run(){
            try{
                socket = new Socket();

                socket.connect(new InetSocketAddress
                        ("10.0.2.2",9999),5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));

                //接收服务器返回的数据
                Thread receiveServerMsg =  new Thread(){
                    @Override
                    public void run(){
                        String fromserver = null;
                        try{
                            while(true)
                            {
                                fromserver = in.readLine();
                                System.out.println(1);
                                System.out.println(fromserver);
                                //发送消息给UI线程
                                if(fromserver=="success") {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = fromserver;
                                    toClientHandler.sendMessage(msg);
                                    break;
                                }
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                receiveServerMsg.start();
            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }


}