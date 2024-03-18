package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    Context context;
    static Bitmap bullet;
    static Bitmap bullet1;
    static Bitmap bullet2;
    static Bitmap bullet3;
    int x,y;

    public Bullet(Context context, int x, int y) {
        this.context = context;
        bullet1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        bullet2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet2);
        bullet3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet3);
        ChangeBullet(MainActivity.shipState);
        this.x = x;
        this.y = y;
    }

    public static void ChangeBullet(int i) {
        if(i==1) bullet = bullet1;
        if(i==2) bullet = bullet2;
        if(i==3) bullet = bullet3;
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

}
