package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sprites
        Button ship1 = (Button) findViewById(R.id.ship1);
        ship1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character.ChangeShip(1);
                Bullet.ChangeBullet(1);
            }
        });
        Button ship2 = (Button) findViewById(R.id.ship2);
        ship1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character.ChangeShip(2);
                Bullet.ChangeBullet(2);
            }
        });
        Button ship3 = (Button) findViewById(R.id.ship3);
        ship1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character.ChangeShip(3);
                Bullet.ChangeBullet(3);
            }
        });
        //levelel
        Button lvl1 = (Button) findViewById(R.id.easy);
        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enemy.ChangeShip(1);
                EnemyBullet.ChangeBullet(1);
                MainGame.ChangeBackground(1);
            }
        });
        Button lvl2 = (Button) findViewById(R.id.normal);
        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enemy.ChangeShip(2);
                EnemyBullet.ChangeBullet(2);
                MainGame.ChangeBackground(2);
            }
        });
        Button lvl3 = (Button) findViewById(R.id.hard);
        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enemy.ChangeShip(2);
                EnemyBullet.ChangeBullet(2);
                MainGame.ChangeBackground(3);
            }
        });
    }

    public void StartGame(View view) {
        MainGame mg = new MainGame(this);
        setContentView(mg);
    }
}