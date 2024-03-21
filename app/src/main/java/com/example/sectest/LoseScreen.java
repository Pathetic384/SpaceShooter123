package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_screen);
        TextView tv = findViewById(R.id.scoreText);
        //String scoreText = "Ur score" + String.valueOf(MainGame.score);

        //SharedPreferences score = getSharedPreferences("score", MODE_PRIVATE);
        //String lastScore = score.getString("score", "");
        ContentValues values = new ContentValues();
        values.put("score", String.valueOf(MainGame.score));
        getContentResolver().insert(Util.URI, values);

        //score.edit().putString("score", lastScore + "\nScore: " + MainGame.score).commit();

        Cursor cursor = getContentResolver().query(Util.URI, new String[]{Util.KEY_ID, Util.KEY_SCORE}, null, null, null, null);
        String scoreDisplay = "";
        if (cursor.moveToFirst()) {
            do {
                scoreDisplay += "Score: ";
                scoreDisplay += cursor.getString(1);
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