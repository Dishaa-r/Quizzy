package com.example.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KdLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kd_levels);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listeners for each card
        findViewById(R.id.kdramaEasyCard).setOnClickListener(this::openCategory);
        findViewById(R.id.kdramaMediumCard).setOnClickListener(this::openCategory);
        findViewById(R.id.kdramaDifficultCard).setOnClickListener(this::openCategory);
    }

    public void openCategory(View view) {
        Intent intent;

        // Get the tag of the clicked level
//        String levelTag = view.getTag().toString();


        // Get the tag of the clicked level
        String levelTag = view.getTag() != null ? view.getTag().toString() : "";

        // Navigate to the appropriate quiz activity based on the tag
        switch (levelTag) {
            case "kdrama_easy":
                intent = new Intent(this, KdEasyQuiz.class);
                break;

            case "kdrama_medium":
                intent = new Intent(this, KdMediumQuiz.class);
                break;

            case "kdrama_difficult":
                intent = new Intent(this, KdDifficultQuiz.class);
                break;

            default:
                return; // Do nothing if no valid tag is found
        }

        // Start the corresponding quiz activity
        startActivity(intent);

    }
}