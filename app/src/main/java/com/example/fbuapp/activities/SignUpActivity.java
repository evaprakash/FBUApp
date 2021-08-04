package com.example.fbuapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fbuapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import www.sanju.motiontoast.MotionToast;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_CODE = "code";
    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private EditText etSignUpLocation;
    private EditText etCode;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpLocation = findViewById(R.id.etSignUpLocation);
        etCode = findViewById(R.id.etCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String location = etSignUpLocation.getText().toString();
                int code = 0;
                if (etCode.getText().toString().equals("")) {
                    MotionToast.Companion.createColorToast(SignUpActivity.this,
                            "Error",
                            "Issue with signup.",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(SignUpActivity.this, R.font.helvetica_regular));
                } else {
                    code = Integer.valueOf(etCode.getText().toString());
                    signUpUser(username, password, location, code);
                }
            }
        });
    }

    private void signUpUser(String username, String password, String location, int code) {
        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);
        user.put(KEY_LOCATION, location);
        user.put(KEY_CODE, code);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    MotionToast.Companion.createColorToast(SignUpActivity.this,
                            "Success",
                            "Sign up completed successfully!",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(SignUpActivity.this,R.font.helvetica_regular));
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    MotionToast.Companion.createColorToast(SignUpActivity.this,
                            "Error",
                            "Issue with signup.",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(SignUpActivity.this,R.font.helvetica_regular));
                    return;
                }
            }
        });
    }
}