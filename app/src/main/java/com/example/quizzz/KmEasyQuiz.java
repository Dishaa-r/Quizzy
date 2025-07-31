package com.example.quizzz;
import java.util.HashMap;
import java.util.Map;

public class KmEasyQuiz extends QuizActivity {

    @Override
    protected Map<String, String> getEmojiHints() {
        Map<String, String> hints = new HashMap<>();
        hints.put("🍔🍟", "FAST FOOD");
        hints.put("🌞🏖️", "BEACH VACATION");
        // Add other emoji hints for the Easy level
        return hints;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Set the category and difficulty level before starting the QuizActivity
//        Intent intent = new Intent(this, QuizActivity.class);
//        intent.putExtra("CATEGORY", "KMovie");
//        intent.putExtra("DIFFICULTY_LEVEL", "Easy");
//        startActivity(intent);
//    }
}
