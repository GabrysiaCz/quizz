package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String QUIZ_TAG = "MainActivity";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button showAnswerButton;
    private TextView questionTextView;
    private TextView answersTextView;
    private int currentIndex = 0;
    private int correctAnswers = 0;
    private boolean answerWasShown = false;
    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, false),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, false),
            new Question(R.string.q_version, true)
    };

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    public class Question {
        private int questionId;
        private boolean trueAnswer;

        public Question(int questionId, boolean trueAnswer) {
            this.questionId = questionId;
            this.trueAnswer = trueAnswer;
        }

        public int getQuestionId() {
            return questionId;
        }

        public boolean isTrueAnswer() {
            return trueAnswer;
        }
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                correctAnswers++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
            if (currentIndex == 4) {
                answersTextView.setText("" + correctAnswers);
            }
        }
    }

    private TextView answerTextView;
    private String answer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);
        answersTextView = findViewById(R.id.answers_text_view);
        answerTextView = findViewById(R.id.answerTextView);
        //showAnswerButton = findViewById(R.id.showAnswerButton);
        Button promptButton = findViewById(R.id.promptButton);

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        /*showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent promptIntent = new Intent(MainActivity.this, PromptActivity.class);
                promptIntent.putExtra("answer", answer);
                startActivityForResult(promptIntent, PROMPT_ACTIVITY_REQUEST_CODE);
            }
        });*/

        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart() wywołane");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume() wywołane");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause() wywołane");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop() wywołane");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy() wywołane");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private static final int PROMPT_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}