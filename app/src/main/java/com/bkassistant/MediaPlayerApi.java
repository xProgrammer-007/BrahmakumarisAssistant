package com.bkassistant;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class MediaPlayerApi {
    public final HomeActivity activity;
    public final Context context;
    MediaPlayer mediaPlayer;
    ArrayList<SongDetail> playlist = new ArrayList<SongDetail>();
    private int activePlayingSongIndex = -1;
    PlayerState playerState = PlayerState.stopped;

    MediaPlayerApi(HomeActivity activity, Context context){
        this.activity =  activity;
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }




    void pause(){
        if(mediaPlayer !=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playerState = PlayerState.paused;
        }
    }

    void resume(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            playerState = PlayerState.playing;
        }
    }


    public void addMusic(SongDetail songDetail){
        playlist.add(songDetail);
    }

    public void removeMusic(int index){
        playlist.remove(index);
    }
    
    
    
    public void play(){
        // SOLVING ArrayIndexOutOfBounds EXCEPTION FOR A PLAYLIST MUSIC
        if( playlist.size() == 0 ) return;
        
        if(activePlayingSongIndex == -1 || playerState == PlayerState.stopped) activePlayingSongIndex = 0;
        
        if(playlist.size() < activePlayingSongIndex +1 ){
            activePlayingSongIndex = 0;
            return;
        }
        
        playMusic(playlist.get(activePlayingSongIndex).songUrl);
        activity.mediaPlayerUi.songTextView.setText(playlist.get(activePlayingSongIndex).songName);
    }



    void playMusic(String url){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();


            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    activity.mediaPlayerUi.seekBar.setProgress(0);
                    next();
                    activity.mediaPlayerUi.setPlayIcon();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void next() {
        activePlayingSongIndex++;
        play();
    }

    void _updateSeekBar(){
        if(mediaPlayer.isPlaying()) {
            System.out.println(mediaPlayer.getCurrentPosition());
            activity.mediaPlayerUi.seekBar.setProgress((mediaPlayer.getCurrentPosition()));
        }
    }

    enum PlayerState{
        playing,
        paused,
        stopped,
        none
    }


}

     class SongDetail{
        public final String songName;
        public final String songUrl;


        public SongDetail(String songName, String songUrl) {
            this.songName = songName;
            this.songUrl = songUrl;
        }
    }


