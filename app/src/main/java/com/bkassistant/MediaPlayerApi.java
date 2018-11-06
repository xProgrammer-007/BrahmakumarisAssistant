package com.bkassistant;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

public class MediaPlayerApi{
    public MainActivity activity;
    MediaPlayer mediaPlayer;
    public int progressPercent;

    MediaPlayerApi(MainActivity activity){
        this.activity=  activity;
        mediaPlayer = new MediaPlayer();
    }


    void pause(){
        if(mediaPlayer !=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    void resume(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }


    void playMusic(String url){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    mp.start();

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if(mp != null && mp.isPlaying()) {
                                System.out.println(mp.getCurrentPosition());
                            }
                        }
                    };
                    activity.musicHandler.postDelayed(runnable,1000);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



