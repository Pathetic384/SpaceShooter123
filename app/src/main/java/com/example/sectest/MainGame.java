package com.example.sectest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    Context context;
    Bitmap background;
    Handler handler;
    static int screenWidth, screenHeight;
    Character character;
    Enemy enemy;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<EnemyBullet> e_bullets = new ArrayList<>();
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
        enemy = new Enemy(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg1);
        handler = new Handler();

        timer = new CountDownTimer(500, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                try{
                    ShootBullet();
                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //background
        canvas.drawBitmap(background, 0,0 ,null);

        //character
        if(character.x > screenWidth - character.getShipWidth())
            character.x = screenWidth - character.getShipWidth();
        else if(character.x<0) character.x = 0;
        if(character.y <= 0) character.y = 0;
        else if(character.y + character.getShipHeight() > screenHeight) character.y = screenHeight - character.getShipHeight();
        character.charFrame++;
        if(character.charFrame > 7) character.charFrame = 0;
        canvas.drawBitmap(character.getShip(character.charFrame), character.x, character.y, null);

        //enemy
        canvas.drawBitmap(enemy.getEnemyShip(), enemy.x, enemy.y, null);

        //explosion
        for(int i=0; i<explosions.size();i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(), explosions.get(i).x, explosions.get(i).y, null);
            explosions.get(i).frame++;
            if(explosions.get(i).frame > 7) explosions.remove(i);
        }

        //own_bullets
        for(int i=0; i < bullets.size();i++) {
            bullets.get(i).y -=30;
            canvas.drawBitmap(bullets.get(i).getBullet(), bullets.get(i).x, bullets.get(i).y, null);
            if(bullets.get(i).y <= 0) bullets.remove(i);
            else if(bullets.get(i).x >= enemy.x
                    && bullets.get(i).x + bullets.get(i).getBulletWidth() <= enemy.x + enemy.getEnemyShipWidth()
                    && bullets.get(i).y >= enemy.y
                    && bullets.get(i).y + bullets.get(i).getBulletHeight() <= enemy.y +enemy.getEnemyShipHeight() ) {
                explosions.add(new Explosion(context, enemy.x + 30, enemy.y + 100));
                bullets.remove(i);
            }
        }

        //enemy_bullet
        for(int i=0; i < e_bullets.size();i++) {
            e_bullets.get(i).y +=15;
            canvas.drawBitmap(e_bullets.get(i).getBullet(), e_bullets.get(i).x, e_bullets.get(i).y, null);
            if(e_bullets.get(i).y > screenHeight) e_bullets.remove(i);
            else if(e_bullets.get(i).x >= character.x
                    && e_bullets.get(i).x + e_bullets.get(i).getBulletWidth() <= character.x + character.getShipWidth()
                    && e_bullets.get(i).y >= character.y
                    && e_bullets.get(i).y + e_bullets.get(i).getBulletHeight() <= character.y +character.getShipHeight() ) {
                explosions.add(new Explosion(context, character.x + 30, character.y + 20));
                e_bullets.remove(i);
            }
        }

        //reset drawing
        handler.postDelayed(runnable, 10);
    }

    private void ShootBullet() {
        Bullet newBullet = new Bullet(context, character.x + character.getShipWidth()/2 - 20, character.y);
        EnemyBullet newEBullet = new EnemyBullet(context, enemy.x + enemy.getEnemyShipWidth()/2 - 20, enemy.y + enemy.getEnemyShipHeight());
        e_bullets.add(newEBullet);
        bullets.add(newBullet);
        timer.start();
    }

    int xDown = 0;
    int yDown = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            xDown = character.x - (int) event.getX();
            yDown = character.y - (int) event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            character.x = (int) event.getX() + xDown;
            character.y = (int) event.getY() + yDown;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            xDown = 0;
            yDown = 0;
        }
        return true;
    }
}
