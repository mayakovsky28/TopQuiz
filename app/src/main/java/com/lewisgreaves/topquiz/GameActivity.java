package com.lewisgreaves.topquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mQuestionText;
    private Button mAnswerOne;
    private Button mAnswerTwo;
    private Button mAnswerThree;
    private Button mAnswerFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionText = findViewById(R.id.activity_game_question_text);
        mAnswerOne = findViewById(R.id.activity_game_answer1_btn);
        mAnswerTwo = findViewById(R.id.activity_game_answer2_btn);
        mAnswerThree = findViewById(R.id.activity_game_answer3_btn);
        mAnswerFour = findViewById(R.id.activity_game_answer4_btn);

    }
}
