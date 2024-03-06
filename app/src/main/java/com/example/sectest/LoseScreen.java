package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        String scoreText = "Ur score" + String.valueOf(MainGame.score);
        tv.setText(scoreText);

        Button menu = (Button)findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoseScreen.this, MainActivity.class));
            }
        });
    }


}