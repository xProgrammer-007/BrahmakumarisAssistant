package com.bkassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private TextView loginView;
    private Button signUpBtn;
    private EditText username , password , confirm_password, email_id;
    SignUp signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = new SignUp(this);
        loginView = findViewById(R.id.link_login);
        signUpBtn = findViewById(R.id.btn_signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmPassword);
        email_id = findViewById(R.id.email_id);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                SignupActivity.this,
                                LoginActivity.class
                        )
                );
                overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );
            }
        });
    }

    private void createAccount() {


        if (!username.getText().toString().isEmpty()) {
            if (!email_id.getText().toString().isEmpty()) {
                if (!password.getText().toString().isEmpty()) {
                    if (password.getText().toString().length() >= 8)
                    {
                        if (password.getText().toString().equals(confirm_password.getText().toString())) {
                            signUp.createAccountUsingEmailPassword(email_id.getText().toString(),password.getText().toString());
                        } else
                            Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(this, "Please enter at least 8 digit", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Please Enter Your Email Id", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Please Enter Your Last Name", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
        );
    }
}