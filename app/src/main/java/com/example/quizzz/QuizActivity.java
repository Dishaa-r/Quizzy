package com.example.quizzz;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class QuizActivity extends AppCompatActivity {

    protected TextView triesTextView, emojiTextView, wordDisplayTextView, resultMessage, earnedCoinsDisplay;
    protected GridLayout letterGridLayout;
    protected Button restartButton, restartBtn, homeBtn, nextRoundButton, homeBtnn, exitBtn, hintButton;

    protected int triesLeft = 5;
    protected int coins = 0;
    protected String currentWord = "";
    protected StringBuilder displayedWord;
    protected boolean isReplay = false; // Flag to determine if the game is replayed
    protected Map<String, String> emojiHints = new HashMap<>();
    protected Set<String> usedEmojis = new HashSet<>();
    protected View resultContainer;

    // Abstract method to be implemented in subclasses for setting level-specific data
    protected abstract Map<String, String> getEmojiHints();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize UI components
        triesTextView = findViewById(R.id.triesTextView);
        emojiTextView = findViewById(R.id.emojiTextView);
        wordDisplayTextView = findViewById(R.id.wordDisplayTextView);
        letterGridLayout = findViewById(R.id.letterGridLayout);
        restartButton = findViewById(R.id.restartButton);
        restartBtn = findViewById(R.id.restartBtn);
        homeBtn = findViewById(R.id.homeBtn);
        homeBtnn = findViewById(R.id.homeBtnn);
        exitBtn = findViewById(R.id.exitBtn);
        nextRoundButton = findViewById(R.id.nextRoundBtn);

        // Result popup
        resultContainer = findViewById(R.id.resultContainer);
        resultMessage = findViewById(R.id.resultMessage);
        earnedCoinsDisplay = findViewById(R.id.earnedCoinsDisplay);

        //Hint
        hintButton = findViewById(R.id.hintButton);
        hintButton.setOnClickListener(v -> useHint());


        // Initialize emoji hints from the subclass
        emojiHints = getEmojiHints();

        // Add button listeners
        restartButton.setOnClickListener(v -> resetGame()); // Full reset - cardLayout
        restartBtn.setOnClickListener(v -> replayCurrentGame()); // Replay current emoji word - pop msg
        homeBtn.setOnClickListener(v -> finish()); // Go back to home - pop msg
        homeBtnn.setOnClickListener(v ->finish()); // Go back to home - cardLayout
        exitBtn.setOnClickListener(v -> finishAffinity()); // Close the app - cardLayout
        nextRoundButton.setOnClickListener(v -> { // Go to next round
            resultContainer.setVisibility(View.GONE); // Hide popup
            startGame(); // Start the next round without resetting coins
        });

        startGame();
    }

    private void startGame() {
        // Reset variables
        triesLeft = 5;
        currentWord = getRandomWord();
        displayedWord = new StringBuilder();

        // Initialize displayed word (_ _ _ _)
        for (char c : currentWord.toCharArray()) {
            if (c == ' ') {
                displayedWord.append("  "); // Double space for word separation
            } else {
                displayedWord.append("_ "); // Underscore followed by a space
            }
        }


        hintButton.setVisibility(View.VISIBLE); // Reset hint button visibility
        updateHintButtonStyle(coins >= 5);

        // Update UI
        triesTextView.setText("Tries Left: " + triesLeft);
        emojiTextView.setText(getEmojiHint(currentWord));
        wordDisplayTextView.setText(displayedWord.toString().trim());
        resultContainer.setVisibility(View.GONE); // Hide popup

        // Generate letter buttons
        generateLetterButtons();
        enableAllLetterButtons();

        // Disable hint if not enough coins
        if (coins < 5) {
            updateHintButtonStyle(false);
        }
    }

    private void enableAllLetterButtons() {
        for (int i = 0; i < letterGridLayout.getChildCount(); i++) {
            View child = letterGridLayout.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(true);
            }
        }
    }

    private void generateLetterButtons() {
        letterGridLayout.removeAllViews(); // Clear existing buttons

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = new Button(this);
            button.setText(String.valueOf(letter));
            button.setOnClickListener(this::handleGuess);

            // Set custom layout parameters for smaller button width
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = (int) (60 * getResources().getDisplayMetrics().density);

            button.setLayoutParams(params);
            letterGridLayout.addView(button);
        }
        enableAllLetterButtons();

    }

    private void handleGuess(View view) {
        Button guessedButton = (Button) view;
        char guessedLetter = guessedButton.getText().charAt(0);
        guessedButton.setEnabled(false); // Disable this button

        boolean correctGuess = false;

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guessedLetter) {
                displayedWord.setCharAt(i * 2, guessedLetter);
                correctGuess = true;
            }
        }

        wordDisplayTextView.setText(displayedWord.toString().trim());

        if (correctGuess) {
            disableLetterButton(guessedLetter); // ← Add this here
            if (!displayedWord.toString().contains("_")) {
                showPopup("You Won!", 10);
            }
        } else {
            triesLeft--;
            triesTextView.setText("Tries Left: " + triesLeft);
            if (triesLeft == 0) {
                showPopup("Try Again!", -5);
            }
        }
    }

    private void disableLetterButton(char letter) {
        for (int i = 0; i < letterGridLayout.getChildCount(); i++) {
            View child = letterGridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (button.getText().toString().equalsIgnoreCase(String.valueOf(letter))) {
                    button.setEnabled(false);
                    break;
                }
            }
        }
    }

    private void useHint() {
        if (coins < 5) {
            Toast.makeText(this, "You don’t have enough coins for a hint!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find all missing letter indices
        List<Integer> missingIndexes = new ArrayList<>();
        for (int i = 0; i < currentWord.length(); i++) {
            if (displayedWord.charAt(i * 2) == '_') {
                missingIndexes.add(i);
            }
        }

        // Reveal a random missing letter
        if (!missingIndexes.isEmpty()) {
            int randomIndex = missingIndexes.get(new Random().nextInt(missingIndexes.size()));
            char revealedChar = currentWord.charAt(randomIndex);

            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == revealedChar) {
                    displayedWord.setCharAt(i * 2, revealedChar);
                }
            }

            wordDisplayTextView.setText(displayedWord.toString().trim());
            coins -= 5;
            updateCoinDisplay();
            disableLetterButton(revealedChar); // Disable the revealed letter button

            if (coins < 5) {
                updateHintButtonStyle(false);
            }
        }
    }

    private void updateCoinDisplay() {
        String styledText = "Coins Earned: " + coins;
        earnedCoinsDisplay.setText(styledText);
        earnedCoinsDisplay.setTextColor(getColor(R.color.black));
        earnedCoinsDisplay.setTextSize(20);
        earnedCoinsDisplay.setTypeface(null, android.graphics.Typeface.BOLD);
    }

    private void updateHintButtonStyle(boolean enabled) {
        hintButton.setEnabled(enabled);
        if (enabled) {
            hintButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6750A3")));
        } else {
            hintButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }
        hintButton.setTextColor(Color.parseColor("#FDD500"));
    }

    private void showPopup(String message, int coinChange) {
        if (!isReplay) {
            coins = Math.max(0, coins + coinChange);
        }
        isReplay = false;

        updateCoinDisplay(); // <-- This alone is enough

        resultMessage.setText(message);
        resultMessage.setTextSize(26);
        resultContainer.setVisibility(View.VISIBLE);
        blockAllButtons();

        // Optionally disable hint after game ends
        updateHintButtonStyle(true);
    }

    private void blockAllButtons() {
        for (int i = 0; i < letterGridLayout.getChildCount(); i++) {
            letterGridLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void resetGame() {
        // Full reset: Coins = 0, new game
        coins = 0;
        startGame();
    }

    private void replayCurrentGame() {
        isReplay = true;
        // Replay current emoji word: keep coins, reset tries
        triesLeft = 5;
        displayedWord = new StringBuilder();

        for (char c : currentWord.toCharArray()) {
            if (c == ' ') {
                displayedWord.append("  "); // Double space for word separation
            } else {
                displayedWord.append("_ "); // Underscore followed by a space
            }
        }


        // Update UI
        triesTextView.setText("Tries Left: " + triesLeft);
        wordDisplayTextView.setText(displayedWord.toString().trim());
        resultContainer.setVisibility(View.GONE); // Hide popup
        generateLetterButtons(); // Reset letter buttons
        enableAllLetterButtons();

    }

    private String getRandomWord() {
        Object[] keys = emojiHints.keySet().toArray();
        String randomKey;
        do {
            randomKey = (String) keys[(int) (Math.random() * keys.length)];
        } while (usedEmojis.contains(randomKey));

        usedEmojis.add(randomKey); // Mark this emoji as used
        if (usedEmojis.size() == emojiHints.size()) {
            usedEmojis.clear(); // Reset when all emojis are used
        }
        return emojiHints.get(randomKey);
    }

    private String getEmojiHint(String word) {
        for (Map.Entry<String, String> entry : emojiHints.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(word)) {
                return entry.getKey();
            }
        }
        return "";
    }
}
