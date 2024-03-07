package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

public class Character {
    Context context;
    static Bitmap ship[] = new Bitmap[26];
    static Bitmap ship1[] = new Bitmap[26];
    static Bitmap ship2[] = new Bitmap[26];
    static Bitmap ship3[] = new Bitmap[26];
    static int x,y;
    static int charFrame;
    static CountDownTimer timer;

    public Character(Context context) {
        this.context = context;
        //loading ship1
        ship1[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char11);
        ship1[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char12);
        ship1[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char13);
        ship1[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char14);
        ship1[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char15);
        ship1[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char16);
        ship1[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char17);
        ship1[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char18);
        ship1[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship1[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship1[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship1[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship1[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship1[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship1[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship1[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship1[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship1[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship1[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship1[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship1[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship1[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship1[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship1[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship1[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        ship1[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        //loading ship2
        ship2[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char11);
        ship2[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char12);
        ship2[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char13);
        ship2[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char14);
        ship2[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char15);
        ship2[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char16);
        ship2[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char17);
        ship2[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char18);
        ship2[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship2[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship2[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship2[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship2[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship2[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship2[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship2[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship2[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship2[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship2[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship2[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship2[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship2[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship2[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship2[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship2[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        ship2[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        //load ship 2
        ship3[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char11);
        ship3[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char12);
        ship3[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char13);
        ship3[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char14);
        ship3[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char15);
        ship3[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char16);
        ship3[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char17);
        ship3[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char18);
        ship3[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship3[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e11);
        ship3[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship3[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e12);
        ship3[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship3[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e13);
        ship3[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship3[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e14);
        ship3[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship3[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e15);
        ship3[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship3[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e16);
        ship3[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship3[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e17);
        ship3[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship3[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e18);
        ship3[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);
        ship3[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.char_e19);

        ship = ship1;
        charFrame = 0;
        resetShip();
    }

    public static void ChangeShip(int i) {
        if(i==1) ship = ship1;
        if(i==2) ship = ship2;
        if(i==3) ship = ship3;
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
    public static void resetShip() {
        x = MainGame.screenWidth/ 2 - Character.getShipWidth()/2;
        y = MainGame.screenHeight;
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
    static void StopSpawning() {
        timer.cancel();
    }
}
