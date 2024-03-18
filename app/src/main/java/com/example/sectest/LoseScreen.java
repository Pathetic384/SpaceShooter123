package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences score = getSharedPreferences("score", MODE_PRIVATE);
        String lastScore = score.getString("score", "");

        //score.edit().putString("score", lastScore + "\nScore: " + MainGame.score).commit();
        tv.setText(score.getString("score", ""));

        Button menu = (Button)findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoseScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}