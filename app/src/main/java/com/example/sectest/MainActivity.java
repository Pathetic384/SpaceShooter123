package com.example.sectest;
import static androidx.core.content.ContextCompat.getSystemService;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String[] difficultyLevels = {"Easy", "Normal", "Hard"};
    private Intent serviceIntent;
    private SensorManager sensorManager;
    private Sensor sensor;
    public static AudioManager audioManager;
    public static boolean paused = false;
    public static int currentLvl = 1;
    public static int shipState = 1;
    public static float xTilt, yTilt;
    public static int gameMode = 1;
    public static boolean ini = false;
    MediaPlayer gyu;
    Spinner spinner;
    Spinner playerList;
    public static String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        //ViewPager2 for ship selection
        ViewPager2 viewPager2 = findViewById(R.id.ship_view_pager);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Thay đổi shipState dựa trên trang được chọn
                switch (position) {
                    case 0:
                        shipState = 1;
                        break;
                    case 1:
                        shipState = 2;
                        break;
                    case 2:
                        shipState = 3;
                        break;
                }
            }

            // triggered when there is
            // scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // Difficulty spinner
        spinner = (Spinner) findViewById(R.id.difficulty_spinner);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(difficultyAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        currentLvl = 1;
                        Enemy.ChangeShip(1);
                        EnemyBullet.ChangeBullet(1);
                        break;
                    case 1:
                        currentLvl = 2;
                        Enemy.ChangeShip(2);
                        EnemyBullet.ChangeBullet(2);
                        break;
                    case 2:
                        currentLvl = 3;
                        Enemy.ChangeShip(2);
                        EnemyBullet.ChangeBullet(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Start the time ticking service
        serviceIntent = new Intent(this, GameService.class);

        // Webview button
        Button webViewButton = (Button) findViewById(R.id.btn_guide);
        webViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        // Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        gyu = MediaPlayer.create(MainActivity.this, R.raw.gyu);

        SeekBar seekBar = (SeekBar)findViewById(R.id.volume);
            seekBar.setMax(maxVolume);
            seekBar.setProgress(currentVolume);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        gyu.start();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) gameMode = 1;
                else if(position == 1) gameMode = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> mode = new ArrayList<>();
        mode.add("Normal Mode");
        mode.add("Tilting Mode");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mode);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

//        //sprites
//        Button ship1 = (Button) findViewById(R.id.ship1);
//        ship1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shipState = 1;
//            }
//        });
//        Button ship2 = (Button) findViewById(R.id.ship2);
//        ship2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shipState = 2;
//            }
//        });
//        Button ship3 = (Button) findViewById(R.id.ship3);
//        ship3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shipState = 3;
//            }
//        });
//        //levelel
//        Button lvl1 = (Button) findViewById(R.id.easy);
//        lvl1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Easy Mode", Toast.LENGTH_SHORT).show();
//                currentLvl = 1;
//                Enemy.ChangeShip(1);
//                EnemyBullet.ChangeBullet(1);
//            }
//        });
//        Button lvl2 = (Button) findViewById(R.id.normal);
//        lvl2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Normal Mode", Toast.LENGTH_SHORT).show();
//                currentLvl = 2;
//                Enemy.ChangeShip(2);
//                EnemyBullet.ChangeBullet(2);
//            }
//        });
//        Button lvl3 = (Button) findViewById(R.id.hard);
//        lvl3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Hard Mode", Toast.LENGTH_SHORT).show();
//                currentLvl = 3;
//                Enemy.ChangeShip(2);
//                EnemyBullet.ChangeBullet(2);
//            }
//        });

        ArrayList<String> players = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] col = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor contactCursor = getContentResolver().query(uri, col, null, null, null);

        if (contactCursor.moveToFirst()) {
            do {
                players.add(contactCursor.getString(1));
            } while(contactCursor.moveToNext());
        }

        ArrayAdapter<String> playersArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, players);
        playersArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playerList = (Spinner) findViewById(R.id.playerList);
        playerList.setAdapter(playersArrayAdapter);
    }

    public void StartGame(View view) {
        playerName = (String) playerList.getSelectedItem();
        ini = true;
        gyu.stop();
        MainGame mg = new MainGame(this);
        setContentView(mg);
        // Check permission before starting service
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED) {
            startForegroundService(serviceIntent);
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.FOREGROUND_SERVICE}, 1);
        }
    }

    protected void onPause() {
        gyu.pause();
        if(ini) Character.bulletSound.stop();
        sensorManager.unregisterListener(this);
        Intent bIntent = new Intent(MainActivity.this, GameBroadcastReceiver.class);
        bIntent.setAction("gameCast");
        bIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(bIntent);

        super.onPause();
        paused = true;
    }

    protected void onResume() {
        gyu.start();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if(paused) {
            paused = false;
            Intent bIntent = new Intent(MainActivity.this, GameBroadcastReceiver.class);
            bIntent.setAction("gameCast");
            bIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(bIntent);
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the service when MainActivity is destroyed
        stopService(serviceIntent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xTilt = event.values[0];
        yTilt = event.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}