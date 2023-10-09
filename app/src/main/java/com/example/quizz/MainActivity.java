package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private Question[] questions = new Question[]{
        new Question(R.string.q_activity, false),
        new Question(R.string.q_find_resources, false),
        new Question(R.string.q_listener, true),
        new Question(R.string.q_resources, false),
        new Question(R.string.q_version, true)
};


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trueButton = findViewById(R.id.button_true);
        Button falseButton = findViewById(R.id.button_false);
        Button nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);

        displayQuestion(currentIndex);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness (true);
        }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness (false);
        }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1)%questions.length;
                setNextQuestion();
        }
        });
        setNextQuestion();
        displayQuestion(currentIndex);
    }
    private void displayQuestion(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questions.length) {
            questionTextView.setText(questions[questionIndex].getQuestionId());
        }
    }
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
    public int getQuestionId(){
        return questionId;
    }
    public boolean isTrueAnswer(){
        return trueAnswer;
    }
}

private void checkAnswerCorrectness(boolean userAnswer){
    boolean correctAnswer = questions[currentIndex].isTrueAnswer();
    int resultMessageId = 0;
    if (userAnswer == correctAnswer){
        resultMessageId = R.string.correct_answer;
    } else {
        resultMessageId = R.string.incorrect_answer;
    }
    Toast.makeText (this, resultMessageId, Toast.LENGTH_SHORT).show();
}
}