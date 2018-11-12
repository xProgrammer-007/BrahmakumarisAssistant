package com.bkassistant;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MediaPlayerApi {
    public final HomeActivity activity;
    public final Context context;
    MediaPlayer mediaPlayer;
    ArrayList<SongDetail> playlist = new ArrayList<SongDetail>();
    private int activePlayingSongIndex = -1;
    PlayerState playerState = PlayerState.stopped;
    private SongDetail songPlaying;
    private OnMusicLoadedListener onMusicLoadedListener;

    MediaPlayerApi(HomeActivity activity, Context context){
        this.activity =  activity;
        this.context = context;
        mediaPlayer = new MediaPlayer();
        activity.mediaPlayerUi.mediaCardHolder.setVisibility(View.GONE);
    }


    public void setOnMusicLoadedListener(OnMusicLoadedListener onMusicLoadedListener) {
        this.onMusicLoadedListener = onMusicLoadedListener;
    }


    public SongDetail getSongPlaying() {
        return songPlaying;
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
        
        playMusic(playlist.get(activePlayingSongIndex));
        activity.mediaPlayerUi.songTextView.setText(playlist.get(activePlayingSongIndex).songName);
    }



    void playMusic(SongDetail songDetail){
        if(playerState != PlayerState.stopped){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    onMusicLoadedListener.onMusicLoaded(mp);
                }

            });
            activity.mediaPlayerUi.mediaCardHolder.setVisibility(View.VISIBLE);
            playerState = PlayerState.loading;
            songPlaying = songDetail;
            System.out.println(songDetail.songUrl);
            mediaPlayer.setDataSource(songDetail.songUrl);
            mediaPlayer.prepareAsync();
            //PROGRESS BAR SHOW ON PLAY/PAUSE BTN
            activity.mediaPlayerUi.mediaProgressBar.setVisibility(View.VISIBLE);
            activity.mediaPlayerUi.playPauseBtn.setVisibility(View.GONE);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    activity.mediaPlayerUi.mediaCardHolder.setVisibility(View.GONE);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer = null;
                    activity.mediaPlayerUi.seekBar.setProgress(0);
                    playerState = PlayerState.stopped;
                    activity.mediaPlayerUi.setPlayIcon();
                    next();
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
        loading,
        paused,
        stopped,
    }

    public interface OnMusicLoadedListener{
        void onMusicLoaded(MediaPlayer mediaPlayer);
    }


}

     class SongDetail implements Serializable {
        public final String songName;
        public final String songUrl;


        public SongDetail(String songName, String songUrl) {
            this.songName = songName;
            this.songUrl = songUrl;
        }
    }


