package com.example.sectest;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MainGame extends View {
    boolean paused = false;
    boolean dead = false;
    Context context;
    Bitmap scoreImg, hp;
    static Bitmap layer1, layer2, layer3, layer4;
    static Bitmap layer11, layer21, layer31, layer41;
    static Bitmap layer12, layer22, layer32, layer42;
    static Bitmap layer13, layer23, layer33, layer43;
    int y2 = 0, y3 = 0, y4 = 0;
    public static int score;
    int lifes;
    Paint scorePaint;
    Handler handler;
    static int screenWidth, screenHeight;
    Character character;
    ExitPoint exitPoint;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<EnemyBullet> e_bullets = new ArrayList<>();
    ArrayList<Explosion> explosions = new ArrayList<>();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public MainGame(Context context) {
        super(context);
        this.context = context;

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenHeight = point.y;
        screenWidth = point.x;

        exitPoint = new ExitPoint(context);
        character = new Character(context);
        scoreImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
        hp = BitmapFactory.decodeResource(context.getResources(), R.drawable.hp);
        //layers ez
        layer11 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer1), screenWidth, screenHeight, false );
        layer21 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer2), screenWidth, screenHeight, false );
        layer31 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer3), screenWidth, screenHeight, false );
        layer41 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer4), screenWidth, screenHeight, false );
        //layer mid
        layer12 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer1), screenWidth, screenHeight, false );
        layer22 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer2), screenWidth, screenHeight, false );
        layer32 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer3), screenWidth, screenHeight, false );
        layer42 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer4), screenWidth, screenHeight, false );
        //layer herd
        layer13 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer1), screenWidth, screenHeight, false );
        layer23 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer2), screenWidth, screenHeight, false );
        layer33 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer3), screenWidth, screenHeight, false );
        layer43 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer4), screenWidth, screenHeight, false );
        ChangeBackground(1);
        //exit point


        //handler
        handler = new Handler();

        lifes = 3;
        score = 0;
        scorePaint = new Paint();
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(80);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        Enemy.SpawnEnemy( context);
        Character.SpawnBullet(500, context);

        handler = new Handler();
    }

    public static void ChangeBackground(int i) {
        if(i==1) {layer1 = layer11; layer2 = layer21; layer3 = layer31; layer4 = layer41;}
        if(i==2) {layer1 = layer12; layer2 = layer22; layer3 = layer32; layer4 = layer42;}
        if(i==3) {layer1 = layer13; layer2 = layer23; layer3 = layer33; layer4 = layer43;}
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //background
        canvas.drawBitmap(layer1, 0,0 ,null);
        y2 += 3;
        canvas.drawBitmap(layer2, 0, y2, null);
        canvas.drawBitmap(layer2, 0, y2 - screenHeight, null);
        if(y2 ==  screenHeight) y2 = 0;
        y3 += 4;
        canvas.drawBitmap(layer3, 0, y3, null);
        canvas.drawBitmap(layer3, 0, y3 - screenHeight, null);
        if(y3 ==  screenHeight) y3 = 0;
        y4 += 5;
        canvas.drawBitmap(layer4, 0, y4, null);
        canvas.drawBitmap(layer4, 0, y4 - screenHeight, null);
        if(y4 ==  screenHeight) y4 = 0;

        //lifes
        for(int i = 1; i<= lifes; i++) {
            canvas.drawBitmap(hp, 15, screenHeight - 70 - (hp.getHeight() + 10)*i, null);
        }
        if((lifes == 0 && !dead)) {//(Character.x + Character.getShipWidth() > screenWidth - ExitPoint.exitWidth + 50 && Character.y < ExitPoint.exitHeight - 50)) {
            lifes = 0;
            dead = true;
            Character.charFrame = 8;
            Enemy.StopSpawning();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    Character.resetShip();
                    for(int i = 0;i<enemies.size();i++) {
                        enemies.get(i).StopShooting();
                    }
                    enemies.clear();
                    bullets.clear();
                    Character.StopSpawning();
                    e_bullets.clear();
                    explosions.clear();
                    handler = null;
                    paused = true;
                    Intent intent = new Intent(context, LoseScreen.class);
                    ((Activity) context).finish();
                    context.startActivity(intent);
                }
            }, 2500);
        }
        canvas.drawBitmap(scoreImg, 15,15 ,null);
        canvas.drawText(String.valueOf(score), scoreImg.getWidth() + 40, 70, scorePaint);

        //exit area
        exitPoint.frame++;
        if(exitPoint.frame>29) exitPoint.frame=0;
        canvas.drawBitmap(exitPoint.getExit(), screenWidth - ExitPoint.exitWidth, 0, null);

        //character
        if(!dead) {
            if (Character.x > screenWidth - Character.getShipWidth())
                Character.x = screenWidth - Character.getShipWidth();
            else if (Character.x < 0) Character.x = 0;
            if (Character.y <= 0) Character.y = 0;
            else if (Character.y + Character.getShipHeight() > screenHeight)
                Character.y = screenHeight - Character.getShipHeight();
            Character.charFrame++;
            if (Character.charFrame > 7) Character.charFrame = 0;
            canvas.drawBitmap(character.getShip(Character.charFrame), Character.x, Character.y, null);
        }
        else if(Character.charFrame < 25){
            Character.charFrame++;
            canvas.drawBitmap(character.getShip(Character.charFrame), Character.x - 120, Character.y -30, null);
        }

        //enemy
        for(int i =0;i<enemies.size();i++) {
            enemies.get(i).y += Enemy.enemyVelocity;
            canvas.drawBitmap(enemies.get(i).getEnemyShip(), enemies.get(i).x, enemies.get(i).y, null);
            if (enemies.get(i).y > screenHeight) {
                lifes--;
                enemies.remove(i);
            }
        }

        //explosion
        for(int i=0; i<explosions.size();i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(), explosions.get(i).x, explosions.get(i).y, null);
            explosions.get(i).frame++;
            if(explosions.get(i).frame > 7) explosions.remove(i);
        }

        //own_bullets
        if(!dead) {
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).y -= 30;
                canvas.drawBitmap(bullets.get(i).getBullet(), bullets.get(i).x, bullets.get(i).y, null);
                if (bullets.get(i).y <= 0) {
                    bullets.remove(i);
                    break;
                }
                for(int j=0;j<enemies.size();j++) {
                    if (bullets.get(i).x >= enemies.get(j).x
                            && bullets.get(i).x + bullets.get(i).getBulletWidth() <= enemies.get(j).x + enemies.get(j).getEnemyShipWidth()
                            && bullets.get(i).y >= enemies.get(j).y
                            && bullets.get(i).y + bullets.get(i).getBulletHeight() <= enemies.get(j).y + enemies.get(j).getEnemyShipHeight()) {
                        explosions.add(new Explosion(context, enemies.get(j).x + 30, enemies.get(j).y + 100));
                        enemies.get(j).StopShooting();
                        enemies.remove(j);
                        bullets.remove(i);
                        score += 10;
                        break;
                    }
                }
            }
        }

        //enemy_bullet
        for(int i=0; i < e_bullets.size();i++) {
            e_bullets.get(i).MovePattern();
            canvas.drawBitmap(e_bullets.get(i).getBullet(), e_bullets.get(i).x, e_bullets.get(i).y, null);
            if(e_bullets.get(i).y > screenHeight) e_bullets.remove(i);
            else if(!dead && e_bullets.get(i).x >= Character.x
                    && e_bullets.get(i).x + e_bullets.get(i).getBulletWidth() <= Character.x + Character.getShipWidth()
                    && e_bullets.get(i).y >= Character.y
                    && e_bullets.get(i).y + e_bullets.get(i).getBulletHeight() <= Character.y + Character.getShipHeight() ) {
                explosions.add(new Explosion(context, Character.x + 30, Character.y + 20));
                e_bullets.remove(i);
                lifes--;
            }
        }

        if(Character.x + Character.getShipWidth() > screenWidth - ExitPoint.exitWidth + 30 && Character.y  < ExitPoint.exitHeight - 30) lifes = 0;

        //reset drawing
        if(!paused) handler.postDelayed(runnable, 10);
    }


    int xDown = 0;
    int yDown = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(dead) return false;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            xDown = Character.x - (int) event.getX();
            yDown = Character.y - (int) event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            Character.x = (int) event.getX() + xDown;
            Character.y = (int) event.getY() + yDown;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            xDown = 0;
            yDown = 0;
        }
        return true;
    }

}
