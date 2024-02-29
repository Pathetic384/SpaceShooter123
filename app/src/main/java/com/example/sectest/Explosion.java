package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Context context;
    Bitmap[] explosion = new Bitmap[8];
    int x,y;
    int frame;
    static int exWidth, exHeight;

    public Explosion(Context context, int x, int y) {
        this.context = context;
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex5);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex6);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex7);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex8);
        this.x = x;
        this.y = y;
        exWidth = explosion[0].getWidth();
        exHeight = explosion[0].getHeight();
        frame = 0;
    }

    public Bitmap getExplosion() {
        return explosion[frame];
    }


}
