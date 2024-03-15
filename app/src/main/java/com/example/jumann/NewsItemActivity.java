package com.example.jumann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.io.File;

public class NewsItemActivity extends AppCompatActivity {
    private int newsId;
    private Item newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        // Retrieve the passed ID from the intent
        Intent intent = getIntent();
        newsId = intent.getIntExtra("news_id", -1);

        // Fetch the specific news item based on the ID from your data source
        DBHelperNews dbHelper = new DBHelperNews(this);
        newsItem = dbHelper.getItemById(newsId);
        dbHelper.close();

        // Use the newsItem to populate the views in the activity_news_item layout
        if (newsItem != null) {
            // Populate the views with the newsItem data
            // For example, set the title, description, and image of the news item
            TextView titleTextView = findViewById(R.id.titleTextView);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            ImageView newsImageView = findViewById(R.id.newsImageView);

            titleTextView.setText(newsItem.getTitle());
            descriptionTextView.setText(newsItem.getDescription());

            // Load the image using the image path
            String imagePath = newsItem.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Glide.with(this)
                            .load(newsItem.getImagePath())
                            .centerCrop()
                            .placeholder(R.drawable.img1)
                            .error(R.drawable.img1)
                            .into(newsImageView);
                } else {
                    // Image file does not exist, handle the error case
                    newsImageView.setImageResource(R.drawable.img1);
                }
            } else {
                // Image path is null or empty, handle the error case
                newsImageView.setImageResource(R.drawable.img1);
            }
        }
    }
}
