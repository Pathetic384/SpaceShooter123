package com.example.sectest;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    Bitmap layer1, layer2, layer3, layer4;
    int y2 = 0, y3 = 0, y4 = 0;
    public static int score;
    int lifes;
    Paint scorePaint;
    Handler handler;
    static int screenWidth, screenHeight;
    Character character;
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
    private CountDownTimer timer;

    public MainGame(Context context) {
        super(context);
        this.context = context;

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenHeight = point.y;
        screenWidth = point.x;

        character = new Character(context);
        scoreImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
        hp = BitmapFactory.decodeResource(context.getResources(), R.drawable.hp);
        //layers
        layer1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer1), screenWidth, screenHeight, false );
        layer2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer2), screenWidth, screenHeight, false );
        layer3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer3), screenWidth, screenHeight, false );
        layer4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.layer4), screenWidth, screenHeight, false );

        handler = new Handler();

        lifes = 3;
        score = 0;
        scorePaint = new Paint();
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(80);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        Enemy.SpawnEnemy(3000, context);
        Character.SpawnBullet(500, context);

        handler = new Handler();
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
        if(lifes == 0 && !dead) {
            dead = true;
            character.charFrame = 8;
            postDelayed(new Runnable() {
                @Override
                public void run() {
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

        //character
        if(!dead) {
            if (Character.x > screenWidth - Character.getShipWidth())
                Character.x = screenWidth - Character.getShipWidth();
            else if (Character.x < 0) Character.x = 0;
            if (Character.y <= 0) Character.y = 0;
            else if (Character.y + Character.getShipHeight() > screenHeight)
                Character.y = screenHeight - Character.getShipHeight();
            character.charFrame++;
            if (character.charFrame > 7) character.charFrame = 0;
            canvas.drawBitmap(character.getShip(character.charFrame), Character.x, Character.y, null);
        }
        else if(character.charFrame < 25){
            character.charFrame++;
            canvas.drawBitmap(character.getShip(character.charFrame), Character.x - 120, Character.y -30, null);
        }

        //enemy
        for(int i =0;i<enemies.size();i++) {
            enemies.get(i).y += 5;
            canvas.drawBitmap(enemies.get(i).getEnemyShip(), enemies.get(i).x, enemies.get(i).y, null);
            if (enemies.get(i).y > screenHeight) enemies.remove(i);
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
                if (bullets.get(i).y <= 0) bullets.remove(i);
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
