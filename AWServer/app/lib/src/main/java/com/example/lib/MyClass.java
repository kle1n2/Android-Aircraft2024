package com.example.lib;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MyClass {
    ServerSocket serverSocket;
    ServerSocketThread opponentThread;
    ServerSocketThread playerThread;
    private PrintWriter pw;
    private Queue<ServerSocketThread> waitingPlayers;
    public static void main(String[] args){
        new MyClass();
    }

    public MyClass(){
        try {
            // 1. 创建ServerSocket
            serverSocket = new ServerSocket(9999);
            waitingPlayers = new LinkedList<>();
            System.out.println("--Listener Port: 9999--");
            start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void start(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New player connected");
                playerThread = new ServerSocketThread(socket);
                matchPlayers(playerThread);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void matchPlayers(ServerSocketThread playerThread) {
        if (waitingPlayers.isEmpty()) {
            waitingPlayers.add(playerThread);
            System.out.println("Player added to waiting list");
        } else {
            opponentThread = waitingPlayers.poll();
            if (opponentThread != null) {
                playerThread.setOpponent(opponentThread);
                opponentThread.setOpponent(playerThread);
                playerThread.start();
                opponentThread.start();
                System.out.println("Two players matched and threads started");
            }
        }
    }
}