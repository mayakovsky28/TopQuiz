package com.lewisgreaves.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisgreaves.topquiz.R;
import com.lewisgreaves.topquiz.model.Question;
import com.lewisgreaves.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = GameActivity.class.getCanonicalName().concat("BUNDLE_EXTRA_SCORE");

    private TextView mQuestionText;
    private Button mAnswerOne;
    private Button mAnswerTwo;
    private Button mAnswerThree;
    private Button mAnswerFour;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        mNumberOfQuestions = 4;

        mQuestionText = findViewById(R.id.activity_game_question_text);
        mAnswerOne = findViewById(R.id.activity_game_answer1_btn);
        mAnswerTwo = findViewById(R.id.activity_game_answer2_btn);
        mAnswerThree = findViewById(R.id.activity_game_answer3_btn);
        mAnswerFour = findViewById(R.id.activity_game_answer4_btn);

        mAnswerOne.setTag(0);
        mAnswerTwo.setTag(1);
        mAnswerThree.setTag(2);
        mAnswerFour.setTag(3);

        for (View button : new View[] {mAnswerOne, mAnswerTwo, mAnswerThree, mAnswerFour}) {
            button.setOnClickListener(GameActivity.this);
        }

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    private void displayQuestion(final Question question) {
        mQuestionText.setText(question.getQuestion());
        mAnswerOne.setText(question.getChoiceList().get(0));
        mAnswerTwo.setText(question.getChoiceList().get(1));
        mAnswerThree.setText(question.getChoiceList().get(2));
        mAnswerFour.setText(question.getChoiceList().get(3));
    }

    public QuestionBank generateQuestions() {
        Question question1 = new Question("In which country was Mayakovsky born?", Arrays.asList("Azerbaijan", "Belarus", "Georgia", "Denmark"), 2);
        Question question2 = new Question("What is Mayakovsky's first name?", Arrays.asList("Anton", "Boris", "Vladimir", "Gerasimov"), 2);
        Question question3 = new Question("Who was the love of Mayakovsky's life?", Arrays.asList("Isadora Duncan", "Lily Brik", "Rosa Luxemburg", "Lyubov Popova"), 1);
        Question question4 = new Question("At what age did Mayakovsky die?", Arrays.asList("27", "30", "33", "36"), 2);

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4));
    }

    private void endGame() {
        new AlertDialog.Builder(this)
                .setTitle("Well done!")
                .setMessage("Your score: " + mScore)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        data.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public void onClick(View view) {
        int userAnswer = (int) view.getTag();
        if (userAnswer == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(GameActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(GameActivity.this, "That's not right!", Toast.LENGTH_SHORT).show();
        }
        if (--mNumberOfQuestions == 0) {
            endGame();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }
}