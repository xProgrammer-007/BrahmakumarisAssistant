package com.bkassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MusicPlayerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_detail);
        final Globals globals = (Globals) getApplicationContext();
        globals.getMediaPlayerApi().playMusic("http://www.largesound.com/ashborytour/sound/brobob.mp3");


    }
}
