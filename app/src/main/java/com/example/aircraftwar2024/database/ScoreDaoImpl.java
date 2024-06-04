package com.example.aircraftwar2024.database;

import android.content.Context;

import java.io.*;
import java.net.ContentHandler;
import java.util.*;

public class ScoreDaoImpl implements ScoreDao{
    private String Scores_file;
    private Context context;
    public ScoreDaoImpl(Context context){
        this.context = context;
    }

    public void setFileName(int diff){
        if(diff == 1){
            Scores_file = "simple.txt";
        }
        else if(diff == 2){
            Scores_file = "normal.txt";
        }
        else Scores_file = "hard.txt";
    }
    @Override
    public List<Score> readScoresFromFile() {
        List<Score> leaderboard = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(Scores_file));
            while (true){
                try{
                    leaderboard.add((Score) ois.readObject());
                } catch (Exception e) {
                    break;
                }
            }
            } catch (Exception e) {
            System.err.println("读取文件时发生错误: " + e.getMessage());
        }
        return leaderboard;
    }

    @Override
    public void doAdd(Score score, List<Score> leaderboard) {
        leaderboard.add(score);
    }

    @Override
    public void writeScoresToFile(List<Score> leaderboard){
        Collections.sort(leaderboard, Comparator.comparingInt(Score::getScore).reversed());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(Scores_file,Context.MODE_PRIVATE));
            for(Score a:leaderboard){
                oos.writeObject(a);
            }
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
    }

    @Override
    public void printLeaderboard(List<Score> leaderboard) {
        int rank = 1;
        System.out.println("****************");
        System.out.println("排行榜");
        System.out.println("****************");
        for (Score score : leaderboard) {
            System.out.printf("第%d名: %s, %d, %s\n",
                    rank++, score.getUsername(), score.getScore(), score.getTimestamp());
        }
        System.out.println("Game Over!");
    }
    @Override
    public void remove(int rank){
        List<Score> leaderboard = readScoresFromFile();
        leaderboard.remove(rank-1);
        writeScoresToFile(leaderboard);
    }
}

