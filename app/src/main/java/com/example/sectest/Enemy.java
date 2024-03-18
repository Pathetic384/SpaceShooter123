package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Random;

public class Enemy {
    Context context;
    static Bitmap enemyShip;
    static Bitmap enemyShip1;
    static Bitmap enemyShip2;
    static Bitmap enemyShip3;
    int x,y;
    static CountDownTimer timer;
    CountDownTimer bullet_timer;
    static int enemyLoop = 3000;
    static int bulletLoop = 1000;
    static int enemyVelocity = 5;

    public Enemy(Context context) {
        this.context = context;
        enemyShip1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enem1);
        enemyShip2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enem2);
        enemyShip3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enem3);
        ChangeShip(MainActivity.currentLvl);

        resetEnemyShip();
        SpawnBullets();
    }

    public static void ChangeShip(int i) {
        if(i==1) {
            enemyShip = enemyShip1;
            bulletLoop = 1000;
            enemyLoop = 3000;
            enemyVelocity = 5;
        }
        if(i==2) {
            enemyShip = enemyShip2;
            bulletLoop = 750;
            enemyLoop = 2500;
            enemyVelocity = 8;
        }
        if(i==3) {
            enemyShip = enemyShip3;
            bulletLoop = 500;
            enemyLoop = 2000;
            enemyVelocity = 10;
        }
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

    public static void SpawnEnemy(Context context) {
        timer = new CountDownTimer(enemyLoop, 20) {
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

    void SpawnBullets(){
        bullet_timer = new CountDownTimer(bulletLoop, 20) {
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
    void ContinueShooting() {bullet_timer.start();}
    static void StopSpawning() {
        timer.cancel();
    }
    static void ContinueSpawning() {
        timer.start();
    }
}
