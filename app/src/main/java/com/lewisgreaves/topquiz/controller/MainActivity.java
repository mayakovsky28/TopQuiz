package com.lewisgreaves.topquiz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lewisgreaves.topquiz.R;
import com.lewisgreaves.topquiz.model.User;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 28;
    public static final String PREFERENCE_KEY_NAME = "PREFERENCE_KEY_NAME";
    public static final String PREFERENCE_KEY_SCORE = "PREFERENCE_KEY_SCORE";

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mGreetingText = findViewById(R.id.activity_main_greeting_text);
        mUser = new User();
        mPreferences = getPreferences(0);

        mPlayButton.setEnabled(false);

            mNameInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mPlayButton.setEnabled(s.toString().length() != 0);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String firstName = mNameInput.getText().toString();
                    mUser.setFirstName(firstName);
                    mPreferences.edit().putString(PREFERENCE_KEY_NAME, mUser.getFirstName()).apply();
                    Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                    startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
                mPreferences.edit().putInt(PREFERENCE_KEY_SCORE, score).apply();
            }
        }
        String userName = getPreferences(0).getString(PREFERENCE_KEY_NAME, "Anonymous");
        int score = getPreferences(0).getInt(PREFERENCE_KEY_SCORE, 0);
        String returnText = "Welcome back " + userName + "! Your last score was " + score + ", will you do better this time?";
        mGreetingText.setText(returnText);
        mNameInput.setText(userName);
        mNameInput.setSelection(userName.length());
    }
}