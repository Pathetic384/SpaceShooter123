package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EnemyBullet {
    Context context;
    Bitmap bullet;
    int x,y;

    public EnemyBullet(Context context, int x, int y) {
        this.context = context;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.ebullet1);
        this.x = x;
        this.y = y;
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
