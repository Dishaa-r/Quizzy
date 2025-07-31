package com.example.quizzz;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply system window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Delay the transition to CategoriesActivity by 2 seconds
        new Handler().postDelayed(() -> {
            // Create an intent to navigate to CategoriesActivity
            Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(intent);
            finish(); // Finish MainActivity so that the user can't return to it using the back button
        }, 2000); // 2000 milliseconds = 2 seconds
    }
}
