package com.example.quizzz;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class KdDifficultQuiz extends QuizActivity {

    protected TextView difficultyLevelTextView;

    @Override
    protected Map<String, String> getEmojiHints() {
        Map<String, String> hints = new HashMap<>();
        hints.put("🕳️💎", "DIAMOND HOLE");
        hints.put("🌪️🍃", "TWISTER WIND");
        // Add other emoji hints for the Difficult level
        return hints;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Set the category and difficulty level before starting the QuizActivity
//        Intent intent = new Intent(this, QuizActivity.class);
//        intent.putExtra("CATEGORY", "KDrama");
//        intent.putExtra("DIFFICULTY_LEVEL", "Difficult");
//        startActivity(intent);
//    }

}
