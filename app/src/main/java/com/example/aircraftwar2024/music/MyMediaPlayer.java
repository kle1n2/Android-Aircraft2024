package com.example.aircraftwar2024.music;

import android.content.Context;
import android.location.GnssMeasurement;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.MainActivity;

public class MyMediaPlayer {
    private static MediaPlayer bgMP;
    private static MediaPlayer bgMP_boss;
    public static void playBGM(Context context){
        if(MainActivity.getMusicSwitch()) {
            bgMP = MediaPlayer.create(context, R.raw.bgm);
            bgMP.start();
            bgMP.setLooping(true);
        }
    }
    public static void playBossBGM(Context context){
        if(MainActivity.getMusicSwitch()) {
            bgMP_boss = MediaPlayer.create(context, R.raw.bgm_boss);
            bgMP_boss.start();
            bgMP_boss.setLooping(true);
        }
    }
    public static void pauseBGM(){
        if(MainActivity.getMusicSwitch()) {
            bgMP.pause();
        }
    }
    public static void stopBossBGM(){
        if(MainActivity.getMusicSwitch()) {
            bgMP_boss.stop();
            bgMP_boss.release();
            bgMP_boss = null;
        }
    }
    public static void continueBGM(){
        if(MainActivity.getMusicSwitch()) {
            int position = bgMP.getCurrentPosition();
            bgMP.seekTo(position);
            bgMP.start();
        }
    }
    public static void stopBGM(){
        if(MainActivity.getMusicSwitch()) {
            bgMP.stop();
            bgMP.release();
            bgMP = null;
        }
    }


}
