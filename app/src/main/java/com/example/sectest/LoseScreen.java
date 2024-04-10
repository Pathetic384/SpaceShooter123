package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class LoseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_screen);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        TextView tv = findViewById(R.id.scoreText);

        ContentValues values = new ContentValues();
        values.put("score", String.valueOf(MainGame.score));
        values.put("name", MainActivity.playerName);
        values.put("location", MainActivity.playerLocation);
        values.put("time", currentDate + " " + currentTime);
        getContentResolver().insert(Util.URI, values);


        Cursor cursor = getContentResolver().query(Util.URI, new String[]{Util.KEY_ID, Util.KEY_SCORE, Util.KEY_NAME}, null, null, null, null);
        String scoreDisplay = "";

        if (cursor.moveToFirst()) {
            do {
                scoreDisplay += "Player: ";
                scoreDisplay += cursor.getString(2);
                scoreDisplay += ", ";
                scoreDisplay += "Score: ";
                scoreDisplay += cursor.getString(1);
                scoreDisplay += "\n";
                scoreDisplay += " Location: ";
                scoreDisplay += cursor.getString(3);
                scoreDisplay += "\n";
                scoreDisplay += " Datetime: ";
                scoreDisplay += cursor.getString(4);
                scoreDisplay += "\n";
                scoreDisplay += "\n";
            } while (cursor.moveToNext());
        }


        tv.setText(scoreDisplay);



        Button menu = (Button)findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character.bulletSound.stop();
                Intent intent = new Intent(LoseScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}