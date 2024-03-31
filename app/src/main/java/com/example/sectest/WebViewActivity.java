package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {
    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(v -> {
            // Go back to the main activity
            finish();
        });
        WebView simpleWebView = findViewById(R.id.web_view_xl);
        simpleWebView.loadUrl("https://www.wikihow.com/Play-Games-Well");
        WebSettings webSettings = simpleWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
