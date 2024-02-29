package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Enemy {
    Context context;
    Bitmap enemyShip;
    int x,y;

    public Enemy(Context context) {
        this.context = context;
        enemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.enem1);

        resetEnemyShip();
    }

    public Bitmap getEnemyShip() {
        return enemyShip;
    }
    public int getEnemyShipHeight() {
        return enemyShip.getHeight();
    }
    public int getEnemyShipWidth() {
        return enemyShip.getWidth();
    }
    public void resetEnemyShip() {
        x = MainGame.screenWidth/2 - getEnemyShipWidth()/2;
        y = 0;
    }
}
