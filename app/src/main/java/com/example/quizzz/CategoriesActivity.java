package com.example.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);

        // Adjust for system window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to handle category clicks
    public void openCategory(View view) {
        Intent intent;

        // Get the tag of the clicked category
        String categoryTag = view.getTag().toString();

        // Navigate to the appropriate LevelsActivity based on the tag
        switch (categoryTag) {
            case "kdrama":
                intent = new Intent(this, KdLevelsActivity.class);
                break;

            case "kmovie":
                intent = new Intent(this, KmLevelsActivity.class);
                break;

            case "kpop":
                intent = new Intent(this, KpLevelsActivity.class);
                break;

            default:
                return; // Do nothing if no valid tag is found
        }

        // Start the corresponding LevelsActivity
        startActivity(intent);
    }
}
