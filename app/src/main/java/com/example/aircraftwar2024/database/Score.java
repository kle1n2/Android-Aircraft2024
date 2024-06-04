package com.example.aircraftwar2024.database;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Score implements Serializable {
    private int score;
    private String time;
    private String username;

    public Score(int score, String username) {
        this.score = score;
        this.username = username;
        // 获取当前时间
        Date currentDate = new Date();
        System.out.println(currentDate);

        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");

        // 将日期转换为字符串
        this.time = dateFormat.format(currentDate);
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return time;
    }
}
