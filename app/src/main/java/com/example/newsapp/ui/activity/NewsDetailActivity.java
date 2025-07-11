package com.example.newsapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

public class NewsDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titleTextView, descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        imageView = findViewById(R.id.detailImageView);
        titleTextView = findViewById(R.id.detailTitleTextView);
        descTextView = findViewById(R.id.detailDescTextView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String imageUrl = intent.getStringExtra("image");
        String description = intent.getStringExtra("description");

        titleTextView.setText(title);
        descTextView.setText(description);
        Glide.with(this).load(imageUrl).into(imageView);
    }
}
