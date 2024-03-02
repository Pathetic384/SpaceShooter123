package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Character {
    Context context;
    Bitmap ship[] = new Bitmap[8];
    int x,y;
    int charFrame;

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
        charFrame = 0;
        resetShip();
    }

    public Bitmap getShip(int charFrame) {
        return ship[charFrame];
    }
    public int getShipHeight() {
        return ship[0].getHeight();
    }
    public int getShipWidth() {
        return ship[0].getWidth();
    }
    public void resetShip() {
        x = 200;
        y = 800;
    }
}
