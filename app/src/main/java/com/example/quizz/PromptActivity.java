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

        // Obsługa przycisku "Pokaż odpowiedź"
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int questionId = getIntent().getIntExtra("questionId", 0);
                String answerIdentifier = "answer_prefix_" + questionId;
                int answerResId = getResources().getIdentifier(answerIdentifier, "string", getPackageName());

                if (answerResId != 0) {
                    String answerText = getResources().getString(answerResId);

                    answerTextView.setText(answerText);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("answer", answerText);

                    setResult(RESULT_OK, resultIntent);
                }
            }
        });


    }
}