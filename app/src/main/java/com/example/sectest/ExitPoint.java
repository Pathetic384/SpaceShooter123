package com.example.sectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ExitPoint {
    Context context;
    Bitmap[] exit = new Bitmap[30];
    int frame;
    static int exitWidth, exitHeight;

    public ExitPoint(Context context) {
        this.context = context;
        exit[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_1);
        exit[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_2);
        exit[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_3);
        exit[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_4);
        exit[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_5);
        exit[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_6);
        exit[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_7);
        exit[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_8);
        exit[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_9);
        exit[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_10);
        exit[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_11);
        exit[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_12);
        exit[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_13);
        exit[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_14);
        exit[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_15);
        exit[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_16);
        exit[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_17);
        exit[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_18);
        exit[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_19);
        exit[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_20);
        exit[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_21);
        exit[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_22);
        exit[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_23);
        exit[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_24);
        exit[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_25);
        exit[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_26);
        exit[26] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_27);
        exit[27] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_28);
        exit[28] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_29);
        exit[29] = BitmapFactory.decodeResource(context.getResources(), R.drawable.convert_20);

        exitWidth = exit[0].getWidth();
        exitHeight = exit[0].getHeight();
        frame = 0;
    }

    public Bitmap getExit() {
        return exit[frame];
    }


}