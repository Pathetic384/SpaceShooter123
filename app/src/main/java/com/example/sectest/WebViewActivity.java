package com.example.sectest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView simpleWebView = findViewById(R.id.web_view_xl);
        simpleWebView.loadUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
    }
}
