package com.bkassistant;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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
