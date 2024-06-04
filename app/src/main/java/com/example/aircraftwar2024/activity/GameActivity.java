package com.example.aircraftwar2024.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.database.Score;
import com.example.aircraftwar2024.database.ScoreDaoImpl;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameActivity extends AppCompatActivity {
    public static Handler handler;
    private ScoreDaoImpl leaderboard;
    private static final String TAG = "GameActivity";

    private  static int gameType=0;
    public static int screenWidth,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);

        getScreenHW();
        handler=new MyHandler();

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
        }
        leaderboard = new ScoreDaoImpl(this);
        leaderboard.setFileName(gameType);

        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        BaseGame baseGameView;
        switch (gameType) {
            case 1: {
                baseGameView = new EasyGame(this);
                setContentView(baseGameView);
                break;
            }
            case 2: {
                baseGameView = new MediumGame(this);
                setContentView(baseGameView);
                break;
            }
            case 3: {
                baseGameView = new HardGame(this);
                setContentView(baseGameView);
                break;
            }
        }

    }
    public static int getGameType(){
        return gameType;
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public List<Map<String, Object>> getData(){
        int i = 1;
        List<Score> scores = leaderboard.readScoresFromFile();
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map ;
        map = new HashMap<String, Object>();
        map.put("rank", "排名");
        map.put("name","用户");
        map.put("score", "分数");
        map.put("time", "时间");
        listitem.add(map);
        for(Score score:scores){
            map = new HashMap<String, Object>();
            map.put("rank", i);
            map.put("name","test");
            map.put("score", score.getScore());
            map.put("time", score.getTimestamp());
            i += 1;
            listitem.add(map);
        }
        return listitem;
    }
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                setContentView(R.layout.activity_record);
                Button button = (Button) findViewById(R.id.back);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        while (ActivityManager.getActivityManager().currentActivity().getClass() != MainActivity.class) {
                            ActivityManager.getActivityManager().finishActivity();
                        }
                    }
                });
                TextView textView = (TextView) findViewById(R.id.difficulty);
                switch (gameType) {
                    case 1: {
                        textView.setText("简单模式");
                        break;
                    }
                    case 2: {
                        textView.setText("普通模式");
                        break;
                    }
                    case 3: {
                        textView.setText("困难模式");
                        break;
                    }
                }
                ListView list = (ListView) findViewById(R.id.ListView01);

                //生成适配器的Item和动态数组对应的元素

                SimpleAdapter listItemAdapter = new SimpleAdapter(
                        GameActivity.this,
                        getData(),
                        R.layout.activity_item,
                        new String[]{"rank", "name", "score", "time"},
                        new int[]{R.id.rank, R.id.name, R.id.score, R.id.time});

                //添加并且显示
                list.setAdapter(listItemAdapter);
                //添加单击监听
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if(arg2 != 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                            builder.setMessage("确定删除第" + (arg2) + "条数据？")
                                    .setTitle("提示");
                            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    leaderboard.remove(arg2);
                                    SimpleAdapter listItemAdapter = new SimpleAdapter(
                                            GameActivity.this,
                                            getData(),
                                            R.layout.activity_item,
                                            new String[]{"rank", "name", "score", "time"},
                                            new int[]{R.id.rank, R.id.name, R.id.score, R.id.time});

                                    //添加并且显示
                                    list.setAdapter(listItemAdapter);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

            }
        }
    }}