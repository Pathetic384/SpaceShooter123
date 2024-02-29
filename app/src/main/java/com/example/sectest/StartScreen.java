package com.example.sectest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        View parent = (View)findViewById(R.id.starting).getParent();

//        parent.post(new Runnable() {
//            @Override
//            public void run() {
//                width = parent.getWidth();
//                height = parent.getHeight();
//            }
//        });

        Button();
    }

    private void Button() {
        Button button1 = (Button) findViewById(R.id.play);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreen.this, MainGame.class));
                finish();
            }
        });
    }
}
