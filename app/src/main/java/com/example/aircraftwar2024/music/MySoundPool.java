package com.example.aircraftwar2024.music;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.MainActivity;
import com.example.aircraftwar2024.game.BaseGame;

import java.util.HashMap;

public class MySoundPool {
    SoundPool mysp;
    HashMap<Integer, Integer> soundPoolMap;

    public MySoundPool(Context context) {
        createSoundPool();
        soundPoolMap = new HashMap<Integer,Integer>();
        soundPoolMap.put(1,mysp.load(context,R.raw.bomb_explosion,1));
        soundPoolMap.put(2,mysp.load(context,R.raw.bullet_hit,1));
        soundPoolMap.put(3,mysp.load(context,R.raw.game_over,1));
        soundPoolMap.put(4,mysp.load(context,R.raw.get_supply ,1));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPool() {
        if (mysp == null) {
            // Android 5.0 及 之后版本
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = null;
                audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mysp = new SoundPool.Builder()
                        .setMaxStreams(10)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else { //Android 5.0 以前版本
                mysp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);  // 创建SoundPool
            }
        }
    }

    public void playSound(int i){
        System.out.println(MainActivity.getMusicSwitch());
        if(MainActivity.getMusicSwitch())
            mysp.play(soundPoolMap.get(i),1,1,0,0,1);
    }
}
