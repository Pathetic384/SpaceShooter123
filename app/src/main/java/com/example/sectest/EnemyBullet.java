package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;

public class EnemyBullet {
    Context context;
    static Bitmap bullet;
    static Bitmap bullet1;
    static Bitmap bullet2;
    static Bitmap bullet3;
    int x,y;
    static int bulletVelocity = 15;

    public EnemyBullet(Context context, int x, int y) {
        this.context = context;
        bullet1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ebullet1);
        bullet2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ebullet1);
        bullet3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ebullet1);
        bullet = bullet1;
        this.x = x;
        this.y = y;

    }
    public static void ChangeBullet(int i) {
        if(i==1) {
            bullet = bullet1;
            bulletVelocity = 15;
        }
        if(i==2) {
            bullet = bullet2;
            bulletVelocity = 25;
        }
        if(i==3) {
            bullet = bullet3;
            bulletVelocity = 35;
        }
    }

    public Bitmap getBullet() {
        return bullet;
    }
    public int getBulletHeight() {
        return bullet.getHeight();
    }
    public int getBulletWidth() {
        return bullet.getWidth();
    }
    public void MovePattern() {
        y+=15;
    }

}
