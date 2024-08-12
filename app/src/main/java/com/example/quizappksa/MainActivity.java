package com.example.quizappksa;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView, scoreTextView, congratulationsTextView, totalQuestionsTextView;
    private RadioGroup answersRadioGroup;
    private Button nextButton, restartButton;

    private String[] questions = {
            "Question 1: Capital of India?",
            "Question 2: Largest state by area?",
            "Question 3: National sport of India?",
            "Question 4: Currency of India?",
            "Question 5: National bird of India?",
            "Question 6: Official language of India?",
            "Question 7: First Prime Minister of India?"
    };

    private String[][] answers = {
            {"New Delhi", "Mumbai", "Kolkata", "Chennai"},
            {"Uttar Pradesh", "Madhya Pradesh", "Rajasthan", "Bihar"},
            {"Cricket", "Football", "Hockey", "Badminton"},
            {"Rupee", "Dollar", "Euro", "Pound"},
            {"Peacock", "Parrot", "Sparrow", "Eagle"},
            {"Hindi", "English", "Tamil", "Bengali"},
            {"Jawaharlal Nehru", "Gandhi", "Patel", "Azad"}
    };

    private int[] correctAnswers = {0, 2, 2, 0, 0, 0, 0}; // Index of correct answers
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        congratulationsTextView = findViewById(R.id.congratulationsTextView);
        restartButton = findViewById(R.id.restartButton);
        totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);

        totalQuestionsTextView.setText("Total Questions: " + questions.length);
        loadQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = answersRadioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedAnswer = findViewById(selectedId);
                    int selectedAnswerIndex = answersRadioGroup.indexOfChild(selectedAnswer);
                    if (selectedAnswerIndex == correctAnswers[currentQuestionIndex]) {
                        score++;
                    }
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        fadeOutQuestion();
                    } else {
                        showResult();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = 0;
                score = 0;
                restartButton.setVisibility(View.GONE);
                congratulationsTextView.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                scoreTextView.setText("");
                loadQuestion();
            }
        });
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionTextView.setText(questions[currentQuestionIndex]);
            String[] options = answers[currentQuestionIndex];
            for (int i = 0; i < options.length; i++) {
                RadioButton radioButton = (RadioButton) answersRadioGroup.getChildAt(i);
                radioButton.setText(options[i]);
            }
            answersRadioGroup.clearCheck(); // Clear previous selection
        }
    }

    private void fadeOutQuestion() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500); // 0.5 seconds
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No action needed
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadQuestion();
                fadeInQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No action needed
            }
        });
        questionTextView.startAnimation(fadeOut);
    }

    private void fadeInQuestion() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500); // 0.5 seconds
        questionTextView.startAnimation(fadeIn);
    }

    private void showResult() {
        scoreTextView.setText("Score: " + score + "/" + questions.length);
        nextButton.setVisibility(View.GONE);
        restartButton.setVisibility(View.VISIBLE);
        if (score == questions.length) {
            congratulationsTextView.setVisibility(View.VISIBLE);
        }
    }
}
