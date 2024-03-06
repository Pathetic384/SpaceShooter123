package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Random;

public class Enemy {
    Context context;
    Bitmap enemyShip;
    int x,y;
    static CountDownTimer timer;
    CountDownTimer bullet_timer;

    public Enemy(Context context) {
        this.context = context;
        enemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.enem1);

        resetEnemyShip();
        SpawnBullets(1000);
    }

    public Bitmap getEnemyShip() {
        return enemyShip;
    }
    public int getEnemyShipHeight() {
        return enemyShip.getHeight();
    }
    public int getEnemyShipWidth() {
        return enemyShip.getWidth();
    }
    public void resetEnemyShip() {
        x = new Random().nextInt(MainGame.screenWidth - getEnemyShipWidth());
        y = 0;
    }

    public static void SpawnEnemy(int loop, Context context) {
        timer = new CountDownTimer(loop, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {

                try{
                    Spawning(context);
                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }
    static void Spawning(Context context) {
        Enemy enemy = new Enemy(context);
        MainGame.enemies.add(enemy);
        timer.start();
    }

    void SpawnBullets(int loop){
        bullet_timer = new CountDownTimer(loop, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {

                try{
                    Bullet_Spawning();
                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }
    void Bullet_Spawning() {
        EnemyBullet enemyBullet = new EnemyBullet(context, x + getEnemyShipWidth()/2 - 20, y + getEnemyShipHeight());
        MainGame.e_bullets.add(enemyBullet);
        bullet_timer.start();
    }
    void StopShooting() {
        bullet_timer.cancel();
    }
}
