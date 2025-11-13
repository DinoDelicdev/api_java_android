package com.example.apiconnectionapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView bodyTextView;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        titleTextView = findViewById(R.id.detailTextViewTitle);
        bodyTextView = findViewById(R.id.detailTextViewBody);
        closeButton = findViewById(R.id.myHalfButton);

        // Intent
        Intent intent = getIntent();


        String title = intent.getStringExtra("EXTRA_TITLE");
        String body = intent.getStringExtra("EXTRA_BODY");

        titleTextView.setText(title);
        bodyTextView.setText(body);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ovo zatvara activity detail
                finish();
            }
        });
    }
}