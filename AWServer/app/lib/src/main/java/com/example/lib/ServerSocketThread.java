package com.example.lib;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerSocketThread extends Thread{
    private BufferedReader in;
    private PrintWriter pw;
    private Socket socket;
    private ServerSocketThread opponent;
    private boolean gameOverFlag = false;
    public ServerSocketThread(Socket socket){
        this.socket = socket;
    }

    public void setOpponent(ServerSocketThread opponent){
        this.opponent = opponent;
    }

    @Override
    public void run(){
        try {
            opponent.sendMessage("success");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            String content;

            while ((content = in.readLine()) != null) {
                if(content.equals("dead")){
                    System.out.println("disconnect from client,close socket");
                    gameOverFlag = true;
                    while (!opponent.getGameOverFlag()){}
                    pw.println("game over");
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                    break;
                }
                else opponent.sendMessage(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage( String message) {
        PrintWriter pout = null;
        try{
            System.out.println(message);
            pout = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),"utf-8")),true);
            pout.println(message);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public boolean getGameOverFlag(){
        return gameOverFlag;
    }
}
