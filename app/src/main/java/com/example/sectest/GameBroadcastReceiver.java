package com.example.sectest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class GameBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!MainActivity.ini) return;
        if(MainActivity.paused) {
            Character.StopSpawning();
            Enemy.StopSpawning();
            for(int i = 0;i<MainGame.enemies.size();i++) {
                MainGame.enemies.get(i).StopShooting();
            }
        }
        else {
            Character.ContinueSpawning();
            Enemy.ContinueSpawning();
            for(int i = 0;i<MainGame.enemies.size();i++) {
                MainGame.enemies.get(i).ContinueShooting();
            }
        }
    }
}