package com.lewisgreaves.topquiz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
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

    private TextView mGreetingText = findViewById(R.id.activity_main_greeting_text);
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private SharedPreferences mPreferences;
    private static String mReturnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mUser = new User();
        mPreferences = getPreferences(0);

        mPlayButton.setEnabled(false);

        String firstname = getPreferences(0).getString(PREFERENCE_KEY_NAME, null);
        int score = getPreferences(0).getInt(PREFERENCE_KEY_SCORE, 0);
        mReturnText = "Welcome back " + firstname + "! Your last score was " + score + ", will you do better this time?";

        if (mPreferences.contains(firstname)) {
            mGreetingText.setText(mReturnText);
            mNameInput.setText(firstname);
        } else {

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREFERENCE_KEY_SCORE, score).apply();
        }
    }
}