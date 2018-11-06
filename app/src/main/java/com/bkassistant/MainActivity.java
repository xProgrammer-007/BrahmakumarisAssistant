package com.bkassistant;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {
    public Handler musicHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MediaPlayerApi mediaPlayerApi = new MediaPlayerApi(this);

        final Globals globalVariable = (Globals) getApplicationContext();

        globalVariable.setMediaPlayerApi(mediaPlayerApi);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            System.out.println("Logged in : redirecting");
            startActivity(new Intent(
                    MainActivity.this,
                    HomeActivity.class)
            );
        }else{
            startActivity(new Intent(
                    MainActivity.this,
                    LoginActivity.class)
            );
        }

    }

}
