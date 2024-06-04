package com.example.aircraftwar2024.database;

import java.util.List;

public interface ScoreDao {
    List<Score> readScoresFromFile();
    void  doAdd(Score score,List<Score> leaderboard);
    void writeScoresToFile(List<Score> leaderboard);
    void printLeaderboard(List<Score> leaderboard);
    void remove(int rank);
}
