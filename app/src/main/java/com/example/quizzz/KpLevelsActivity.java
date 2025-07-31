package com.example.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KpLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kp_levels);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listeners for each card
        findViewById(R.id.kpopEasyCard).setOnClickListener(this::openCategory);
        findViewById(R.id.kpopMediumCard).setOnClickListener(this::openCategory);
        findViewById(R.id.kpopDifficultCard).setOnClickListener(this::openCategory);
    }

    public void openCategory(View view) {
        Intent intent;

        // Get the tag of the clicked level
//        String levelTag = view.getTag().toString();


        // Get the tag of the clicked level
        String levelTag = view.getTag() != null ? view.getTag().toString() : "";

        // Navigate to the appropriate quiz activity based on the tag
        switch (levelTag) {
            case "kpop_easy":
                intent = new Intent(this, KpEasyQuiz.class);
                break;

            case "kpop_medium":
                intent = new Intent(this, KpMediumQuiz.class);
                break;

            case "kpop_difficult":
                intent = new Intent(this, KpDifficultQuiz.class);
                break;

            default:
                return; // Do nothing if no valid tag is found
        }

        // Start the corresponding quiz activity
        startActivity(intent);

    }
}