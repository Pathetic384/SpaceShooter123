package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

public class Character {
    Context context;
    static Bitmap ship[] = new Bitmap[26];
    static int x,y;
    int charFrame;
    static CountDownTimer timer;

    public Character(Context context) {
        this.context = context;
        ship[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char11);
        ship[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char12);
        ship[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char13);
        ship[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char14);
        ship[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char15);
        ship[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char16);
        ship[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char17);
        ship[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char18);
        ship[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        ship[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        charFrame = 0;
        resetShip();
    }

    public Bitmap getShip(int charFrame) {
        return ship[charFrame];
    }
    public static int getShipHeight() {
        return ship[0].getHeight();
    }
    public static int getShipWidth() {
        return ship[0].getWidth();
    }
    public void resetShip() {
        x = 200;
        y = 800;
    }

    public static void SpawnBullet(int loop, Context context) {
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
        Bullet bullet = new Bullet(context, x + getShipWidth()/2 - 20, y);
        MainGame.bullets.add(bullet);
        timer.start();
    }
}
