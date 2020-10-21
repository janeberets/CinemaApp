package com.example.vkinfo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vkinfo.R;

public class SeancesActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        textView = findViewById(R.id.tv_display);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String enteredText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText(enteredText);
        }
    }
}