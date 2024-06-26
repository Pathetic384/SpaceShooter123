package com.example.sectest;

import android.Manifest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
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
    MediaPlayer punch;
    MediaPlayer girlfront;
    Spinner spinner;
    Spinner playerList;
    public static String playerName;
    public static LocationManager locationManager;
    public double playerLong;
    public double playerLat;

    public static String playerLocation;
    private static final int REQUEST_PERMISSIONS = 1;
    boolean allPermissionsGranted = false;

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private NominatimService nominatimService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS};
        while (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            allPermissionsGranted = true;
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                }
            }
        }

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


        // Difficulty button with popup menu for selecting difficulty level (easy, normal, hard)
        TextView difficultyText = (TextView) findViewById(R.id.difficulty_text);
        Button difficultyButton = (Button) findViewById(R.id.difficulty_button);
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu difficultyMenu = new PopupMenu(MainActivity.this, difficultyButton);
                difficultyMenu.getMenuInflater().inflate(R.menu.difficulty_menu, difficultyMenu.getMenu());
                difficultyMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.difficulty_easy:
                                currentLvl = 1;
                                Enemy.ChangeShip(1);
                                EnemyBullet.ChangeBullet(1);
                                difficultyText.setText("Difficulty: Easy");
                                break;
                            case R.id.difficulty_medium:
                                currentLvl = 2;
                                Enemy.ChangeShip(2);
                                EnemyBullet.ChangeBullet(2);
                                difficultyText.setText("Difficulty: Medium");
                                break;
                            case R.id.difficulty_hard:
                                currentLvl = 3;
                                Enemy.ChangeShip(2);
                                EnemyBullet.ChangeBullet(2);
                                difficultyText.setText("Difficulty: Hard");
                                break;
                        }
                        return true;
                    }
                });
                difficultyMenu.show();
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

        //audio
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        punch = MediaPlayer.create(MainActivity.this, R.raw.punch);
        girlfront = MediaPlayer.create(MainActivity.this, R.raw.girlfront);

        SeekBar seekBar = (SeekBar) findViewById(R.id.volume);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);

        //update volume
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
        punch.start();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) gameMode = 1;
                else if (position == 1) gameMode = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //select gamemode
        ArrayList<String> mode = new ArrayList<>();
        mode.add("Normal Mode");
        mode.add("Tilting Mode");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mode);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);


        //get username
        ArrayList<String> players = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] col = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor contactCursor = getContentResolver().query(uri, col, null, null, null);

        if (contactCursor.moveToFirst()) {
            do {
                players.add(contactCursor.getString(1));
            } while (contactCursor.moveToNext());
        }

        //select username
        ArrayAdapter<String> playersArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, players);
        playersArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playerList = (Spinner) findViewById(R.id.playerList);
        playerList.setAdapter(playersArrayAdapter);

        //location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);

    }

    public void StartGame(View view) {
        playerName = (String) playerList.getSelectedItem();
        double[] loc = getPlayerLocation();
        playerLocation = "lat: " + loc[0] + ", long: " + loc[1];
        getActualAddress();
        ini = true;
        punch.stop();
        girlfront.start();
        MainGame mg = new MainGame(this);
        setContentView(mg);
        // Check permission before starting service
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED) {
            startForegroundService(serviceIntent);
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, 1);
        }
    }

    protected void onPause() {
        //pause music & send sroadcast intent
        punch.pause();
        girlfront.pause();
        if (ini) Character.bulletSound.stop();
        sensorManager.unregisterListener(this);
        Intent bIntent = new Intent(MainActivity.this, GameBroadcastReceiver.class);
        bIntent.setAction("gameCast");
        bIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(bIntent);

        super.onPause();
        paused = true;
    }

    protected void onResume() {
        //resume music and send broadcast intent
        punch.start();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (paused) {
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
        girlfront.stop();
        super.onDestroy();

        // Stop the service when MainActivity is destroyed
        stopService(serviceIntent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //get tilting value
        xTilt = event.values[0];
        yTilt = event.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //update long lat
    public void onLocationChanged(Location location) {
        playerLong = location.getLongitude();
        playerLat = location.getLatitude();
    }

    //get long lat
    public double[] getPlayerLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double[] latLong = new double[2];
        onLocationChanged(location);
        latLong[0] = playerLat;
        latLong[1] = playerLong;
        return latLong;
    }


    //request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

        }
    }

    //get irl location
    public void getActualAddress() {


        nominatimService = NominatimClient.getClient().create(NominatimService.class);
        double latitude = playerLat;
        double longitude = playerLong;

        Call<NominatimResponse> call = nominatimService.reverseGeocode("json", latitude, longitude);
        call.enqueue(new Callback<NominatimResponse>() {
            @Override
            public void onResponse(Call<NominatimResponse> call, Response<NominatimResponse> response) {
                if (response.isSuccessful()) {
                    NominatimResponse nominatimResponse = response.body();
                    if (nominatimResponse != null) {
                        String address = nominatimResponse.getDisplay_name();
                        playerLocation = address;
                        Log.d("Address", address);

                    }
                } else {
                    Log.e("Error", "Response was not successful.");
                }
            }

            @Override
            public void onFailure(Call<NominatimResponse> call, Throwable t) {
                Log.e("Error", "Failed to get response from server: " + t.getMessage());
            }
        });

    }
}