//package com.example.kquizzy;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class KdEasyQuiz extends AppCompatActivity {
//
//    private TextView triesTextView, emojiTextView, wordDisplayTextView, resultMessage, earnedCoinsDisplay;
//    private GridLayout letterGridLayout;
//    private Button restartButton, restartBtn, homeButton, nextRoundButton;
//
//    private int triesLeft = 5;
//    private int coins = 0;
//    private String currentWord = "";
//    private StringBuilder displayedWord;
//    private boolean isReplay = false; // Flag to determine if the game is replayed
//    private final Map<String, String> emojiHints = new HashMap<>();
//    private final Set<String> usedEmojis = new HashSet<>();
//
//    private View resultContainer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_kd_easy_quiz);
//
//        // Initialize UI components
//        triesTextView = findViewById(R.id.triesTextView);
//        emojiTextView = findViewById(R.id.emojiTextView);
//        wordDisplayTextView = findViewById(R.id.wordDisplayTextView);
//        letterGridLayout = findViewById(R.id.letterGridLayout);
//        restartButton = findViewById(R.id.restartButton);
//        restartBtn = findViewById(R.id.restartBtn);
//        homeButton = findViewById(R.id.homeBtn);
//        nextRoundButton = findViewById(R.id.nextRoundBtn);
//
//        // Result popup
//        resultContainer = findViewById(R.id.resultContainer);
//        resultMessage = findViewById(R.id.resultMessage);
//        earnedCoinsDisplay = findViewById(R.id.earnedCoinsDisplay);
//
//        // Add emoji hints for Kdrama Easy level
//        emojiHints.put("👑😭", "QUEEN OF TEARS");
//        emojiHints.put("🚖☮", "TAXI DRIVER");
//        emojiHints.put("🚀💻📈", "START UP");
//        emojiHints.put("🌊🗿🦸‍♂️", "LEGEND OF THE BLUE SEA");
//        emojiHints.put("📶", "SIGNAL");
//        emojiHints.put("❤", "HEART");
//        emojiHints.put(":)", "SMILE");
//        emojiHints.put("👑", "CROWN");
//        emojiHints.put("🦸‍", "MAN");
//        emojiHints.put("👧", "WOMEN");
//        emojiHints.put("🦑🎮", "SQUID GAME");
//        emojiHints.put("🐭🧠", "MOUSE");
//        emojiHints.put("🌸👿", "FLOWER OF EVIL");
//        emojiHints.put("✨🍉", "TWINKLING WATERMELON");
//        emojiHints.put("📚⚖️🏫", "LAW SCHOOL");
//
//        // Add button listeners
//        restartButton.setOnClickListener(v -> resetGame()); // Full reset
//        restartBtn.setOnClickListener(v -> replayCurrentGame()); // Replay current emoji word
//        homeButton.setOnClickListener(v -> finish()); // Go back to home
//        nextRoundButton.setOnClickListener(v -> {
//            resultContainer.setVisibility(View.GONE); // Hide popup
//            startGame(); // Start the next round without resetting coins
//        });
//
//        startGame();
//    }
//
//    private void startGame() {
//        // Reset variables
//        triesLeft = 5;
//        currentWord = getRandomWord();
//        displayedWord = new StringBuilder();
//
//        // Initialize displayed word (_ _ _ _)
//        for (char c : currentWord.toCharArray()) {
//            if (c == ' ') {
//                displayedWord.append("  "); // Double space for word separation
//            } else {
//                displayedWord.append("_ "); // Underscore followed by a space
//            }
//        }
//
//        // Update UI
//        triesTextView.setText("Tries Left: " + triesLeft);
//        emojiTextView.setText(getEmojiHint(currentWord));
//        wordDisplayTextView.setText(displayedWord.toString().trim());
//        resultContainer.setVisibility(View.GONE); // Hide popup
//
//        // Generate letter buttons
//        generateLetterButtons();
//    }
//
//    private void generateLetterButtons() {
//        letterGridLayout.removeAllViews(); // Clear existing buttons
//
//        for (char letter = 'A'; letter <= 'Z'; letter++) {
//            Button button = new Button(this);
//            button.setText(String.valueOf(letter));
//            button.setOnClickListener(this::handleGuess);
//
//            // Set custom layout parameters for smaller button width
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
//            params.width = (int) (80 * getResources().getDisplayMetrics().density);
//
//            button.setLayoutParams(params);
//            letterGridLayout.addView(button);
//        }
//    }
//
//    private void handleGuess(View view) {
//        Button guessedButton = (Button) view;
//        char guessedLetter = guessedButton.getText().charAt(0);
//        guessedButton.setEnabled(false); // Disable button after guessing
//
//        boolean correctGuess = false;
//
//        // Update displayed word if the guess is correct
//        for (int i = 0; i < currentWord.length(); i++) {
//            if (currentWord.charAt(i) == guessedLetter) {
//                // Replace the corresponding underscore with the guessed letter
//                displayedWord.setCharAt(i * 2, guessedLetter); // Each letter is at index i*2
//                correctGuess = true;
//            }
//        }
//
//        // Update word display
//        wordDisplayTextView.setText(displayedWord.toString().trim());
//
//        if (correctGuess) {
//            // Check if the user has guessed the entire word
//            if (!displayedWord.toString().contains("_")) {
//                showPopup("You Won!", 10);
//            }
//        } else {
//            // If incorrect guess, decrement tries
//            triesLeft--;
//            triesTextView.setText("Tries Left: " + triesLeft);
//
//            if (triesLeft == 0) {
//                showPopup("Try Again!", -5);
//            }
//        }
//    }
//
//    private void showPopup(String message, int coinChange) {
//        if (!isReplay) { // Only update coins if not in replay mode
//            coins = Math.max(0, coins + coinChange); // Update coins (not below 0)
//        }
//
//        // Reset replay flag after the popup is shown
//        isReplay = false;
//
//        // Update UI
//        earnedCoinsDisplay.setText(String.valueOf(coins));
//        resultMessage.setText(message);
//        resultContainer.setVisibility(View.VISIBLE); // Show popup
//        blockAllButtons(); // Disable all buttons to prevent further guesses
//    }
//
//
//    private void blockAllButtons() {
//        for (int i = 0; i < letterGridLayout.getChildCount(); i++) {
//            letterGridLayout.getChildAt(i).setEnabled(false);
//        }
//    }
//
//    private void resetGame() {
//        // Full reset: Coins = 0, new game
//        coins = 0;
//        startGame();
//    }
//
//    private void replayCurrentGame() {
//        isReplay = true;
//        // Replay current emoji word: keep coins, reset tries
//        triesLeft = 5;
//        displayedWord = new StringBuilder();
//
//        for (char c : currentWord.toCharArray()) {
//            if (c == ' ') {
//                displayedWord.append("  "); // Double space for word separation
//            } else {
//                displayedWord.append("_ "); // Underscore followed by a space
//            }
//        }
//
//        // Update UI
//        triesTextView.setText("Tries Left: " + triesLeft);
//        wordDisplayTextView.setText(displayedWord.toString().trim());
//        resultContainer.setVisibility(View.GONE); // Hide popup
//        generateLetterButtons(); // Reset letter buttons
//    }
//
//    private String getRandomWord() {
//        Object[] keys = emojiHints.keySet().toArray();
//        String randomKey;
//        do {
//            randomKey = (String) keys[(int) (Math.random() * keys.length)];
//        } while (usedEmojis.contains(randomKey));
//
//        usedEmojis.add(randomKey); // Mark this emoji as used
//        if (usedEmojis.size() == emojiHints.size()) {
//            usedEmojis.clear(); // Reset when all emojis are used
//        }
//        return emojiHints.get(randomKey);
//    }
//
//    private String getEmojiHint(String word) {
//        for (Map.Entry<String, String> entry : emojiHints.entrySet()) {
//            if (entry.getValue().equalsIgnoreCase(word)) {
//                return entry.getKey();
//            }
//        }
//        return "";
//    }
//}
package com.example.quizzz;

import java.util.HashMap;
import java.util.Map;

public class KdEasyQuiz extends QuizActivity {

    @Override
    protected Map<String, String> getEmojiHints() {
        Map<String, String> hints = new HashMap<>();
        hints.put("👑😭", "QUEEN OF TEARS");
        hints.put("🚖☮", "TAXI DRIVER");
        // Add other emoji hints for the Easy level
        return hints;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Set the category and difficulty level before starting the QuizActivity
//        Intent intent = new Intent(this, QuizActivity.class);
//        intent.putExtra("CATEGORY", "KDrama");
//        intent.putExtra("DIFFICULTY_LEVEL", "Easy");
//        startActivity(intent);
//    }
}
