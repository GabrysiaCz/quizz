package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;


public class PromptActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView answerTextView;
    private Button showAnswerButton;
    private boolean correctAnswer;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "com.example.quiz.answerShown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        // Inicjalizacja widoków
        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);
        showAnswerButton = findViewById(R.id.showAnswerButton);

        String question = "Czy na pewno chcesz zobaczyć odpowiedź?";
        questionTextView.setText(question);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
    }
        private void setAnswerShownResult(boolean answerWasShown){
            Intent resultIntent = new Intent();
            resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
            setResult(RESULT_OK, resultIntent);
        }

    }