//package com.example.kquizzy;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class KpMediumQuiz extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_kp_medium_quiz);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

package com.example.quizzz;

import java.util.HashMap;
import java.util.Map;

public class KpMediumQuiz extends QuizActivity {

    @Override
    protected Map<String, String> getEmojiHints() {
        Map<String, String> hints = new HashMap<>();
        hints.put("🎭🎤", "PERFORMING ARTS");
        hints.put("🛹💨", "SKATEBOARDING");
        // Add other emoji hints for the Medium level
        return hints;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Set the category and difficulty level before starting the QuizActivity
//        Intent intent = new Intent(this, QuizActivity.class);
//        intent.putExtra("CATEGORY", "Kpop");
//        intent.putExtra("DIFFICULTY_LEVEL", "Medium");
//        startActivity(intent);
//    }
}
